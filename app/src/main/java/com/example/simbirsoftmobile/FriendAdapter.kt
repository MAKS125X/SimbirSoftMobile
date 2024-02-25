package com.example.simbirsoftmobile

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.simbirsoftmobile.databinding.FriendItemBinding
import com.example.simbirsoftmobile.models.FriendUI

class FriendAdapter : ListAdapter<FriendUI, FriendAdapter.ViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder =
        ViewHolder(
            FriendItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: FriendItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: FriendUI) {
            with(binding) {
                setupUI(model)
            }
        }

        private fun FriendItemBinding.setupUI(model: FriendUI) {
            nameTextView.text = model.name
            profileIV.setImageResource(model.imageUrl)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<FriendUI>() {
        override fun areItemsTheSame(
            oldItem: FriendUI,
            newItem: FriendUI,
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: FriendUI,
            newItem: FriendUI,
        ): Boolean {
            return oldItem == newItem
        }
    }

    class CustomItemDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State,
        ) {
            super.getItemOffsets(outRect, view, parent, state)

            val position = parent.getChildAdapterPosition(view)
            val itemCount = parent.adapter?.itemCount ?: 0

            if (position == itemCount - 1) {
                outRect.bottom = 0
            }
        }
    }
}
