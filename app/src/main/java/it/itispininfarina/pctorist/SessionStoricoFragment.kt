package it.itispininfarina.pctorist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

class SessionStoricoFragment : Fragment() {
    private val appViewModel: PctoRistViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //qua NON si mette l'osservatore per i cambiamenti dello stato dell'utente firebase perchè questo fragment viene ammazzato quando viene avviato subito il fragment di registrazione alla fine di questo OnCreate().


        if (appViewModel.getFirebaseUser().value != null) {

            val startDestination = findNavController().graph.startDestinationId
            val navOptions = NavOptions.Builder()
                .setPopUpTo(startDestination, false)
                .build()

            findNavController().navigate(R.id.storicoFragment, null, navOptions)


        } else {
            val startDestination = findNavController().graph.startDestinationId
            val navOptions = NavOptions.Builder()
                .setPopUpTo(startDestination, false)
                .build()
            findNavController().navigate(R.id.loginFragment, null, navOptions)
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