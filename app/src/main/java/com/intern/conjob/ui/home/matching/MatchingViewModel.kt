package com.intern.conjob.ui.home.matching

import com.intern.conjob.arch.extensions.FlowResult
import com.intern.conjob.arch.extensions.onSuccess
import com.intern.conjob.arch.util.Constants.POST_LIMIT
import com.intern.conjob.arch.util.SharedPref
import com.intern.conjob.data.datasource.PostRemoteDataSource
import com.intern.conjob.data.datasource.TokenRemoteDataSource
import com.intern.conjob.data.model.Post
import com.intern.conjob.data.model.Token
import com.intern.conjob.data.repository.LocalRepository
import com.intern.conjob.data.repository.PostRepository
import com.intern.conjob.data.repository.TokenRepository
import com.intern.conjob.data.response.BaseDataResponse
import com.intern.conjob.data.response.PostResponse
import com.intern.conjob.data.response.TokenResponse
import com.intern.conjob.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MatchingViewModel(
    private val postRepository: PostRepository = PostRepository(PostRemoteDataSource.getInstance()),
    private val tokenRepository: TokenRepository = TokenRepository(TokenRemoteDataSource.getInstance())
) : BaseViewModel() {

    var list = mutableListOf<Post>()
    private val _posts: MutableStateFlow<List<Post>> = MutableStateFlow(listOf())
    var posts: StateFlow<List<Post>> = _posts

    private var token: String = SharedPref.getInstance().getToken()

    private val refreshToken: Token = Token(
        SharedPref.getInstance().getRefreshToken()
    )

    private var page = 1

    fun getPosts(): Flow<FlowResult<BaseDataResponse<PostResponse>>> {
        return postRepository.getPosts(token, page, POST_LIMIT).onSuccess {
            list.addAll(it.data?.items!!)
            _posts.value = list.toList()
            page++
        }
    }

    fun getNewToken(): Flow<FlowResult<BaseDataResponse<TokenResponse>>> {
        return tokenRepository.refreshToken(refreshToken).onSuccess { t ->
            t.data?.token?.let { it1 -> token = it1 }
            LocalRepository().saveToken(token, null)
        }
    }
}
