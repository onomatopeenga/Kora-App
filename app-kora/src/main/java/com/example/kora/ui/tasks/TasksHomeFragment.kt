package com.example.kora.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kora.BaseFragment
import com.example.kora.R
import com.example.kora.adapter.TasksAdapter
import com.example.kora.database.TaskDatabase
import com.example.kora.databinding.FragmentTasksHomeBinding
import com.example.kora.entities.Tasks
import com.example.kora.ui.notes.NotesHomeFragment
import kotlinx.coroutines.launch

class TasksHomeFragment : BaseFragment() {
    private var tasksHomeBinding: FragmentTasksHomeBinding? = null
    var arrTasks = ArrayList<Tasks>()
    var tasksAdapter: TasksAdapter = TasksAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tasksHomeBinding = FragmentTasksHomeBinding.inflate(inflater, container, false)
        return tasksHomeBinding?.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            TasksHomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tasksHomeBinding = FragmentTasksHomeBinding.bind(view)

        tasksHomeBinding!!.fabbutton.setOnClickListener {
            replaceFragment(CreateTaskFragment.newInstance(), false)
        }
        tasksHomeBinding!!.notesHeaderBtn.setOnClickListener {
            replaceFragment(NotesHomeFragment.newInstance(), false)
        }

        tasksHomeBinding!!.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            tasksHomeBinding!!.recyclerView.adapter = tasksAdapter

        }

        launch {
            context?.let {
                var tasks = TaskDatabase.getDatabase(it).taskDao().getAllTasks()
                tasksAdapter.setData(tasks)
                arrTasks = tasks as ArrayList<Tasks>
                tasksHomeBinding!!.recyclerView.adapter = tasksAdapter
            }
        }
        tasksAdapter.setOnClickListener(onClicked)


        }

    private val onClicked = object : TasksAdapter.OnItemClickListener {
        override fun onClicked(taskId: Int) {
            val fragment: Fragment
            val bundle = Bundle()
            bundle.putInt("taskId", taskId)
            fragment = CreateTaskFragment.newInstance()
            fragment.arguments = bundle
            replaceFragment(fragment, false)
        }

        override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

        }


    }


    //replaces displayed fragment
    private fun replaceFragment(fragment: Fragment, istransition: Boolean) {
        val fragmentTransition = requireActivity().supportFragmentManager.beginTransaction()

        if (istransition) {
            fragmentTransition.setCustomAnimations(
                android.R.anim.slide_out_right,
                android.R.anim.slide_in_left
            )
        }
        fragmentTransition.replace(R.id.frame_layout, fragment)
            .addToBackStack(fragment.javaClass.simpleName).commit()
    }

    }
