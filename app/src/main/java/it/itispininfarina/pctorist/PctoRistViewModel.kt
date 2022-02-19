package it.itispininfarina.pctorist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.annotation.Nonnull

class PctoRistViewModel(@Nonnull application: Application) : AndroidViewModel(application) {
    private var repository: PctoRistRepository = PctoRistRepository(application)
    private var registraResult: MutableSharedFlow<Task<AuthResult>>
    private var loginresultshared: MutableSharedFlow<Task<AuthResult>>
    private var resetresult: MutableSharedFlow<Task<Void>>
    private var firebaseUser: MutableStateFlow<FirebaseUser?>

    init {
        repository = PctoRistRepository(application)
        firebaseUser = repository.getFirebaseUser()
        registraResult = repository.getRegistraResult()
        loginresultshared = repository.getLoginResult()
        resetresult = repository.getResetResult()
    }

    suspend fun registra(email: String, password: String) {
        viewModelScope.launch {
            repository.registra(email, password)
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            repository.login(email, password)
        }
    }

    fun resetPassword(email: String){
        viewModelScope.launch {
            repository.resetPassword(email)
        }
    }

    fun getRegistraResult(): MutableSharedFlow<Task<AuthResult>> {
        return registraResult
    }

    fun getLoginResult(): MutableSharedFlow<Task<AuthResult>> {
        return loginresultshared
    }

    fun getFirebaseUser(): MutableStateFlow<FirebaseUser?> {
        return firebaseUser
    }

    fun getResetResult(): MutableSharedFlow<Task<Void>> {
        return resetresult
    }


    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

}