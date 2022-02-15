package it.itispininfarina.pctorist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import android.widget.Toast.*


class OrdiniFragment : Fragment()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_ordini, container, false)

        //adattamento dello spinner MARINARA

        val spinner: Spinner = layout.findViewById(R.id.spinnerMarinara)

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.quantità,
            android.R.layout.simple_spinner_item
        ).also { adapter ->

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinner.adapter = adapter

        }
        //FINE ADATTAMENTO SPINNER



        // Creazione funzione di quando si seleziona un item

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            //variabili da inizializzare per poi essere utilizzate in modo globale nel codice

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val quantityMarinara = parent?.getItemAtPosition(position)
                Toast.makeText(context, "$quantityMarinara", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        //FINE SPINNER MARINARA

        //-------------------------------------------------------------------------------------------------------------------

        //INIZIO SPINNER MARGHERITA
        val spinnerMargherita: Spinner = layout.findViewById(R.id.spinnerMargherita)

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.quantità,
            android.R.layout.simple_spinner_item
        ).also { adapter ->

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinnerMargherita.adapter = adapter

        }
        //FINE ADATTAMENTO SPINNER

        spinnerMargherita.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            //variabili da inizializzare per poi essere utilizzate in modo globale nel codice

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val quantityMargherita = parent?.getItemAtPosition(position)
                Toast.makeText(context, "$quantityMargherita", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


        return layout
    }




}