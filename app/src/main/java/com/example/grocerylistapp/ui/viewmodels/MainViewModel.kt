package com.example.grocerylistapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.grocerylistapp.GroceryListApp
import com.example.grocerylistapp.data.model.Item
import com.example.grocerylistapp.data.repository.ItemsRepo

class MainViewModel(private val repo: ItemsRepo): ViewModel() {
    private val _items: MutableLiveData<List<Item>> = MutableLiveData()
    val items: LiveData<List<Item>> = _items
    val item: MutableLiveData<Item> = MutableLiveData()

    init { getAll() }

    fun getAll() { _items.value = repo.getAll() }

    fun getItemsByCategory(category: String) {
        getAll()
        if(category != "Unsorted") {
            _items.value = _items.value?.filter { it.category == category }
        }
    }

    fun delete(id: Int) { repo.deleteItem(id); _items.value = repo.getAll() }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = checkNotNull(this[APPLICATION_KEY]) as GroceryListApp
                MainViewModel(app.repo)
            }
        }
    }
}