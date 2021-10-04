package com.example.loadmoredemo.ApiModel


import java.io.Serializable

data class Category(
    val id: Int,
    val name: String?,
    val type: Int,
    ) :Serializable