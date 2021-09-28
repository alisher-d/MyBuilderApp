package uz.texnopos.mybuilderapp.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.firestore.FirebaseFirestore
import uz.texnopos.mybuilderapp.core.Constants.SharedPref.USER_EMAIL
import uz.texnopos.mybuilderapp.core.Constants.SharedPref.USER_FULLNAME
import uz.texnopos.mybuilderapp.core.Constants.SharedPref.USER_PHONE_NUMBER
import uz.texnopos.mybuilderapp.core.Constants.USER_EXISTS
import uz.texnopos.mybuilderapp.core.getSharedPreferences
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
        onSuccess: (Boolean) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                onSuccess.invoke(true)
            } else {
                if (it.exception is FirebaseAuthInvalidCredentialsException) onFailure.invoke("Invalid OTP")
                else onFailure.invoke(it.exception?.message)
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
        onSuccess: () -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        db.collection("users").document(auth.currentUser!!.uid).get()
            .addOnSuccessListener {
                if (it.exists()) {
                    val user=it.toObject(UserModel::class.java)
                    getSharedPreferences().setValue(USER_FULLNAME, user?.fullname)
                    getSharedPreferences().setValue(USER_PHONE_NUMBER, user?.phone)
                    getSharedPreferences().setValue(USER_EMAIL, user?.email)
                    getSharedPreferences().setValue(USER_EXISTS, 1)
                    onSuccess.invoke()
                }
                else {
                    getSharedPreferences().setValue(USER_EXISTS, -1)
                    onSuccess.invoke()
                }
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }
}