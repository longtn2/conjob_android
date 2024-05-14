package com.intern.conjob.ui.home.post

import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.lifecycle.bindCommonError
import androidx.lifecycle.bindLoading
import androidx.lifecycle.viewModelScope
import com.intern.conjob.arch.extensions.FlowResult
import com.intern.conjob.arch.extensions.onSuccess
import com.intern.conjob.data.datasource.PostRemoteDataSource
import com.intern.conjob.data.model.CreatePost
import com.intern.conjob.data.repository.PostRepository
import com.intern.conjob.data.response.BaseDataResponse
import com.intern.conjob.data.response.CreatePostResponse
import com.intern.conjob.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class PublishPostViewModel(
    private val postRepository: PostRepository = PostRepository(PostRemoteDataSource.getInstance())
) : BaseViewModel() {
    private var _createPostProgress: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val createPostProgress: StateFlow<Boolean> = _createPostProgress
    var selectedJobId: Long? = null
    var fileUri: Uri? = null
    var file: File? = null
    private var isCreatePost = false
    private var isUploadFile = false

    fun isLoading(): Boolean {
        return isCreatePost || isUploadFile
    }

    fun createPost(createPost: CreatePost): Flow<FlowResult<BaseDataResponse<CreatePostResponse>>> {
        return postRepository.createPost(createPost)
            .bindLoading(this)
            .bindCommonError(this)
            .onSuccess {
                it.data?.url?.let { url ->
                    file?.let { file ->
                        uploadFile(url, file)
                    }
                }
            }
    }

    private fun uploadFile(url: String, file: File) {
        val type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(file.extension).toString()
        val requestBody = file.asRequestBody(type.toMediaTypeOrNull())
        postRepository.uploadFile(url, requestBody)
            .bindLoading(this)
            .bindCommonError(this)
            .onSuccess {
                _createPostProgress.value = true
            }
            .launchIn(viewModelScope)
    }
}
