package com.example.nytimesapp.ui.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.nytimesapp.Network.Status
import com.example.nytimesapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    lateinit var viewModel: HomeViewModel
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
        viewModel = HomeViewModel()
        adapter = ArticleAdapter(null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.articleRv.adapter = adapter
        viewModel.articlesLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> adapter.updateData(it.data!!)

            }
        })
    }
}