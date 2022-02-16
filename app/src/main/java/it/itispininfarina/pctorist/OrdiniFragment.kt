package it.itispininfarina.pctorist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.Toast.*
import androidx.core.view.get


class OrdiniFragment : Fragment(), AdapterView.OnItemSelectedListener  {

    lateinit var conto : TextView
    var prezzo = 0.0
    var prezzoUnitario = arrayOf(5.0, 4.5)
    var acconto = arrayOf(0.0, 0.0)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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


        //adattamento dello spinner MARINARA

        val spinnerMarinara: Spinner = layout.findViewById(R.id.spinnerMarinara)
        spinnerMarinara.adapter = adapter

        //click sullo spinner Marinara

        spinnerMarinara.onItemSelectedListener = this

        //FINE SPINNER MARINARA


        //INIZIO SPINNER MARGHERITA
        val spinnerMargherita: Spinner = layout.findViewById(R.id.spinnerMargherita)
        spinnerMargherita.adapter = adapter

        //FINE ADATTAMENTO SPINNER

        spinnerMargherita.onItemSelectedListener = this

        //FINE SPINNER MARGHERITA




        return layout
    }


override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {

    when (parent.id) {
        R.id.spinnerMargherita -> {
            prezzo -= acconto[0]
            acconto[0] = (pos * prezzoUnitario[0])
            prezzo += acconto[0]
            conto.text = "$prezzo"
        }
        R.id.spinnerMarinara -> {
            prezzo -= acconto[1]
            acconto[1] = (pos * prezzoUnitario[1])
            prezzo += acconto[1]
            conto.text = "$prezzo"
        }


    }
}

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

}