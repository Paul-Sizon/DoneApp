package com.boss.mytodo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.boss.mytodo.R
import com.boss.mytodo.data.db.entity.Task
import kotlinx.android.synthetic.main.items_layout.view.*


//
class MyAdapter(taskEvents: TaskEvents) :
    ListAdapter<Task, MyAdapter.MyViewHolder>(TaskDiffCallback()) {

    private val listener: TaskEvents = taskEvents
    private var data: List<Task> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.items_layout, parent, false)
        )
    }

    fun setNewList(newList: List<Task>) {
        data = newList
        notifyDataSetChanged()
    }

    //override fun getItemCount() = data.size

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
        /** "getItem(position)"  instead of "data[position]" gives the animation */
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

//
//class MyAdapter(taskEvents: TaskEvents) :
//    ListAdapter<Task, MyAdapter.ViewHolder>(TaskDiffCallback()) {
//
//    private var data: List<Task> = emptyList()
//    private val listener: TaskEvents = taskEvents
//
//
//    fun setNewList(newList: List<Task>) {
//        data = newList
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(
//            LayoutInflater.from(parent.context).inflate(R.layout.items_layout, parent, false)
//        )
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val currentItem = data[position]
//        holder.bind(currentItem, listener)
//
//        holder.title.text = currentItem.title
//        holder.description.text = currentItem.description
//
//
//    }
//
//    override fun getItemCount() = data.size
//
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        fun bind(task: Task, listener: TaskEvents) = with(itemView) {
//            item.setOnClickListener { listener.onViewClicked(task, itemView) }
//            checkBox.setOnClickListener { listener.onDeleteClicked(task, itemView) }
//            ViewCompat.setTransitionName(item, task.taskId.toString())
//        }
//        val title: TextView = itemView.findViewById(R.id.title_item)
//        val description: TextView = itemView.findViewById(R.id.describ_item)
//        val item: CardView = itemView.findViewById(R.id.item_lay)
//        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
//    }
//
//    class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
//        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
//            return oldItem.taskId == newItem.taskId
//        }
//
//        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
//            return oldItem == newItem
//        }
//    }
//
//        interface TaskEvents {
//        fun onDeleteClicked(task: Task, view: View)
//        fun onViewClicked(task: Task, view: View)
//    }
//}