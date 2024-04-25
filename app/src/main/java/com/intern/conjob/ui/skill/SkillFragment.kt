package com.intern.conjob.ui.skill

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.viewBinding
import com.intern.conjob.databinding.FragmentSkillBinding
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel

class SkillFragment: BaseFragment(R.layout.fragment_skill) {
    private val binding by viewBinding(FragmentSkillBinding::bind)
    private val viewModel by viewModels<SkillViewModel>()

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            recyclerViewSkill.adapter = SkillAdapter()
            recyclerViewSkill.layoutManager = StaggeredGridLayoutManager(4, LinearLayoutManager.HORIZONTAL)
            btnConfirm.setOnClickListener {
                controller.popBackStack()
            }
        }
    }
}
