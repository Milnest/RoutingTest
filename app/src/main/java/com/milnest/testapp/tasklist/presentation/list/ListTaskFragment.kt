package com.milnest.testapp.tasklist.presentation.list

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.milnest.testapp.App
import com.milnest.testapp.R
import com.milnest.testapp.others.components.DividerDecoration
import com.milnest.testapp.router.BaseFragment
import com.milnest.testapp.router.FragType
import com.milnest.testapp.tasklist.ID
import kotlinx.android.synthetic.main.fragment_list_task.*

class ListTaskFragment : BaseFragment(), ListTaskView {

    @InjectPresenter
    lateinit var taskPresenter: ListTaskPresenter

    private fun initPresenter() {
        recycler_view_cb.layoutManager = LinearLayoutManager(context)
        recycler_view_cb.addItemDecoration(DividerDecoration(App.context, R.drawable.divider, 0))
        taskPresenter.setAdapter(recycler_view_cb)
        taskPresenter.setStartList(arguments)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_task, container, false)
    }

    override fun onStart() {
        super.onStart()
        initPresenter()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.task_list_menu_task_list, menu)
        menu?.findItem(R.id.action_task_list_save)?.setOnMenuItemClickListener(saveClickListener)
    }

    val saveClickListener
    get() = object : MenuItem.OnMenuItemClickListener {
        override fun onMenuItemClick(p0: MenuItem?): Boolean {
            when (p0?.itemId) {
                R.id.action_task_list_save -> {
                    taskPresenter.saveClicked()
                }

            }
            return true
        }
    }

    override fun getFragType(): FragType {
        return FragType.TASK_LIST_LIST
    }

    companion object {
        fun newInstance(args: Bundle): ListTaskFragment {
            val fragment = ListTaskFragment()
            if (!args.isEmpty){
                fragment.arguments = args
            }
            return fragment
        }
    }

}
