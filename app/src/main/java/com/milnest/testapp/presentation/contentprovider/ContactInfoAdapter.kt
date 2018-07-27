package com.milnest.testapp.presentation.contentprovider

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.milnest.testapp.R

class ContactInfoAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var contactInfoList : MutableList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContactInfoItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.contact_info_item, null, false))
    }

    override fun getItemCount(): Int {
        return contactInfoList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val contactHolder = holder as ContactInfoItemHolder
        contactHolder.contactInfoItemTextView.text = contactInfoList[position]
    }

    inner class ContactInfoItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var contactInfoItemTextView: TextView = itemView.findViewById(R.id.contactInfoItemTextView)
    }
}