package com.intern.conjob.ui.home.matching.fragment

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.core.view.isEmpty
import androidx.core.view.size
import androidx.fragment.app.viewModels
import androidx.lifecycle.emitErrorModel
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.onError
import com.intern.conjob.arch.extensions.onSuccess
import com.intern.conjob.arch.extensions.viewBinding
import com.intern.conjob.arch.util.Constants.BLUR_EFFECT_RADIUS
import com.intern.conjob.arch.util.Constants.CARD_STACK_VIEW_MAX_DEGREE
import com.intern.conjob.arch.util.Constants.CARD_STACK_VIEW_SWIPE_THRESHOLD
import com.intern.conjob.arch.util.Constants.CARD_STACK_VIEW_VISIBLE_COUNT
import com.intern.conjob.arch.util.Constants.CLOSE_DETAILS_VIEW_KEY
import com.intern.conjob.arch.util.FileType
import com.intern.conjob.arch.util.PostOnClickListener
import com.intern.conjob.arch.util.VideoPlayer
import com.intern.conjob.data.error.ErrorModel
import com.intern.conjob.databinding.FragmentMatchingBinding
import com.intern.conjob.databinding.ItemPostBinding
import com.intern.conjob.ui.MainActivity
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel
import com.intern.conjob.ui.home.matching.MatchingViewModel
import com.intern.conjob.ui.home.matching.adapter.PostAdapter
import com.intern.conjob.ui.widget.CustomProgressDialog
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import java.net.HttpURLConnection

class MatchingFragment : BaseFragment(R.layout.fragment_matching) {
    private val binding by viewBinding(FragmentMatchingBinding::bind)
    private val viewModel by viewModels<MatchingViewModel>()
    private var adapter: PostAdapter? = null
    private var cardView: View? = null
    private var blurView: View? = null
    private var currentPlayerView: PlayerView? = null

    private val progressDialog by lazy {
        CustomProgressDialog.newInstance()
    }

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

        viewModel.posts.onEach {
            adapter?.posts = it
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        controller.currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
            CLOSE_DETAILS_VIEW_KEY)
            ?.observe(viewLifecycleOwner) {
                currentPlayerView?.player = VideoPlayer.player
            }
    }

    private fun initAdapter() {
        binding.apply {
            adapter = PostAdapter()
            getPosts()
            adapter?.setOnClickListener(object : PostOnClickListener {
                override fun onDetailClick() {
                    currentPlayerView?.player = null
                    controller.navigate(R.id.action_homeFragment_to_postDetailFragment)
                }

                override fun onAvatarClick() {
                    Toast.makeText(
                        context,
                        getString(R.string.toast_matching_profile),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onInteractClick() {
                    Toast.makeText(
                        context,
                        getString(R.string.toast_matching_interact),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onCommentClick() {
                    Toast.makeText(
                        context,
                        getString(R.string.toast_matching_comment),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onShareClick() {
                    Toast.makeText(
                        context,
                        getString(R.string.toast_matching_share),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }

    private fun initCardStackView() {
        val cardLayoutManager =
            CardStackLayoutManager(activity as MainActivity, object : CardStackListener {
                @OptIn(UnstableApi::class)
                override fun onCardDragging(direction: Direction?, ratio: Float) {
                    cardView?.let {
                        VideoPlayer.player?.pause()
                        currentPlayerView?.hideController()
                    }
                    renderBlurEffect(true)
                }

                override fun onCardSwiped(direction: Direction?) {
                    cardSwiped()
                }

                override fun onCardRewound() = Unit

                override fun onCardCanceled() {
                    cardView?.foreground = null
                    if (cardView?.tag == FileType.VIDEO) {
                        VideoPlayer.player?.play()
                    }
                    renderBlurEffect(false)
                }

                override fun onCardAppeared(view: View?, position: Int) {
                    cardView = view
                    cardView?.let { cv ->
                        val cardViewBinding = ItemPostBinding.bind(cv)
                        blurView = cardViewBinding.constraintLayout
                        adapter?.let {
                            if (it.posts[position].type == FileType.VIDEO.type) {
                                VideoPlayer.player?.play()
                                cardView?.tag = FileType.VIDEO
                                currentPlayerView = cardViewBinding.playerView
                                currentPlayerView?.player = VideoPlayer.player
                            }
                        }
                    }
                }

                override fun onCardDisappeared(view: View?, position: Int) {
                    cardView?.foreground = null
                    cardView?.tag = null
                    currentPlayerView?.player = null
                    renderBlurEffect(false)
                    adapter?.let {
                        if (adapter!!.posts[position].type == FileType.VIDEO.type) {
                            VideoPlayer.player?.currentMediaItemIndex?.let { index ->
                                VideoPlayer.player?.removeMediaItem(index)
                            }
                            VideoPlayer.player?.seekTo(0)
                            currentPlayerView = null
                        }
                    }
                }
            })
        cardLayoutManager.setVisibleCount(CARD_STACK_VIEW_VISIBLE_COUNT)
        cardLayoutManager.setMaxDegree(CARD_STACK_VIEW_MAX_DEGREE)
        cardLayoutManager.setSwipeThreshold(CARD_STACK_VIEW_SWIPE_THRESHOLD)
        cardLayoutManager.setDirections(Direction.HORIZONTAL)
        binding.cardStackView.layoutManager = cardLayoutManager
        binding.cardStackView.adapter = adapter
    }

    private fun cardSwiped() {
        cardView?.let {
            if (cardView!!.x < 0) {
                Toast.makeText(
                    activity as MainActivity,
                    getString(R.string.toast_matching_skip),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    activity as MainActivity,
                    getString(R.string.toast_matching_accept),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        if (binding.cardStackView.size <= CARD_STACK_VIEW_VISIBLE_COUNT - 1) {
            getPosts()
        }
    }

    private fun renderBlurEffect(isShow: Boolean) {
        if (isShow) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                blurView?.setRenderEffect(
                    RenderEffect.createBlurEffect(
                        BLUR_EFFECT_RADIUS, BLUR_EFFECT_RADIUS, Shader.TileMode.MIRROR
                    )
                )
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                blurView?.setRenderEffect(null)
            }
        }
    }

    private fun getPosts() {
        viewModel.getPosts().onStart {
            binding.tvEmpty.visibility = View.GONE
            if (binding.cardStackView.isEmpty()) {
                progressDialog.show(
                    childFragmentManager,
                    CustomProgressDialog::class.java.simpleName
                )
            }
        }.onCompletion {
            binding.tvEmpty.visibility = View.VISIBLE
            progressDialog.dismissAllowingStateLoss()
        }.onError { errorModel ->
            when ((errorModel as? ErrorModel.Http.ApiError)?.code) {
                //Error 401 -> Get new token
                HttpURLConnection.HTTP_UNAUTHORIZED.toString() -> {
                    viewModel.getNewToken().onSuccess {
                        if (it.data != null) {
                            getPosts()
                        }
                    }.launchIn(lifecycleScope)
                }
                else -> {
                    viewModel.emitErrorModel(errorModel)
                }
            }
        }.launchIn(lifecycleScope)
    }
}
