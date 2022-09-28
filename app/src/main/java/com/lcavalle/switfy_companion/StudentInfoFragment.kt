package com.lcavalle.switfy_companion

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.lcavalle.switfy_companion.databinding.FragmentSecondBinding
import com.lcavalle.switfy_companion.repositories.StudentRepository
import com.lcavalle.switfy_companion.viewModels.StudentViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "StudentInfoFragment"

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class StudentInfoFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val model: StudentViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                model.student.collect { student ->
                    Log.d(TAG, "student $student")
                    // todo: update the ui
                }
            }
        }

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}