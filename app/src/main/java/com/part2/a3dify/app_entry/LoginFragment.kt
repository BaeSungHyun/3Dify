package com.part2.a3dify.app_entry

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.part2.a3dify.R
import com.part2.a3dify.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding : FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)

        binding.signupButton.setOnClickListener {
            parentFragmentManager.commit {
                replace<RegisterFragment>(R.id.fragment)
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}