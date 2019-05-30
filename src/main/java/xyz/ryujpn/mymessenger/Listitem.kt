package xyz.ryujpn.mymessenger

import java.util.*

data class Listitem(
    var taskName: String = "",
    var userId: String = "",
    val taskList: MutableList<String>? = null,
    var registerTime: Date = Date())