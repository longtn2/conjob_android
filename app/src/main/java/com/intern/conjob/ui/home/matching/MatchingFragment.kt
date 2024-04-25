package com.intern.conjob.ui.home.matching

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.viewBinding
import com.intern.conjob.arch.util.Constants.BLUR_EFFECT_RADIUS
import com.intern.conjob.arch.util.Constants.MIN_SWIPE_PROGRESS
import com.intern.conjob.arch.util.PostOnClickListener
import com.intern.conjob.databinding.FragmentMatchingBinding
import com.intern.conjob.ui.MainActivity
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel
import com.intern.conjob.ui.home.matching.adapter.PostAdapter
import com.yalantis.library.KolodaListener
import kotlin.math.abs


class MatchingFragment : BaseFragment(R.layout.fragment_matching) {
    private val binding by viewBinding(FragmentMatchingBinding::bind)
    private val viewModel by viewModels<MatchingViewModel>()
    private var adapter: PostAdapter? = null

    companion object {
        fun newInstance() = MatchingFragment()
    }

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        binding.imgBtnSearch.setOnClickListener {
            Toast.makeText(context, getString(R.string.toast_matching_search), Toast.LENGTH_SHORT).show()
        }
    }

    private fun initAdapter() {
        binding.apply {
            adapter = PostAdapter()
            adapter?.posts = viewModel.getTempData()
            adapter?.setOnClickListener(object : PostOnClickListener {
                override fun onDetailClick() {
                    Toast.makeText(context, getString(R.string.toast_matching_details), Toast.LENGTH_SHORT).show()
                }

                override fun onAvatarClick() {
                    Toast.makeText(context, getString(R.string.toast_matching_profile), Toast.LENGTH_SHORT).show()
                }

                override fun onInteractClick() {
                    Toast.makeText(context, getString(R.string.toast_matching_interact), Toast.LENGTH_SHORT).show()
                }

                override fun onCommentClick() {
                    Toast.makeText(context, getString(R.string.toast_matching_comment), Toast.LENGTH_SHORT).show()
                }

                override fun onShareClick() {
                    Toast.makeText(context, getString(R.string.toast_matching_share), Toast.LENGTH_SHORT).show()
                }
            })
            koloda.adapter = adapter
            koloda.kolodaListener = object : KolodaListener {
                override fun onCardSwipedLeft(position: Int) {
                    super.onCardSwipedLeft(position)
                    Toast.makeText(context, getString(R.string.toast_matching_skip), Toast.LENGTH_SHORT).show()
                    changeStatusVisibility(false)
                }

                override fun onCardSwipedRight(position: Int) {
                    super.onCardSwipedRight(position)
                    Toast.makeText(context, getString(R.string.toast_matching_accept), Toast.LENGTH_SHORT).show()
                    changeStatusVisibility(false)
                }

                override fun onEmptyDeck() {
                    super.onEmptyDeck()
                    koloda.reloadAdapterData()
                }

                override fun onCardDrag(position: Int, cardView: View, progress: Float) {
                    super.onCardDrag(position, cardView, progress)
                    when {
                        abs(progress) > MIN_SWIPE_PROGRESS -> {
                            with(progress > 0) {
                                cardView.foreground = if (this) {
                                    AppCompatResources.getDrawable(
                                        activity as MainActivity,
                                        R.color.swipe_accept_bg_color
                                    )
                                } else {
                                    AppCompatResources.getDrawable(
                                        activity as MainActivity,
                                        R.color.swipe_skip_bg_color
                                    )
                                }
                                changeStatusView(this)
                            }
                            changeStatusVisibility(true)
                            createBlurEffect(cardView)
                        }
                        else -> {
                            cardView.foreground = null
                            changeStatusVisibility(false)
                            changeStatusView(false)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                cardView.setRenderEffect(null)
                            }
                        }
                    }
                    constraintLayoutStatus.x = cardView.x
                    constraintLayoutStatus.y = cardView.y
                    constraintLayoutStatus.rotation = cardView.rotation
                }
            }
        }
    }

    private fun changeStatusVisibility(isShow: Boolean) {
        if (isShow) {
            binding.constraintLayoutStatus.visibility = View.VISIBLE
        } else {
            binding.constraintLayoutStatus.visibility = View.GONE
        }
    }

    private fun changeStatusView(isAccept: Boolean) {
        if (isAccept) {
            binding.imgStatus.setImageResource(R.drawable.ic_matching_accept)
            binding.tvStatus.text = getString(R.string.matching_accept)
        } else {
            binding.imgStatus.setImageResource(R.drawable.ic_matching_skip)
            binding.tvStatus.text = getString(R.string.matching_skip)
        }
    }

    private fun createBlurEffect(cardView: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            cardView.setRenderEffect(
                RenderEffect.createBlurEffect(
                    BLUR_EFFECT_RADIUS, BLUR_EFFECT_RADIUS, Shader.TileMode.MIRROR
                )
            )
        }
    }
}
