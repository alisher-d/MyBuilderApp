package uz.texnopos.mybuilderapp.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import uz.texnopos.mybuilderapp.core.Constants.TAG
import uz.texnopos.mybuilderapp.data.models.Feedback
import uz.texnopos.mybuilderapp.data.models.JobModel
import uz.texnopos.mybuilderapp.data.models.ResumeModel

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
        onResumeAdded: (ResumeModel) -> Unit,
        onResumeModified: (ResumeModel) -> Unit,
        onResumeRemoved: (String) -> Unit,
        onResumesSize:(Int)->Unit,
        onFailure: (String?) -> Unit
    ) {
        db.collection("users/${auth.currentUser!!.uid}/resumes")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    onFailure.invoke(e.localizedMessage)
                } else {
                    onResumesSize.invoke(snapshot?.documents!!.size)
                    for (cv in snapshot.documentChanges) {
                        val resume = cv.document.toObject(ResumeModel::class.java)
                        when (cv.type) {
                            DocumentChange.Type.ADDED -> onResumeAdded.invoke(resume)
                            DocumentChange.Type.MODIFIED -> onResumeModified.invoke(resume)
                            DocumentChange.Type.REMOVED -> onResumeRemoved.invoke(resume.resumeID!!)
                        }
                    }
                }
            }
    }
    fun getJobs(
        onJobAdded: (JobModel) -> Unit,
        onJobModified: (JobModel) -> Unit,
        onJobRemoved: (String) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        db.collection("jobs")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    onFailure.invoke(e.localizedMessage)
                } else {
                    for (cv in snapshot!!.documentChanges) {
                        val job = cv.document.toObject(JobModel::class.java)
                        when (cv.type) {
                            DocumentChange.Type.ADDED -> onJobAdded.invoke(job)
                            DocumentChange.Type.MODIFIED -> onJobModified.invoke(job)
                            DocumentChange.Type.REMOVED -> onJobRemoved.invoke(job.jobId!!)
                        }
                    }
                }
            }
    }

    fun removeResume(
        resumeId: String,
        onSuccess: (msg: String) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        db.collection("users/${auth.currentUser!!.uid}/resumes")
            .document(resumeId).delete()
            .addOnSuccessListener {
                onSuccess.invoke("Removed")
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun getSingleResume(
        userId: String, resumeId: String,
        onSuccess: (ResumeModel) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        db.collection("users/${userId}/resumes").document(resumeId).get()
            .addOnSuccessListener {
                val resume = it.toObject(ResumeModel::class.java)!!
                onSuccess.invoke(resume)
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun postFeedback(
        feedback: Feedback,
        onSuccess: (String) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        feedback.authorId = auth.currentUser!!.uid
        db.collection("users/${feedback.receiverId}/resumes/${feedback.resumeId}/feedbacks")
            .document(feedback.id!!).set(feedback)
            .addOnSuccessListener {
                onSuccess.invoke("Thanks")
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun getAllFeedbacks(
        userId: String, resumeId: String,
        onAdded: (Feedback) -> Unit,
        onModified: (Feedback) -> Unit,
        onRemoved: (String) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        db.collection("users/${userId}/resumes/${resumeId}/feedbacks")
            .addSnapshotListener { docs, e ->
                if (e!=null){
                    onFailure.invoke(e.localizedMessage)
                }else{
                    if (docs!!.documents.isNotEmpty())
                    for (doc in docs.documentChanges){
                        val feedback=doc.document.toObject(Feedback::class.java)
                        Log.d(TAG, "getAllFeedbacks: ${feedback} ")
                        when(doc.type) {
                            DocumentChange.Type.ADDED -> onAdded.invoke(feedback)
                            DocumentChange.Type.MODIFIED -> onModified.invoke(feedback)
                            DocumentChange.Type.REMOVED-> onRemoved.invoke(feedback.id!!)
                        }
                    }
                }

            }

    }
}