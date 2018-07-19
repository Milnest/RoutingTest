//package com.milnest.testapp.tasklist.presentation.text
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.Menu
//import android.view.MenuItem
//import android.widget.Toast
//import com.arellomobile.mvp.MvpAppCompatActivity
//import com.arellomobile.mvp.presenter.InjectPresenter
//import com.milnest.testapp.R
//import kotlinx.android.synthetic.main.task_list_activity_text_task.*
//import kotlinx.android.synthetic.main.task_list_toolbar.*
//
//class TextTaskTaskActivity : MvpAppCompatActivity(), /*AppCompatActivity(),*/ TextTaskView {
//    @InjectPresenter
//    lateinit var taskPresenter : TextTaskPresenter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.task_list_activity_text_task)
//        bindViews()
//    }
//
//    private fun bindViews() {
//        /*taskPresenter = TextTaskPresenter()
//        taskPresenter.attachView(this)*/
//        setSupportActionBar(toolbar)
//    }
//
//    override fun onResume() {
//        super.onResume()
//        taskPresenter.setStartText(intent.extras)
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.task_list_menu_task_text, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val title = taskTitle.text.toString()
//        val text = taskText.text.toString()
//        when (item.itemId) {
//            R.id.action_task_text_save -> {
//                taskPresenter.saveClicked(title, text)
//            }
//            R.id.action_task_text_share -> {
//                taskPresenter.shareClicked(title, text)
//            }
//            R.id.action_task_text_translate -> taskPresenter.translationClicked(title, text)
//        }
//        return true
//    }
//
//    override fun startShareAct(shareIntent: Intent){
//        startActivity(Intent.createChooser(shareIntent, getString(R.string.share)))
//    }
//
//    override fun setText(title: String?, text: String?){
//        if (title is String) taskTitle.setText(title)
//        if (text is String) taskText.setText(text)
//    }
//
//    override fun showToast(toShow: Int) {
//        Toast.makeText(this, getString(toShow),
//                Toast.LENGTH_SHORT).show()
//    }
//
//}
