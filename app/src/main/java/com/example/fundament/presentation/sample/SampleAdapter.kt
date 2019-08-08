package com.example.fundament.presentation.sample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fundament.R
import com.example.fundament.entities.Sample
import kotlinx.android.synthetic.main.item_sample.view.*

class SampleAdapter: RecyclerView.Adapter<SampleAdapter.UserViewHolder>(){

    private var dataSet = ArrayList<Sample>()
    private var listener: MyClickListener? = null

    fun setDataSet(list: ArrayList<Sample>){
        dataSet = list
        this.notifyDataSetChanged()
    }

    fun getDataSetSize() = dataSet.size

    fun setListener(link: MyClickListener){
        listener = link
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): UserViewHolder {
        return UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_sample, parent, false))
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    fun addItem(sampleObject: Sample) {
        dataSet.add(sampleObject)
        notifyItemInserted(dataSet.size)
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(item: Sample){
            itemView.title.text = item.title
            itemView.description.text = item.description
        }

        init {
            itemView.setOnClickListener{
                listener?.onClick(dataSet[adapterPosition])
            }
        }
    }

    interface MyClickListener{
        fun onClick(item: Sample)
    }
}