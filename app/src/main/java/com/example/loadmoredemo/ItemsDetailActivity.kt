package com.example.loadmoredemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.room.Room
import com.example.loadmoredemo.ApiModel.Content
import com.example.loadmoredemo.Sharedpreferences.SharedPreferencesFavorite
import com.example.loadmoredemo.database.ContentDataBase
import com.example.loadmoredemo.database.ContentEntities
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_detail_layout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ItemsDetailActivity : AppCompatActivity() {
    private var clickedItems: Content? = null
    private val BASE_URL = "https://youngkids-dev.acaziasoft.com/statics/"
    private var isFavorite = false
    private val sharedFAV = SharedPreferencesFavorite(this)
    private val SAVE_KEY = "SAVE_KEY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_detail_layout)

        getParam()

        btn_back.setOnClickListener {

            val intent1 = Intent(this, MainActivity::class.java)
            startActivity(intent1)
        }

        img_views.setOnClickListener {
            val intent2 = Intent(this, ImageViewActivity::class.java)
            intent2.putExtra("listsss", clickedItems)
            val options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, img_views, "boder")
                    .toBundle()
            startActivity(intent2, options)
        }
        btn_favorite.setOnClickListener {

            if (isFavorite == false) {
                btn_favorite.setImageResource(R.drawable.baseline_favorite_red_500_24dp)
                isFavorite = true
                clickedItems?.isfavorite = true
                saveToSharedPreferences()
                saveToDatabase()

            } else {
                btn_favorite.setImageResource(R.drawable.baseline_favorite_border_red_500_24dp)
                isFavorite = false
                clickedItems?.isfavorite = false

            }
        }
    }


    private fun getParam() {
        val inten = intent
        clickedItems = inten.getSerializableExtra("litsproduct") as Content
        tv_name_detail.text = clickedItems!!.name
        colap_apbar.title = clickedItems!!.name
        tv_description_detail.text = clickedItems!!.description
        tv_Total_detail.text = clickedItems!!.total.toString()
        tv_price_detail.text = clickedItems!!.selling_price.toString()
        Picasso.get().load(BASE_URL + clickedItems!!.image).into(img_views)
        Picasso.get().load(BASE_URL + clickedItems!!.image).into(image_back)
    }

    private fun saveToSharedPreferences() {

        val intet = intent
        val listF = intet.getSerializableExtra("litsFAV") as HashMap<String, Content>
        Log.i("FAV", listF.toString())
        if (listF.isEmpty()) {
            listF.put(clickedItems!!.id.toString(), clickedItems!!)
        } else if (listF.containsValue(clickedItems!!) == false) {
            listF.put(clickedItems!!.id.toString(), clickedItems!!)
        }
        sharedFAV.saveListFAV(SAVE_KEY, listF)

        Log.i("listFAV", listF.size.toString())
        Log.i("listFAVKey", listF.keys.toString())
    }

    private fun saveToDatabase() {
        val id = clickedItems!!.id
        val imgURL = clickedItems!!.image.trim()
        val name = clickedItems!!.name.trim()
        val price = clickedItems!!.price
        val isFavorite = clickedItems!!.isfavorite
        val newEntities = ContentEntities(id, imgURL, name, price, isFavorite)

        GlobalScope.launch(Dispatchers.IO) {
            val nameInDB =
                Room.databaseBuilder(applicationContext,
                    ContentDataBase::class.java,
                    "database-name")
                    .fallbackToDestructiveMigration()
                    .build()
                    .userDao()
                    .getName()

            if (nameInDB.isEmpty()) {
                launch {
                    Room.databaseBuilder(
                        applicationContext,
                        ContentDataBase::class.java, "database-name"
                    ).fallbackToDestructiveMigration().build().userDao().inSertItems(newEntities)
                }
            } else {
                if (nameInDB.contains(id) == false) {
                    launch {
                        Room.databaseBuilder(
                            applicationContext,
                            ContentDataBase::class.java, "database-name"
                        ).fallbackToDestructiveMigration().build().userDao()
                            .inSertItems(newEntities)
                    }
                }
            }
        }
    }
}