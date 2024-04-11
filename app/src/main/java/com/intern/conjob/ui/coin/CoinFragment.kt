package com.intern.conjob.ui.coin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.viewBinding
import com.intern.conjob.databinding.FragmentCoinBinding
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel
import com.intern.conjob.ui.coin.adapter.CoinAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CoinFragment : BaseFragment(R.layout.fragment_coin) {

    private val binding by viewBinding(FragmentCoinBinding::bind)
    private val viewModel by viewModels<CoinViewModel>()

    companion object {
        fun newInstance() = CoinFragment()
    }

    private var coinAdapter: CoinAdapter? = null

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getTrending().launchIn(lifecycleScope)
        coinAdapter = CoinAdapter()
        initAdapter()
        initListener()
        viewModel.coins.onEach {
            coinAdapter?.coins = it
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initAdapter() {
        binding.apply {
            rvCoin.adapter = coinAdapter
        }
    }

    private fun initListener() {
        coinAdapter?.onItemClicked = {
            viewModel.getTrending().launchIn(lifecycleScope)
        }
    }
}
