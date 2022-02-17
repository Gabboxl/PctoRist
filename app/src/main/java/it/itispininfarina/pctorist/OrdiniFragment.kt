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


class OrdiniFragment : Fragment(), View.OnClickListener  {
    private val appViewModel: PctoRistViewModel by activityViewModels()

    lateinit var conto : TextView
    lateinit var quanPB : TextView
    lateinit var quanLas : TextView
    lateinit var quanPP : TextView
    lateinit var quanCot : TextView
    lateinit var quanBistec : TextView
    lateinit var quanTiramisu : TextView
    lateinit var quanCrostata : TextView
    var prezzo = 0.0
    var prezzoUnitario = arrayOf(6.0, 10.0, 8.0, 12.0, 20.0, 6.5, 6.0)
    var quantity = arrayOf (0, 0, 0, 0, 0, 0, 0)


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
        when(parent.id){
            R.id.addB1 -> {
                quantity[0]++
                quanPB.text = ""+quantity[0]
                prezzo += prezzoUnitario[0]
                conto.text = "$prezzo"
            }
            R.id.addB2 -> {
                quantity[1]++
                quanLas.text = ""+quantity[1]
                prezzo += prezzoUnitario[1]
                conto.text = "$prezzo"
            }
            R.id.addB3 -> {
                quantity[2]++
                quanPP.text = ""+quantity[2]
                prezzo += prezzoUnitario[2]
                conto.text = "$prezzo"
            }
            R.id.addB4 -> {
                quantity[3]++
                quanCot.text = ""+quantity[3]
                prezzo += prezzoUnitario[3]
                conto.text = "$prezzo"
            }
            R.id.addB5 -> {
                quantity[4]++
                quanBistec.text = ""+quantity[4]
                prezzo += prezzoUnitario[4]
                conto.text = "$prezzo"
            }
            R.id.addB6 -> {
                quantity[5]++
                quanTiramisu.text = ""+quantity[5]
                prezzo += prezzoUnitario[5]
                conto.text = "$prezzo"
            }
            R.id.addB7 -> {
                quantity[6]++
                quanCrostata.text = ""+quantity[6]
                prezzo += prezzoUnitario[6]
                conto.text = "$prezzo"
            }
            R.id.removeB1 -> {
                if(quantity[0]>0) {
                    quantity[0]--
                    prezzo -= prezzoUnitario[0]
                    conto.text = "$prezzo"
                }
                else{
                    quantity[0]=0
                }
                quanPB.text = ""+quantity[0]
            }
            R.id.removeB2 -> {
                if(quantity[1]>0) {
                    quantity[1]--
                    prezzo -= prezzoUnitario[1]
                    conto.text = "$prezzo"
                }
                else{
                    quantity[1]=0
                }
                quanLas.text = ""+quantity[1]
            }
            R.id.removeB3 -> {
                if(quantity[2]>0) {
                    quantity[2]--
                    prezzo -= prezzoUnitario[2]
                    conto.text = "$prezzo"
                }
                else{
                    quantity[2]=0
                }
                quanPP.text = ""+quantity[2]
            }
            R.id.removeB4 -> {
                if(quantity[3]>0) {
                    quantity[3]--
                    prezzo -= prezzoUnitario[3]
                    conto.text = "$prezzo"
                }
                else{
                    quantity[3]=0
                }
                quanCot.text = ""+quantity[3]
            }
            R.id.removeB5 -> {
                if(quantity[4]>0) {
                    quantity[4]--
                    prezzo -= prezzoUnitario[4]
                    conto.text = "$prezzo"
                }
                else{
                    quantity[3]=0
                }
                quanBistec.text = ""+quantity[4]
            }
            R.id.removeB6 -> {
                if(quantity[5]>0) {
                    quantity[5]--
                    prezzo -= prezzoUnitario[5]
                    conto.text = "$prezzo"
                }
                else{
                    quantity[5]=0
                }
                quanTiramisu.text = ""+quantity[5]
            }
            R.id.removeB7 -> {
                if(quantity[6]>0) {
                    quantity[6]--
                    prezzo -= prezzoUnitario[6]
                    conto.text = "$prezzo"
                }
                else{
                    quantity[6]=0
                }
                quanCrostata.text = ""+quantity[6]
            }

        }
    }


}