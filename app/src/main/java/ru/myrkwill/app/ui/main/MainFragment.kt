package ru.myrkwill.app.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
        viewModel.newsLiveData.observe(viewLifecycleOwner) { response ->
            when(response) {
               is Resource.Success -> {
                   binding.pagProgressBar.visibility = View.INVISIBLE
                   response.data?.let {
                       newsAdapter.differ.submitList(it.articles)
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
        newsRecyclerView.layoutManager = LinearLayoutManager(activity)
        newsRecyclerView.adapter = newsAdapter
    }

}