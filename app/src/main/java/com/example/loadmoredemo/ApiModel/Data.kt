package com.example.loadmoredemo.ApiModel

data class Data(
    val content: List<Content>,
    val page: Int,
    val size: Int,
    val total_elements: Int
)