package com.milnest.testapp.presentation.start

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.app.ActionBar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.arellomobile.mvp.presenter.InjectPresenter
import com.milnest.testapp.R
import com.milnest.testapp.others.utils.setUpBar
import com.milnest.testapp.presentation.contentprovider.ContentProviderActivity
import com.milnest.testapp.presentation.main.MainActivity
import com.milnest.testapp.router.BaseFragment
import com.milnest.testapp.router.FragType
import kotlinx.android.synthetic.main.fragment_start.*


class StartFragment : BaseFragment(), StartView {
    override fun showAngles(angles: String) {
        coords.text = angles
    }

    @InjectPresenter
    lateinit var presenter: StartPresenter

    lateinit var diagButton: Button

    var actBar: ActionBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        //presenter.setFragmentManager(activity!!.supportFragmentManager)
        super.onCreate(savedInstanceState)
    }

    override fun startContentProviderActivity() {
        startActivity(Intent(context, ContentProviderActivity::class.java))
    }

    override fun startContactActivity() {
        /*val pickIntent = Intent(Intent.ACTION_VIEW)*/
        val pickIntent = Intent(Intent.ACTION_PICK)
        pickIntent.type = "vnd.android.cursor.dir/contact"/*ContactsContract.Contacts.CONTENT_ITEM_TYPE*//*ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE*/
        //pickIntent.data = Uri.parse("content://contacts/people/")
        val chooser = Intent.createChooser(pickIntent, "TITLE")
        //startActivityForResult(pickIntent, 1)
        startActivityForResult(chooser, 1)
    }

    override fun getFragType(): FragType {
        return FragType.START
    }

    override fun onStart() {
        super.onStart()






        setUpActionBar()
        bindViews()
        presenter.setUpSensorManager()
    }

    override fun onResume() {
        super.onResume()
        presenter.startSensorManager()
    }

    override fun onPause() {
        super.onPause()
        presenter.stopSensorManager()
    }

    override fun setUpActionBar() {
        //Показыват стандартный аппбар(для перехода с аним-фрагмента)
        (activity as MainActivity?)?.findViewById<AppBarLayout>(R.id.start_appbar)?.visibility = View.VISIBLE
        setUpBar(activity, getString(R.string.start_title), true)
    }

    override fun showInfo(color: Int) {
        /*var res = ""
        for (value in valuesResult) {
            res = res + value.toString() + "///"
        }
        coords.text = res
        when(valuesResult[1]){
            90f ->{
                startFragment.setBackgroundColor(resources.getColor(R.color.colorBrown_200))
            }
        }*/
        //startFragment.setBackgroundColor(color)
        startFragmentRoot.setBackgroundColor(color)
    }

    private fun bindViews() {
        button_to_view_pager.setOnClickListener(presenter.onClickListener)
        button_to_diag.setOnClickListener(presenter.onClickListener)
        button_to_task_list.setOnClickListener(presenter.onClickListener)
        button_to_demo.setOnClickListener(presenter.onClickListener)
        button_to_content_provider.setOnClickListener(presenter.onClickListener)
        button_start_contacts_list.setOnClickListener(presenter.onClickListener)
        button_to_anim.setOnClickListener(presenter.onClickListener)
        button_to_webview.setOnClickListener(presenter.onClickListener)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_start, container, false)
        //actBar.setDisplayHomeAsUpEnabled(true)       
        return view
    }
}

