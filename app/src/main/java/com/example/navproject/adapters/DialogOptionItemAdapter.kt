package com.example.navproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.navproject.R
import com.example.navproject.models.AlertModel
import kotlinx.android.synthetic.main.item_option_todo.view.*

class DialogOptionItemAdapter :
    RecyclerView.Adapter<DialogOptionItemAdapter.DialogOptionItemViewHolder>() {
    private var mListDialogOption = ArrayList<AlertModel>()
    private var mCallback: ((Int, String) -> Unit)? = null

    fun setList(list: ArrayList<AlertModel>) {
        mListDialogOption = list
        notifyDataSetChanged()
    }

    fun setCallback(callback: (Int, String) -> Unit) {
        mCallback = callback
    }

    inner class DialogOptionItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogOptionItemViewHolder {
        return DialogOptionItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_option_todo, parent, false)
        )
    }

    override fun getItemCount() = mListDialogOption.size

    override fun onBindViewHolder(holder: DialogOptionItemViewHolder, position: Int) {
        val itemData = mListDialogOption[position]
        holder.itemView.apply {
            tvLastDone.text = itemData.alertField
            setOnClickListener {
                mCallback?.invoke(itemData.alertKey ?: 0, itemData.alertField ?: "")
            }
        }
    }
}