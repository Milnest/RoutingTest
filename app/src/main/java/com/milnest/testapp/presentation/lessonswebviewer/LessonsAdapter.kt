package com.milnest.testapp.presentation.lessonswebviewer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.milnest.testapp.R
import com.milnest.testapp.entities.Lesson
import com.milnest.testapp.tasklist.presentation.main.IClickListener

class LessonsAdapter(val iClickListener: IClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var lessonsList: MutableList<Lesson> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LessonItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_lessons_lesson_item, parent, false))
    }

    override fun getItemCount(): Int {
        return lessonsList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val lessonHolder = holder as LessonItemHolder
        lessonHolder.lessonNameTextView.text = lessonsList[position].name
        lessonHolder.lessonDateTextView.text = lessonsList[position].date
    }

    fun getLessonLink(position: Int): String{
        return lessonsList[position].link
    }

    inner class LessonItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var lessonNameTextView : TextView = itemView.findViewById(R.id.lesson_name)
        internal var lessonDateTextView : TextView = itemView.findViewById(R.id.lesson_date)
        init {
            itemView.setOnClickListener { iClickListener.onItemClick(layoutPosition) }
        }
    }
}