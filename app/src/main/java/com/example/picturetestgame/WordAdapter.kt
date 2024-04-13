package com.example.picturetestgame

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.picturetestgame.databinding.ItemWordBinding
import com.example.picturetestgame.models.WordData

class WordAdapter :
    ListAdapter<WordData, WordAdapter.ViewHolder>(object : DiffUtil.ItemCallback<WordData>() {
        override fun areItemsTheSame(oldItem: WordData, newItem: WordData) = oldItem == newItem
        override fun areContentsTheSame(oldItem: WordData, newItem: WordData) =
            oldItem.id == newItem.id
    }) {

    inner class ViewHolder(private val binding: ItemWordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val data = getItem(position)
            binding.tvWord.text = data.word
            binding.tvWord.show()

            binding.root.setOnClickListener {
                onItemClickListener?.invoke(data)
            }
        }
    }

    fun removeItem(item: WordData) {
        val list = currentList.toMutableList()
        list.remove(item)
        submitList(list)
    }

    fun addItem(item: WordData) {
        val list = currentList.toMutableList()
        var bool = false
        for (i in list.indices) {
            if (list[i] == item) {
                bool = true
            }
        }
        if (bool.not()) {
            list.add(item)
            submitList(list)
        }
    }

    fun clearAdapter() {
        val list = mutableListOf<WordData>()
        submitList(list)
    }

    private var onItemClickListener: ((WordData) -> Unit)? = null
    fun setOnItemClickListeners(block: ((WordData) -> Unit)) {
        onItemClickListener = block
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemWordBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position)
}