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
import com.milnest.testapp.customview.ContactPhotoHolder
import com.milnest.testapp.customview.RoundedTransformation
import com.milnest.testapp.customview.RoundedTransformationChecked
import com.milnest.testapp.entities.ContactShortInfo
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
        /*TODO: сделать холдер*/ContactShortInfo.SHORT_INFO_PHOTO -> ShortContactWithPhotoHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_content_provider_short_item_info_photo, parent, false))
            else -> ShortContactWithoutPhotoHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_content_provider_short_item_info, parent, false
            ))
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
        var contactHolder = holder as ShortContactItemHolder
        contactHolder.nameTextView.text = contactsList[position].name
        contactHolder.phoneTextView.text = contactsList[position].phone
        /*contactHolder.toggleButton.isChecked = selectedPosition == position*/
        when (type) {
            ContactShortInfo.SHORT_INFO_PHOTO -> {
                contactHolder = contactHolder as ShortContactWithPhotoHolder
                if (selectedPosition != position)
                    Picasso.get().load(Uri.parse(contactsList[position].photoUriString)).transform(RoundedTransformation(50,0)).resize(100,100).into(contactHolder.photoImage)
                else {
                    Picasso.get().load(Uri.parse(contactsList[position].photoUriString)).transform(RoundedTransformationChecked(50,0)).resize(100,100).into(contactHolder.photoImage)
                }
            }
            else -> {
                contactHolder = contactHolder as ShortContactWithoutPhotoHolder
                if (selectedPosition != position)
                    (contactHolder).photoHolder.text = contactsList[position].photoUriString
                else {
                    (contactHolder).photoHolder.text = "V"
                    contactHolder.photoHolder.color = App.context.resources.getColor(R.color.colorBlueGray_500)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (contactsList[position].type) {
            ContactShortInfo.SHORT_INFO_PHOTO -> ContactShortInfo.SHORT_INFO_PHOTO
            else -> ContactShortInfo.SHORT_INFO_PHOTO_PLACEHOLDER
        }
    }

    open inner class ShortContactItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var nameTextView: TextView = itemView.findViewById(R.id.titleTextView)
        internal var phoneTextView: TextView = itemView.findViewById(R.id.contentTextView)
        /*internal var toggleButton: ToggleButton = itemView.findViewById(R.id.toggle)*/

        /*init {
            toggleButton.setOnClickListener {
                iClickListener.onItemClick(layoutPosition)
                selectedPosition = adapterPosition
                notifyDataSetChanged()
            }
        }*/
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
        internal var photoHolder: ContactPhotoHolder = itemView.findViewById(R.id.contactPhotoHolderShortInfo)

        init {
            itemView.setOnClickListener {
                iClickListener.onItemClick(layoutPosition)
                selectedPosition = adapterPosition
                notifyDataSetChanged()
            }
        }
    }
}