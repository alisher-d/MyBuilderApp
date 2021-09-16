package uz.texnopos.mybuilderapp.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.base.BaseFragment
import uz.texnopos.mybuilderapp.core.*
import uz.texnopos.mybuilderapp.databinding.FragmentProfileBinding
import uz.texnopos.mybuilderapp.ui.MainActivity


class ProfileFragment : BaseFragment(R.layout.fragment_profile) {
    private lateinit var navController: NavController
    private lateinit var bind: FragmentProfileBinding
    private val resumeAdapter = ResumeAdapter()
    private val auth: FirebaseAuth by inject()
    private val viewModel by viewModel<ProfileViewModel>()
    private val resumeIsEmpty=MutableLiveData<Boolean>()
    val bundle=Bundle()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        bind = FragmentProfileBinding.bind(view)
        setHasOptionsMenu(true)
        bind.apply {
            tvFullName.text= getFullName()
            tvPhone.text= getPhoneNumber()
            tvEmail.text= getEmail()
            settings.onClick {
                auth.signOut()
                getSharedPreferences().removeKey("succes")
                clearLoginPref()
                navController.navigate(R.id.action_navigation_profile_to_navigation_login)
            }

            firstCreateResume.onClick {
                bundle.clear()
                navController.navigate(R.id.action_navigation_profile_to_fragmentResume)
            }

            createNewResume.onClick {
                bundle.clear()
                navController.navigate(R.id.action_navigation_profile_to_fragmentResume)
            }
            rvResumes.adapter = resumeAdapter

            resumeAdapter.resumeCardOnClickListener {
                bundle.putParcelable("resume",it)
                navController.navigate(R.id.action_navigation_profile_to_fragmentResume,bundle)
            }

            resumeIsEmpty.observe(requireActivity(),{
                firstCreateResume.isVisible=it
                allResumes.isVisible=!it
            })
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getUserResumes(
            {
                resumeAdapter.add(it)
            },
            {
                resumeAdapter.modify(it)
            },
            {
                resumeAdapter.remove(it)
            },
            {
                resumeIsEmpty.value=it==0
            },
            {
                toast(it!!)
            }
        )
    }

    override fun onStart() {
        super.onStart()
        showNavBar(true)
    }
    }