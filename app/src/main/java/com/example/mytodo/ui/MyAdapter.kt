package com.example.mytodo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodo.R
import com.example.mytodo.data.Task
import kotlinx.android.synthetic.main.items_layout.view.*


class MyAdapter(taskEvents: TaskEvents) :
    ListAdapter<Task, MyAdapter.MyViewHolder>(TaskDiffCallback()) {
    var myDataList = listOf<Task>()
    val listener: TaskEvents = taskEvents


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.items_layout, parent, false)
        )
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(task: Task, listener: TaskEvents) = with(itemView) {
            // bind views with data
            itemView.title_item.text = task.title
            itemView.describ_item.text = task.describtion


            itemView.checkBox.setOnCheckedChangeListener { _, _ -> listener.onDeleteClicked(task, itemView.item_lay)  }


            ViewCompat.setTransitionName(itemView.item_lay, task.taskId.toString())
            itemView.item_lay.setOnClickListener { listener.onViewClicked(task, itemView.item_lay) }

        }

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, listener)

    }



    class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.taskId == newItem.taskId
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }

    }


    interface TaskEvents {
        fun onDeleteClicked(task: Task, view: View)
        fun onViewClicked(task: Task, view: View)

    }


}

