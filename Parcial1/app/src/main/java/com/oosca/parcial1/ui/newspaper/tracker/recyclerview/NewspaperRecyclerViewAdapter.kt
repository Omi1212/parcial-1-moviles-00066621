package com.oosca.parcial1.ui.newspaper.tracker.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oosca.parcial1.data.model.NewspaperModel
import com.oosca.parcial1.databinding.NewspaperItemBinding

class NewspaperRecyclerViewAdapter(private val clickListener: (NewspaperModel) -> Unit)
    : RecyclerView.Adapter<NewspaperRecyclerViewHolder>() {
    private val newspapers = ArrayList<NewspaperModel>()

    fun setData(newspapersList: List<NewspaperModel>){
        newspapers.clear()
        newspapers.addAll(newspapersList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewspaperRecyclerViewHolder {
        val binding = NewspaperItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewspaperRecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return newspapers.size
    }

    override fun onBindViewHolder(holder: NewspaperRecyclerViewHolder, position: Int) {
        val newspaper = newspapers[position]
        holder.bind(newspaper, clickListener)
    }
}