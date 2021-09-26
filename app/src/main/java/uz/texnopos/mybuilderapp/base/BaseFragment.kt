package uz.texnopos.mybuilderapp.base

import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import uz.texnopos.mybuilderapp.ui.MainActivity
import uz.texnopos.mybuilderapp.ui.MainFragment

abstract class BaseFragment(contentLayout:Int):Fragment(contentLayout) {
    fun hideProgress() {
        if (activity != null)
            (requireActivity() as AppBaseActivity).showProgress(false)
    }
    fun showProgress() {
        if (activity != null)
            (requireActivity() as AppBaseActivity).showProgress(true)
    }
    fun showNavBar(fragment: Fragment,isShow:Boolean){
        (fragment.parentFragmentManager as MainFragment).navView.isVisible=isShow
    }

}