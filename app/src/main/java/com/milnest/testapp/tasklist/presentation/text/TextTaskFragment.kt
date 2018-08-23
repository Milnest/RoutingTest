package com.milnest.testapp.tasklist.presentation.text

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.milnest.testapp.R
import com.milnest.testapp.others.utils.setUpBar
import com.milnest.testapp.router.BaseFragment
import com.milnest.testapp.router.FragType
//import kotlinx.android.synthetic.main.fragment_text_task.*

class TextTaskFragment : BaseFragment(), TextTaskView {
    @InjectPresenter
    lateinit var taskPresenter : TextTaskPresenter

    var title : EditText? = null
    var text : EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_text_task, container, false)
    }

    override fun onStart() {
        super.onStart()
        setUpActionBar()
        title = view?.findViewById<EditText>(R.id.taskTitle)
        text = view?.findViewById<EditText>(R.id.taskText)
        setHasOptionsMenu(true)
    }

    override fun setUpActionBar() {
        setUpBar(activity, getString(R.string.text_task_title), true)
    }

    override fun onResume() {
        super.onResume()
        taskPresenter.setStartText(arguments)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.task_list_menu_task_text, menu)
        menu?.findItem(R.id.action_task_text_save)?.setOnMenuItemClickListener(onOptionsClickListener)
        menu?.findItem(R.id.action_task_text_share)?.setOnMenuItemClickListener(onOptionsClickListener)
        menu?.findItem(R.id.action_task_text_translate)?.setOnMenuItemClickListener(onOptionsClickListener)
    }

    val onOptionsClickListener
        get() = object : MenuItem.OnMenuItemClickListener{
            /*val title = taskTitle.text.toString()
            val text = taskText.text.toString()*/
            override fun onMenuItemClick(item: MenuItem): Boolean {
                val taskTitle = title?.text.toString()
                val taskText = text?.text.toString()
                when (item.itemId) {
                    R.id.action_task_text_save -> {
                        taskPresenter.saveClicked(taskTitle, taskText)
                    }
                    R.id.action_task_text_share -> {
                        taskPresenter.shareClicked(taskTitle, taskText)
                    }
                    R.id.action_task_text_translate -> taskPresenter.translationClicked(taskTitle, taskText)
                }
                return true
            }
        }

    override fun startShareAct(shareIntent: Intent){
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share)))
    }

    override fun setText(title: String?, text: String?){
        if (title is String) this.title?.setText(title)
        if (text is String) this.text?.setText(text)
    }

    override fun showToast(toShow: Int) {
        Toast.makeText(context, getString(toShow),
                Toast.LENGTH_SHORT).show()
    }

    override fun getFragType(): FragType {
        return FragType.TASK_LIST_TEXT
    }

    companion object {
        fun newInstance(args: Bundle): TextTaskFragment {
            val fragment = TextTaskFragment()
            if(!args.isEmpty) fragment.arguments = args
            return fragment
        }
    }
}
