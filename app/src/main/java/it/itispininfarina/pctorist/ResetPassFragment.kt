package it.itispininfarina.pctorist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ResetPassFragment : Fragment() {
    private val appViewModel: PctoRistViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_reset_pass, container, false)

        var resetbtn = layout.findViewById<Button>(R.id.resetButton)
        val textMailReset = layout.findViewById<TextView>(R.id.editMailReset)
        val textcomplete = layout.findViewById<TextView>(R.id.textViewresetComplete)
        val progressbarReset = layout.findViewById<ProgressBar>(R.id.progressBarReset)

        lifecycleScope.launchWhenStarted {
            appViewModel.getResetResult().collect { result ->
                if (result.isSuccessful) {

                    progressbarReset.visibility = View.INVISIBLE
                    textcomplete.visibility = View.VISIBLE
                    Toast.makeText(context, "Link inviato correttamente", Toast.LENGTH_SHORT).show()

                    findNavController().navigate(R.id.ordiniFragment)


                } else {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Errore")
                        .setMessage(result.exception?.localizedMessage)
                        .setPositiveButton("Ok") { dialog, which ->
                        }
                        .show()

                    resetbtn.visibility = View.VISIBLE
                    progressbarReset.visibility = View.INVISIBLE
                }
            }
        }

        resetbtn.setOnClickListener{
            if (textMailReset.text.toString().isNotEmpty()) {


                        resetbtn.visibility = View.INVISIBLE
                        progressbarReset.visibility = View.VISIBLE


                    appViewModel.resetPassword(textMailReset.text.toString())

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