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
import androidx.navigation.NavOptions
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


                    Toast.makeText(context, "Account creato correttamente", Toast.LENGTH_SHORT).show()

                    
                    val startDestination = findNavController().graph.startDestinationId
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(startDestination, false)
                        .build()

                    findNavController().navigate(R.id.ordiniFragment, null, navOptions)


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
            if (mailRegistra.text.toString().isNotEmpty() && passRegistra.text.toString()
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



        return layout
    }

}