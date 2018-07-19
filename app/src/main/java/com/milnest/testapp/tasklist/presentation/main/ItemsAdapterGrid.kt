package com.milnest.testapp.tasklist.presentation.main

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.milnest.testapp.R
import com.milnest.testapp.tasklist.entities.Task
import com.milnest.testapp.tasklist.other.utils.JsonAdapter
import com.squareup.picasso.Picasso
import java.io.File

class ItemsAdapterGrid(private val iClickListener: IClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var tempViewHolder: RecyclerView.ViewHolder? = null
    private val mViewHolderList: MutableList<RecyclerView.ViewHolder> = java.util.ArrayList()
    private var mItems: MutableList<taskWithState>? = ArrayList()
    private val LIST_SIZE = 4

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v: View

        when (viewType) {
            Task.TYPE_ITEM_TEXT -> {
                v = LayoutInflater.from(parent.context).inflate(R.layout.task_list_item_main_text,
                        parent, false)
                tempViewHolder = TextItemHolder(v)
                return tempViewHolder as TextItemHolder
            }
            Task.TYPE_ITEM_IMAGE -> {
                v = LayoutInflater.from(parent.context).inflate(R.layout.task_list_item_main_img,
                        parent, false)
                tempViewHolder = ImgItemHolder(v)
                return tempViewHolder as ImgItemHolder
            }
        /*Task.TYPE_ITEM_LIST*/ else -> {
            v = LayoutInflater.from(parent.context).inflate(
                    R.layout.task_list_item_main_cblist, parent, false)
            tempViewHolder = CheckboxListItemHolder(v)
            return tempViewHolder as CheckboxListItemHolder
        }
        //else ->
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        mViewHolderList.add(holder.adapterPosition, holder)
        val taskListItem = mItems!![position]
        val type = taskListItem.task.type
        if (mItems!![position].state)
            holder.itemView.setBackgroundResource(R.color.black)
        else
            holder.itemView.setBackgroundResource(R.color.colorWhite)
        when (type) {
            Task.TYPE_ITEM_TEXT -> {
                val textItemHolder = holder as TextItemHolder
                textItemHolder.mName.text = taskListItem.task.title
                textItemHolder.mText.text = taskListItem.task.data
            }
            Task.TYPE_ITEM_IMAGE -> {
                val imgItemHolder = holder as ImgItemHolder
                imgItemHolder.mImgName.text = taskListItem.task.title
                Picasso.get().load(File(taskListItem.task.data)).resize(250, 250).into(imgItemHolder.mImage)
            }
            Task.TYPE_ITEM_LIST -> {
                val cbListItemHolder = holder as CheckboxListItemHolder
                //val listView = cbListItemHolder.cbListView
                val recView = cbListItemHolder.cbRecyclerView
                recView.layoutManager = LinearLayoutManager(holder.itemView.context)
                val listOfCb = JsonAdapter.fromJson(taskListItem.task.data)
                if (listOfCb.size > LIST_SIZE){
                    val recAdapter = CbAdapterMainRecycler(listOfCb.subList(0, LIST_SIZE))
                    recAdapter.hasMoreItems = true
                    recView.adapter = recAdapter
                }
                else{
                    val recAdapter = CbAdapterMainRecycler(listOfCb)
                    recAdapter.hasMoreItems = false
                    recView.adapter = recAdapter
                }
            }
        }
    }


    override fun getItemCount(): Int {
        return mItems?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return mItems!!.get(position).task.type
    }


    open inner class TaskItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { iClickListener.onItemClick(layoutPosition) }
            itemView.setOnLongClickListener { iClickListener.onItemLongClick(layoutPosition) }
        }
    }

    inner class TextItemHolder(itemView: View) : TaskItemHolder(itemView) {
        internal var mName: TextView = itemView.findViewById(R.id.name)
        internal var mText: TextView = itemView.findViewById(R.id.text)
    }

    inner class ImgItemHolder(itemView: View) : TaskItemHolder(itemView) {
        internal var mImgName: TextView = itemView.findViewById(R.id.imgName)
        internal var mImage: ImageView = itemView.findViewById(R.id.img)
    }

    inner class CheckboxListItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var  cbRecyclerView : RecyclerView = itemView.findViewById(R.id.recycler_view_cb)
        init {
            cbRecyclerView.setOnTouchListener { _, _ -> false }
            itemView.findViewById<View>(R.id.clickView).setOnClickListener { iClickListener.onItemClick(layoutPosition) }
            itemView.findViewById<View>(R.id.clickView).setOnLongClickListener { iClickListener.onItemLongClick(layoutPosition) }
        }
    }

    //TODO:?????
    fun addSelection(position: Int) {
        var holderToSelect: RecyclerView.ViewHolder? = null
        for (viewHolder in mViewHolderList) {
            if (viewHolder.adapterPosition == position) holderToSelect = viewHolder
        }
        holderToSelect?.itemView?.setBackgroundResource(R.color.black)
        mItems!!.get(position).state = true
    }

    fun removeSelection() {
        for (item in mItems!!) {
            item.state = false
        }
        for (viewHolder in mViewHolderList) {
            viewHolder.itemView.setBackgroundResource(R.color.colorWhite)
        }
    }

    inner class taskWithState(var task: Task, var state: Boolean)

    fun setData(data: List<Task>) {
        mItems!!.clear()
        for (item in data) {
            mItems!!.add(taskWithState(item, false))
        }

        notifyDataSetChanged()
    }


    override fun getItemId(position: Int): Long {
        return mItems!![position].task.id.toLong()
    }
}