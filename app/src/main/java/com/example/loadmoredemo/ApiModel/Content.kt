package com.example.loadmoredemo.ApiModel

import java.io.Serializable

data class Content(
    val attributes: String,
    val auditing_status: Int,
    val category: Category,
    val category_id: Int,
    val code: String,
    val create_date: Long,
    val description: String,
    val discount: Double,
    val id: Int,
    val image: String,
    val images: List<Image>,
    val name: String,
    val popular: Int,
    val price: Double,
    val quantity_sold: Int,
    val retail_price: Double,
    val seller_info: String,
    val selling_price: Double,
    val supplier: String,
    val supplier_id: Int,
    val total: Int,
    val update_date: Long,
    var isfavorite : Boolean
) : Serializable