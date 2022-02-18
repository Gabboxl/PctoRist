package it.itispininfarina.pctorist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.annotation.Nonnull

class PctoRistViewModel(@Nonnull application: Application) : AndroidViewModel(application) {
    private var repository: PctoRistRepository = PctoRistRepository(application)
    private var loggedoutMutableLiveData: MutableStateFlow<Boolean>
    private var registraResult: MutableSharedFlow<Task<AuthResult>?>
    private var firebaseUser: FirebaseUser?

        init {
            firebaseUser = repository.getFirebaseUser()
            repository = PctoRistRepository(application)
            registraResult = repository.getRegistraResult()
            loggedoutMutableLiveData = repository.getloggedoutMutableLiveData()
        }

    suspend fun registra(email: String, password: String){
        repository.registra(email, password)
    }

    fun login(email: String, password: String){
        repository.login(email, password)
    }


    fun getRegistraResult(): MutableSharedFlow<Task<AuthResult>?>{
        return registraResult
    }

    fun getFirebaseUser(): FirebaseUser?{
        return firebaseUser
    }

    fun getloggedoutMutableLiveData(): MutableStateFlow<Boolean>
    {
        return loggedoutMutableLiveData
    }

    suspend fun logout(){
        repository.logout()
    }

}