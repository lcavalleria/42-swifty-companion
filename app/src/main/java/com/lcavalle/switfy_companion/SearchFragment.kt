package com.lcavalle.switfy_companion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.lcavalle.switfy_companion.databinding.FragmentSearchBinding
import com.lcavalle.switfy_companion.viewModels.StudentViewModel
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val model: StudentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.resetStudent()
        var isFirstIgnored = false
        val notFoundStr = " User login not found."

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                model.student.observe(viewLifecycleOwner) { student ->
                    if (student != null)
                        findNavController().navigate(R.id.action_SearchFragment_to_StudentInfoFragment)
                    else if (isFirstIgnored)
                        Toast.makeText(context, notFoundStr, Toast.LENGTH_SHORT).show()
                    else
                        isFirstIgnored = true
                }
            }
        }

        binding.buttonSearch.setOnClickListener {
            val studentLogin = binding.editTextLogin.text.toString()
            model.fetchStudentInfo(studentLogin)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}