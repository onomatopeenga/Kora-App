package com.example.kora.model

class TasksModel {
    var id = 0
    var status = 0
    var task: String? = null

    @JvmName("getId1")
    fun getId(): Int {
        return id
    }

    @JvmName("setId1")
    fun setId(id: Int) {
        this.id = id
    }

    @JvmName("getStatus1")
    fun getStatus(): Int {
        return status
    }

    @JvmName("setStatus1")
    fun setStatus(status: Int) {
        this.status = status
    }

    @JvmName("getTask1")
    fun getTask(): String? {
        return task
    }

    @JvmName("setTask1")
    fun setTask(task: String?) {
        this.task = task
    }
}