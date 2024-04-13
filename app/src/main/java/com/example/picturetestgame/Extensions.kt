package com.example.picturetestgame

import android.net.Uri
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

fun View.gone() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun ImageView.setImage(name: Int) {
    Glide.with(this).load(name).into(this)
}
