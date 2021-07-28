package uz.texnopos.mybuilderapp.ui.home

import android.os.Bundle
import android.view.View

import uz.texnopos.mybuilderapp.base.BaseFragment
import uz.texnopos.mybuilderapp.ui.MainActivity
import uz.texnopos.mybuilderapp.R
import uz.texnopos.mybuilderapp.databinding.FragmentHomeBinding
import uz.texnopos.mybuilderapp.data.models.JobModel
import uz.texnopos.mybuilderapp.core.toast


class HomeFragment : BaseFragment(R.layout.fragment_home),HomeView {
    val presenter=HomePresenter(this)
    lateinit var bind: FragmentHomeBinding
    val adapter = JobListAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind = FragmentHomeBinding.bind(view)
        bind.rvBuilders.adapter = adapter
//        presenter.getJobList()
    }


    override fun onStart() {
        super.onStart()
        (activity as MainActivity).navView.visibility=View.VISIBLE
    }

    override fun getJobs(list: MutableList<JobModel>) {
        adapter.models=list
    }

    override fun showMessage(msg: String) {
        toast(msg)
    }
}