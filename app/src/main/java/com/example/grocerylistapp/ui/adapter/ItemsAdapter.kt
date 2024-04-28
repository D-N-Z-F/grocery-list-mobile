package com.example.grocerylistapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerylistapp.R
import com.example.grocerylistapp.data.model.Item
import com.example.grocerylistapp.databinding.LayoutItemBinding

class ItemsAdapter(
    private var items: List<Item>
): RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {
    var listener: Listener? = null
    var context: Context? = null

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ItemViewHolder {
        context = parent.context
        val binding = LayoutItemBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder, position: Int
    ) { holder.bind(items[position]) }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: List<Item>) { this.items = newItems; notifyDataSetChanged() }

    inner class ItemViewHolder(
        private val binding: LayoutItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.run {
                tvName.text = item.name
                tvCategory.text = "Category: ${item.category}"
                tvQuantity.text = "Quantity: ${item.quantity}"
                if(item.checkedOff) {
                    cbCheckOff.isChecked = true
                    clItem.setBackgroundColor(
                        ContextCompat.getColor(context!!, R.color.green)
                    )
                } else {
                    cbCheckOff.isChecked = false
                    clItem.setBackgroundColor(
                        ContextCompat.getColor(context!!, R.color.blue)
                    )
                }
                cbCheckOff.setOnClickListener {
                    if(!item.checkedOff) {
                        item.checkedOff = true; cbCheckOff.isChecked = true
                        clItem.setBackgroundColor(
                            ContextCompat.getColor(context!!, R.color.green)
                        )
                    } else {
                        item.checkedOff = false; cbCheckOff.isChecked = false
                        clItem.setBackgroundColor(
                            ContextCompat.getColor(context!!, R.color.blue)
                        )
                    }
                }
                clItem.setOnLongClickListener {
                    listener?.onLongClick(item); true
                }
            }
        }
    }

    interface Listener { fun onLongClick(item: Item) }
}