package com.example.mytodo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodo.data.Task
import com.example.mytodo.fragments.ListFragmentDirections
import com.example.mytodo.network.model.Post
import kotlinx.android.synthetic.main.items_layout.view.*


class MyAdapter(taskEvents: TaskEvents) : ListAdapter<Task, MyAdapter.MyViewHolder>(TaskDiffCallback()) {
    var myDataList = ArrayList<Task>()
    val listener: TaskEvents = taskEvents

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.items_layout, parent, false)
        )
    }




    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(task: Task, listener: TaskEvents){
            // bind views with data
            itemView.title_item.text = task.title
            itemView.describ_item.text = task.describtion
            itemView.checkBox.setOnClickListener { listener.onDeleteClicked(task)}

        }

//        fun bind(task: Task, clickListener: (Task) -> Unit) {
//            itemView.title_item.text = task.title
//            itemView.describ_item.text = task.describtion
//            itemView.checkBox.setOnClickListener { clickListener(task) }
//        }

    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        val currentItem = getItem(position)
        holder.bind(currentItem, listener)

//        fun deleteItem(position: Int) {
//            currentItem.removeAt(position)
//            notifyItemRemoved(position)
//            notifyItemRangeChanged(position, mDataSet.size)
//            holder.itemView.visibility = View.GONE
//        }

        //opens specific task (using safeargs)
        holder.itemView.item_lay.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }





    class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.taskId == newItem.taskId
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }

    }
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    interface TaskEvents {
        fun onDeleteClicked(task: Task)

    }

}

