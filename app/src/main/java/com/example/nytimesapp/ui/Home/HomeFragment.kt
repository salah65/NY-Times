package com.example.nytimesapp.ui.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.OrientationHelper
import com.example.nytimesapp.Core.showDialog
import com.example.nytimesapp.Network.Status
import com.example.nytimesapp.R
import com.example.nytimesapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    lateinit var binding: FragmentHomeBinding
    lateinit var adapter: ArticleAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ArticleAdapter {
            findNavController().navigate(
                R.id.action_homeFragment_to_detailsFragment,
                bundleOf("article" to it)
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.articleRv.adapter = adapter
        binding.articleRv.startLayoutAnimation()
        val dividerItemDecoration = DividerItemDecoration(
            binding.articleRv.context,
            OrientationHelper.VERTICAL
        )
        binding.articleRv.addItemDecoration(dividerItemDecoration)
        binding.swipeTorefresh.setOnRefreshListener {
            viewModel.getArticles()
            binding.swipeTorefresh.isRefreshing = false
        }
        viewModel.articlesLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.loading.visibility = View.GONE
                    adapter.updateData(it.data!!)
                    binding.articleRv.scheduleLayoutAnimation()

                }
                Status.ERROR -> showDialog(requireContext(), it.message, title = it.errorMsg!!)
                Status.LOADING -> binding.loading.visibility = View.VISIBLE

            }
        })
    }
}