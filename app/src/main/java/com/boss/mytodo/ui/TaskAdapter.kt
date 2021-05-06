package com.boss.mytodo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.boss.mytodo.R
import com.boss.mytodo.data.db.entity.Task
import kotlinx.android.synthetic.main.items_layout.view.*


class TaskAdapter(taskEvents: TaskEvents) :
    ListAdapter<Task, TaskAdapter.MyViewHolder>(TaskDiffCallback()) {

    private val listener: TaskEvents = taskEvents


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.items_layout, parent, false)
        )
    }


//    private var data: List<Task> = emptyList()
//    fun setNewList(newList: List<Task>) {
//        data = newList
//        notifyDataSetChanged()
//    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(task: Task, listener: TaskEvents) = with(itemView) {
            // bind views with data
            itemView.title_item.text = task.title
            itemView.describ_item.text = task.description

            itemView.checkBox.setOnClickListener { listener.onDeleteClicked(task, itemView) }

            ViewCompat.setTransitionName(itemView.item_lay, task.taskId.toString())
            itemView.item_lay.setOnClickListener { listener.onViewClicked(task, itemView.item_lay) }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        /** "getItem(position)"  instead of "data[position]" gives the animation  */
        val currentItem = getItem(position)
        holder.bind(currentItem, listener)
    }

    public override fun getItem(position: Int): Task {
        return super.getItem(position)
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

