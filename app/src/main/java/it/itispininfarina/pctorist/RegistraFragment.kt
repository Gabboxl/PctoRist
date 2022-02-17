package it.itispininfarina.pctorist

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class RegistraFragment : Fragment() {
    private val appViewModel: PctoRistViewModel by activityViewModels()


    override fun onStart() {
        super.onStart()

        setHasOptionsMenu(true)
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




        appViewModel.getUserMutableLiveData().observe(viewLifecycleOwner, object : Observer<FirebaseUser?> {
            override fun onChanged(firebaseUser: FirebaseUser?) {
                if(firebaseUser != null){
                    Toast.makeText(context, "Login effettuato omgggg", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.ordinitestFragment)
                }
            }

        })


        val mail = layout.findViewById<EditText>(R.id.editMail)
        val pass = layout.findViewById<EditText>(R.id.editPass)
        val resistrabtn = layout.findViewById<Button>(R.id.registraButton)
        val scrivibtn = layout.findViewById<Button>(R.id.buttonScrivi)
        val leggibtn = layout.findViewById<Button>(R.id.buttonLeggi)

        resistrabtn.setOnClickListener {
            if(mail.text.toString().isNotEmpty() || pass.text.toString().isNotEmpty()) {
                appViewModel.registra(mail.text.toString(), pass.text.toString())
            }else {
                val dialogerror = MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Errore")
                    .setMessage("Non puoi lasciare i campi email o password vuoti.")
                    .setPositiveButton("Ok") { dialog, which ->
                    }
                    .show()
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