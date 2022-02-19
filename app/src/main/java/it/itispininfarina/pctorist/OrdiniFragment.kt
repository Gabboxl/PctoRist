package it.itispininfarina.pctorist

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.*
import android.widget.ImageButton
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton


class OrdiniFragment : Fragment(), View.OnClickListener {
    private val appViewModel: PctoRistViewModel by activityViewModels()

    lateinit var conto: TextView
    lateinit var quanPB: TextView
    lateinit var quanLas: TextView
    lateinit var quanPP: TextView
    lateinit var quanCot: TextView
    lateinit var quanBistec: TextView
    lateinit var quanTiramisu: TextView
    lateinit var quanCrostata: TextView
    var prezzo = 0.0
    var prezzoUnitario = arrayOf(6.0, 10.0, 8.0, 12.0, 20.0, 6.5, 6.0)
    var quantity = arrayOf(0, 0, 0, 0, 0, 0, 0)


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

                    findNavController().navigate(R.id.sessioneFragment, null, navOptions)


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


    override fun onDestroyView() {
        super.onDestroyView()

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




        lifecycleScope.launchWhenStarted {
            appViewModel.getScriviResult().collect { result ->

                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("oh yeah")
                    .setMessage("ho scritto tutto, ora puoi andare alla pagina di comferma dell'ordine")
                    .setPositiveButton("Ok") { dialog, which ->
                    }
                    .show()
            }
        }


        lifecycleScope.launchWhenStarted {
            appViewModel.getScriviException().collect { result ->

                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Errore")
                        .setMessage(result.localizedMessage)
                        .setPositiveButton("Ok") { dialog, which ->
                        }
                        .show()
                }
            }







        val ordinefab = layout.findViewById<ExtendedFloatingActionButton>(R.id.confermaOrdineFab)
        val scrollviewordini = layout.findViewById<NestedScrollView>(R.id.scrollviewOrdini)


        //disabilito l'overscroll per la scrollview altrimenti con lo shrink e l'extend del fab button è un casino
        scrollviewordini.overScrollMode = View.OVER_SCROLL_NEVER


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollviewordini.setOnScrollChangeListener { view, scrollX, scrollY, oldScrollX, oldScrollY ->

                if (scrollY > oldScrollY && ordinefab.isExtended) {
                    ordinefab.shrink();
                } else if(scrollY < oldScrollY && !ordinefab.isExtended) {
                    ordinefab.extend();
                }

                if (scrollY == 0) {
                    ordinefab.extend();
                }
            }
        }

        ordinefab.setOnClickListener {
            if(prezzo != 0.0) {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Confermare l'ordine?")
                    .setMessage(
                        HtmlCompat.fromHtml(
                            "Vuoi confermare questo ordine di: <br><br><b>${quantity[0]}</b>x Pasta in bianco<br>" +
                                    "<b>${quantity[1]}</b>x  Lasagna<br>" +
                                    "<b>${quantity[2]}</b>x Pasta al pesto<br>" +
                                    "<b>${quantity[3]}</b>x Cotoletta<br>" +
                                    "<b>${quantity[4]}</b>x Bistecca<br>" +
                                    "<b>${quantity[5]}</b>x Tiramisù<br>" +
                                    "<b>${quantity[6]}</b>x Crostata ai frutti di bosco<br>" +
                                    "<br>per un costo totale di <b>$prezzo</b> Euro?",
                            FROM_HTML_MODE_COMPACT
                        )
                    )
                    .setNegativeButton("Annulla") { dialog, which -> }
                    .setPositiveButton("Conferma") { dialog, which ->
                        appViewModel.creaOrdine(quantity[0], quantity[1], quantity[2], quantity[3], quantity[4], quantity[5], quantity[6])
                    }
                    .setCancelable(false)
                    .show()
            } else {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Attenzione")
                    .setMessage("Per effettuare un ordine devi almeno scegliere un piatto.")
                    .setPositiveButton("Ok") { dialog, which ->
                    }
                    .show()
            }
        }



        //trovo i testi dentro il layout
        conto = layout.findViewById(R.id.conto)
        quanPB = layout.findViewById<TextView>(R.id.quanPastaBianco)
        quanLas = layout.findViewById<TextView>(R.id.quanLasagna)
        quanPP = layout.findViewById<TextView>(R.id.quanPastaPesto)
        quanCot = layout.findViewById<TextView>(R.id.quanCotoletta)
        quanBistec = layout.findViewById<TextView>(R.id.quanBistecca)
        quanTiramisu = layout.findViewById<TextView>(R.id.quanTiramisu)
        quanCrostata = layout.findViewById<TextView>(R.id.quanCrostata)

        //trovo i pulsanti dentro il layout
        val addB1 = layout.findViewById<ImageButton>(R.id.addB1)
        val addB2 = layout.findViewById<ImageButton>(R.id.addB2)
        val addB3 = layout.findViewById<ImageButton>(R.id.addB3)
        val addB4 = layout.findViewById<ImageButton>(R.id.addB4)
        val addB5 = layout.findViewById<ImageButton>(R.id.addB5)
        val addB6 = layout.findViewById<ImageButton>(R.id.addB6)
        val addB7 = layout.findViewById<ImageButton>(R.id.addB7)
        val removeB1 = layout.findViewById<ImageButton>(R.id.removeB1)
        val removeB2 = layout.findViewById<ImageButton>(R.id.removeB2)
        val removeB3 = layout.findViewById<ImageButton>(R.id.removeB3)
        val removeB4 = layout.findViewById<ImageButton>(R.id.removeB4)
        val removeB5 = layout.findViewById<ImageButton>(R.id.removeB5)
        val removeB6 = layout.findViewById<ImageButton>(R.id.removeB6)
        val removeB7 = layout.findViewById<ImageButton>(R.id.removeB7)

        //inizializzo i testi a 0
        conto.text = "$prezzo"
        quanPB.text = "0"
        quanLas.text = "0"
        quanPP.text = "0"
        quanCot.text = "0"
        quanBistec.text = "0"
        quanTiramisu.text = "0"
        quanCrostata.text = "0"

        //metodo che poi andrà a cambiare il conto e la quantità
        addB1.setOnClickListener(this)
        addB2.setOnClickListener(this)
        addB3.setOnClickListener(this)
        addB4.setOnClickListener(this)
        addB5.setOnClickListener(this)
        addB6.setOnClickListener(this)
        addB7.setOnClickListener(this)
        removeB1.setOnClickListener(this)
        removeB2.setOnClickListener(this)
        removeB3.setOnClickListener(this)
        removeB4.setOnClickListener(this)
        removeB5.setOnClickListener(this)
        removeB6.setOnClickListener(this)
        removeB7.setOnClickListener(this)



        return layout
    }

    override fun onClick(parent: View) {
        when (parent.id) {
            R.id.addB1 -> {
                quantity[0]++
                quanPB.text = quantity[0].toString()
                prezzo += prezzoUnitario[0]
                conto.text = "$prezzo"
            }
            R.id.addB2 -> {
                quantity[1]++
                quanLas.text = quantity[1].toString()
                prezzo += prezzoUnitario[1]
                conto.text = "$prezzo"
            }
            R.id.addB3 -> {
                quantity[2]++
                quanPP.text = quantity[2].toString()
                prezzo += prezzoUnitario[2]
                conto.text = "$prezzo"
            }
            R.id.addB4 -> {
                quantity[3]++
                quanCot.text = quantity[3].toString()
                prezzo += prezzoUnitario[3]
                conto.text = "$prezzo"
            }
            R.id.addB5 -> {
                quantity[4]++
                quanBistec.text = quantity[4].toString()
                prezzo += prezzoUnitario[4]
                conto.text = "$prezzo"
            }
            R.id.addB6 -> {
                quantity[5]++
                quanTiramisu.text = quantity[5].toString()
                prezzo += prezzoUnitario[5]
                conto.text = "$prezzo"
            }
            R.id.addB7 -> {
                quantity[6]++
                quanCrostata.text = quantity[6].toString()
                prezzo += prezzoUnitario[6]
                conto.text = "$prezzo"
            }
            R.id.removeB1 -> {
                if (quantity[0] > 0) {
                    quantity[0]--
                    prezzo -= prezzoUnitario[0]
                    conto.text = "$prezzo"
                } else {
                    quantity[0] = 0
                }
                quanPB.text = quantity[0].toString()
            }
            R.id.removeB2 -> {
                if (quantity[1] > 0) {
                    quantity[1]--
                    prezzo -= prezzoUnitario[1]
                    conto.text = "$prezzo"
                } else {
                    quantity[1] = 0
                }
                quanLas.text = quantity[1].toString()
            }
            R.id.removeB3 -> {
                if (quantity[2] > 0) {
                    quantity[2]--
                    prezzo -= prezzoUnitario[2]
                    conto.text = "$prezzo"
                } else {
                    quantity[2] = 0
                }
                quanPP.text = quantity[2].toString()
            }
            R.id.removeB4 -> {
                if (quantity[3] > 0) {
                    quantity[3]--
                    prezzo -= prezzoUnitario[3]
                    conto.text = "$prezzo"
                } else {
                    quantity[3] = 0
                }
                quanCot.text = quantity[3].toString()
            }
            R.id.removeB5 -> {
                if (quantity[4] > 0) {
                    quantity[4]--
                    prezzo -= prezzoUnitario[4]
                    conto.text = "$prezzo"
                } else {
                    quantity[3] = 0
                }
                quanBistec.text = quantity[4].toString()
            }
            R.id.removeB6 -> {
                if (quantity[5] > 0) {
                    quantity[5]--
                    prezzo -= prezzoUnitario[5]
                    conto.text = "$prezzo"
                } else {
                    quantity[5] = 0
                }
                quanTiramisu.text = quantity[5].toString()
            }
            R.id.removeB7 -> {
                if (quantity[6] > 0) {
                    quantity[6]--
                    prezzo -= prezzoUnitario[6]
                    conto.text = "$prezzo"
                } else {
                    quantity[6] = 0
                }
                quanCrostata.text = quantity[6].toString()
            }

        }
    }


}