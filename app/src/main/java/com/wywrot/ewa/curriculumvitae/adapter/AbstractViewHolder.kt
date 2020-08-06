@file:Suppress("NOTHING_TO_INLINE")

package com.wywrot.ewa.curriculumvitae.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView

inline fun RecyclerView.ViewHolder.getContext(): Context = itemView.context

inline fun TextView.setText(@StringRes stringRes: Int, vararg args: Any) {
    text = context.getString(stringRes, *args)
}

abstract class AbstractViewHolder : RecyclerView.ViewHolder {
    constructor(itemView: View) : super(itemView)
    constructor(parent: ViewGroup, res: Int) :
            super(LayoutInflater.from(parent.context).inflate(res, parent, false))

    abstract fun bind()
}