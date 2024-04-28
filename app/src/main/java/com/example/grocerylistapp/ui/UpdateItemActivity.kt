package com.example.grocerylistapp.ui

import android.app.Activity
import android.app.AlertDialog
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
import com.example.grocerylistapp.databinding.ActivityUpdateItemBinding
import com.example.grocerylistapp.databinding.LayoutAlertViewBinding
import com.example.grocerylistapp.ui.viewmodels.UpdateViewModel
import com.google.android.material.snackbar.Snackbar

class UpdateItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateItemBinding
    private val viewModel: UpdateViewModel by viewModels { UpdateViewModel.Factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(Constants.TASK_ID, -1)
        if(id == -1) {
            val intent = Intent().apply { putExtra(Constants.INVALID, true) }

            setResult(Activity.RESULT_CANCELED, intent); finish()
        }
        else { setupView(id) }
    }

    private fun showDeleteDialog(id: Int) {
        binding.run {
            val deleteDialog = AlertDialog.Builder(this@UpdateItemActivity).create()
            val alertView = LayoutAlertViewBinding.inflate(layoutInflater)

            deleteDialog.setView(alertView.root)
            alertView.run {
                btnPositive.setOnClickListener {
                    viewModel.delete(id)
                    deleteDialog.dismiss()
                    Toast.makeText(
                        this@UpdateItemActivity,
                        "Deleted Successfully.",
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent().apply { putExtra(Constants.REFRESH, true) }

                    setResult(Activity.RESULT_OK, intent); finish()
                }
                btnNegative.setOnClickListener { deleteDialog.dismiss() }
            }
            deleteDialog.show()
        }
    }

    private fun onUpdate(id: Int) {
        binding.run {
            val name = etName.text.toString()
            val category = spCategory.selectedItem.toString()
            val quantity = npQuantity.value
            val item = viewModel.item.value

            if(name.isEmpty()) {
                Snackbar.make(
                    main,
                    "Name cannot be empty.",
                    Snackbar.LENGTH_LONG
                ).show()
            } else if(
                name == item?.name
                && category == item.category
                && quantity == item.quantity
            ) {
                Snackbar.make(
                    main,
                    "Nothing to update.",
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                viewModel.edit(id, Item(
                    id = id,
                    name = name,
                    category = category,
                    quantity = quantity
                ))
                Toast.makeText(
                    this@UpdateItemActivity,
                    "Updated Successfully.",
                    Toast.LENGTH_SHORT
                ).show()
                setResult(
                    Activity.RESULT_OK,
                    Intent().apply {
                        putExtra(Constants.REFRESH, true)
                    }
                )
                finish()
            }
        }
    }

    private fun setupView(id: Int) {
        viewModel.getItem(id)
        val item = viewModel.item.value
        binding.run {
            etName.setText(item?.name)
            spCategory.setSelection(
                when(item?.category) {
                    "Essentials" -> 1
                    "Miscellaneous" -> 2
                    else -> 0
                }
            )
            npQuantity.minValue = 1
            npQuantity.maxValue = 50
            npQuantity.value = item?.quantity!!.toInt()
            btnUpdate.setOnClickListener { onUpdate(item.id!!) }
            btnDelete.setOnClickListener { showDeleteDialog(item.id!!) }
        }
    }
}