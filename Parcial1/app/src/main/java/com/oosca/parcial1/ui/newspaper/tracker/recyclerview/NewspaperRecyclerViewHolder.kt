package com.oosca.parcial1.ui.newspaper.tracker.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.oosca.parcial1.data.model.NewspaperModel
import com.oosca.parcial1.databinding.NewspaperItemBinding

class NewspaperRecyclerViewHolder (private val binding: NewspaperItemBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(newspaper: NewspaperModel, clickListener: (NewspaperModel) -> Unit){
        binding.textView2.text = newspaper.name
        binding.textView3.text = newspaper.publicationDate
        binding.cardView.setOnClickListener{
            clickListener(newspaper)
        }
    }
}