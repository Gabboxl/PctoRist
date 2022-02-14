package it.itispininfarina.pctorist

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class RegistraFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Toast.makeText(requireContext(), "bella sei già loggato", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val layout = inflater.inflate(R.layout.fragment_registra, container, false)


        val mail = layout.findViewById<EditText>(R.id.editMail)
        val pass = layout.findViewById<EditText>(R.id.editPass)
        val loginbtn = layout.findViewById<Button>(R.id.loginButton)
        val scrivibtn = layout.findViewById<Button>(R.id.buttonScrivi)
        val leggibtn = layout.findViewById<Button>(R.id.buttonLeggi)




        //inizializzo l'autenticazione firebase
        auth = Firebase.auth


        loginbtn.setOnClickListener {
            auth.createUserWithEmailAndPassword(mail.text.toString(), pass.text.toString())
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(ContentValues.TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        Toast.makeText(requireContext(), user.toString(), Toast.LENGTH_LONG)
                            .show()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            requireContext(), "Authentication failed. " + task.result,
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
                        requireContext(),
                        "DocumentSnapshot added with ID: ${documentReference.id}", //lesgoo
                        Toast.LENGTH_LONG
                    ).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Errore aggiunta doc $e", Toast.LENGTH_LONG)
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
                        requireContext(),
                        "${doc.id} => ${doc.data}",
                        Toast.LENGTH_LONG
                    ).show()

                    //   }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(
                        requireContext(),
                        "Errore prendendo docs $exception",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }

        return layout
    }

}