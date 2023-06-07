package com.example.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.todoapp.model.Todo
import com.example.todoapp.model.TodoDatabase
import com.example.todoapp.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListTodoViewModel(application: Application)
: AndroidViewModel(application), CoroutineScope {
    val todoLD = MutableLiveData<List<Todo>>()
    val todoLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    private var job = Job()


    fun refresh() {
        loadingLD.value = true
        todoLoadErrorLD.value = false
        launch {
            val db = buildDb(getApplication())

            todoLD.postValue(db.TodoDao().selectAllTodo())
        }
    }

    fun checkTask(todo: Todo, uuid:Int) {
        launch {
            val db = buildDb(getApplication())
            db.TodoDao().updateIdDone(0, uuid)

            todoLD.postValue(db.TodoDao().selectAllTodo())
        }
    }
    fun unCheckTask(todo: Todo, uuid: Int) {
        launch {
            val db = buildDb(getApplication())
            db.TodoDao().updateIdDone(1, uuid )

            todoLD.postValue(db.TodoDao().selectAllTodo())
        }
    }


    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

}
