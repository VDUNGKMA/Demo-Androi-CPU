package com.example.cpuperfdemo


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ViewHolderBinder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var item: T? = null
}
class basicAdapterWithLayoutAndBinder<T>(
    private val items: List<T>,
    private val layoutId: Int,
    private val binder: (holder: ViewHolderBinder<T>, item: T) -> Unit
) : RecyclerView.Adapter<ViewHolderBinder<T>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderBinder<T> {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)

        return ViewHolderBinder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolderBinder<T>, position: Int) {
        val item = items[position]
        holder.item = item
        binder(holder, item)
    }

}