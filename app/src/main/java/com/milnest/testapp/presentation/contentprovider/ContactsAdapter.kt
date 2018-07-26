package com.milnest.testapp.presentation.contentprovider

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ToggleButton
import com.milnest.testapp.R
import com.milnest.testapp.entities.ContactShortInfo
import com.milnest.testapp.tasklist.presentation.main.IClickListener

class ContactsAdapter(val iClickListener: IClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    var contactsList: MutableList<ContactShortInfo> = ArrayList()

    var selectedPosition = -1
    /*fun fillContactList(list: MutableList<ContactShortInfo>){
        contactsList = list
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ShortContactItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.short_contact_info, null, false))
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    override fun getItemId(position: Int): Long {
        return contactsList.get(position).id
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val contactHolder = holder as ShortContactItemHolder
        contactHolder.nameTextView.text = contactsList[position].name
        contactHolder.phoneTextView.text = contactsList[position].phone
        contactHolder.toggleButton.isChecked = selectedPosition == position
    }

    inner class ShortContactItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var nameTextView : TextView = itemView.findViewById(R.id.nameTextView)
        internal var phoneTextView : TextView = itemView.findViewById(R.id.phoneTextView)
        internal var toggleButton: ToggleButton = itemView.findViewById(R.id.toggle)
        init {
            toggleButton.setOnClickListener {
                iClickListener.onItemClick(layoutPosition)
                selectedPosition = adapterPosition
            }
            /*itemView.setOnClickListener {
                iClickListener.onItemClick(layoutPosition)
                selectedPosition = adapterPosition
            }*/
        }
    }
}