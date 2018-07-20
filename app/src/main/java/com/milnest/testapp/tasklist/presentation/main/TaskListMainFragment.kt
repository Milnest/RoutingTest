package com.milnest.testapp.tasklist.presentation.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.milnest.testapp.R
import com.milnest.testapp.presentation.main.MainActivity
import com.milnest.testapp.router.BaseFragment
import com.milnest.testapp.router.FragType
import com.milnest.testapp.tasklist.CAMERA_RESULT
import com.milnest.testapp.tasklist.GALLERY_RESULT
import com.milnest.testapp.tasklist.entities.ResultOfActivity
import kotlinx.android.synthetic.main.task_list_activity_main.*


class TaskListMainFragment : BaseFragment(), MainView {
    @InjectPresenter
    lateinit var presenter: MainPresenter
    private lateinit var searchView: SearchView
    private lateinit var dialog: AlertDialog
    private var splitMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_list_main, container, false)
    }

    override fun onStart() {
        super.onStart()
        bindViews()
    }

    private fun bindViews() {
        //val actBar = (activity as MainActivity).supportActionBar
        //setHasOptionsMenu(true)
        //setSupportActionBar(toolbar)
        presenter.attachView(this)
        presenter.setAdapter(recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context!!)

        val dialogBuilder = AlertDialog.Builder(context!!) // TODO: изменено
        dialogBuilder.setTitle(getString(R.string.add_photo))
                .setMessage(getString(R.string.choose_source))
                .setPositiveButton(getString(R.string.new_cam_photo)) { _, _ ->
                    presenter.photoClicked()
                }
                .setNegativeButton(getString(R.string.gallery_photo)) { _, _ ->
                    openGallery()
                }
        dialog = dialogBuilder.create()
        add_task_text.setOnClickListener(presenter.addTextTask())
        add_task_list.setOnClickListener(presenter.addListTask())
        add_task_photo.setOnClickListener(presenter.addImgTask())
    }

    fun openGallery() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        createTaskActivity(photoPickerIntent, GALLERY_RESULT)
    }

    override fun showDialog() {
        if (!dialog.isShowing) {
            dialog.show()
        }
    }

    override fun finishActionMode() {
        presenter.actionMode!!.finish()
        presenter.actionMode = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.task_list_menu_main, menu)
        splitMenuItem = menu.findItem(R.id.action_split)
        splitMenuItem?.setOnMenuItemClickListener(splitListener)
        val searchManager = activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.setOnQueryTextListener(presenter.searchListener)
        searchView.setOnQueryTextFocusChangeListener(presenter.searchChangeFocus())
    }


    val splitListener
    get() = object : MenuItem.OnMenuItemClickListener {
        override fun onMenuItemClick(p0: MenuItem?): Boolean {
            when (p0?.itemId) {
                R.id.action_split -> {
                    splitMenuItem = p0
                    presenter.setAdapter(recyclerView)
                }

            }
            return true
        }
    }

    override fun startPhotoActivity(cameraIntent: Intent) {
        startActivityForResult(cameraIntent, CAMERA_RESULT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.processViewRes(ResultOfActivity(requestCode, resultCode, data))
    }

    override fun showNotif(toShow: Int) {
        Toast.makeText(context, getString(toShow), Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        presenter.updateList()
    }

    override fun showActionBar(title: Int) {
        presenter.actionMode = (activity as MainActivity).startSupportActionMode(presenter.onActionModeListener)
        presenter.actionMode?.title = getString(title)
    }

    override fun closeActionBar() {
        finishActionMode()
    }

    override fun startTaskActivity(activityClass: Class<*>?, itemId: Int, actResType: Int) {
        /*val intentChange = Intent(this, activityClass)
        intentChange.putExtra(ID, itemId)
        startActivityForResult(intentChange, actResType)*/
    }

    override fun createTaskActivity(createTaskIntent: Intent, taskType: Int) {
        startActivityForResult(createTaskIntent, taskType)
    }

    override fun createTaskActivity(taskType: Int, taskClass: Class<*>) {
        /*val textIntent = Intent(this, taskClass)
        startActivity(textIntent)*/
    }

    override fun setSplitIcon(iconResource: Int) {
        splitMenuItem?.setIcon(iconResource)
    }

    override fun getFragType(): FragType {
        return FragType.TASK_LIST_MAIN
    }
}
