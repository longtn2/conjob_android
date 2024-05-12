package com.intern.conjob.ui.home.post.fragment

import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.showErrorAlert
import com.intern.conjob.arch.extensions.viewBinding
import com.intern.conjob.arch.util.Constants
import com.intern.conjob.arch.util.FileUtils
import com.intern.conjob.data.model.CreatePost
import com.intern.conjob.databinding.FragmentPublishPostBinding
import com.intern.conjob.ui.MainActivity
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel
import com.intern.conjob.ui.home.post.PublishPostViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File

class PublishPostFragment : BaseFragment(R.layout.fragment_publish_post) {
    private val binding by viewBinding(FragmentPublishPostBinding::bind)
    private val viewModel by viewModels<PublishPostViewModel>()
    private var mediaPicker: ActivityResultLauncher<PickVisualMediaRequest>? = null
    private var player: ExoPlayer? = null

    override fun getViewModel(): BaseViewModel = viewModel

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
        initListener()

        binding.edtCaption.doAfterTextChanged {
            isEnableButton()
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Long>(
            Constants.JOB_ID_KEY
        )?.observe(viewLifecycleOwner) {
            viewModel.selectedJobId = it
        }

        viewModel.createPostProgress.onEach {
            if (it == 3) {
                findNavController().popBackStack()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initListener() {
        binding.apply {
            tvPublishPost.setOnClickListener {
                viewModel.fileUri?.let { uri ->
                    viewModel.file = File(FileUtils.getPath(uri, activity as MainActivity) ?: "")
                    viewModel.file?.let {  file ->
                        if (!file.path.isNullOrEmpty()) {
                            viewModel.createPost(
                                CreatePost(
                                    title = edtTitle.text.toString(),
                                    caption = edtCaption.text.toString(),
                                    fileName = file.name,
                                    fileType = file.extension)
                            ).launchIn(lifecycleScope)
                        } else {
                            (activity as MainActivity).showErrorAlert(
                                message = Constants.FILE_NOT_EXIST,
                                buttonTitleRes = R.string.OK, onOkClicked = {
                                }
                            )
                        }
                    }
                }
            }

            toolBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            tvChooseJob.setOnClickListener {
                findNavController().navigate(PublishPostFragmentDirections.actionPublishPostFragmentToListJobFragment())
            }

            cardViewMedia.setOnClickListener {
                mediaPicker?.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
                )
            }
        }
    }

    private fun isEnableButton() {
        binding.apply {
            tvPublishPost.isEnabled =
                (viewModel.fileUri != null && !edtCaption.text.isNullOrEmpty())
        }
    }

}
