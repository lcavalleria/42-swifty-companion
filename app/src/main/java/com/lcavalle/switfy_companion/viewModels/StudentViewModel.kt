package com.lcavalle.switfy_companion.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lcavalle.switfy_companion.dataClasses.Student
import com.lcavalle.switfy_companion.repositories.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentViewModel @Inject constructor(
    private val repository: StudentRepository
) : ViewModel() {

    private val _student = MutableStateFlow<Student?>(null)
    val student: StateFlow<Student?> = _student

    fun fetchStudent(login: String) = viewModelScope.launch {
        val tokenPlaceholder = "haha"

        _student.value = repository.getStudent(tokenPlaceholder, login)
    }
}
