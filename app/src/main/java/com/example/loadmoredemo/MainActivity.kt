package com.example.loadmoredemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.loadmoredemo.ApiModel.Content
import com.example.loadmoredemo.ApiModel.Uniform
import com.example.loadmoredemo.api.ApiExacutor
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity(), UniformAdapter.iUnfadapter {
    private val unfAdapter = UniformAdapter(this)
    private var listProdcut: ArrayList<Content> = ArrayList()
    private val sort = "update_date:asc,quantity_sold:desc"
    private val api = ApiExacutor()
    private var isLoading: Boolean = true
    private var isLastpage: Boolean = true
    private var pageSize = 10
    private var currenPage = 1

    //todo loadmore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RCV_product.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val itemDecorationCount = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        RCV_product.addItemDecoration(itemDecorationCount)
        RCV_product.adapter = unfAdapter
        loadProdcut(0, pageSize, sort)

        RCV_product.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleCount = recyclerView.layoutManager?.childCount ?: 0
                val totalItemsCount = recyclerView.layoutManager?.itemCount ?: 0
                val firstVsbItems = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                Log.d("======= ", "=======================")
                Log.d("visiblecount ", visibleCount.toString())
                Log.d("totalitemscout", totalItemsCount.toString())
                Log.d("firstvsbitems", firstVsbItems.toString())
                Log.d("isLoading", isLoading.toString())
                Log.d("islastpage", isLastpage.toString())

                if (isLoading == false || isLastpage == false) {
                    return
                } else
                    if (firstVsbItems >= 0 && (visibleCount + firstVsbItems) >= totalItemsCount - 1) {
                        loadMore()
                    }

            }
        })
        sw_refresh.setOnRefreshListener {
            Log.d("TAG", "refresh")
            currenPage = 0
            listProdcut.removeAll(listProdcut)
            loadProdcut(0,pageSize,sort)
            unfAdapter.notifyDataSetChanged()
            sw_refresh.isRefreshing = false
            isLastpage = true

        }
    }

    private fun loadMore() {
        loadProdcut(currenPage, pageSize, sort)
        Log.d("TAG", "loadMore: $currenPage")
        currenPage += 1

        Timer().schedule(1000){
            if (listProdcut.size != (currenPage * pageSize)) {
                isLastpage = false
            }
            else{
                isLoading = true
                isLastpage = true
            }
        }

    }
    private fun loadProdcut(page: Int, size: Int, sort: String) {
        val call: Call<Uniform> = api.callapi.callApi(page, size, sort)
        call.enqueue(object : Callback<Uniform> {
            override fun onResponse(call: Call<Uniform>, response: Response<Uniform>) {
                val uniform: Uniform = response.body() as Uniform
                val listContent: List<Content> = uniform.data.content
                val sucCess : Boolean = uniform.success
                if (listContent.isEmpty() || sucCess == false){
                    Log.i("List content","is emty")

                }else{
                    listContent.forEach {
                        listProdcut.add(it)
                    }
                    unfAdapter.notifyDataSetChanged()
                    unfAdapter.setData(listProdcut)
                }

            }
            override fun onFailure(call: Call<Uniform>, t: Throwable) {
                Log.i("failed", "", t)
            }
        })
    }

    override fun onItemClick(position: Int) {
        Log.i("Items :", "number $position")
    }

}