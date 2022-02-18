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
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.runBlocking


class PctoRistRepository(application: Application) {
    private var auth: FirebaseAuth
    private var currentUser: FirebaseUser?
    private var registraresult = MutableSharedFlow<Task<AuthResult>>()
    private var _loginresult = MutableSharedFlow<Task<AuthResult>>()
    private var loginresultshared = _loginresult.asSharedFlow()
    private var resetresult = MutableSharedFlow<Task<Void>>()
    private var loggedoutMutableStateFlow: MutableStateFlow<Boolean>
    private var firebaseUser: MutableStateFlow<FirebaseUser?>


    init {

        auth = Firebase.auth
        currentUser = auth.currentUser
        loggedoutMutableStateFlow = MutableStateFlow(true)
        firebaseUser = MutableStateFlow(auth.currentUser)

        //    loginresult = MutableSharedFlow<Task<AuthResult>?>().asSharedFlow()
    }

    suspend fun registra(email: String, password: String) {
        val result = auth.createUserWithEmailAndPassword(email, password)
        result.addOnCompleteListener {
            runBlocking {
                registraresult.emit(it)

                if(result.isSuccessful) {
                    firebaseUser.emit(it.result?.user)
                }
            }
        }
    }


    suspend fun login(email: String, password: String) {
        val result = auth.signInWithEmailAndPassword(email, password)
        result.addOnCompleteListener {
            runBlocking {
                _loginresult.emit(it)
                //loggedoutMutableStateFlow.emit(false)

                if(result.isSuccessful) {
                    firebaseUser.emit(it.result?.user)
                }
            }

        }

    }

    suspend fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                runBlocking {
                    resetresult.emit(task)
                }
            }
    }

    suspend fun logout() {
        auth.signOut()
        //loggedoutMutableStateFlow.emit(true)
        firebaseUser.emit(auth.currentUser)
    }

    fun getFirebaseUser(): MutableStateFlow<FirebaseUser?> {
        return firebaseUser
    }

    fun getRegistraResult(): MutableSharedFlow<Task<AuthResult>> {
        return registraresult
    }

    fun getLoginResult(): SharedFlow<Task<AuthResult>> {
        return loginresultshared
    }

    fun getResetResult(): MutableSharedFlow<Task<Void>> {
        return resetresult
    }

    fun getloggedoutMutableLiveData(): MutableStateFlow<Boolean> {
        return loggedoutMutableStateFlow
    }


}