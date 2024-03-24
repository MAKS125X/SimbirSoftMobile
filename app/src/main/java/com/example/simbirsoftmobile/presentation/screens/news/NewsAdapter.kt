package com.example.simbirsoftmobile.presentation.screens.news

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.simbirsoftmobile.R
import com.example.simbirsoftmobile.databinding.ItemNewBinding
import com.example.simbirsoftmobile.presentation.models.event.Event
import com.example.simbirsoftmobile.presentation.screens.utils.TypedListener
import com.example.simbirsoftmobile.presentation.screens.utils.getRemainingDateInfo

class NewsAdapter(
    private val onItemClick: TypedListener<Int>,
    private val context: Context,
) : ListAdapter<Event, NewsAdapter.ViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder =
        ViewHolder(
            ItemNewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            onItemClick,
            context,
        )

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        holder.bind(currentList[position])
    }

    class ViewHolder(
        private val binding: ItemNewBinding,
        private val onItemClick: TypedListener<Int>,
        private val context: Context,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Event) {
            with(binding) {
                setupUI(model, onItemClick)
            }
        }

        private fun ItemNewBinding.setupUI(
            model: Event,
            onItemClick: TypedListener<Int>,
        ) {
            newLayout.setOnClickListener {
                onItemClick(model.id)
            }
            titleTV.text = model.title
            descriptionTV.text = model.description
            remainDateTV.text = getRemainingDateInfo(model.dateStart, model.dateEnd, context)

            try {
                val drawable = ContextCompat.getDrawable(context, model.imageUrl)
                if (drawable != null) {
                    previewIV.setImageDrawable(drawable)
                } else {
                    previewIV.setImageResource(R.drawable.news_preview_not_found)
                }
            } catch (e: Resources.NotFoundException) {
                previewIV.setImageResource(R.drawable.news_preview_not_found)
            }
        }
    }

    companion object {
        private val DiffCallback =
            object : DiffUtil.ItemCallback<Event>() {
                override fun areItemsTheSame(
                    oldItem: Event,
                    newItem: Event,
                ): Boolean {
                    return oldItem.title == newItem.title
                }

                override fun areContentsTheSame(
                    oldItem: Event,
                    newItem: Event,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
