package it.itispininfarina.pctorist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SessioneFragment : Fragment() {
    private val appViewModel: PctoRistViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //qua NON si mette l'osservatore per i cambiamenti dello stato dell'utente firebase perchè questo fragment viene ammazzato quando viene avviato subito il fragment di registrazione alla fine di questo OnCreate().


        if (appViewModel.getFirebaseUser().value != null) {

            val startDestination = findNavController().graph.startDestinationId
            val navOptions = NavOptions.Builder()
                .setPopUpTo(startDestination, false)
                .build()

            findNavController().navigate(R.id.ordiniFragment, null, navOptions)
            //Toast.makeText(context, "Hey sei già loggato + ", Toast.LENGTH_SHORT).show()
        } else {
            val startDestination = findNavController().graph.startDestinationId
            val navOptions = NavOptions.Builder()
                .setPopUpTo(startDestination, false)
                .build()
            findNavController().navigate(R.id.loginFragment, null, navOptions)
            //Toast.makeText(context, "heu non sei loggcatowttff + " + appViewModel.getFirebaseUser().value + Firebase.auth.currentUser, Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return null
    }

}