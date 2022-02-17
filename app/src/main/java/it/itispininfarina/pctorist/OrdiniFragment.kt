package it.itispininfarina.pctorist

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import android.widget.Toast.*
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.NonCancellable.cancel


class OrdiniFragment : Fragment(), AdapterView.OnItemSelectedListener  {
    private val appViewModel: PctoRistViewModel by activityViewModels()



    lateinit var conto : TextView
    var prezzo = 0.0
    var prezzoUnitario = arrayOf(6.0, 10.0, 8.0, 12.0)
    var acconto = arrayOf(0.0, 0.0, 0.0, 0.0)



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
                        Toast.makeText(context, "ok tranguillo", Toast.LENGTH_SHORT).show()
                    }
                    .setPositiveButton("Si") { dialog, which ->
                        appViewModel.logout()

                        Toast.makeText(context, "Ok sei stato sloggato", Toast.LENGTH_SHORT).show()
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
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_ordini, container, false)

        conto = layout.findViewById(R.id.conto)

        //adattamento dello spinner UNIVERSALE

        var adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.quantità,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        //adattamento dello spinner Pasta in bianco

        val spinnerPastaBianco: Spinner = layout.findViewById(R.id.spinnerPastaBianco)
        spinnerPastaBianco.adapter = adapter

        //click sullo spinner Pasta in bianco

        spinnerPastaBianco.onItemSelectedListener = this

        //FINE SPINNER Pasta in bianco
        //------------------------------------------------------------------------------------

        //INIZIO SPINNER Lasagna
        val spinnerLasagna: Spinner = layout.findViewById(R.id.spinnerLasagna)
        spinnerLasagna.adapter = adapter

        //FINE ADATTAMENTO SPINNER

        spinnerLasagna.onItemSelectedListener = this

        //FINE SPINNER Lasagna
        //------------------------------------------------------------------------------------

        //INIZIO SPINNER Pasta al pesto
        val spinnerPastaPesto: Spinner = layout.findViewById(R.id.spinnerPastaPesto)
        spinnerPastaPesto.adapter = adapter

        //FINE ADATTAMENTO SPINNER

        spinnerPastaPesto.onItemSelectedListener = this

        //FINE SPINNER Pasta al pesto
        //------------------------------------------------------------------------------------

        //INIZIO SPINNER Cotoletta
        val spinnerCotoletta: Spinner = layout.findViewById(R.id.spinnerCotoletta)
        spinnerCotoletta.adapter = adapter

        //FINE ADATTAMENTO Cotoletta

        spinnerCotoletta.onItemSelectedListener = this

        //FINE SPINNER Cotoletta
        //------------------------------------------------------------------------------------



        return layout
    }


override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {

    when (parent.id) {
        R.id.spinnerPastaBianco -> {
            prezzo -= acconto[0]
            acconto[0] = (pos * prezzoUnitario[0])
            prezzo += acconto[0]
            conto.text = "$prezzo"
        }
        R.id.spinnerLasagna -> {
            prezzo -= acconto[1]
            acconto[1] = (pos * prezzoUnitario[1])
            prezzo += acconto[1]
            conto.text = "$prezzo"
        }
        R.id.spinnerPastaPesto -> {
            prezzo -= acconto[2]
            acconto[2] = (pos * prezzoUnitario[2])
            prezzo += acconto[2]
            conto.text = "$prezzo"
        }
        R.id.spinnerCotoletta -> {
            prezzo -= acconto[3]
            acconto[3] = (pos * prezzoUnitario[3])
            prezzo += acconto[3]
            conto.text = "$prezzo"
        }


    }
}

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

}