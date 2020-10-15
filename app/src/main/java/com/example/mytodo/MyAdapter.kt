package com.example.mytodo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodo.data.Task
import com.example.mytodo.fragments.ListFragmentDirections
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

        fun bind(task: Task, listener: TaskEvents) {
            // bind views with data
            itemView.item_lay.transitionName = "shared_element_currentCard"
            itemView.title_item.text = task.title
            itemView.describ_item.text = task.describtion
            itemView.checkBox.setOnClickListener { listener.onDeleteClicked(task) }

            //itemView.item_lay.setOnClickListener { listener.onViewClicked(task)}

        }

    }


    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        val currentItem = getItem(position)
        holder.bind(currentItem, listener)

        holder.itemView.item_lay.transitionName = currentItem.taskId.toString()
        ViewCompat.setTransitionName(holder.itemView.item_lay, currentItem.taskId.toString())

        //opens specific task (using safeargs)
        holder.itemView.item_lay.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            val extras =
                FragmentNavigatorExtras(holder.itemView.item_lay to currentItem.taskId.toString())
            holder.itemView.findNavController().navigate(action, extras)
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


    interface TaskEvents {
        fun onDeleteClicked(task: Task)
       // fun onViewClicked(task: Task)

    }


}

