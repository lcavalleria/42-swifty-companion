package com.lcavalle.switfy_companion.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lcavalle.switfy_companion.dataSources.api42.Student
import com.lcavalle.switfy_companion.repositories.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentViewModel @Inject constructor(
    private val repository: StudentRepository
) : ViewModel() {

    private val _student = MutableStateFlow<Student?>(Student.Example)
    val student: StateFlow<Student?> = _student

    fun fetchStudent(login: String) = viewModelScope.launch {
        _student.update { repository.getStudent(login) }
    }
}
