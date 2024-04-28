package com.example.grocerylistapp

import android.app.Application
import com.example.grocerylistapp.data.repository.ItemsRepo

class GroceryListApp: Application() {
    lateinit var repo: ItemsRepo

    override fun onCreate() {
        super.onCreate()
        repo = ItemsRepo()
    }
}