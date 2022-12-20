package com.lcavalle.switfy_companion.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _student = MutableLiveData<Result<Student?>>(Result.success(null))
    val student: LiveData<Result<Student?>> = _student

    private val _projects = MutableStateFlow<List<StudentProject>>(emptyList())
    val projects: StateFlow<List<StudentProject>> = _projects

    private val _page = MutableStateFlow(1)
    val page: StateFlow<Int> = _page

    private var isLastPage: Boolean = false

    /**
     * @return true if successful, false if student was updated with Null.
     */
    fun fetchStudentInfo(login: String) = viewModelScope.launch {
        val result = repository.getStudent(login)
        _student.value = result
    }

    fun resetStudent() = viewModelScope.launch {
        _student.value = Result.success(null)
    }

    fun fetchStudentProjects(page: Int) = viewModelScope.launch {
        val projects = repository.getStudentProjects(
            // this will never be called with _student.value? being null
            studentId = _student.value?.getOrNull()?.id ?: 0, page = page
        )
        if (projects != null) {
            if (projects.isEmpty()) isLastPage = true
            else {
                _page.update { page }
                _projects.update { projects }
            }
        }

    }
}
