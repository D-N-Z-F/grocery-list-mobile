package com.example.grocerylistapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.grocerylistapp.R
import com.example.grocerylistapp.core.Constants
import com.example.grocerylistapp.data.model.Item
import com.example.grocerylistapp.databinding.ActivityAddItemBinding
import com.example.grocerylistapp.ui.viewmodels.AddViewModel
import com.google.android.material.snackbar.Snackbar

class AddItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddItemBinding
    private val viewModel: AddViewModel by viewModels { AddViewModel.Factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
        binding.btnAdd.setOnClickListener { onAdd() }
    }

    private fun onAdd() {
        binding.run {
            val name = etName.text.toString()
            val category = spCategory.selectedItem.toString()
            val quantity = npQuantity.value

            if(name.isEmpty()) {
                Snackbar.make(
                    binding.main,
                    "Please enter an appropriate item name.",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                viewModel.add(
                    Item(name = name, category = category, quantity = quantity)
                )
                Toast.makeText(
                    this@AddItemActivity,
                    "Item added successfully.",
                    Toast.LENGTH_SHORT
                ).show()
                setResult(
                    Activity.RESULT_OK,
                    Intent().apply {
                    putExtra(Constants.REFRESH, true)
                })
                finish()
            }
        }
    }

    private fun setup() {
        binding.run {
            npQuantity.minValue = 1
            npQuantity.maxValue = 50
        }
    }
}