package com.khayrultw.movielist.ui.presentations.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.khayrultw.movielist.MainActivity
import com.khayrultw.movielist.R
import com.khayrultw.movielist.ui.presentations.base.viewModel.ViewModel

abstract class BaseFragment<T: ViewDataBinding>: Fragment() {
    abstract val viewModel: ViewModel
    lateinit var navController: NavController
    var binding: T? = null

    abstract fun getLayoutResource(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenResumed {
            viewModel.onViewCreated()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutResource(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = NavHostFragment.findNavController(this)
        onBindView(view)
    }
    
    protected open fun onBindView(view: View) {

        binding = DataBindingUtil.bind(view)
        binding?.lifecycleOwner = this

        viewModel.loading.asLiveData().observe(viewLifecycleOwner) {
            if(it) {
                showLoader()
            } else {
                hideLoader()
            }
        }
        
        viewModel.snackbar.asLiveData().observe(viewLifecycleOwner) {
            val snackbar = Snackbar.make(view, it.first, Snackbar.LENGTH_LONG)
            if(it.second) {
                snackbar.setBackgroundTint(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.red
                    )
                )
            }
            snackbar.show()
        }
    }
    
    private fun showLoader() {
        (requireActivity() as MainActivity).showLoader()
    }
    
    private fun hideLoader() {
        (requireActivity() as MainActivity).hideLoader()
    }
}