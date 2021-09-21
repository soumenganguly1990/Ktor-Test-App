package com.soumen.ktortestapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soumen.ktortestapp.databinding.ItemUserBinding

class CommonRecyclerViewAdapter<T>(
    private val mContext: Context,
    private val dataList: List<T>?,
    private val layout: Int,
    private val itemToBindListener: (Triple<ItemUserBinding, Int, T?>) -> Unit
) :
    RecyclerView.Adapter<CommonRecyclerViewAdapter.CommonViewHolder>() {

    /* we will be sending back the binding, the positiion and the selectd item, can be used in different use cases */

    class CommonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemUserBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        return CommonViewHolder(LayoutInflater.from(mContext).inflate(layout, parent, false))
    }

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        itemToBindListener.invoke(Triple(holder.binding, position, dataList?.get(position)))
    }

    override fun getItemCount(): Int {
        dataList?.let { return dataList.size }
        return 0
    }
}