package com.part2.a3dify.app_entry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.part2.a3dify.R
import com.part2.a3dify.app_entry.viewmodels.RegisterFragmentViewModel
import com.part2.a3dify.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
//    private val viewModel: RegisterFragmentViewModel by viewModels {
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater)

        setupListeners()

        return binding.root
    }

    private fun setupListeners() {
        binding.registerButton.setOnClickListener {
            validateInputs()
        }
    }

    private fun validateInputs() {
        val username = binding.usernameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val confirmPassword = binding.passwordCheckEditText.text.toString()

        // Example validation logic
        when {
            username.isBlank() -> {
                binding.usernameEditText.error = getString(R.string.username_error)
                return
            }
            !isValidEmail(email) -> {
                binding.emailEditText.error = getString(R.string.email_error)
                return
            }
            password.length < 8 -> {
                binding.passwordEditText.error = getString(R.string.password_error)
                return
            }
            binding.passwordEditText != binding.passwordEditTextCheck -> {
                binding.passwordEditTextCheck.error = getString(R.string.password_mismatch_error)
                return
            }
            else -> {
                // Proceed with registration or further validation
                performRegistration(username, email, password)
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun performRegistration(username: String, email: String, password: String) {
        // Implement your registration logic here

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}