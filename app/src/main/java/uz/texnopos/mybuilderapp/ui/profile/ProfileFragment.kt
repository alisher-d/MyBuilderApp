package uz.texnopos.mybuilderapp.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.base.BaseFragment
import uz.texnopos.mybuilderapp.core.*
import uz.texnopos.mybuilderapp.data.LoadingState
import uz.texnopos.mybuilderapp.data.models.UserModel
import uz.texnopos.mybuilderapp.data.models.UserModel2
import uz.texnopos.mybuilderapp.databinding.FragmentProfileBinding
import uz.texnopos.mybuilderapp.ui.MainActivity
import uz.texnopos.mybuilderapp.ui.resume.BuilderActivity


class ProfileFragment : BaseFragment(R.layout.fragment_profile) {
    private lateinit var navController: NavController
    private lateinit var bind: FragmentProfileBinding
    private val resumeAdapter = ResumeAdapter()
    private val auth: FirebaseAuth by inject()
    private val viewModel by viewModel<ProfileViewModel>()
    private lateinit var intent: Intent
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        bind = FragmentProfileBinding.bind(view)
        intent = Intent(requireActivity(), BuilderActivity::class.java)
        setHasOptionsMenu(true)
        bind.settings.onClick {
            auth.signOut()
            getSharedPreferences().removeKey("succes")
            clearLoginPref()
            navController.navigate(R.id.action_navigation_profile_to_navigation_login)
        }

        bind.firstCreateResume.onClick {
            intent.removeExtra("resume")
            startActivity(intent)
        }

        bind.createNewResume.onClick {
            intent.removeExtra("resume")
            startActivity(intent)
        }
        bind.rvResumes.adapter = resumeAdapter

        resumeAdapter.resumeCardOnClickListener {
            intent.putExtra("resume",it)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).navView.visibility = View.VISIBLE
    }


    private fun manageResumeViews(user: UserModel) {
        bind.tvFullName.text = user.fullname
        bind.tvEmail.text = user.email
        bind.tvPhone.text = user.phone
        intent.putExtra("user_info",UserModel2("",user.fullname,user.phone,user.email))
        if (user.resumes.isEmpty()) {
            bind.firstCreateResume.visibility = View.VISIBLE
            bind.createNewResume.visibility = View.GONE
        } else {
            bind.allResumes.visibility = View.VISIBLE
            bind.firstCreateResume.visibility = View.GONE
            bind.createNewResume.visibility = View.VISIBLE
            resumeAdapter.models = user.resumes
        }
    }


    }