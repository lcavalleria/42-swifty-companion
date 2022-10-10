package com.lcavalle.switfy_companion

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.lcavalle.switfy_companion.databinding.FragmentStudentProjectsBinding
import com.lcavalle.switfy_companion.viewModels.StudentViewModel
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class StudentProjects : Fragment() {
    private var _binding: FragmentStudentProjectsBinding? = null
    private val model: StudentViewModel by activityViewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudentProjectsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                model.projects.collect { projects ->
                    projects.forEach { ptl -> // page to list
                        Log.d(SwiftyCompanion.TAG, "page: ${ptl.key}")
                        ptl.value.forEach { sp ->
                            Log.d(
                                SwiftyCompanion.TAG,
                                "project: ${sp.project?.name}, mark: ${sp.finalMark}, status: ${sp.status}, validated? : ${sp.validated}"
                            )
                        }
                    }
                }
            }
        }

        binding.buttonBackToStudent.setOnClickListener {
            findNavController().navigate(R.id.action_StudentProjects_to_SecondFragment)
        }
    }
}