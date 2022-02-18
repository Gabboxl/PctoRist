package it.itispininfarina.pctorist

import android.app.Application
import android.content.ContentValues
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await


class PctoRistRepository(application: Application) {
    private var auth: FirebaseAuth
    private var currentUser: FirebaseUser?
    private var registraresult: MutableSharedFlow<Task<AuthResult>?>
    private var loginresult: MutableSharedFlow<Task<AuthResult>?>
    private var loggedoutMutableStateFlow: MutableStateFlow<Boolean>
    private var firebaseUser: FirebaseUser?


    init {

        auth = Firebase.auth
        currentUser = auth.currentUser
        loggedoutMutableStateFlow = MutableStateFlow(false)
        registraresult = MutableStateFlow(null)
        loginresult = MutableStateFlow(null)
        firebaseUser = auth.currentUser
    }

    suspend fun registra(email: String, password: String)  {
        val result = auth.createUserWithEmailAndPassword(email, password)
            result.await()

        firebaseUser = auth.currentUser
        registraresult.emit(result)
    }





    fun login(email: String, password: String) = flow<FirebaseUser>{
        val result = auth.signInWithEmailAndPassword(email, password)

                if (result.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                  //  _userMutableStateFlow.emit(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "signInWithEmail:failure", result.exception)
                }

    }

    fun resetPassword(email: String){
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful)
                {

                    Log.d(ContentValues.TAG, "sendPasswordResetEmail:success")

                } else {
                    Log.w(ContentValues.TAG, "sendPasswordResetEmail:failure", task.exception)
                }
            }
    }

    suspend fun logout() = flow<Boolean>{
        auth.signOut()
        loggedoutMutableStateFlow.emit(true)
    }

    fun getFirebaseUser(): FirebaseUser?{
        return firebaseUser
    }

    fun getRegistraResult(): MutableSharedFlow<Task<AuthResult>?>{
        return registraresult
    }

    fun getLoginResult(): MutableSharedFlow<Task<AuthResult>?>{
        return loginresult
    }

    fun getloggedoutMutableLiveData(): MutableStateFlow<Boolean>{
        return loggedoutMutableStateFlow
    }


}