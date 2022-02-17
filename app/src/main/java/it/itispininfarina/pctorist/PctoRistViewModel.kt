package it.itispininfarina.pctorist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import javax.annotation.Nonnull

class PctoRistViewModel(@Nonnull application: Application) : AndroidViewModel(application) {
    private var repository: PctoRistRepository = PctoRistRepository(application)
    private var userMutableLiveData: MutableLiveData<FirebaseUser>
    private var loggedoutMutableLiveData: MutableLiveData<Boolean>

        init {
            repository = PctoRistRepository(application)
            userMutableLiveData = repository.getUserMutableLiveData()
            loggedoutMutableLiveData = repository.getloggedoutMutableLiveData()
        }

    fun registra(email: String, password: String){
        repository.registra(email, password)
    }

    fun login(email: String, password: String){
        repository.login(email, password)
    }


    fun getUserMutableLiveData(): MutableLiveData<FirebaseUser>{
        return userMutableLiveData
    }

    fun getloggedoutMutableLiveData(): MutableLiveData<Boolean>
    {
        return loggedoutMutableLiveData
    }

    fun logout(){
        repository.logout()
    }

}