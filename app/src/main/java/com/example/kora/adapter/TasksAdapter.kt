package com.example.kora.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.example.kora.databinding.TaskItemBinding
import com.example.kora.entities.Notes
import com.example.kora.entities.Tasks

class TasksAdapter() :
    RecyclerView.Adapter<TasksAdapter.TasksViewHolder>() {
    var listener: AdapterView.OnItemClickListener? = null
    var arrayList = ArrayList<Tasks>()
    private var taskItemBinding: TaskItemBinding? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TasksAdapter.TasksViewHolder {
        taskItemBinding =
            TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TasksAdapter.TasksViewHolder(taskItemBinding!!)
    }

    fun setOnClickListener(listener1:OnItemClickListener) {
        listener = listener1
    }
    override fun onBindViewHolder(holder: TasksAdapter.TasksViewHolder, position: Int) {
        holder.itemBinding.taskText.text = arrayList[position].title
        holder.itemBinding.tvDateTime.text = arrayList[position].dateTime
        if (taskItemBinding!!.checkboxCompleted.isChecked){
            holder.itemBinding.taskText.paint.isStrikeThruText
        }
        holder.itemBinding.cardView.setOnClickListener {
        }

    }
    override fun getItemCount(): Int = arrayList.size

    fun setData(arrTasksList: List<Tasks>) {
        arrayList = arrTasksList as ArrayList<Tasks>
    }


    class TasksViewHolder(var itemBinding: TaskItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {


    }

    interface OnItemClickListener : AdapterView.OnItemClickListener {
        fun onClicked(taskId: Int)
    }
}
