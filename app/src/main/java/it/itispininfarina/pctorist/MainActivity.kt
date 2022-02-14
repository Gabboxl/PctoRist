package it.itispininfarina.pctorist

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Toast.makeText(applicationContext, "bella sei già loggato", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mail = findViewById<EditText>(R.id.editMail)
        val pass = findViewById<EditText>(R.id.editPass)
        val loginbtn = findViewById<Button>(R.id.loginButton)
        val scrivibtn = findViewById<Button>(R.id.buttonScrivi)
        val leggibtn = findViewById<Button>(R.id.buttonLeggi)




        //inizializzo l'autenticazione firebase
        auth = Firebase.auth


        loginbtn.setOnClickListener {
            auth.createUserWithEmailAndPassword(mail.text.toString(), pass.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        Toast.makeText(applicationContext, user.toString(), Toast.LENGTH_LONG)
                            .show()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed. " + task.result,
                            Toast.LENGTH_SHORT
                        ).show()

                        //faccio niente
                    }
                }
        }



        //inizializzo il database
        val db = Firebase.firestore

        scrivibtn.setOnClickListener {
            // Create a new user with a first and last name
            val usernuov = hashMapOf(
                "first" to "Ada",
                "last" to "Lovelace",
                "born" to 1815
            )

            //aggiungo un nuovo documento di test al database
            db.collection("users")
                .add(usernuov)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(
                        applicationContext,
                        "DocumentSnapshot added with ID: ${documentReference.id}", //lesgoo
                        Toast.LENGTH_LONG
                    ).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(applicationContext, "Errore aggiunta doc $e", Toast.LENGTH_LONG)
                        .show()
                }

        }


        leggibtn.setOnClickListener {
            //leggo dati


            db.collection("users")
                .get()
                .addOnSuccessListener { result ->
                    val doc: DocumentSnapshot = result.documents[0] //prendo solo il primo documento
                    //     for (document in result) { //faccio qualcosa per tutti i documenti
                    Toast.makeText(
                        applicationContext,
                        "${doc.id} => ${doc.data}",
                        Toast.LENGTH_LONG
                    ).show()

                    //   }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(
                        applicationContext,
                        "Errore prendendo docs $exception",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }


    }
}