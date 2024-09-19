package com.saefulrdevs.articleflow.viewmodel

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.saefulrdevs.articleflow.data.ProgrammingLanguage
import com.saefulrdevs.articleflow.databinding.ItemRowProgrammingLanguageBinding

class ListProgrammingLanguage(private val listPL: ArrayList<ProgrammingLanguage>) : RecyclerView.Adapter<ListProgrammingLanguage.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    class ListViewHolder(binding: ItemRowProgrammingLanguageBinding) : RecyclerView.ViewHolder(binding.root) {
        val imgPhoto: ImageView = binding.imgItemPhoto
        val tvName: TextView = binding.tvItemName
        val tvDescription: TextView = binding.tvItemDescription
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRowProgrammingLanguageBinding.inflate(inflater, parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listPL.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, description, photo) = listPL[position]
        holder.imgPhoto.setImageResource(photo)
        holder.tvName.text = name
        holder.tvDescription.text = description
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listPL[holder.adapterPosition])
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ProgrammingLanguage)
    }
}