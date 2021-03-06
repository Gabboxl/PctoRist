package it.itispininfarina.pctorist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.annotation.Nonnull

class PctoRistViewModel(@Nonnull application: Application) : AndroidViewModel(application) {
    private var repository: PctoRistRepository = PctoRistRepository(application)
    private var registraResult: MutableSharedFlow<Task<AuthResult>>
    private var loginresultshared: MutableSharedFlow<Task<AuthResult>>
    private var resetresult: MutableSharedFlow<Task<Void>>
    private var firebaseUser: MutableStateFlow<FirebaseUser?>
    private var scriviresult: MutableSharedFlow<Task<Void>>
    private val scriviexception: MutableSharedFlow<Exception>

    init {
        repository = PctoRistRepository(application)
        firebaseUser = repository.getFirebaseUser()
        registraResult = repository.getRegistraResult()
        loginresultshared = repository.getLoginResult()
        resetresult = repository.getResetResult()
        scriviresult = repository.getScriviResult()
        scriviexception = repository.getScriviException()
    }

    fun registra(email: String, password: String) {
        viewModelScope.launch {
            repository.registra(email, password)
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            repository.login(email, password)
        }
    }

    fun creaOrdine(
        prod1: Int,
        prod2: Int,
        prod3: Int,
        prod4: Int,
        prod5: Int,
        prod6: Int,
        prod7: Int
    ) {
        viewModelScope.launch {
            repository.creaOrdine(prod1, prod2, prod3, prod4, prod5, prod6, prod7)
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun resetPassword(email: String) {
        viewModelScope.launch {
            repository.resetPassword(email)
        }
    }

    fun getScriviResult(): MutableSharedFlow<Task<Void>> {
        return scriviresult
    }

    fun getScriviException(): MutableSharedFlow<Exception> {
        return scriviexception
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


}