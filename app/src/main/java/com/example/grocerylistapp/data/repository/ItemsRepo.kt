package com.example.grocerylistapp.data.repository

import com.example.grocerylistapp.data.model.Item

class ItemsRepo {
    private var count = 0
    private val items = mutableMapOf(
        0 to Item(
            id = count,
            name = "Chicken",
            category = "Food",
            quantity = 1
        )
    )

    fun getAll(): List<Item> = items.values.toList()

    fun getItemById(id: Int): Item? = items[id]

    fun addItem(item: Item) { items[++count] = item.copy(id = count) }

    fun deleteItem(id: Int) = items.remove(id)

    fun updateItem(id: Int, item: Item) { items[id] = item.copy(id = id) }
}