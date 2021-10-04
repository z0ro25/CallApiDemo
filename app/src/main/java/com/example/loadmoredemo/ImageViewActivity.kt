package com.example.loadmoredemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.Menu
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.loadmoredemo.ApiModel.Content
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.img_layout.*
import kotlinx.android.synthetic.main.item_detail_layout.*

class ImageViewActivity : AppCompatActivity(), View.OnTouchListener {
    private var listimage : ArrayList<Content> = ArrayList()
    private val BASE_URL = "https://youngkids-dev.acaziasoft.com/statics/"
    private var poss = 0
    private var x  = 0
    private var y  = 0
    private var dx = 0
    private var dy = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.img_layout)
        getimage()
        image_View.setOnTouchListener(this)
    }

    private fun getimage() {
        val inten = intent
        listimage = inten.getSerializableExtra("listsss") as ArrayList<Content>
        poss = inten.getIntExtra("position",0)
        Picasso.get()
            .load(BASE_URL + listimage.get(poss).image)
            .into(image_View)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN){
            x = event.getX().toInt()
            y = event.getY().toInt()
        }
        if (event?.action == MotionEvent.ACTION_MOVE){
            dx = event.getX().toInt() - x
            dy = event.getY().toInt() - y
            image_View.x = image_View.x + dx
            image_View.y = image_View.y + dy
            x = event.getX().toInt()
            y = event.getY().toInt()
            Log.i("X",x.toString())
            Log.i("y",y.toString())

        }
     return true
    }

    private fun opentDetail() {
        val inte = Intent(this,MainActivity::class.java)
        startActivity(inte)
    }


}