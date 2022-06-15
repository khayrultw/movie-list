package com.khayrultw.movielist

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater

class MainActivity : AppCompatActivity() {
    private lateinit var loader: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loader = Dialog(this, R.style.Loader)
    }

    fun showLoader() {
        val view = layoutInflater.inflate(R.layout.fullscreen_loader, null, false)
        loader.setContentView(view)
        loader.setCancelable(false)
        loader.show()
    }

    fun hideLoader() {
        loader.hide()
    }
}