package com.example.mytodo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodo.data.Task
import kotlinx.android.synthetic.main.items_layout.view.*


class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    var myDataList = emptyList<Task>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleItem: TextView = itemView.title_item
        val describItem: TextView = itemView.describ_item
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.items_layout, parent, false))
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val currentItem = myDataList[position]
        holder.titleItem.text = currentItem.title
        holder.describItem.text = currentItem.describtion
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataList.size


    fun setData(task: List<Task>) {
        this.myDataList = task
        notifyDataSetChanged()
    }
}