package com.milnest.testapp.tasklist.presentation.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.milnest.testapp.R
import com.milnest.testapp.tasklist.entities.CheckboxTaskListItem

class CbAdapterMainRecycler(val cbList: List<CheckboxTaskListItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val CHECKBOX_ITEM_TYPE: Int = 0
    val HAS_MORE_ITEM_TYPE: Int = 1
    var hasMoreItems: Boolean = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v: View
        when (viewType) {
            CHECKBOX_ITEM_TYPE -> {
                return CbItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.task_list_item_main_cb, null, false))
            }
            else -> return MoreItemsHolder(LayoutInflater.from(parent.context).inflate(R.layout.task_list_item_main_has_more, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return if(hasMoreItems) cbList.size + 1 else cbList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == cbList.size) HAS_MORE_ITEM_TYPE else CHECKBOX_ITEM_TYPE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CbItemHolder) {
            val element = cbList[position]
            holder.mCb.isChecked = element.isCbState
            holder.mCb.setText(element.cbText)
        }
        else{
            val moreHolder = holder as MoreItemsHolder
        }
    }

    inner class CbItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var mCb: CheckBox = itemView.findViewById(R.id.cb_main)
    }

    inner class MoreItemsHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        internal var iHasMore : TextView =  itemView.findViewById(R.id.has_more_elements)
    }
}