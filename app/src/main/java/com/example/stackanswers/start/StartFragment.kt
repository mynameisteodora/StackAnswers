package com.example.stackanswers.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.stackanswers.R
import com.example.stackanswers.databinding.FragmentStartBinding


class StartFragment: Fragment() {

    private lateinit var binding : FragmentStartBinding

    private lateinit var args: Bundle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_start, container, false)
        args = Bundle()

        binding.searchButton.setOnClickListener {
            this.findNavController()
                .navigate(StartFragmentDirections.actionStartFragmentToSearchResultsFragment(binding.searchBox.text.toString()))
        }

        return binding.root
    }

}