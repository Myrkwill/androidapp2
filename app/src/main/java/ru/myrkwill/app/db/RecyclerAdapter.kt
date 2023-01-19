package ru.myrkwill.app.db

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.myrkwill.app.EditActivity
import ru.myrkwill.app.databinding.RcItemBinding

class RecyclerAdapter(private var list: ArrayList<ListItem>, private val context: Context): RecyclerView.Adapter<RecyclerAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RcItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding, context)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun update(newList: List<ListItem>) {
        list.clear()
        list.addAll(newList)
        Log.d("MyTag", "Update items")
        notifyDataSetChanged()
    }

    fun removeItem(position: Int, databaseManager: DatabaseManager) {
        databaseManager.removeItem(list[position].id.toString())
        list.removeAt(position)
        Log.d("MyTag", "Remove items")
        notifyItemRangeChanged(0, list.size)
        notifyItemRemoved(position)
    }

    class Holder(private val binding: RcItemBinding, val context: Context): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ListItem) = with(binding) {
            tvTitle.text = item.title
            binding.root.setOnClickListener {
                val intent = Intent(context, EditActivity::class.java).apply {
                    putExtra(IntentConstant.INTENT_ID_KEY, item.id)
                    putExtra(IntentConstant.INTENT_TITLE_KEY, item.title)
                    putExtra(IntentConstant.INTENT_DESK_KEY, item.desc)
                    putExtra(IntentConstant.INTENT_URI_KEY, item.uri)
                }

                context.startActivity(intent)
            }
        }

    }
}