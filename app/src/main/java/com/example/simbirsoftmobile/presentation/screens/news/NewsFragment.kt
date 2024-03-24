package com.example.simbirsoftmobile.presentation.screens.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.simbirsoftmobile.R
import com.example.simbirsoftmobile.databinding.FragmentNewsBinding
import com.example.simbirsoftmobile.presentation.screens.eventDetails.EventDetailsFragment
import com.example.simbirsoftmobile.presentation.screens.filter.FilterFragment
import com.example.simbirsoftmobile.repository.CategoryRepository
import com.example.simbirsoftmobile.repository.EventRepository

class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding: FragmentNewsBinding
        get() = _binding!!

    private var adapter: NewsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.open_filter ->
                    parentFragmentManager.beginTransaction().replace(
                        R.id.fragmentHolder,
                        FilterFragment.newInstance(),
                        FilterFragment.TAG,
                    ).addToBackStack(FilterFragment.TAG).commit()
            }
            true
        }
    }

    private fun moveToEventDetailsFragment(eventId: Int) {
        parentFragmentManager.beginTransaction().replace(
            R.id.fragmentHolder,
            EventDetailsFragment.newInstance(eventId),
            EventDetailsFragment.TAG,
        ).addToBackStack(EventDetailsFragment.TAG).commit()
    }

    private fun initAdapter() {
        adapter = NewsAdapter(this::moveToEventDetailsFragment, requireContext())

        binding.recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        val requiredCategories = CategoryRepository.getSelectedCategoriesId(requireContext())
        if (requiredCategories.isEmpty()) {
            binding.notFoundTV.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
            binding.notFoundTV.text = getString(R.string.not_select_any_categories)
        } else {
            val events =
                EventRepository.getAllEventsByCategories(
                    requiredCategories,
                    requireContext(),
                )
            if (events.isEmpty()) {
                binding.notFoundTV.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
                binding.notFoundTV.text = getString(R.string.nothing_was_found_based_on_filters)
            } else {
                binding.notFoundTV.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                adapter?.submitList(events)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }

    companion object {
        const val TAG = "NewsFragment"

        @JvmStatic
        fun newInstance() = NewsFragment()
    }
}
