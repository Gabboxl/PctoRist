package it.itispininfarina.pctorist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collectLatest


class LoginFragment : Fragment() {
    private val appViewModel: PctoRistViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_login, container, false)

        val emailLog = layout.findViewById<TextView>(R.id.editMailLogin)
        val passLog = layout.findViewById<TextView>(R.id.editPassLogin)
        val logBtn = layout.findViewById<Button>(R.id.loginButton)
        val progressLog = layout.findViewById<ProgressBar>(R.id.progressBarLogin)
        val creaAccountBtn = layout.findViewById<Button>(R.id.buttonNavigateCreaAccount)
        val passDimenticataBtn = layout.findViewById<Button>(R.id.buttonNavigateResetPassword)

        lifecycleScope.launchWhenStarted {
            appViewModel.getLoginResult().collectLatest { result ->
                if (result.isSuccessful) {


                    Toast.makeText(context, "Login completato", Toast.LENGTH_SHORT).show()

                    findNavController().navigate(R.id.ordiniFragment)



                }else {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Errore")
                        .setMessage(result.exception?.localizedMessage)
                        .setPositiveButton("Ok") { dialog, which ->
                        }
                        .show()
                }

                progressLog.visibility = View.INVISIBLE
                logBtn.visibility = View.VISIBLE
            }
        }



        creaAccountBtn.setOnClickListener {
            findNavController().navigate(R.id.registraFragment)
        }

        passDimenticataBtn.setOnClickListener {
            findNavController().navigate(R.id.resetPassFragment)
        }



        logBtn.setOnClickListener {
            if(emailLog.text.toString().isNotEmpty() && passLog.text.toString().isNotEmpty()) {
                        progressLog.visibility = View.VISIBLE
                        logBtn.visibility = View.INVISIBLE
                    appViewModel.login(emailLog.text.toString(), passLog.text.toString())

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