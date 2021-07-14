package com.example.contentprovider

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactsAdapter(items:List<ContactPOJO>): RecyclerView.Adapter<ContactsAdapter.ContectsViewHolder>() {

    private var list = items

    inner class ContectsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val name: TextView = itemView.findViewById(R.id.contact_name)
        val number: TextView = itemView.findViewById(R.id.contact_num)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContectsViewHolder {
        return ContectsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.contact_item,parent,false))
    }

    override fun onBindViewHolder(holder: ContectsViewHolder, position: Int) {
        holder.name.setText(list[position].name)
        holder.number.setText(list[position].number)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}