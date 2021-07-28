package uz.texnopos.mybuilderapp.ui.home

import uz.texnopos.mybuilderapp.data.models.JobModel

interface HomeView {
    fun getJobs(list:MutableList<JobModel>)
    fun showMessage(msg:String)
    fun showProgress()
    fun hideProgress()

}
