package com.milnest.testapp.tasklist.presentation.list

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.milnest.testapp.App
import com.milnest.testapp.tasklist.ID
import com.milnest.testapp.tasklist.data.repository.DBRepository
import com.milnest.testapp.tasklist.data.repository.IDataRepository
import com.milnest.testapp.tasklist.entities.CheckboxTaskListItem
import com.milnest.testapp.tasklist.entities.Task
import com.milnest.testapp.tasklist.other.utils.JsonAdapter
import javax.inject.Inject

@InjectViewState
class ListTaskPresenter: MvpPresenter<ListTaskView>() {

    private val itemsList: MutableList<CheckboxTaskListItem> = ArrayList()
    val adapter = ListTaskAdapter(onItemClickListener)
    private var listId: Int? = null
    private var task = Task(Task.TYPE_ITEM_LIST)

    var rep = App.appComponent.dbRep() //TODO: если не будет работать при смене конфига - перенести в онкриэйт

    /*@Inject
    lateinit var rep: IDataRepository*/

    /*init {
        App.appComponent?.inject(this);
        App.appComponent?.dbRep()
    }*/

    fun saveClicked() {
        val listToSave = ArrayList<CheckboxTaskListItem>()
        for (item in itemsList){
            if (item.cbText != "") /*itemsList.remove(item)*/listToSave.add(item)
        }
        task.data = JsonAdapter.toJson(/*itemsList*/listToSave)
        rep.saveTask(task)
        //taskView.get()?.finish()
        //viewState.finish()
        App.getRouter().exit()
    }

    fun setStartList(extras: Bundle?) {
        adapter.setData(itemsList)
        if (extras != null) {
            listId = extras.getInt(ID)
            task = rep.getTaskById(listId!!)!!
            itemsList.addAll(JsonAdapter.fromJson(task.data))
            adapter.notifyDataSetChanged()
        } else {
            itemsList.add(CheckboxTaskListItem("", false))
            adapter.notifyDataSetChanged()
        }
    }

    fun setAdapter(recycler_view_cb: RecyclerView) {
        recycler_view_cb.adapter = adapter
        adapter.setRecycler(recycler_view_cb)
    }

    private val onItemClickListener: ListTaskAdapter.CbClickListener
        get() = object : ListTaskAdapter.CbClickListener {
            override fun onAddItem() {
                itemsList.add(CheckboxTaskListItem("", false))
                adapter.notifyItemInserted(itemsList.lastIndex)
            }

            override fun onTextChanged(layoutPosition: Int, text: String) {
                itemsList[layoutPosition].cbText = text
                //adapter.notifyDataSetChanged()
            }

            override fun onStateChanged(layoutPosition: Int, state: Boolean) {
                    val cbAndText = itemsList[layoutPosition]
                    cbAndText.isCbState = state
                    itemsList[layoutPosition] = cbAndText
            }

            override fun onRemoveItem(position: Int) {
                if (position != -1) {
                    itemsList.removeAt(position)
                    adapter.notifyItemRemoved(position)
                }
            }
        }
}