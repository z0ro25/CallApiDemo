package com.example.loadmoredemo.fragment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loadmoredemo.ApiModel.Content
import com.example.loadmoredemo.ApiModel.Uniform
import com.example.loadmoredemo.ItemsDetailActivity
import com.example.loadmoredemo.R
import com.example.loadmoredemo.Sharedpreferences.SharedPreferencesFavorite
import com.example.loadmoredemo.adapter.UniformAdapter
import com.example.loadmoredemo.api.ApiExacutor
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_list_product.*
import kotlinx.android.synthetic.main.item_detail_layout.*
import kotlinx.android.synthetic.main.product_item.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class ListProductFragment : Fragment() , UniformAdapter.iUnfadapter{
    private val unfAdapter = UniformAdapter(this)
    private var listProdcut: ArrayList<Content> = ArrayList()
    private var listFAV: HashMap<String,Content> = hashMapOf()
    private val sort = "update_date:asc,quantity_sold:desc"
    private val api = ApiExacutor()
    private var isLoading: Boolean = true
    private var canLoadMore: Boolean = true
    private var pageSize = 10
    private var currenPage = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_list_product, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RCV_product.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val itemDecorationCount = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        RCV_product.addItemDecoration(itemDecorationCount)
        RCV_product.adapter = unfAdapter
        loadProdcut(currenPage, pageSize, sort)
        updateListFAV()

        RCV_product.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleCount = recyclerView.layoutManager?.childCount ?: 0
                val totalItemsCount = recyclerView.layoutManager?.itemCount ?: 0
                val firstVsbItems =
                    (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                if (isLoading == true || canLoadMore == false) {
                    return
                } else
                    if (firstVsbItems >= 0 && (visibleCount + firstVsbItems) >= totalItemsCount - 1) {
                        loadMore()

                    }

            }
        })
        sw_refresh.setOnRefreshListener {
            currenPage = 0
            listProdcut.removeAll(listProdcut)
            loadProdcut(0, pageSize, sort)
            unfAdapter.notifyDataSetChanged()
            sw_refresh.isRefreshing = false
            canLoadMore = true

        }
    }

    private fun updateListFAV() {
        val favShaed = SharedPreferencesFavorite(requireActivity().application)
        val gson = Gson()
        val getLFAV = favShaed.getListFAV("SAVE_KEY"," ")
        val list :HashMap<String,Content> = gson.fromJson(getLFAV,object : TypeToken<HashMap<String,Content>> () {}.type)
        list.forEach{
            listFAV.put(it.key,it.value)
        }
        Log.i("updatelist",listFAV.size.toString())
    }


    private fun loadMore() {
        currenPage += 1
        loadProdcut(currenPage, pageSize, sort)
    }

    private fun loadProdcut(page: Int, size: Int, sort: String) {
        isLoading = true
        val call: Call<Uniform> = api.callapi.callApi(page, size, sort)
        call.enqueue(object : Callback<Uniform> {
            override fun onResponse(call: Call<Uniform>, response: Response<Uniform>) {
                val uniform: Uniform = response.body() as Uniform
                val listContent: List<Content> = uniform.data.content!!
                val sucCess: Boolean = uniform.success
                if (sucCess) {
                    canLoadMore = listContent.size == size
                }
                if (listContent.isEmpty() || sucCess == false) {
                    Log.i("List content", "is emty")

                } else {
                    listContent.forEach {
                        listProdcut.add(it)
                    }
                    unfAdapter.notifyDataSetChanged()
                    unfAdapter.setData(listProdcut)
                }
                isLoading = false
            }

            override fun onFailure(call: Call<Uniform>, t: Throwable) {
                isLoading = false
            }
        })
    }


    override fun onItemClick(position: Int) {
        val inten = Intent(requireActivity().application,ItemsDetailActivity::class.java)
        inten.putExtra("litsproduct",listProdcut.get(position))
        inten.putExtra("litsFAV",listFAV)
        startActivity(inten)
    }
}