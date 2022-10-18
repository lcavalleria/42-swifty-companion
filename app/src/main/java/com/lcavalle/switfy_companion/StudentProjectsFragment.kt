package com.lcavalle.switfy_companion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lcavalle.switfy_companion.databinding.FragmentStudentProjectsBinding
import com.lcavalle.switfy_companion.viewModels.StudentViewModel
import com.lcavalle.switfy_companion.views.StudentProjectAdapter
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class StudentProjectsFragment : Fragment() {
    private var _binding: FragmentStudentProjectsBinding? = null
    private val model: StudentViewModel by activityViewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudentProjectsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewProjectsList.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewProjectsList.adapter = StudentProjectAdapter()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                model.projects.collect { projects ->
                    (binding.recyclerViewProjectsList.adapter as StudentProjectAdapter)
                        .updateProjects(projects)
                    binding.recyclerViewProjectsList.scrollToPosition(0)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                model.page.collect { page ->
                    binding.textViewPage.text = page.toString()
                }
            }
        }

        binding.btnNextPage.setOnClickListener {
            model.fetchStudentProjects(model.page.value + 1)
        }

        binding.btnPrevPage.setOnClickListener {
            if (model.page.value > 1) {
                model.fetchStudentProjects(model.page.value - 1)
            }
        }

        binding.buttonBackToStudent.setOnClickListener {
            findNavController().navigate(R.id.action_StudentProjects_to_StudentInfoFragment)
        }
    }
}