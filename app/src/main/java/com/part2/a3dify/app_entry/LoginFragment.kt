package com.part2.a3dify.app_entry

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.credentials.exceptions.GetCredentialException
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
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





    fun handleSignIn(result: GetCredentialResponse) {
        // Handle the successfully returned credential.
        val credential = result.credential

        when (credential) {
//            is PublicKeyCredential -> {
//                // Share responseJson such as a GetCredentialResponse on your server to
//                // validate and authenticate
//                responseJson = credential.authenticationResponseJson
//            }

            is PasswordCredential -> {
                // Send ID and password to your server to validate and authenticate.
                val username = credential.id
                val password = credential.password
                Log.d("TAG", username)
                Log.d("TAG", password)
            }

            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        // Use googleIdTokenCredential and extract id to validate and
                        // authenticate on your server.
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e("TAG", "Received an invalid google id token response", e)
                    }
                } else {
                    // Catch any unrecognized custom credential type here.
                    Log.e("TAG", "Unexpected type of credential")
                }
            }
//
            else -> {
                // Catch any unrecognized credential type here.
                Log.e("TAG", "Unexpected type of credential")
            }
        }
    }


//    private lateinit var gso: GoogleSignInOptions
//    private lateinit var mGoogleSignInClient: GoogleSignInClient

//    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result->
//        Log.d("Launcher", "Invoked")
//        if (result.resultCode == Activity.RESULT_OK) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//            handleSignInResult(task)
//            Log.d("Launcher", "Result OK")
//        }
//    }

//    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
//        try {
//            val account = task.getResult(ApiException::class.java)
//            // Signed in successfully, show authenticated UI or proceed with the account details.
//            Log.d("Sign In", "Successful")
//        } catch (e: ApiException) {
//            // The ApiException status code indicates the detailed failure reason.
//            Log.w("API Error", "signInResult:failed code=" + e.statusCode)
//        }
//    }

    // before view, check for login state of Google Account
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configure sign-in to request the user's ID, email address and basic profile.
        // ID and basic profile are included in DEFAULT_SIGN_IN
//        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(SERVER_CLIENT_ID)
//            .requestEmail()
//            .build() // for addional scopes, specify them with 'requestScopes'
//
//        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)

        val credentialManager = CredentialManager.create(requireContext())

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setServerClientId(SERVER_CLIENT_ID)
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        binding.googleLoginButton.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val result = credentialManager.getCredential(
                        request = request,
                        context = requireContext()
                    )
                    handleSignIn(result)
                } catch (e: GetCredentialException) {
                    Log.d("TAG1", e.message.toString())
                }
            }
        }

//        binding.googleLoginButton.setOnClickListener {
//            val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(requireContext())
//            if (account == null || account.idToken == null) {
//                val signInIntent = mGoogleSignInClient.signInIntent
////            startActivityForResult(signInIntent, RC_SIGN_IN)
//                resultLauncher.launch(signInIntent)
//            } else {
//                Log.d("Account Check", "Existing")
//                Log.d("email", "${account.email}")
//                Log.d("email", "${account.idToken}")
//                Log.d("email", "${account.id}")
//            }
//        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}