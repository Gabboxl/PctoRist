package it.itispininfarina.pctorist

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.currentRecomposeScope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.coroutineContext


class PctoRistRepository(application: Application) {
    private var auth: FirebaseAuth
    private var currentUser: FirebaseUser?
    private var registraresult = MutableSharedFlow<Task<AuthResult>>()
    private var _loginresult = MutableSharedFlow<Task<AuthResult>>()
    private var loginresultshared = _loginresult.asSharedFlow()
    private var loggedoutMutableStateFlow: MutableStateFlow<Boolean>
    private var firebaseUser: MutableStateFlow<FirebaseUser?>


    init {

        auth = Firebase.auth
        currentUser = auth.currentUser
        loggedoutMutableStateFlow = MutableStateFlow(true)
        firebaseUser = MutableStateFlow(auth.currentUser)

    //    loginresult = MutableSharedFlow<Task<AuthResult>?>().asSharedFlow()
    }

    suspend fun registra(email: String, password: String)  {
        val result = auth.createUserWithEmailAndPassword(email, password)
        result.addOnCompleteListener {  runBlocking {
            registraresult.emit(it)
            firebaseUser.emit(auth.currentUser)
        } }
    }



    suspend fun login(email: String, password: String) {
        val result = auth.signInWithEmailAndPassword(email, password)
        result.addOnCompleteListener {  runBlocking {
            _loginresult.emit(it)
            loggedoutMutableStateFlow.emit(false)
            firebaseUser.emit(it.result?.user)
        }

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

    suspend fun logout() {
        auth.signOut()
        //loggedoutMutableStateFlow.emit(true)
        firebaseUser.emit(auth.currentUser)
    }

    fun getFirebaseUser(): MutableStateFlow<FirebaseUser?>{
        return firebaseUser
    }

    fun getRegistraResult(): MutableSharedFlow<Task<AuthResult>>{
        return registraresult
    }

    fun getLoginResult(): SharedFlow<Task<AuthResult>>{
        return loginresultshared
    }

    fun getloggedoutMutableLiveData(): MutableStateFlow<Boolean>{
        return loggedoutMutableStateFlow
    }


}