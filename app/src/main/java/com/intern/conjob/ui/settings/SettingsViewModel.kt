package com.intern.conjob.ui.settings

import androidx.lifecycle.viewModelScope
import com.intern.conjob.arch.util.SharedPref
import com.intern.conjob.data.datasource.AuthRemoteDataSource
import com.intern.conjob.data.model.Token
import com.intern.conjob.data.repository.AuthRepository
import com.intern.conjob.data.repository.LocalRepository
import com.intern.conjob.ui.base.BaseViewModel
import kotlinx.coroutines.flow.launchIn

class SettingsViewModel(
    private val authRepository: AuthRepository = AuthRepository(AuthRemoteDataSource.getInstance())
): BaseViewModel() {

    fun logout() {
        SharedPref.getRefreshToken()?.let {
            authRepository.logout(Token(it)).launchIn(viewModelScope)
        }
    }

    fun clearToken() {
        LocalRepository().saveToken("", "")
    }
}
