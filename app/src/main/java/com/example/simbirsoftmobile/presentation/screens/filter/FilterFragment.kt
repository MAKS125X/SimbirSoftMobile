package com.example.simbirsoftmobile.presentation.screens.filter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simbirsoftmobile.R
import com.example.simbirsoftmobile.databinding.FragmentFilterBinding
import com.example.simbirsoftmobile.presentation.models.category.CategorySetting
import com.example.simbirsoftmobile.repository.CategoryRepository
import com.google.android.material.divider.MaterialDividerItemDecoration

class FilterFragment : Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding: FragmentFilterBinding
        get() = _binding!!

    private var _settingsList: List<CategorySetting>? = null
    private val settingsList: List<CategorySetting>
        get() = _settingsList!!

    private var _adapter: CategorySettingAdapter? = null
    private val adapter: CategorySettingAdapter
        get() = _adapter!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        _settingsList = CategoryRepository.getCategorySettings(context)
        _adapter = CategorySettingAdapter(settingsList, context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        binding.toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.accept_filter -> {
                    Log.d(TAG, "onViewCreated: $settingsList")
                    CategoryRepository.saveCategorySettings(
                        requireContext(),
                        settingsList,
                    )
                }
            }

            parentFragmentManager.popBackStack()
            true
        }
    }

    private fun initAdapter() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val divider = MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        divider.isLastItemDecorated = false
        binding.recyclerView.addItemDecoration(divider)
    }

    companion object {
        const val TAG = "FilterFragment"

        @JvmStatic
        fun newInstance() = FilterFragment()
    }
}
