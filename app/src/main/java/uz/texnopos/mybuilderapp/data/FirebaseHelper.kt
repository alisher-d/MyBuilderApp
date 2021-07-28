package uz.texnopos.mybuilderapp.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import uz.texnopos.mybuilderapp.core.Constants.SharedPref.ADDRESS
import uz.texnopos.mybuilderapp.core.Constants.SharedPref.CREATED
import uz.texnopos.mybuilderapp.core.Constants.SharedPref.DESCRIPTION
import uz.texnopos.mybuilderapp.core.Constants.SharedPref.PROFESSION
import uz.texnopos.mybuilderapp.core.Constants.SharedPref.PUBLISH
import uz.texnopos.mybuilderapp.core.Constants.SharedPref.SELECTABLE_JOBS
import uz.texnopos.mybuilderapp.core.Constants.SharedPref.USER_EMAIL
import uz.texnopos.mybuilderapp.core.Constants.SharedPref.USER_FULL_NAME
import uz.texnopos.mybuilderapp.core.Constants.SharedPref.USER_PHONE_NUMBER
import uz.texnopos.mybuilderapp.core.getSharedPreferences
import uz.texnopos.mybuilderapp.data.models.JobModel
import uz.texnopos.mybuilderapp.data.models.ResumeModel
import uz.texnopos.mybuilderapp.data.models.UserModel
import uz.texnopos.mybuilderapp.data.models.UserModel2

class FirebaseHelper(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) {

    fun addNewUser(
        user: UserModel2,
        onSuccess: (msg: String) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        db.collection("users").document(auth.currentUser!!.uid).set(user)
            .addOnSuccessListener {
                onSuccess.invoke("User added")
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun getUserData(
        onSuccess: (data: UserModel?) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        db.collection("users").document(auth.currentUser!!.uid).get()
            .addOnCompleteListener {
                if (it.result.exists())
                    onSuccess.invoke(it.result.toObject(UserModel::class.java)!!)
                else {
                    onSuccess.invoke(null)
                    onFailure.invoke("User not found")
                }
            }

    }

    fun setResume(
        resume: ResumeModel,
        onSuccess: (msg: String) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        db.collection("users/${auth.currentUser?.uid}/resumes")
            .document(resume.resumeID).set(resume, SetOptions.merge())
            .addOnSuccessListener {
                onSuccess.invoke("Saved")
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun getJobs(
        onSuccess: (jobs:MutableList<JobModel>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        db.collection("jobs").get()
            .addOnSuccessListener {
            val jobs= mutableListOf<JobModel>()
                for (i in it){
                    jobs.add(i.toObject(JobModel::class.java))
                }
                onSuccess.invoke(jobs)
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun updateUserInfo(
        user: UserModel2,
        onSuccess: (msg: String) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        user.userId=auth.currentUser!!.uid
        db.collection("users").document(user.userId).set(user, SetOptions.merge())
            .addOnSuccessListener {
                onSuccess.invoke("Updated")
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }


    private fun saveUserData(user: UserModel) {
        getSharedPreferences().setValue(USER_FULL_NAME, user.fullname)
        getSharedPreferences().setValue(USER_PHONE_NUMBER, user.phone)
        getSharedPreferences().setValue(USER_EMAIL, user.email)

    }

    private fun saveBuilderData(resume: ResumeModel) {
        getSharedPreferences().setValue(CREATED, resume.isCreated)
        getSharedPreferences().setValue(PUBLISH, resume.isPublished)
        getSharedPreferences().setValue(PROFESSION, resume.profession)
        getSharedPreferences().setValue(ADDRESS, resume.address)
        getSharedPreferences().setValue(DESCRIPTION, resume.description)
        getSharedPreferences().setValue(SELECTABLE_JOBS, resume.trades)
    }
}