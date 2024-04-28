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

class UpdateViewModel(private val repo: ItemsRepo): ViewModel() {
    private val _item: MutableLiveData<Item> = MutableLiveData()
    val item: LiveData<Item> = _item

    fun getItem(id: Int) { _item.value = repo.getItemById(id) }

    fun edit(id: Int, item: Item) = repo.updateItem(id, item)

    fun delete(id: Int) = repo.deleteItem(id)

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = checkNotNull(this[APPLICATION_KEY]) as GroceryListApp
                UpdateViewModel(app.repo)
            }
        }
    }
}