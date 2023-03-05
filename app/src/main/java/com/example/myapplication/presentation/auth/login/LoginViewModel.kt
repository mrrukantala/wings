package com.example.myapplication.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.entity.UserD
import com.example.myapplication.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val useCase: UserUseCase) : ViewModel() {

    private val _state = MutableStateFlow<LoginState>(LoginState.Init)
    val state get() = _state

    private val _data = MutableStateFlow<UserD>(UserD("", ""))
    val data get() = _data

    fun login(data: UserD) {
        viewModelScope.launch(Dispatchers.Default) {
            useCase.login(data.user, data.password)
                .onStart {
                    _state.value = LoginState.Loading()
                }
                .catch {

                }
                .collect {
                    when (it) {
                        is com.example.bossku.utils.common.base.Result.Success -> {
                            successHandler(it.data)
                        }
                        is com.example.bossku.utils.common.base.Result.Error -> {
                            errorHandler(it.response.toUserEntity())
                        }
                    }
                }
        }
    }

    private fun errorHandler(toUserEntity: UserD) {
        _state.value = LoginState.Error(toUserEntity)
        _state.value = LoginState.Init
    }

    private fun successHandler(data: UserD) {
        _state.value = LoginState.Success(data)
    }


}

sealed class LoginState {
    object Init : LoginState()
    data class Loading(val loading: Boolean = true) : LoginState()
    data class Success(val data: UserD) : LoginState()
    data class Error(val data: UserD) : LoginState()
}