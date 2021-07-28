package uz.texnopos.mybuilderapp.base

import androidx.fragment.app.Fragment

abstract class BaseFragment(contentLayout:Int):Fragment(contentLayout) {
    fun hideProgress() {
        if (activity != null)
            (requireActivity() as AppBaseActivity).showProgress(false)
    }
    fun showProgress() {
        if (activity != null)
            (requireActivity() as AppBaseActivity).showProgress(true)
    }

}