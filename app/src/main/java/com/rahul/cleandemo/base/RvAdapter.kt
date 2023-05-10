package com.rahul.cleandemo.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class RvAdapter<T, V : ViewDataBinding>(private val layoutId: Int) :
    RecyclerView.Adapter<RvAdapter.RvAdapterViewHolder<V>>() {

    internal fun setup(rv: RecyclerView) {
        rv.adapter = this
    }

    abstract fun onBindItem(data: T, rvItemBinding: V)

    private var items: ArrayList<T> = ArrayList()

    override fun getItemCount(): Int = items.size

    fun setItems(data: List<T>?) {
        items.clear()
        if (itemCount > 0) {
            notifyItemRangeChanged(0, itemCount - 1)
        }
        if (data != null && data.isNotEmpty()) {
            items.addAll(data)
            notifyItemRangeInserted(0, data.size - 1)
        }
    }


    fun newItems(data: List<T>?) {

        if (data == null) {
            return
        }
        if (data.isNotEmpty()) {
            items.addAll(data)
            notifyItemRangeInserted(0, data.size - 1)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvAdapterViewHolder<V> {
        val viewDataBinding: V = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false
        )
        return RvAdapterViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: RvAdapterViewHolder<V>, position: Int) {
        onBindItem(data = items[holder.adapterPosition], holder.rvItemBinding)
    }


    class RvAdapterViewHolder<V : ViewDataBinding>(internal val rvItemBinding: V) :
        RecyclerView.ViewHolder(rvItemBinding.root)


}