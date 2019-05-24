package xyz.ryujpn.mymessenger

class ToDoData {
    public lateinit var title: String
    public lateinit var firebaseKey: String

    constructor(title: String) {
//        this.firebaseKey = key
        this.title = title
    }

}