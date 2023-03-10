package ru.myrkwill.app.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.myrkwill.app.databinding.FragmentMainBinding
import ru.myrkwill.app.ui.adapters.NewsAdapter
import ru.myrkwill.app.utils.Resource

@AndroidEntryPoint
class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding

    private val viewModel: MainViewModel by viewModels()

    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initAdapter()

        newsAdapter.setOnItemClickListener {
            val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(it)
            view.findNavController().navigate(action)
        }

        viewModel.newsLiveData.observe(viewLifecycleOwner) { response ->
            when(response) {
               is Resource.Success -> {
                   binding.pagProgressBar.visibility = View.INVISIBLE
                   response.data?.let {
                       newsAdapter.differ.submitList(it)
                   }
               }
               is Resource.Error -> {
                   binding.pagProgressBar.visibility = View.INVISIBLE
               }
               is Resource.Loading -> {
                   binding.pagProgressBar.visibility = View.VISIBLE
               }
            }
        }
    }

    private fun initAdapter() = with(binding) {
        newsAdapter = NewsAdapter()
        newsRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = newsAdapter
        }
    }

}