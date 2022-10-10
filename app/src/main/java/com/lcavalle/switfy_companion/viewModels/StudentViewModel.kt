package com.lcavalle.switfy_companion.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lcavalle.switfy_companion.dataSources.api42.Student
import com.lcavalle.switfy_companion.dataSources.api42.StudentProject
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

    private val _projects = MutableStateFlow<Map<Int, List<StudentProject>>>(emptyMap())
    val projects: StateFlow<Map<Int, List<StudentProject>>> = _projects

    fun fetchStudentInfo(login: String) = viewModelScope.launch {
        _student.update { repository.getStudent(login) }
    }

    fun fetchStudentProjects(page: Int) = viewModelScope.launch {
        _projects.update {
            _projects.value + (page to repository.getStudentProjects(
                studentId = _student.value?.id ?: 0,
                page = page
            ))
        }
    }
}
