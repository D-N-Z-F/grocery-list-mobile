package com.example.grocerylistapp.data.model

data class Item(
    val id: Int? = null,
    val name: String,
    val category: String,
    val quantity: Int,
    var checkedOff: Boolean = false
)
