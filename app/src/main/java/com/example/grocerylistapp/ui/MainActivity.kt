package com.example.grocerylistapp.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grocerylistapp.core.Constants
import com.example.grocerylistapp.data.model.Item
import com.example.grocerylistapp.databinding.ActivityMainBinding
import com.example.grocerylistapp.databinding.LayoutAlertViewBinding
import com.example.grocerylistapp.ui.adapter.ItemsAdapter
import com.example.grocerylistapp.ui.fragments.BottomSheetFragment
import com.example.grocerylistapp.ui.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ItemsAdapter
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAdapter()
        viewModel.items.observe(this) { adapter.setItems(it) }
        resultLauncher = setupLauncher()
        binding.fabAdd.setOnClickListener {
            resultLauncher.launch(
                Intent(this, AddItemActivity::class.java)
            )
        }
        binding.spCategory.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val category = binding.spCategory.selectedItem.toString()
                viewModel.getItemsByCategory(category)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun launchUpdateActivity(id: Int) {
        resultLauncher.launch(Intent(
            this,
            UpdateItemActivity::class.java
        ).apply { putExtra(Constants.TASK_ID, id) })
    }

    private fun showDeleteDialog(id: Int) {
        val deleteDialog = AlertDialog.Builder(this@MainActivity).create()
        val alertView = LayoutAlertViewBinding.inflate(layoutInflater)

        deleteDialog.setView(alertView.root)
        alertView.run {
            btnPositive.setOnClickListener {
                viewModel.delete(id)
                binding.spCategory.setSelection(0)
                deleteDialog.dismiss()
                Toast.makeText(
                    this@MainActivity,
                    "Deleted Successfully.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            btnNegative.setOnClickListener { deleteDialog.dismiss() }
        }
        deleteDialog.show()
    }

    private fun setupAdapter() {
        adapter = ItemsAdapter(emptyList())
        adapter.listener = object: ItemsAdapter.Listener {
            override fun onLongClick(item: Item) {
                BottomSheetFragment.getInstance(
                    onEdit = { launchUpdateActivity(item.id!!) },
                    onDelete = { showDeleteDialog(item.id!!) }
                ).show(
                    supportFragmentManager,
                    "bottom_sheet"
                )
            }
        }
        binding.rvItems.adapter = adapter
        binding.rvItems.layoutManager = LinearLayoutManager(this)
    }

    private fun setupLauncher(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if(result.resultCode == Activity.RESULT_OK) {
                result.data?.let { data ->
                    val refresh =
                        data.getBooleanExtra(Constants.REFRESH, false)
                    if(refresh) {
                        binding.spCategory.setSelection(0)
                        viewModel.getAll()
                    }
                }
            } else if(result.resultCode == Activity.RESULT_CANCELED) {
                result.data?.let { data ->
                    val invalid =
                        data.getBooleanExtra(Constants.INVALID, false)
                    if(invalid) {
                        Snackbar.make(
                            binding.main,
                            "Unexpected Error Occurred.",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}