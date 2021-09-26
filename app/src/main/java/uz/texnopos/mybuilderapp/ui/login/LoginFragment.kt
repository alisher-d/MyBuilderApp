package uz.texnopos.mybuilderapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.base.BaseFragment
import uz.texnopos.mybuilderapp.core.*
import uz.texnopos.mybuilderapp.core.Constants.RC_SIGN_IN
import uz.texnopos.mybuilderapp.core.Constants.USER_EXISTS
import uz.texnopos.mybuilderapp.data.LoadingState
import uz.texnopos.mybuilderapp.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment(R.layout.fragment_login) {
    val auth: FirebaseAuth by inject()
    val viewModel by viewModel<LoginViewModel>()
    private lateinit var bind: FragmentLoginBinding
    private lateinit var navController: NavController
    private lateinit var googleSignInClient: GoogleSignInClient
    private val fragments = listOf(SignPhoneFragment(this), SignGmailFragment(this))
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind = FragmentLoginBinding.bind(view)
        setUpObserver()
        navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_activity_main)
        val adapter =
            ViewPagerAdapter(requireActivity().supportFragmentManager, lifecycle, fragments)
        bind.viewPager.adapter = adapter
        TabLayoutMediator(bind.tabLayout, bind.viewPager) { tab, pos ->
            tab.text = arrayOf("Phone", "Gmail")[pos]
        }.attach()
        bind.signInGoogle.onClick {
            if (isNetworkAvailable()) {
                showProgress()
                val signInIntent = googleSignInClient.signInIntent
                startActivityForResult(signInIntent, RC_SIGN_IN)
            }
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode ==RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(Constants.TAG, "firebaseAuthWithGoogle:" + account.id)
                viewModel.authWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                toast(e.message!!)
                Log.w(Constants.TAG, "Google sign in failed", e)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.updateUI()
    }

    private fun setUpObserver() {
        viewModel.exists.observe(requireActivity(), {
            when (it.status) {
                LoadingState.LOADING -> showProgress()
                LoadingState.SUCCESS -> {
                    if (it!!.data!!) getSharedPreferences().setValue(USER_EXISTS, 1)
                    else getSharedPreferences().setValue(USER_EXISTS, -1)
                    viewModel.updateUI()
                }
                LoadingState.ERROR -> {
                    hideProgress()
                    toast(it.message!!)
                }
            }

        })
        viewModel.condition.observe(requireActivity(), { condition ->
            if (auth.currentUser != null) {
                when (condition) {
                    0 -> {
                        showProgress()
                        viewModel.getUserData()
                    }
                    1 -> {
                        hideProgress()
                        requireActivity().onBackPressed()
                    }
                    -1 -> {
                        hideProgress()
                        navController.navigate(R.id.action_loginFragment_to_shortInfoFragment)
                    }
                }
            }
        })
        viewModel.registration.observe(requireActivity(), {
            when (it.status) {
                LoadingState.LOADING -> showProgress()
                LoadingState.SUCCESS -> {
                    hideProgress()
                    viewModel.updateUI()
                }
                LoadingState.ERROR -> {
                    hideProgress()
                    toast(it.message!!)
                }
            }
        })
    }
}