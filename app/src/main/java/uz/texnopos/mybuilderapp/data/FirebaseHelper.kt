package uz.texnopos.mybuilderapp.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import uz.texnopos.mybuilderapp.data.models.JobModel
import uz.texnopos.mybuilderapp.data.models.ResumeModel
import uz.texnopos.mybuilderapp.data.models.UserModel

class FirebaseHelper(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) {

    fun addNewUser(
        user: Map<String,String>,
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
            .document(resume.resumeID!!).set(resume, SetOptions.merge())
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
                val jobs = mutableListOf<JobModel>()
                for (i in it) {
                    jobs.add(i.toObject(JobModel::class.java))
                }
                onSuccess.invoke(jobs)
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun getUserResumes(
        onResumeAdded: (resume: ResumeModel) -> Unit,
        onResumeModified: (resume: ResumeModel) -> Unit,
        onResumeRemoved: (resumeId: String) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        db.collection("users/${auth.currentUser!!.uid}/resumes")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    onFailure.invoke(e.localizedMessage)
                } else {
                    for (cv in snapshot!!.documentChanges) {
                        val resume = cv.document.toObject(ResumeModel::class.java)
                        when (cv.type) {
                            DocumentChange.Type.ADDED -> onResumeAdded.invoke(resume)
                            DocumentChange.Type.MODIFIED -> onResumeModified.invoke(resume)
                            DocumentChange.Type.REMOVED->onResumeRemoved.invoke(resume.resumeID!!)
                        }
                    }
                }
            }
    }
}