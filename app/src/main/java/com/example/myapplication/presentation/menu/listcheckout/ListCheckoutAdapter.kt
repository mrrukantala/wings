package com.example.myapplication.presentation.menu.listcheckout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.LayoutListTransactionItemBinding
import com.example.myapplication.domain.entity.ListTransactionEntity

class ListCheckoutAdapter(val onClickListener: (entity: ListTransactionEntity) -> Unit) :
    androidx.recyclerview.widget.ListAdapter<ListTransactionEntity, ListCheckoutAdapter.ViewHolder>(
        DiffCallback
    ) {

    object DiffCallback : DiffUtil.ItemCallback<ListTransactionEntity>() {
        override fun areItemsTheSame(
            oldItem: ListTransactionEntity,
            newItem: ListTransactionEntity
        ) =
            oldItem == newItem

        override fun areContentsTheSame(
            oldItem: ListTransactionEntity,
            newItem: ListTransactionEntity
        ) =
            oldItem == newItem
    }

    inner class ViewHolder(
        private val binding: LayoutListTransactionItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListTransactionEntity) {
            binding.executePendingBindings()
            with(binding) {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutListTransactionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClickListener.invoke(item)
        }
    }

}