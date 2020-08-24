package com.example.sqldelightsample

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val items: List<ItemInCart>) : RecyclerView.Adapter<CustomAdapter.ItemHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CustomAdapter.ItemHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    class ItemHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item, parent, false)){
            private var mLabelView: TextView? = null
            private var mQuantityView: TextView? = null
        private var mLinkView: TextView? = null
        private var mBrandView: TextView? = null


            init {
                mLabelView = itemView.findViewById(R.id.item_label)
                mQuantityView = itemView.findViewById(R.id.item_quantity)
                mLinkView = itemView.findViewById(R.id.item_link)
                mBrandView = itemView.findViewById(R.id.item_brand)
            }

            fun bind(item: ItemInCart) {
                mLabelView?.text = "Label : " + item.label
                mQuantityView?.text = "Quantity : " + item.quantity.toString()
                mLinkView?.text = "Link : " + item.link
                mBrandView?.text = "Brand : " + item.brand
            }
    }
}