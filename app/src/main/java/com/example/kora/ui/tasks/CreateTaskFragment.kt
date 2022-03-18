package com.example.kora.ui.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.kora.BaseFragment
import com.example.kora.database.NotesDatabase
import com.example.kora.database.TaskDatabase
import com.example.kora.databinding.FragmentCreateTaskBinding
import com.example.kora.entities.Notes
import com.example.kora.entities.Tasks
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class CreateTaskFragment : BaseFragment() {
    private var createTaskBinding : FragmentCreateTaskBinding?=null
    private var taskId = -1
    var currentDate:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        createTaskBinding = FragmentCreateTaskBinding.inflate(inflater, container,false)
        return createTaskBinding?.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CreateTaskFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createTaskBinding = FragmentCreateTaskBinding.bind(view)

        //for reminders feature | alarm manager
        createTaskBinding!!.setReminderBtn.setOnClickListener {

        }

        if (taskId != -1) {
            launch {
                context?.let {
                    var tasks = TaskDatabase.getDatabase(it).taskDao().getSpecificTask(taskId)
                    createTaskBinding?.newTaskText?.setText(tasks.title)
                }
            }

        }
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())
        createTaskBinding!!.tvDateTime.text = currentDate

        createTaskBinding!!.imgDone.setOnClickListener {

            saveTask()

        }
    }
//saves task
    private fun saveTask() {
        if (createTaskBinding?.newTaskText?.text.isNullOrEmpty()) {
            Toast.makeText(context, "Task Name is Required", Toast.LENGTH_SHORT).show()
        } else {
            launch {
                val tasks = Tasks()
                tasks.title = createTaskBinding?.newTaskText?.text.toString()
                tasks.dateTime = currentDate

                context?.let {
                    TaskDatabase.getDatabase(it).taskDao().insertTasks(tasks)
                    createTaskBinding?.newTaskText?.setText("")
                    requireActivity().supportFragmentManager.popBackStack()
                }

            }
        }
    }
}