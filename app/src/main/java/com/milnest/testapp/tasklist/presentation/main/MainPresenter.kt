package com.milnest.testapp.tasklist.presentation.main

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.support.v7.view.ActionMode
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.milnest.testapp.App
import com.milnest.testapp.R
import com.milnest.testapp.router.FragType
import com.milnest.testapp.tasklist.CAMERA_RESULT
import com.milnest.testapp.tasklist.GALLERY_RESULT
import com.milnest.testapp.tasklist.data.repository.DBRepository
import com.milnest.testapp.tasklist.entities.ResultOfActivity
import com.milnest.testapp.tasklist.entities.Task
import com.milnest.testapp.tasklist.other.utils.ImgUtil
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.io.File

@InjectViewState
class MainPresenter : MvpPresenter<MainView>(){
    var adapter = ItemsAdapter(onItemClickListener)
    var adapterGrid = ItemsAdapterGrid(onItemClickListener)
    var curAdapterType: Int = GRID_TYPE
    private lateinit var photoFile: File
    var curPosDelete = -1

    var actionMode : ActionMode? = null
    val linearLayoutManager = LinearLayoutManager(App.context)
    val gridLayoutManager = GridLayoutManager(App.context, 2)

    fun setAdapter(itemsView: RecyclerView) {
        if (curAdapterType == LINEAR_TYPE) {
            itemsView.adapter = adapterGrid
            itemsView.layoutManager = gridLayoutManager
            curAdapterType = GRID_TYPE
            viewState.setSplitIcon(R.drawable.ic_linear_split)
            adaptersUpdateData()
        }else{
            itemsView.adapter = adapter
            itemsView.layoutManager = linearLayoutManager
            curAdapterType = LINEAR_TYPE
            viewState.setSplitIcon(R.drawable.ic_tasks_column_split)
            adaptersUpdateData()
        }
    }

    private fun notifToActivity(toShow: Int) {
        viewState.showNotif(toShow);
    }

    fun processViewRes(result: ResultOfActivity?) {
        if (result!!.resultCode == Activity.RESULT_OK) {
            when (result.requestCode) {
                CAMERA_RESULT -> {
                    try {
                        DBRepository.addTask("", Task.TYPE_ITEM_IMAGE, photoFile.canonicalPath)
                        adaptersUpdateData()
                    } catch (ex: Exception) {
                        notifToActivity(R.string.no_external)
                    }
                }

                GALLERY_RESULT -> {
                    Observable.just(result.data!!.data)
                            .map { imgUri: Uri ->
                                MediaStore.Images.Media.getBitmap(
                                        App.context.contentResolver, imgUri)
                            }
                            .map { img: Bitmap -> ImgUtil.saveImageToFile(img) }
                            .map { imgFile: File ->
                                MediaStore.Images.Media.insertImage(
                                        App.context.contentResolver, imgFile.canonicalPath,
                                        imgFile.name, imgFile.name)
                                return@map imgFile.canonicalPath
                            }
                            .map { filePath: String -> (DBRepository.addTask("", Task.TYPE_ITEM_IMAGE, filePath)) }
                            .subscribe(object : Observer<Unit> {
                                override fun onComplete() {}

                                override fun onSubscribe(d: Disposable) {}

                                override fun onNext(t: Unit) {
                                    adaptersUpdateData()
                                }

                                override fun onError(e: Throwable) {
                                    notifToActivity(R.string.no_external)
                                }

                            })
                }
            }
        } else notifToActivity(R.string.save_canceled)
    }

    private fun adaptersUpdateData() {
        if (curAdapterType == LINEAR_TYPE) adapter.setData(DBRepository.getAllTasks())
        else adapterGrid.setData(DBRepository.getAllTasks())
    }

    private fun adaptersSearchData(searchDynamicTask: MutableList<Task>) {
        if (curAdapterType == LINEAR_TYPE) adapter.setData(searchDynamicTask)
        else adapterGrid.setData(searchDynamicTask)
    }

    private fun adaptersAddSelection(position: Int) {
        if (curAdapterType == LINEAR_TYPE) adapter.addSelection(position)
        else adapterGrid.addSelection(position)
    }

    private fun adaptersRemoveSelection() {
        if (curAdapterType == LINEAR_TYPE) adapter.removeSelection()
        else adapterGrid.removeSelection()
    }

    fun photoClicked() {
        savePhoto()
    }

    private fun savePhoto() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        photoFile = ImgUtil.createFilePath()
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile))

        viewState.startPhotoActivity(cameraIntent)
    }

    fun updateList() {
        adaptersUpdateData()
    }

    fun addTextTask() = View.OnClickListener {
        App.getRouter().navigateTo(FragType.TASK_LIST_TEXT.name)
    }

    fun addListTask() = View.OnClickListener {
        App.getRouter().navigateTo(FragType.TASK_LIST_LIST.name)
    }

    fun addImgTask() = View.OnClickListener {
        viewState.showDialog()
    }

    fun searchChangeFocus() = View.OnFocusChangeListener { _: View, _: Boolean ->
        adaptersUpdateData()
    }

    val searchListener: SearchView.OnQueryTextListener
        get() = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()) adaptersUpdateData()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adaptersSearchData(DBRepository.searchDynamicTask(newText))
                return true
            }
        }

    private val onItemClickListener: /*ItemsAdapter.*/IClickListener
        get() = object : /*ItemsAdapter.*/IClickListener {
            override fun onItemClick(position: Int) {
                val id = adapter.getItemId(position).toInt()
                val type = adapter.getItemViewType(position)

                when (type) {
                    Task.TYPE_ITEM_TEXT -> {
                        App.getRouter().navigateTo(FragType.TASK_LIST_TEXT.name, id)
                    }
                    Task.TYPE_ITEM_LIST -> {
                        App.getRouter().navigateTo(FragType.TASK_LIST_LIST.name, id)
                    }
                }
            }

            override fun onItemLongClick(position: Int): Boolean {
                if (actionMode == null) {
                    curPosDelete = position
                    viewState.showActionBar(R.string.action_mode)
                    adaptersAddSelection(position)
                } else {
                    curPosDelete = -1
                    viewState.closeActionBar()
                }
                return true
            }
        }

    val onActionModeListener: ActionMode.Callback
        get() = object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                val inflater = mode.menuInflater
                inflater.inflate(R.menu.task_list_menu_context_task, menu)
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode, menu: Menu) = false

            override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
                if (curPosDelete != -1) {
                    DBRepository.deleteTask(adapter.getItemId(curPosDelete))
                    adaptersUpdateData()
                    viewState.closeActionBar()
                }
                return false
            }

            override fun onDestroyActionMode(mode: ActionMode) {
                adaptersRemoveSelection()
            }
        }

    companion object {
        val GRID_TYPE = 0
        val LINEAR_TYPE = 1
    }
}