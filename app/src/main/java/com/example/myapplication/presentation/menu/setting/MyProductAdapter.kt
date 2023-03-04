package com.example.myapplication.presentation.menu.setting

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bossku.utils.toRupiah
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemProductBinding
import com.example.myapplication.domain.entity.ProductEntity

class MyProductAdapter(val onClickListener: (entity: ProductEntity) -> Unit) :
    androidx.recyclerview.widget.ListAdapter<ProductEntity, MyProductAdapter.ViewHolder>(
        DiffCallback
    ) {


    object DiffCallback : DiffUtil.ItemCallback<ProductEntity>() {
        override fun areItemsTheSame(oldItem: ProductEntity, newItem: ProductEntity) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: ProductEntity, newItem: ProductEntity) =
            oldItem == newItem
    }

    inner class ViewHolder(
        private val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ProductEntity) {
            binding.executePendingBindings()
            with(binding) {

                tvNamaProduct.text = data.productName
                tvHargaProduct.text = toRupiah(data.price.toString())
                tvStockProduct.text = "Stok selalu tersedia"
                ivFotoProduct.setImageResource(R.drawable.img)

                if (data.discount == 0) {

                } else {
                    tvDiscount.text = toRupiah(data.discount.toString())
                    tvDiscount.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemProductBinding.inflate(
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