package com.karo.examu2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karo.examu2.data.room.User
import com.karo.examu2.data.room.UserDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class UserViewModel(private val dao: UserDao) : ViewModel() {

    val user: Flow<User?> = dao.getAllUsers().map { it.firstOrNull() }

    val users: Flow<List<User>> = dao.getAllUsers()

    fun saveUser(name: String, age: Int, email: String) {
        viewModelScope.launch {
            dao.insertUser(User(name = name, age = age, email = email))
        }
    }
}
