package com.example.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.model.Todo
import com.example.todoapp.viewmodel.ListTodoViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TodoListFragment : Fragment() {
    private lateinit var viewModel: ListTodoViewModel
    val uuid = EditTodoFragmentArgs.fromBundle(requireArguments()).uuid
    private val todoListAdapter  = TodoListAdapter(arrayListOf(), {item -> viewModel.checkTask(item, uuid)}) { item ->
        viewModel.unCheckTask(
            item as Todo,
            uuid
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ListTodoViewModel::class.java)
        viewModel.refresh()
        var recViewTodo = view.findViewById<RecyclerView>(R.id.recViewTodo)
        val fabAddTodo = view.findViewById<FloatingActionButton>(R.id.fabAddTodo)
        recViewTodo.layoutManager = LinearLayoutManager(context)
        recViewTodo.adapter = todoListAdapter
        fabAddTodo.setOnClickListener {
            val action = TodoListFragmentDirections.actionCreateTodo()
            Navigation.findNavController(it).navigate(action)
        }

        observeViewModel()

    }
    fun observeViewModel() {
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
            todoListAdapter.updateTodoList(it)
            var txtEmpty = view?.findViewById<TextView>(R.id.textEmpty)
            if(it.isEmpty()) {
                txtEmpty?.visibility = View.VISIBLE
            } else {
                txtEmpty?.visibility = View.GONE
            }
        })
    }









}