package com.example.simbirsoftmobile.presentation.screens.help

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simbirsoftmobile.databinding.ItemCategoryBinding
import com.example.simbirsoftmobile.presentation.models.CategoryUI

class CategoryAdapter(private val categoryArray: Array<CategoryUI>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder =
        ViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )

    override fun getItemCount(): Int = categoryArray.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        holder.bind(categoryArray[position])
    }

    class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: CategoryUI) {
            with(binding) {
                setupUI(model)
            }
        }

        private fun ItemCategoryBinding.setupUI(model: CategoryUI) {
            categoryNameIV.setImageResource(model.imageUrl)
            categoryNameTV.text = model.name
        }
    }
}
