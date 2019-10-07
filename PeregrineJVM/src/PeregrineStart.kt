import java.nio.file.Paths
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.pow

val COMMANDS = HashMap<String, String>()
val COMMAND_STRINGS = arrayOf<String>("commands", "newEvent", "startChecking", "quit")
val CIN = Scanner(System.`in`)
var QUIT = false

val EVENTS = ClickEventList()
val BROWSER_PATH = """C:\Program Files (x86)\Google\Chrome\Application\chrome.exe"""


fun main(args: Array<String>) {
    fillCommands()
    println("Welcome to Peregrine")
    println("For a list of commands write 'commands'")

    while(! QUIT) {
        println("waiting for commands")
        menu(CIN.next())
    }
}

fun menu(command : String){
    when(command){
        COMMAND_STRINGS[0] -> printCommands()
        COMMAND_STRINGS[1] -> newEvent()
        COMMAND_STRINGS[2] -> startChecking()
        COMMAND_STRINGS[3] -> QUIT = true
    }
}

fun fillCommands(){
    COMMANDS[COMMAND_STRINGS[0]] = "Prints all possible commands"
    COMMANDS[COMMAND_STRINGS[1]] = "Adds an Event to the EventList"
    COMMANDS[COMMAND_STRINGS[2]] = "starts looking for Events"
    COMMANDS[COMMAND_STRINGS[3]] = "terminates the program"
}

fun printCommands(){
    for(key in COMMANDS)
        println("${key.key} : ${key.value}")
}

fun newEvent(){

    println("When should I click?")
    val time = CIN.nextLong()

    /*
    val time = System.currentTimeMillis() + 5_000
    println("imma click in 5 seconds")
    */

    /*
    println("Which url is the Button at?")
    val url = CIN.next()
    */

    val url = "https://tiss.tuwien.ac.at/education/course/groupList.xhtml?dswid=4262&dsrid=802&courseNr=185A91&semester=2019W"
    println("url defaulted to $url")

    println("Which block is the Button in?")
    val block = CIN.nextInt()

    println("Which column is the Button in?")
    val column = CIN.nextInt()

    EVENTS.add(ClickEvent(time, url, block, column))
}

fun startChecking(){
    val checkThread = Thread {
        println("started checking")

        while(!QUIT) {
            checkOnce()
            Thread.sleep(500)
        }
    }.start()



}

fun checkOnce(){
    val elementsToDelete = ArrayList<ClickEvent>()

    for(event in EVENTS){
        if(event.isDue){
            handleEvent(clickEvent = event)
            elementsToDelete.add(event)
        } //possible because the list is sorted by time
        else break
    }

    //to avoid concurrent exeption
    for(event in elementsToDelete){
        EVENTS.remove(event)
    }

}

fun handleEvent(clickEvent: ClickEvent){

    val courseNrRegex = Regex("courseNr")
    val match = courseNrRegex.find(clickEvent.url)
    val numberRange = match!!.range.endInclusive + 2 .. match.range.endInclusive + 7
    val courseNr = clickEvent.url.substring(numberRange)
    println(courseNr)

    val path = Paths.get("urlColumnMatches/match$courseNr.JSON")
    clickEvent.urlColumnJSONMap(path)

    Runtime.getRuntime().exec("$BROWSER_PATH ${clickEvent.url}")
    println("window '${clickEvent.url}' started")
}

