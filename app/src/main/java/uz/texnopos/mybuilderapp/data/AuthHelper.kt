package uz.texnopos.mybuilderapp.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.firestore.FirebaseFirestore
import uz.texnopos.mybuilderapp.core.Constants
import uz.texnopos.mybuilderapp.data.models.UserModel

class AuthHelper(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) {

    fun authWithGoogle(
        idToken: String,
        onSuccess: (boolean:Boolean) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) onSuccess.invoke(true)
                else onFailure.invoke(task.exception?.localizedMessage!!)
            }
    }

    fun phoneAuth(
        credential: PhoneAuthCredential,
        onSuccess: (boolean: Boolean) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                onSuccess.invoke(true)
            } else {
                onFailure.invoke(it.exception?.message)
            }
        }
    }
    fun signInWithGoogle(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onSuccess.invoke()
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
            .addOnSuccessListener {
                if (it!=null) onSuccess.invoke(it.toObject(UserModel::class.java))
                else onSuccess.invoke(null)
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }
}