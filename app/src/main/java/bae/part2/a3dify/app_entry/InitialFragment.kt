package bae.part2.a3dify.app_entry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.add
import bae.part2.a3dify.R
import bae.part2.a3dify.databinding.FragmentInitialBinding

class InitialFragment : Fragment() {
    private lateinit var binding : FragmentInitialBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInitialBinding.inflate(layoutInflater)

        binding.nonMember.setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                add<MainFragment>(R.id.fragment)
            }
        }

        binding.member.setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                add<LoginFragment>(R.id.fragment)
            }
        }

        return binding.root
    }
}