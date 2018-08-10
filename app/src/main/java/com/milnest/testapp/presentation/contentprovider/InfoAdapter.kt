package com.milnest.testapp.presentation.contentprovider

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.milnest.testapp.App
import com.milnest.testapp.R
import com.milnest.testapp.entities.Info

class InfoAdapter(val phoneClickListener: PhoneClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var infoList: MutableList<Info> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Info.TYPE_CONTACT_PHONE -> PhoneItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_content_provider_contact_info_item_phone, parent, false))
            else -> ContactInfoItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_content_provider_contact_info_item, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return infoList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        /*val type = infoList[position].itemType
        when(type){
            InfoItem.INFO_TYPE_TEXT -> {
                val contactHolder = holder as ContactInfoItemHolder
                contactHolder.contactInfoItemTextView.text = infoList[position].content
            }
            InfoItem.INFO_TYPE_PHOTO -> {
                val photoItemHolder = holder as PhotoItemHolder
                Picasso.get().load(Uri.parse(infoList[position].content)).into(photoItemHolder.contactPhotoImageView)
            }
            InfoItem.INFO_TYPE_PHOTO_TEXT -> {
                val photoTextItemHolder = holder as PhotoTextItemHolder
                photoTextItemHolder.contactInfoText.text = infoList[position].content
                *//*photoTextItemHolder.contactInfoItemTextView.text = infoList[position].content*//*
            }
        }*/
        (holder as ContactInfoItemHolder).infoItemTextView.text = infoList[position].content/*.content*/
        when (infoList[position].type){
            Info.TYPE_CONTACT_EMAIL -> holder.infoTitleTextView.text = App.context.getString(R.string.emailTitle)
            Info.TYPE_CONTACT_PHONE -> holder.infoTitleTextView.text = App.context.getString(R.string.phoneTitle)
            Info.TYPE_CONTACT_NAME -> holder.infoTitleTextView.text = App.context.getString(R.string.nameTitle)
        }
        
    }

    open inner class ContactInfoItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var infoItemTextView: TextView = itemView.findViewById(R.id.infoItemTextView)
        internal var infoTitleTextView: TextView = itemView.findViewById(R.id.infoTitleTextView)
    }

    inner class PhoneItemHolder(itemView: View) : ContactInfoItemHolder(itemView) {
        internal var callImageButton: ImageButton = itemView.findViewById(R.id.callPhone)
        init {
            callImageButton.setOnClickListener{
                phoneClickListener.onItemClick(infoItemTextView.text.toString())
            }
        }
    }

    interface PhoneClickListener {
        fun onItemClick(phoneNumber: String)
    }

    /*inner class PhotoItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var contactPhotoImageView: ImageView = itemView.findViewById(R.id.contactInfoPhotoImg)
    }

    inner class PhotoTextItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var contactInfoText : ContactPhotoPlaceholder = itemView.findViewById(R.id.contactPhotoHolder)
        *//*internal var contactInfoItemTextView: TextView = itemView.findViewById(R.id.contactInfoPhotoText)*//*
    }*/

    override fun getItemViewType(position: Int): Int {
        return when (infoList[position].type) {
            Info.TYPE_CONTACT_PHONE -> Info.TYPE_CONTACT_PHONE
            else -> Info.TYPE_JUST_TEXT
        }
    }
}