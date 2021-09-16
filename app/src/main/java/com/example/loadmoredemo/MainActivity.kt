package com.example.loadmoredemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.loadmoredemo.ApiModel.Content
import com.example.loadmoredemo.ApiModel.Uniform
import com.example.loadmoredemo.api.ApiExacutor
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val unfAdapter = UniformAdapter()
    private var listProdcut: ArrayList<Content> = ArrayList()
    val api = ApiExacutor()

    var isLoading : Boolean = true
    var isLastpage : Boolean = true
    var CURRENPAGE = 1
    val TOTALPAGE = 5
    //todo loadmore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RCV_product.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val itemDecorationCount = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        RCV_product.addItemDecoration(itemDecorationCount)
        RCV_product.adapter = unfAdapter
        loadProdcut(0,10,"update_date:asc,quantity_sold:desc")

        RCV_product.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleCount = recyclerView.layoutManager?.childCount ?: 0
                val totalItemsCount = recyclerView.layoutManager?.itemCount ?: 0
                val firstVsbItems = (recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()

                Log.d("visiblecount",visibleCount.toString() )
                Log.d("totalitemscout", totalItemsCount.toString())
                Log.d("firstvsbitems", firstVsbItems.toString())
                Log.d("isLoading", isLoading.toString())
                Log.d("islastpage", isLastpage.toString())

                if (isLoading == false  || isLastpage == false ) {
                    return
                }else
                if (firstVsbItems >= 0 && (visibleCount + firstVsbItems) >= totalItemsCount - 1) {
                    loadMore()
                }

            }
        })
        sw_refresh.setOnRefreshListener {
            Log.d("TAG", "refresh")
            listProdcut.removeAll(listProdcut)
            loadProdcut(0,10,"update_date:asc,quantity_sold:desc")
            unfAdapter.notifyDataSetChanged()
            sw_refresh.isRefreshing = false
            isLastpage = true
        }
    }

    private fun loadMore() {
        Log.d("TAG", "loadMore: $CURRENPAGE")
        CURRENPAGE += 1
        var counter = 10
        val call: Call<Uniform> = api.callapi.callApi(0,100,"update_date:asc,quantity_sold:desc")
        call.enqueue(object : Callback<Uniform> {
            override fun onResponse(call: Call<Uniform>, response: Response<Uniform>) {
                val uniform: Uniform = response.body() as Uniform
                val listContent: List<Content> = uniform.data.content.subList(counter,counter+counter)
                listContent.forEach {
                    listProdcut.add(it)
                }
                unfAdapter.setData(listProdcut)
                counter += counter
            }
            override fun onFailure(call: Call<Uniform>, t: Throwable) {
                Log.i("failed", "", t)
            }
        })
        isLoading = true
        isLastpage = true
        if (CURRENPAGE == TOTALPAGE){
            isLastpage = false
        }

    }


    private fun loadProdcut(page : Int,size : Int , sort:String)  {
        val call: Call<Uniform> = api.callapi.callApi(page,size,sort)
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