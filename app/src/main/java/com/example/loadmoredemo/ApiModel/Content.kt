package com.example.loadmoredemo.ApiModel

data class Content(
    val attributes: Any ? = null,
    val auditing_status: Int? = null,
    val category: Category? = null,
    val category_id: Int? = null,
    val code: String? = null,
    val create_date: Long? = null,
    val description: String? = null,
    val discount: Double? = null,
    val id: Int? = null,
    val image: String? = null,
    val images: List<Image>? = null,
    val name: String? = null,
    val popular: Int? = null,
    val price: Double? = null,
    val quantity_sold: Int? = null,
    val retail_price: Double? = null,
    val seller_info: Any? = null,
    val selling_price: Double? = null,
    val supplier: Any? = null,
    val supplier_id: Int? = null,
    val total: Int? = null,
    val update_date: Long ? = null
)