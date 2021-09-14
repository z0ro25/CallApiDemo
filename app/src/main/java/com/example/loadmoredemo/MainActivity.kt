package com.example.loadmoredemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.example.loadmoredemo.ApiModel.Content
import com.example.loadmoredemo.ApiModel.Uniform
import com.example.loadmoredemo.api.ApiExacutor
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val unfAdapter = UniformAdapter()
    private var listProdcut: ArrayList<Content> = ArrayList()
    val api = ApiExacutor()

    var isLoading = false
    var islastpage = false
    var CURRENPAGE = 1
    val TOTALPAGE = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RCV_product.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val itemDecorationCount = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        RCV_product.addItemDecoration(itemDecorationCount)
        RCV_product.adapter = unfAdapter
        loadProdcut()

        RCV_product.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visiblecount = recyclerView.layoutManager?.childCount ?: 0
                val totalitemscout = recyclerView.layoutManager?.itemCount ?: 0
                val firstvsbitems = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                if (isLoading || islastpage) {
                    return
                } else if (firstvsbitems >= 0 && (visiblecount + firstvsbitems) >= totalitemscout - 1) {
                    loadMore()

                }
            }
        })
        sw_refresh.setOnRefreshListener {
            listProdcut.removeAll(listProdcut)
            loadProdcut()
            sw_refresh.isRefreshing = false
        }
    }

    private fun loadMore() {
        Log.d("TAG", "loadMore: $CURRENPAGE")
        CURRENPAGE += 1
       loadProdcut()
        // check is last page
        if (CURRENPAGE == TOTALPAGE){
            islastpage = true
            isLoading = true
        }
    }


    private fun loadProdcut() {
        val call: Call<Uniform> = api.callapi.callApi()
        call.enqueue(object : Callback<Uniform> {
            override fun onResponse(call: Call<Uniform>, response: Response<Uniform>) {
                val uniform: Uniform = response.body() as Uniform
                val listContent: List<Content> = uniform.data.content
                listContent.forEach {
                    listProdcut.add(it)
                }
                unfAdapter.setData(listProdcut)
            }

            override fun onFailure(call: Call<Uniform>, t: Throwable) {
                Log.i("failed", "", t)
            }
        })
    }

}