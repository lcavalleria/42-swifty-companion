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
import com.lcavalle.switfy_companion.dataSources.api42.Cursus
import com.lcavalle.switfy_companion.dataSources.api42.ImageLoader
import com.lcavalle.switfy_companion.databinding.FragmentStudentInfoBinding
import com.lcavalle.switfy_companion.viewModels.StudentViewModel
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class StudentInfoFragment : Fragment() {

    private var _binding: FragmentStudentInfoBinding? = null
    private val model: StudentViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                model.student.collect { student ->
                    ImageLoader.loadImage(binding.imageView, student?.imageUrl)
                    student?.cursus?.skills?.forEach { skill: Cursus.Companion.Skill ->
                        Log.d(
                            SwiftyCompanion.TAG,
                            "skill: ${skill.name}, level: ${skill.level}"
                        )
                    }
                }
            }
            // todo: update the ui
        }

        binding.buttonBackToSearch.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        binding.buttonProjects.setOnClickListener {
            model.fetchStudentProjects(0)
            findNavController().navigate(R.id.action_SecondFragment_to_StudentProjects)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}