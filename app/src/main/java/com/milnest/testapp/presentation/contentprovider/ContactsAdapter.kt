package com.milnest.testapp.presentation.contentprovider

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.milnest.testapp.App
import com.milnest.testapp.R
import com.milnest.testapp.customview.ContactPhotoPlaceholder
import com.milnest.testapp.customview.RoundedTransformation
import com.milnest.testapp.customview.RoundedTransformationChecked
import com.milnest.testapp.entities.ContactShortInfo
import com.milnest.testapp.others.utils.ColorPick
import com.milnest.testapp.tasklist.presentation.main.IClickListener
import com.squareup.picasso.Picasso

class ContactsAdapter(val iClickListener: IClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var contactsList: MutableList<ContactShortInfo> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ContactShortInfo.SHORT_INFO_PHOTO -> ShortContactWithPhotoHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.activity_content_provider_short_item_info_photo, parent, false))
            ContactShortInfo.SHORT_INFO_PHOTO_PLACEHOLDER -> ShortContactWithoutPhotoHolder(
                    LayoutInflater.from(parent.context).inflate(
                            R.layout.activity_content_provider_short_item_info, parent, false))
            else -> GroupItemHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.activity_content_provider_group_item, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    override fun getItemId(position: Int): Long {
        return contactsList.get(position).id
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val type = contactsList[position].type
        var contactHolder = holder
        /*contactHolder.toggleButton.isChecked = selectedPosition == position*/
        when (type) {
            ContactShortInfo.SHORT_INFO_PHOTO -> {
                contactHolder = contactHolder as ShortContactWithPhotoHolder
                if (selectedPosition != position)
                    Picasso.get().load(Uri.parse(contactsList[position].photoUriString)).transform(RoundedTransformation(50, 0)).resize(100, 100).into(contactHolder.photoImage)
                else {
                    Picasso.get().load(Uri.parse(contactsList[position].photoUriString)).transform(RoundedTransformationChecked(50, 0)).resize(100, 100).into(contactHolder.photoImage)
                }
                contactHolder.nameTextView.text = contactsList[position].name
                contactHolder.phoneTextView.text = contactsList[position].phone
            }
            ContactShortInfo.SHORT_INFO_PHOTO_PLACEHOLDER -> {
                contactHolder = contactHolder as ShortContactWithoutPhotoHolder
                if (selectedPosition != position) {

                    contactHolder.photoPlaceholder.isActive = false
                    /*(contactHolder).photoPlaceholder.text = contactsList[position].photoUriString
                    val colors = ColorPick.pickLiteralColors()
                    contactHolder.photoPlaceholder.textColor = colors.first
                    contactHolder.photoPlaceholder.textColorSecond = colors.second*/
                }
                else {
                    contactHolder.photoPlaceholder.isActive = true
                    //(contactHolder).photoPlaceholder.text = "V"
                    //contactHolder.photoPlaceholder.color = App.context.resources.getColor(R.color.colorBlueGray_500)
                }
                (contactHolder).photoPlaceholder.text = contactsList[position].photoUriString
                val colors = ColorPick.pickLiteralColors()
                contactHolder.photoPlaceholder.textColor = colors.first
                contactHolder.photoPlaceholder.textColorSecond = colors.second
                contactHolder.nameTextView.text = contactsList[position].name
                contactHolder.phoneTextView.text = contactsList[position].phone
            }
            else -> {
                val groupHolder = holder as GroupItemHolder
                if (position != contactsList.lastIndex){
                    if(contactsList[position + 1].type != ContactShortInfo.SHORT_INFO_GROUP)
                        groupHolder.groupTitleTextView.text = contactsList[position].name
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return contactsList[position].type
    }

    open inner class ShortContactItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var nameTextView: TextView = itemView.findViewById(R.id.titleTextView)
        internal var phoneTextView: TextView = itemView.findViewById(R.id.contentTextView)
    }

    inner class GroupItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var groupTitleTextView: TextView = itemView.findViewById(R.id.groupTitleTextView)
    }

    inner class ShortContactWithPhotoHolder(itemView: View) : ShortContactItemHolder(itemView) {
        /*internal var cardView: CardView = itemView.findViewById(R.id.cardViewShortPhoto)*/
        internal var photoImage: ImageView = itemView.findViewById(R.id.contactShortPhotoImg)

        init {
            itemView.setOnClickListener {
                iClickListener.onItemClick(layoutPosition)
                selectedPosition = adapterPosition
                notifyDataSetChanged()
            }
        }
    }

    inner class ShortContactWithoutPhotoHolder(itemView: View) : ShortContactItemHolder(itemView) {
        internal var photoPlaceholder: ContactPhotoPlaceholder = itemView.findViewById(R.id.contactPhotoPlaceholder)

        init {
            itemView.setOnClickListener {
                iClickListener.onItemClick(layoutPosition)
                selectedPosition = adapterPosition
                notifyDataSetChanged()
            }
        }
    }
}