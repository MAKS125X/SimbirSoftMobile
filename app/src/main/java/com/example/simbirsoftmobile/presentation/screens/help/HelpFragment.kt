package com.example.simbirsoftmobile.presentation.screens.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.simbirsoftmobile.R
import com.example.simbirsoftmobile.databinding.FragmentHelpBinding
import com.example.simbirsoftmobile.presentation.models.CategoryUI

class HelpFragment : Fragment() {
    private var _binding: FragmentHelpBinding? = null
    private val binding: FragmentHelpBinding
        get() = _binding!!

    val adapter: CategoryAdapter by lazy { CategoryAdapter(testList) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHelpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "HelpFragment"

        @JvmStatic
        fun newInstance() = HelpFragment()

        private val testList =
            arrayOf(
                CategoryUI("Дети", R.drawable.category_children),
                CategoryUI("Взрослые", R.drawable.category_adults),
                CategoryUI("Пожилые", R.drawable.category_old),
                CategoryUI("Животные", R.drawable.category_animals),
                CategoryUI("Мероприятия", R.drawable.category_events),
            )
    }
}
