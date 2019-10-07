import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.get
import kotlin.browser.document
import kotlin.browser.window
import kotlin.js.Date
import org.w3c.xhr.XMLHttpRequest
import kotlin.math.pow
class UrlColumnMap(val url : String, val blockOnPage : Int, val columnOnPage : Int)

fun main(args: Array<String>) {
    println("hello - im here to sign you into a tiss course")

    /*
    if(document.URL.substring(0..35) == "https://iu.zid.tuwien.ac.at/AuthServ") {
        println("clicking")

        val submits = document.getElementsByTagName("input");


        println("writing values data");
        val username = (submits[0] as HTMLInputElement)
        println(username)
        username.value = "matrikelnummer"
        println(username.value)

        val pw = (submits[1] as HTMLInputElement)
        println(pw)
        pw.value = "password"
        println(pw.value)



        val submit = (submits[5] as HTMLInputElement)
        submit.click()
    }else */if(document.URL == "https://tiss.tuwien.ac.at/education/course/courseRegistration.xhtml" || document.URL == "https://tiss.tuwien.ac.at/education/course/groupList.xhtml"){
        println("clicking")
        val button = document.getElementById("regForm:j_id_2x") as HTMLInputElement
        button.click()
    }

    val url = document.URL

    val courseNrRegex = Regex("courseNr")
    val match = courseNrRegex.find(url)
    val numberRange = match!!.range.endInclusive + 2 .. match.range.endInclusive + 7
    val courseNr = url.substring(numberRange)
    println(courseNr)

    val path = "chrome-extension://mmojljechnociddmllimabfekkidbpgh/PeregrineJVM/urlColumnMatches/match$courseNr.JSON"
    clickAtPos(path)
}

fun clickAtPos(path : String){
    var fileInput : String
    val xhr =  XMLHttpRequest()
    xhr.onreadystatechange = {
        if (xhr.readyState == 4.toShort() && xhr.status == 200.toShort()){
            fileInput = xhr.responseText

            val urlColumnMap = JSON.parse<UrlColumnMap>(fileInput)
            println(urlColumnMap.blockOnPage)
            println(urlColumnMap.columnOnPage)

            println(document.getElementById("groupContentForm:j_id_4t:${urlColumnMap.blockOnPage}:j_id_57:${urlColumnMap.columnOnPage}:j_id_9q"))
            val button = document.getElementById("groupContentForm:j_id_4t:${urlColumnMap.blockOnPage}:j_id_57:${urlColumnMap.columnOnPage}:j_id_9q") as HTMLInputElement
            button.click()
        }
    }
    xhr.open("GET", path)
    xhr.send()
}
