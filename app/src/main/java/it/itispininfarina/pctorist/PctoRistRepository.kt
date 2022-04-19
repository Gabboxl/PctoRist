package it.itispininfarina.pctorist

import android.app.Application
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await


class PctoRistRepository(application: Application) {
    private var auth: FirebaseAuth
    private var db: FirebaseFirestore
    private var currentUser: FirebaseUser?
    private var registraresult = MutableSharedFlow<Task<AuthResult>>()
    private var loginresult = MutableSharedFlow<Task<AuthResult>>()
    private var resetresult = MutableSharedFlow<Task<Void>>()
    private var firebaseUser: MutableStateFlow<FirebaseUser?>

    private var scriviresult = MutableSharedFlow<Task<Void>>()
    private var scriviexception = MutableSharedFlow<Exception>()

    init {

        auth = Firebase.auth
        db = Firebase.firestore
        currentUser = auth.currentUser
        firebaseUser = MutableStateFlow(auth.currentUser)

        //    loginresult = MutableSharedFlow<Task<AuthResult>?>().asSharedFlow()
    }

    suspend fun registra(email: String, password: String) {
        val result = auth.createUserWithEmailAndPassword(email, password)
        result.addOnCompleteListener {
            runBlocking {
                registraresult.emit(it)

                if (result.isSuccessful) {
                    firebaseUser.emit(it.result?.user)
                }
            }
        }
    }


    suspend fun login(email: String, password: String) {
        val result = auth.signInWithEmailAndPassword(email, password)
        result.addOnCompleteListener {
            runBlocking {
                loginresult.emit(it)

                if (result.isSuccessful) {
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

    suspend fun creaOrdine(
        prod1: Int,
        prod2: Int,
        prod3: Int,
        prod4: Int,
        prod5: Int,
        prod6: Int,
        prod7: Int
    ) {
        try {

            var nordinenuovo: Long = 1
            val pathordini =
                db.collection("users").document(auth.currentUser!!.email!!).collection("ordini")
            val pathcount = db.collection("users").document(auth.currentUser!!.email!!).get().await()

             if(pathcount.get("nordini") != null) {
                 nordinenuovo = pathcount.get("nordini") as Long + 1

                 val dati = hashMapOf(
                     "nordini" to nordinenuovo
                 )
                 db.collection("users").document(auth.currentUser!!.email!!).set(dati)

                } else {
                 val dati = hashMapOf(
                     "nordini" to nordinenuovo
                 )
                 db.collection("users").document(auth.currentUser!!.email!!).set(dati)
             }


            Log.e("triplosette", nordinenuovo.toString())


            val qtprodotti = hashMapOf(
                "nordine" to nordinenuovo,
                "prod1" to prod1,
                "prod2" to prod2,
                "prod3" to prod3,
                "prod4" to prod4,
                "prod5" to prod5,
                "prod6" to prod6,
                "prod7" to prod7,
            )

            //aggiungo un nuovo documento di test al database
            val resultfinale = pathordini.document(nordinenuovo.toString()).set(qtprodotti)

            scriviresult.emit(resultfinale)


        } catch (e: Exception) {
            scriviexception.emit(e)
        }
    }

    fun getScriviResult(): MutableSharedFlow<Task<Void>> {
        return scriviresult
    }

    fun getScriviException(): MutableSharedFlow<Exception>{
        return scriviexception
    }

    fun getFirebaseUser(): MutableStateFlow<FirebaseUser?> {
        return firebaseUser
    }

    fun getRegistraResult(): MutableSharedFlow<Task<AuthResult>> {
        return registraresult
    }

    fun getLoginResult(): MutableSharedFlow<Task<AuthResult>> {
        return loginresult
    }

    fun getResetResult(): MutableSharedFlow<Task<Void>> {
        return resetresult
    }


}