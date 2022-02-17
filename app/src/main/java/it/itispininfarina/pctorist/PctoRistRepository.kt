package it.itispininfarina.pctorist

import android.app.Application
import android.content.ContentValues
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class PctoRistRepository(application: Application) {
    private var auth: FirebaseAuth
    private var userMutableLiveData: MutableLiveData<FirebaseUser>
    private var application: Application
    private var loggedoutMutableLiveData: MutableLiveData<Boolean>


    init {
        auth = Firebase.auth
        userMutableLiveData = MutableLiveData()
        this.application = Application()

        loggedoutMutableLiveData = MutableLiveData()
    }

    fun registra(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    userMutableLiveData.value = user!!
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)

                    //faccio niente
                }
            }
    }

    fun login(email: String, password: String){

    }

    fun logout(){
        auth.signOut()
        loggedoutMutableLiveData.value= true
    }

    fun getUserMutableLiveData(): MutableLiveData<FirebaseUser>{
        return userMutableLiveData
    }

    fun getloggedoutMutableLiveData(): MutableLiveData<Boolean>{
        return loggedoutMutableLiveData
    }


}