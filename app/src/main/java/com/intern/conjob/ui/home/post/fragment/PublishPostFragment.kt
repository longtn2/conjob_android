package com.intern.conjob.ui.home.post.fragment

import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.showToastOnClick
import com.intern.conjob.arch.extensions.viewBinding
import com.intern.conjob.arch.util.Constants
import com.intern.conjob.databinding.FragmentPublishPostBinding
import com.intern.conjob.ui.MainActivity
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel
import com.intern.conjob.ui.home.post.PublishPostViewModel

class PublishPostFragment : BaseFragment(R.layout.fragment_publish_post) {
    private val binding by viewBinding(FragmentPublishPostBinding::bind)
    private val viewModel by viewModels<PublishPostViewModel>()
    private var mediaPicker: ActivityResultLauncher<PickVisualMediaRequest>? = null
    private var player: ExoPlayer? = null

    override fun getViewModel(): BaseViewModel = BaseViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mediaPicker = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                viewModel.fileUri = it
                if (context?.contentResolver?.getType(it).toString().contains(
                        Constants.MEDIA_IMAGE.lowercase()
                    )
                ) {
                    Glide.with(activity as MainActivity).load(it).centerCrop()
                        .into(binding.imgPreview)
                    binding.imgPreview.visibility = View.VISIBLE
                    binding.playerViewPreview.visibility = View.GONE
                } else {
                    if (player == null) {
                        player =
                            ExoPlayer.Builder(activity as MainActivity).build().also { exoPlayer ->
                                exoPlayer.repeatMode = ExoPlayer.REPEAT_MODE_ONE
                                exoPlayer.playWhenReady = false
                            }
                    }
                    player?.addMediaItem(MediaItem.fromUri(it))
                    player?.prepare()
                    binding.imgPreview.visibility = View.GONE
                    binding.playerViewPreview.visibility = View.VISIBLE
                    binding.playerViewPreview.player = player
                }
                isEnableButton()
                binding.tvChooseMediaHint.visibility = View.GONE
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvPublishPost.showToastOnClick(
            activity as MainActivity, getString(R.string.publish_post)
        )

        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.tvChooseJob.setOnClickListener {
            findNavController().navigate(PublishPostFragmentDirections.actionPublishPostFragmentToListJobFragment())
        }

        binding.cardViewMedia.setOnClickListener {
            mediaPicker?.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
            )
        }

        binding.edtCaption.doAfterTextChanged {
            isEnableButton()
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Long>(
            Constants.JOB_ID_KEY
        )?.observe(viewLifecycleOwner) {
            viewModel.selectedJobId = it
        }
    }

    private fun isEnableButton() {
        binding.apply {
            tvPublishPost.isEnabled =
                (viewModel.fileUri != null && !edtCaption.text.isNullOrEmpty())
        }
    }

}
