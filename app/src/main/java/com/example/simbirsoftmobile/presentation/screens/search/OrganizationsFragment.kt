package com.example.simbirsoftmobile.presentation.screens.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simbirsoftmobile.databinding.FragmentOrganizationsBinding
import com.google.android.material.divider.MaterialDividerItemDecoration

class OrganizationsFragment : Fragment() {
    private var _binding: FragmentOrganizationsBinding? = null
    private val binding: FragmentOrganizationsBinding
        get() = _binding!!

    private val adapter: SearchResultAdapter by lazy { SearchResultAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentOrganizationsBinding.inflate(inflater, container, false)
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
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val divider = MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        adapter.submitList(getRandomTestList())
        divider.isLastItemDecorated = false
        binding.recyclerView.addItemDecoration(divider)
    }

    override fun onResume() {
        super.onResume()
        adapter.submitList(getRandomTestList())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "EventsFragment"

        @JvmStatic
        fun newInstance() = EventsFragment()
    }
}
