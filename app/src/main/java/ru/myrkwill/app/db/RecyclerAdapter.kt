package ru.myrkwill.app.db

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.myrkwill.app.R
import ru.myrkwill.app.databinding.RcItemBinding

class RecyclerAdapter(private var list: ArrayList<String>): RecyclerView.Adapter<RecyclerAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RcItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun update(newList: List<String>) {
        list.clear()
        list.addAll(newList)
        Log.d("MyTag", "${newList}")
        notifyDataSetChanged()
    }

    class Holder(private val binding: RcItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(title: String) = with(binding) {
            tvTitle.text = title
        }

    }
}