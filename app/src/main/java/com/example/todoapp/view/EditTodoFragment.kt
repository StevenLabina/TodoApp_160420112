package com.example.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.todoapp.R
import com.example.todoapp.viewmodel.DetailTodoViewModel

class EditTodoFragment : Fragment() {
    private lateinit var viewModel: DetailTodoViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_todo, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)
        val uuid = EditTodoFragmentArgs.fromBundle(requireArguments()).uuid
        viewModel.fetch(uuid)
        val txtTitle = view.findViewById<EditText>(R.id.txtTitle)
        val txtNotes = view.findViewById<EditText>(R.id.txtNotes)

        observeViewModel(txtTitle , txtNotes )

        val txtJudulTodo = view.findViewById<TextView>(R.id.txtJudulTodo)
        val btnAdd = view.findViewById<Button>(R.id.btnAdd)
        txtJudulTodo.text = "Edit Todo"
        btnAdd.text = "Save Changes"
        btnAdd.setOnClickListener(){
            val radioGropPriority = view.findViewById<RadioGroup>(R.id.RadioGroupButton)
            val radio = view.findViewById<RadioButton>(radioGropPriority.checkedRadioButtonId)
            viewModel.update(txtTitle.text.toString(), txtNotes.text.toString(),
                radio.tag.toString().toInt(), uuid)
            Toast.makeText(view.context, "Todo updated", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).popBackStack()
        }






    }
    fun observeViewModel(txtTitle:EditText , txtNotes:EditText) {
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
            val radioLow = view?.findViewById<RadioButton>(R.id.btnLow)
            val radioMedium = view?.findViewById<RadioButton>(R.id.btnMedium)
            val radioHigh = view?.findViewById<RadioButton>(R.id.btnHigh)

            txtTitle.setText(it.title)
            txtNotes.setText(it.notes)
            when (it.priority) {
                1 -> radioLow?.isChecked = true
                2 -> radioMedium?.isChecked = true
                else -> radioHigh?.isChecked = true
            }
        })
    }




}