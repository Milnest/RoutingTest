package com.milnest.testapp.presentation.contentprovider

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.milnest.testapp.App
import com.milnest.testapp.R
import com.milnest.testapp.customview.ContactPhotoHolder
import com.milnest.testapp.entities.InfoItem
import com.squareup.picasso.Picasso

class InfoAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var infoList: MutableList<InfoItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            InfoItem.INFO_TYPE_TEXT -> ContactInfoItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_content_provider_contact_info_item, null, false))
            InfoItem.INFO_TYPE_PHOTO -> PhotoItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_content_provider_photo_item, null, false))
            else -> PhotoTextItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_content_provider_photo_text_item, null, false))
        }
    }

    override fun getItemCount(): Int {
        return infoList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val type = infoList[position].itemType
        when(type){
            InfoItem.INFO_TYPE_TEXT -> {
                val contactHolder = holder as ContactInfoItemHolder
                contactHolder.contactInfoItemTextView.text = infoList[position].content
            }
            InfoItem.INFO_TYPE_PHOTO -> {
                val photoItemHolder = holder as PhotoItemHolder
                Picasso.get().load(Uri.parse(infoList[position].content)).resize(150, 150).into(photoItemHolder.contactPhotoImageView)
            }
            InfoItem.INFO_TYPE_PHOTO_TEXT -> {
                val photoTextItemHolder = holder as PhotoTextItemHolder
                photoTextItemHolder.contactInfoText.text = infoList[position].content
                /*photoTextItemHolder.contactInfoItemTextView.text = infoList[position].content*/
            }
        }
    }

    inner class ContactInfoItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var contactInfoItemTextView: TextView = itemView.findViewById(R.id.contactInfoItemTextView)
    }

    inner class PhotoItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var contactPhotoImageView: ImageView = itemView.findViewById(R.id.contactInfoPhotoImg)
    }

    inner class PhotoTextItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var contactInfoText : ContactPhotoHolder = itemView.findViewById(R.id.contactPhotoHolder)
        /*internal var contactInfoItemTextView: TextView = itemView.findViewById(R.id.contactInfoPhotoText)*/
    }

    override fun getItemViewType(position: Int): Int {
        return when (infoList[position].itemType) {
            InfoItem.INFO_TYPE_TEXT -> InfoItem.INFO_TYPE_TEXT
            InfoItem.INFO_TYPE_PHOTO -> InfoItem.INFO_TYPE_PHOTO
            else -> InfoItem.INFO_TYPE_PHOTO_TEXT
        }
    }
}