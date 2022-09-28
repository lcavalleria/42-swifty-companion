package com.lcavalle.switfy_companion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.lcavalle.switfy_companion.dataClasses.Student
import com.lcavalle.switfy_companion.databinding.FragmentFirstBinding
import com.lcavalle.switfy_companion.repositories.StudentRepository
import com.lcavalle.switfy_companion.viewModels.StudentViewModel
import java.time.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SearchFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val model: StudentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSearch.setOnClickListener {
            val studentLogin = binding.textViewLogin.text.toString()
            model.fetchStudent(studentLogin)
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun searchStudent(login: String): Student {
        return Student(login, "Example Student", LocalDate.parse("1992-03-25"))
    }
}