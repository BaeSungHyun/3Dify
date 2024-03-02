package bae.part2.a3dify.app_entry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.add
import androidx.fragment.app.replace
import bae.part2.a3dify.R
import bae.part2.a3dify.databinding.FragmentInitialBinding

class InitialFragment : Fragment() {
    private lateinit var binding : FragmentInitialBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInitialBinding.inflate(inflater)

        binding.nonMember.setOnClickListener {
            parentFragmentManager.commit {
                replace<MainFragment>(R.id.fragment)
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }

        binding.member.setOnClickListener {
            parentFragmentManager.commit {
                replace<LoginFragment>(R.id.fragment)
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }

        return binding.root
    }
}