package it.itispininfarina.pctorist

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class StoricoFragment : Fragment() {
    private val appViewModel: PctoRistViewModel by activityViewModels()


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_opzioni_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.options_logout -> {

            val dialog = MaterialAlertDialogBuilder(requireContext())
                .setTitle("Sei sicuro?")
                .setMessage("Vuoi davvero sloggarti?")
                /*   .setNeutralButton("aspe") { dialog, which ->
                       // Respond to neutral button press
                   }*/
                .setNegativeButton("Annulla") { dialog, which ->
                    //Toast.makeText(context, "ok tranguillo", Toast.LENGTH_SHORT).show()
                }
                .setPositiveButton("Si") { dialog, which ->
                    appViewModel.logout()

                    val startDestination = findNavController().graph.startDestinationId
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(startDestination, false)
                        .build()

                    findNavController().navigate(R.id.sessioneStoricoFragment, null, navOptions)


                    Toast.makeText(context, "Logout completato", Toast.LENGTH_SHORT).show()
                }
                .show()
            true
        }


        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //imposto la visualizzazione del menu opzioni per questo frammento
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_storico, container, false)


        val recyclerView: RecyclerView = layout.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        val adapterOrdini = OrdiniAdapter()
        recyclerView.adapter = adapterOrdini



        val db = Firebase.firestore
        val pathordini = db.collection("users").document(appViewModel.getFirebaseUser().value!!.email!!).collection("ordini")
            .orderBy("nordine", Query.Direction.DESCENDING) //così visualizzo l'ultimo ordine effettuato in cima alla lista
            .addSnapshotListener(object :  EventListener<QuerySnapshot> {
                override fun onEvent(snapshot: QuerySnapshot?,
                    error: FirebaseFirestoreException?) {

                    if (error != null) {
                        Log.e("Firestore error", error.message.toString())
                        return
                    }

                    adapterOrdini.submitList(snapshot!!.documents)
                }
            })



        return layout
    }

}