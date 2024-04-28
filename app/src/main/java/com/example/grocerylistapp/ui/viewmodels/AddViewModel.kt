package com.example.grocerylistapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.grocerylistapp.GroceryListApp
import com.example.grocerylistapp.data.model.Item
import com.example.grocerylistapp.data.repository.ItemsRepo

class AddViewModel(private val repo: ItemsRepo): ViewModel() {
    fun add(item: Item) = repo.addItem(item)

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = checkNotNull(this[APPLICATION_KEY]) as GroceryListApp
                AddViewModel(app.repo)
            }
        }
    }
}