package com.part2.a3dify.app_entry

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.internal.SignInHubActivity
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.part2.a3dify.R
import com.part2.a3dify.app_containers.LoginFragmentContainer
import com.part2.a3dify.app_containers.ThrDifyApplication
import com.part2.a3dify.app_entry.viewmodels.LoginFragmentViewModel
import com.part2.a3dify.common.SERVER_CLIENT_ID
import com.part2.a3dify.databinding.FragmentLoginBinding
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private lateinit var binding : FragmentLoginBinding
    private val viewModel : LoginFragmentViewModel by viewModels {
        var temp = (requireActivity().application as ThrDifyApplication).mainContainer.loginFragmentContainer
        if (temp == null) {
            temp = LoginFragmentContainer()
            return@viewModels temp.loginFragmentViewModelFactory
        } else
            return@viewModels temp.loginFragmentViewModelFactory
    }

    private lateinit var gso: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result->
        Log.d("Launcher", "${result.resultCode}")
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
            Log.d("Launcher", "Result OK")
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI or proceed with the account details.
            Log.d("Sign In", "Successful")

            if (account != null) {
                Log.d("email", "${account.email}")
                Log.d("email", "${account.serverAuthCode}")
                Log.d("email", "${account.idToken}")
                Log.d("email", "${account.grantedScopes}")
                Log.d("email", "${account.isExpired}")
                Log.d("email", "${account.id}")
            }
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            Log.w("API Error", "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun moveSignUpActivity() {
        requireActivity().run {
            startActivity(Intent(requireContext(), SignInHubActivity::class.java))
            finish()
        }
    }

    // before view, check for login state of Google Account
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Configure sign-in to request the user's ID, email address and basic profile.
        // ID and basic profile are included in DEFAULT_SIGN_IN
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestServerAuthCode(getString(R.string.server_client_id)) // Only for backend Server
            .requestScopes(Scope(Scopes.DRIVE_APPFOLDER))
            .requestEmail()
            .build() // for addional scopes, specify them with 'requestScopes'

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        binding = FragmentLoginBinding.inflate(inflater)

        binding.googleLoginButton.setOnClickListener {
            val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(requireContext())

            mGoogleSignInClient.signOut()

            val signInIntent = mGoogleSignInClient.signInIntent
//            startActivityForResult(signInIntent, RC_SIGN_IN)
            resultLauncher.launch(signInIntent)

//            if (account == null) {
//                val signInIntent = mGoogleSignInClient.signInIntent
////            startActivityForResult(signInIntent, RC_SIGN_IN)
//                resultLauncher.launch(signInIntent)
//            } else {
//                Log.d("Account Check", "Existing")
//                Log.d("email", "${account.email}")
//                Log.d("email", "${account.serverAuthCode}")
//                Log.d("email", "${account.id}")
//            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}