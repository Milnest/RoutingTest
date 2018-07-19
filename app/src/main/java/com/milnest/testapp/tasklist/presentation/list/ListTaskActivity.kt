//package com.milnest.testapp.tasklist.presentation.list
//
//import android.os.Bundle
//import android.support.v7.widget.LinearLayoutManager
//import android.view.Menu
//import android.view.MenuItem
//import com.arellomobile.mvp.MvpAppCompatActivity
//import com.arellomobile.mvp.MvpAppCompatFragment
//import com.arellomobile.mvp.presenter.InjectPresenter
//import com.milnest.testapp.R
//import kotlinx.android.synthetic.main.task_list_activity_list_task.*
//import kotlinx.android.synthetic.main.task_list_toolbar.*
//
//class ListTaskActivity : MvpAppCompatActivity()/*AppCompatActivity()*/, ListTaskView {
//    @InjectPresenter
//    lateinit var taskPresenter: ListTaskPresenter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.task_list_activity_list_task)
//        bindViews()
//    }
//
//    private fun bindViews() {
//        setSupportActionBar(toolbar)
//        initPresenter()
////        new_cb.setOnClickListener(taskPresenter.addNewCheckBox())
//    }
//
//    private fun initPresenter() {
//        taskPresenter = ListTaskPresenter()
//        taskPresenter.attachView(this)
//        recycler_view_cb.layoutManager = LinearLayoutManager(this)
//        taskPresenter.setAdapter(recycler_view_cb)
//        taskPresenter.setStartList(intent.extras)
//        /*recycler_view_cb.layoutManager = LinearLayoutManager(this)*/
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.task_list_menu_task_list, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        taskPresenter.saveClicked()
//        return true
//    }
//}
