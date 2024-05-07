package com.intern.conjob.ui.home.matching

import androidx.lifecycle.bindLoading
import com.intern.conjob.arch.extensions.FlowResult
import com.intern.conjob.arch.extensions.onSuccess
import com.intern.conjob.arch.util.Constants.POST_LIMIT
import com.intern.conjob.data.datasource.PostRemoteDataSource
import com.intern.conjob.data.model.Post
import com.intern.conjob.data.repository.PostRepository
import com.intern.conjob.data.response.BaseDataResponse
import com.intern.conjob.data.response.PostResponse
import com.intern.conjob.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onCompletion

class MatchingViewModel(
    private val postRepository: PostRepository = PostRepository(PostRemoteDataSource.getInstance())
) : BaseViewModel() {

    private val _posts: MutableStateFlow<List<Post>> = MutableStateFlow(listOf())
    var posts: StateFlow<List<Post>> = _posts

    private var page = 1
    private var isLoading: Boolean = false
    private var hasReachedEnd = false

    fun canCallApiGetMorePosts(): Boolean {
        return !isLoading && !hasReachedEnd
    }

    fun isGetMorePosts(): Boolean {
        return _posts.value.isNotEmpty() && !hasReachedEnd
    }

    fun getPosts(): Flow<FlowResult<BaseDataResponse<PostResponse>>> {
        page = 1
        isLoading = true
        hasReachedEnd = false
        _posts.value = listOf()
        return postRepository.getPosts(page, POST_LIMIT).onCompletion {
            isLoading = false
        }.bindLoading(this).onSuccess {
            it.data?.items?.let { items ->
                _posts.value = items
                page++
            }
        }
    }

    fun getMorePosts(): Flow<FlowResult<BaseDataResponse<PostResponse>>> {
        val data = _posts.value.toMutableList()
        isLoading = true
        return postRepository.getPosts(page, POST_LIMIT).onCompletion {
            isLoading = false
        }.onSuccess {
            it.data?.items?.let { items ->
                data.addAll(items)
                _posts.value = data.toList()
                page++
                hasReachedEnd = items.size < POST_LIMIT
            }
        }
    }
}
