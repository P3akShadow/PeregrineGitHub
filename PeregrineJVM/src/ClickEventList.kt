class ClickEventList : ArrayList<ClickEvent>() {
    override fun add(element: ClickEvent): Boolean {
        for((i, event) in this.withIndex()){
            if(event.time > element.time) {
                super.add(i, element)
                return true
            }
        }
        return super.add(element)

        return super.add(element)
    }
}