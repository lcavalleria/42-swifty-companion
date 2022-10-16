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
import com.lcavalle.switfy_companion.dataSources.api42.ImageLoader
import com.lcavalle.switfy_companion.databinding.FragmentStudentInfoBinding
import com.lcavalle.switfy_companion.viewModels.StudentViewModel
import com.lcavalle.switfy_companion.views.SkillsAdapter
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class StudentInfoFragment : Fragment() {

    private var _binding: FragmentStudentInfoBinding? = null
    private val binding get() = _binding!!
    private val model: StudentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewSkills.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewSkills.adapter = SkillsAdapter()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                model.student.observe(viewLifecycleOwner) { student ->
                    if (student != null) {
                        binding.textViewStudentLogin.text = student.login
                        binding.textViewStudentName.text = student.fullName
                        binding.textViewStudentEmail.text = student.email
                        binding.textViewCorrectionValue.text = student.correctionPoints.toString()
                        binding.textViewWalletValue.text = student.wallet.toString()
                        ImageLoader.loadImage(binding.imageView, student.imageUrl)
                        (binding.recyclerViewSkills.adapter as SkillsAdapter).updateSkills(student.cursus?.skills)
                    }
                }
            }
        }


        binding.buttonBackToSearch.setOnClickListener {
            model.resetStudent()
            findNavController().navigate(R.id.action_StudentInfoFragment_to_SearchFragment)
        }

        binding.buttonProjects.setOnClickListener {
            model.fetchStudentProjects(1)
            findNavController().navigate(R.id.action_SecondFragment_to_StudentProjects)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}