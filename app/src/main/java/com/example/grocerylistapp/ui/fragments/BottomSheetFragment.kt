package com.example.grocerylistapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.grocerylistapp.databinding.LayoutBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment private constructor(
    val onEdit: () -> Unit,
    val onDelete: () -> Unit
): BottomSheetDialogFragment() {
    private lateinit var binding: LayoutBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutBottomSheetBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            tvEdit.setOnClickListener { onEdit(); dismiss() }
            tvDelete.setOnClickListener { onDelete(); dismiss() }
        }
    }

    companion object {
        fun getInstance(
            onEdit: () -> Unit,
            onDelete: () -> Unit
        ): BottomSheetFragment = BottomSheetFragment(onEdit, onDelete)
    }
}