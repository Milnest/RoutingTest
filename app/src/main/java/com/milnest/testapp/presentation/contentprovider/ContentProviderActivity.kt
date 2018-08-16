package com.milnest.testapp.presentation.contentprovider

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jackandphantom.blurimage.BlurImage
import com.milnest.testapp.App
import com.milnest.testapp.R
import com.milnest.testapp.customview.ContactPhotoBackgroundTransformation
import com.milnest.testapp.customview.ContactPhotoTransformation
import com.milnest.testapp.customview.ContactPlaceholderBackground
import com.milnest.testapp.customview.ContactPlaceholderTransformation
import com.milnest.testapp.entities.ContactLongInfo
import com.milnest.testapp.entities.Info
import com.milnest.testapp.others.components.DividerDecoration
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_content_provider.*


class ContentProviderActivity : ContentProviderView, MvpAppCompatActivity() {

    @InjectPresenter
    lateinit var presenter: ContentProviderPresenter

    private lateinit var bar: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(this.javaClass.simpleName, "***** fun onCreate")
        setContentView(R.layout.activity_content_provider)
        bindViews()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (drawer_layout.isDrawerOpen(nav_view_left) || drawer_layout.isDrawerOpen(nav_view_right)) {
            drawer_layout.closeDrawers()
        }
        else {
            if (bar.onOptionsItemSelected(item)) return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun bindViews() {
        setUpDrawerBar()
        button_accept.setOnClickListener(presenter.acceptListener)
        button_show_events.setOnClickListener(presenter.showButtonListener)
        button_show_contacts.setOnClickListener(presenter.showButtonListener)
    }

    fun setUpDrawerBar(){
        bar = ActionBarDrawerToggle(this, drawer_layout, R.string.drawer_open, R.string.drawer_close)
        drawer_layout.addDrawerListener(bar)
        bar.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setLockContactsDrawer(true)
    }

    override fun onStart() {
        super.onStart()
        Log.d(this.javaClass.simpleName, "***** fun onStart")
        fillRecycler()
        presenter.onStart()
    }

    override fun onResume() {
        super.onResume()
        Log.d(this.javaClass.simpleName, "***** fun onResume")
    }

    fun fillRecycler(){
        recyclerViewContactsShort.adapter = presenter.getContactsAdapter()
        recyclerViewContactsShort.addItemDecoration(DividerDecoration(App.context, R.drawable.divider, 0))
        recyclerViewContactsShort.layoutManager = LinearLayoutManager(App.context)
        //TODO: ресайклер в корневую вью
        recyclerViewEventsShort.adapter = presenter.getMyEventsAdapter()
        recyclerViewEventsShort.addItemDecoration(DividerDecoration(App.context, R.drawable.divider, 0))
        recyclerViewEventsShort.layoutManager = LinearLayoutManager(App.context)
    }
    override fun showInfo(info: ContactLongInfo) {
        //TODO: в презентер, как setUpImages
        /***************/
        if (info.photo.type == Info.TYPE_CONTACT_PHOTO) {

            Picasso.get().load(Uri.parse(info.photo.content)).transform(ContactPhotoTransformation(0))/*.fit().centerInside()*/.into(contactInfoPhotoImg)
            Observable.just(Uri.parse(info.photo.content))
                    .subscribeOn(Schedulers.io())
                    .map { photoUri ->
                        Picasso.get()
                                .load(photoUri)
                                .transform(ContactPhotoBackgroundTransformation())
                                .get()
                    }
                    .map { bitmap -> BlurImage.with(App.context).load(bitmap).intensity(5f).Async(false).imageBlur }
                    .map { bitmap -> BitmapDrawable(App.context.resources, bitmap) }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { drawable -> photoCenter.setImageDrawable(drawable)/*contactInfoPhotoImg.background = drawable*/ }
        }
        else{
            Picasso.get().load(R.drawable.blank)/*.fit().centerInside()*/.transform(ContactPlaceholderTransformation(0, info.id, info.photo.content, info.color)).into(contactInfoPhotoImg)
            Observable.just(R.drawable.blank)
                    .subscribeOn(Schedulers.io())
                    .map { photoBlank ->
                        Picasso.get()
                                .load(photoBlank)
                                .transform(ContactPlaceholderBackground(0, App.context.resources.getColor(R.color.yellow_a400)))
                                .get()
                    }
                    .map { bitmap -> BitmapDrawable(App.context.resources, bitmap) }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { drawable -> photoBack.background = drawable }
        }
        /***************/
        val adapter = InfoAdapter(presenter.phoneListener)
        adapter.infoList = info.info
        recyclerViewContactInfo.adapter = adapter
        recyclerViewContactInfo.layoutManager = LinearLayoutManager(App.context)
        drawer_layout.closeDrawers()
        if(intent.action == Intent.ACTION_PICK){
            button_accept.visibility = View.VISIBLE
        }
    }

    override fun interactProgressBar(show: Boolean) {
        if (show){
            progressBar.visibility = ProgressBar.VISIBLE
        }
        else{
            progressBar.visibility = ProgressBar.GONE
        }
    }

    override fun showEvents() {
        recyclerViewEventsShort.visibility = View.VISIBLE
        drawer_layout.closeDrawer(nav_view_left)
        setLockContactsDrawer(true)
    }

    fun setLockContactsDrawer(isLock: Boolean){
        if (isLock)
            drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, nav_view_right)
        else
            drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, nav_view_right)
    }

    override fun hideEvents() {
        recyclerViewEventsShort.visibility = View.GONE
    }

    override fun showContacts() {
        contactsLayout.visibility = View.VISIBLE
        drawer_layout.closeDrawer(nav_view_left)
        drawer_layout.openDrawer(nav_view_right)
        setLockContactsDrawer(false)
    }

    override fun hideContacts() {
        contactsLayout.visibility = View.GONE
    }

    override fun onPause() {
        super.onPause()
        Log.d(this.javaClass.simpleName, "***** fun onPause")
    }

    override fun onStop() {
        presenter.onStop()
        super.onStop()
        Log.d(this.javaClass.simpleName, "***** fun onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(this.javaClass.simpleName, "***** fun onDestroy")
    }

}
