package it.itispininfarina.pctorist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RegistraFragment : Fragment() {
    private val appViewModel: PctoRistViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val layout = inflater.inflate(R.layout.fragment_registra, container, false)


        val mailRegistra = layout.findViewById<EditText>(R.id.editMailRegistra)
        val passRegistra = layout.findViewById<EditText>(R.id.editPassRegistra)
        val registrabtn = layout.findViewById<Button>(R.id.registraButton)
        // val scrivibtn = layout.findViewById<Button>(R.id.buttonScrivi)
        // val leggibtn = layout.findViewById<Button>(R.id.buttonLeggi)
        val progressRegistra = layout.findViewById<ProgressBar>(R.id.progressBarRegistra)



        lifecycleScope.launchWhenStarted {
            appViewModel.getRegistraResult().collect { result ->
                if (result.isSuccessful) {


                    Toast.makeText(context, "Registrazione completata", Toast.LENGTH_SHORT).show()

                    findNavController().navigate(R.id.ordiniFragment)


                } else {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Errore")
                        .setMessage(result.exception?.localizedMessage)
                        .setPositiveButton("Ok") { dialog, which ->
                        }
                        .show()
                }

                progressRegistra.visibility = View.INVISIBLE
                registrabtn.visibility = View.VISIBLE
            }
        }




        registrabtn.setOnClickListener {
            if (mailRegistra.text.toString().isNotEmpty() || passRegistra.text.toString()
                    .isNotEmpty()
            ) {
                appViewModel.viewModelScope.launch(Dispatchers.Default) {
                    withContext(Dispatchers.Main)
                    {
                        progressRegistra.visibility = View.VISIBLE
                        registrabtn.visibility = View.INVISIBLE
                    }

                    appViewModel.registra(
                        mailRegistra.text.toString(),
                        passRegistra.text.toString()
                    )
                }
            } else {
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

        /*  scrivibtn.setOnClickListener {
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
          }*/

        return layout
    }

}