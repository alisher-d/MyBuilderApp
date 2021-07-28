package uz.texnopos.mybuilderapp.ui.home

import uz.texnopos.mybuilderapp.data.FirebaseHelper

class HomePresenter(val view:HomeView) {
//    private val dbHelper=FirebaseHelper()
    fun getJobList(){
        view.showProgress()
//        dbHelper.getJobList(
//            {
//                view.getJobs(it)
//                view.hideProgress()
//            },
//            {
//                view.showMessage(it!!)
//                view.hideProgress()
//            }
//        )
    }
}