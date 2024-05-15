package com.intern.conjob.ui.home.matching

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.size
import androidx.fragment.app.viewModels
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.viewBinding
import com.intern.conjob.arch.util.Constants.BLUR_EFFECT_RADIUS
import com.intern.conjob.arch.util.Constants.CARD_STACK_VIEW_MAX_DEGREE
import com.intern.conjob.arch.util.Constants.CARD_STACK_VIEW_SWIPE_THRESHOLD
import com.intern.conjob.arch.util.Constants.CARD_STACK_VIEW_VISIBLE_COUNT
import com.intern.conjob.arch.util.PostOnClickListener
import com.intern.conjob.databinding.FragmentMatchingBinding
import com.intern.conjob.ui.MainActivity
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel
import com.intern.conjob.ui.home.matching.adapter.PostAdapter
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction

class MatchingFragment : BaseFragment(R.layout.fragment_matching) {
    private val binding by viewBinding(FragmentMatchingBinding::bind)
    private val viewModel by viewModels<MatchingViewModel>()
    private var adapter: PostAdapter? = null
    private var cardView: View? = null

    companion object {
        fun newInstance() = MatchingFragment()
    }

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initCardStackView()
        binding.imgBtnSearch.setOnClickListener {
            Toast.makeText(context, getString(R.string.toast_matching_search), Toast.LENGTH_SHORT).show()
        }
    }

    private fun initAdapter() {
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
    }

    private fun initCardStackView() {
        val cardLayoutManager = CardStackLayoutManager(activity as MainActivity, object: CardStackListener {
            override fun onCardDragging(direction: Direction?, ratio: Float) {
                cardView?.let {
                    if (cardView!!.x < 0) {
                        changeStatusVisibility(true)
                        changeStatusView(false)
                        cardView!!.foreground = AppCompatResources.getDrawable(
                            activity as MainActivity,
                            R.color.swipe_skip_bg_color
                        )
                    } else {
                        changeStatusVisibility(true)
                        changeStatusView(true)
                        cardView!!.foreground = AppCompatResources.getDrawable(
                            activity as MainActivity,
                            R.color.swipe_accept_bg_color
                        )
                    }
                    binding.apply {
                        constraintLayoutStatus.x = cardView!!.x
                        constraintLayoutStatus.y = cardView!!.y
                        constraintLayoutStatus.rotation = cardView!!.rotation
                    }
                }
            }

            override fun onCardSwiped(direction: Direction?) {
                cardView?.let {
                    if (cardView!!.x < 0) {
                        Toast.makeText(activity as MainActivity, getString(R.string.toast_matching_skip), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(activity as MainActivity, getString(R.string.toast_matching_accept), Toast.LENGTH_SHORT).show()
                    }
                }
                if (binding.cardStackView.size <= 0) {
                    adapter?.posts = viewModel.getTempData()
                }
            }

            override fun onCardRewound() = Unit

            override fun onCardCanceled() {
                changeStatusVisibility(false)
                cardView?.foreground = null
            }

            override fun onCardAppeared(view: View?, position: Int) {
                cardView = view
            }

            override fun onCardDisappeared(view: View?, position: Int) {
                changeStatusVisibility(false)
                cardView?.foreground = null
            }

        })
        cardLayoutManager.setVisibleCount(CARD_STACK_VIEW_VISIBLE_COUNT)
        cardLayoutManager.setMaxDegree(CARD_STACK_VIEW_MAX_DEGREE)
        cardLayoutManager.setSwipeThreshold(CARD_STACK_VIEW_SWIPE_THRESHOLD)
        cardLayoutManager.setDirections(Direction.FREEDOM)
        binding.cardStackView.layoutManager = cardLayoutManager
        binding.cardStackView.adapter = adapter
    }

    private fun changeStatusVisibility(isShow: Boolean) {
        if (isShow) {
            binding.constraintLayoutStatus.visibility = View.VISIBLE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                cardView?.setRenderEffect(
                    RenderEffect.createBlurEffect(
                        BLUR_EFFECT_RADIUS, BLUR_EFFECT_RADIUS, Shader.TileMode.MIRROR
                    )
                )
            }
        } else {
            binding.constraintLayoutStatus.visibility = View.GONE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                cardView?.setRenderEffect(null)
            }
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

}
