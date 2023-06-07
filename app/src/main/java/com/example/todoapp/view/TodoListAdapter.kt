package com.example.todoapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.model.Todo

class TodoListAdapter(
    val todoList: ArrayList<Todo>,
    val checkOnClick: (Todo) -> Unit,
    val unCheckOnClick: (Any) -> Unit
)
    : RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {
    class TodoViewHolder(var view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.todo_item_layout, parent, false)

        return TodoViewHolder(view)

    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        var checktask = holder.view.findViewById<CheckBox>(R.id.checkTask)
        var imgEdit = holder.view.findViewById<ImageView>(R.id.imgEdit)
        checktask.text = todoList[position].title
        checktask.isChecked = false
        imgEdit.setOnClickListener {
            val action =
                TodoListFragmentDirections.actionEditTodoFragment(todoList[position].uuid)

            Navigation.findNavController(it).navigate(action)
        }
        checktask.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked) {
                checkOnClick(todoList[position])
            }
            if(!isChecked){
                unCheckOnClick(todoList[position])
            }
        }




    }


    override fun getItemCount(): Int {
        return todoList.size
    }
    fun updateTodoList(newTodoList: List<Todo>) {
        todoList.clear()
        todoList.addAll(newTodoList)
        notifyDataSetChanged()
    }



}

