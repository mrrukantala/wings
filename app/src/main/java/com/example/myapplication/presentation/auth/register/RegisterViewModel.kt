package com.example.myapplication.presentation.auth.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.UserItem
import com.example.myapplication.domain.entity.UserD
import com.example.myapplication.domain.usecase.UserUseCase
import com.example.myapplication.presentation.auth.login.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val useCase: UserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<RegisterState>(RegisterState.Init)
    val state get() = _state

    private val _data = MutableStateFlow<UserD>(UserD("", ""))
    val data get() = _data


    fun getData(key: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getByUser(key)
                .onStart {
                    Log.v("DATA", "LOADING")
                }.catch {

                }.collect {
                    when (it) {
                        is com.example.bossku.utils.common.base.Result.Success -> {
                            Log.v("DATA", "SUKSES ${it.data}")
                        }
                        is com.example.bossku.utils.common.base.Result.Error -> {
                            Log.v("DATA", "ERROR ${it.response}")
                        }
                    }
                }
        }
    }

    fun register(data: UserD) {
        viewModelScope.launch(Dispatchers.Default) {
            useCase.insert(UserItem(data.user, data.password))
                .onStart {
                    _state.value = RegisterState.Loading()
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
        _state.value = RegisterState.Error(toUserEntity)
        _state.value = RegisterState.Init
    }

    private fun successHandler(data: UserD) {
        _state.value = RegisterState.Success(data)
    }
}

sealed class RegisterState {
    object Init : RegisterState()
    data class Loading(val loading: Boolean = true) : RegisterState()
    data class Success(val data: UserD) : RegisterState()
    data class Error(val data: UserD) : RegisterState()
}