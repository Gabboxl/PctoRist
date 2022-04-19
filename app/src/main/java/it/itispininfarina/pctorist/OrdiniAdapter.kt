package it.itispininfarina.pctorist


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot


class OrdiniAdapter :
    ListAdapter<DocumentSnapshot, OrdiniAdapter.OrdineHolder>(DIFF_CALLBACK) {
    var posizioneitem: Int = -1

    companion object {

        private var DIFF_CALLBACK: DiffUtil.ItemCallback<DocumentSnapshot> = object :
            DiffUtil.ItemCallback<DocumentSnapshot>() {
            override fun areItemsTheSame(
                oldItem: DocumentSnapshot,
                newItem: DocumentSnapshot
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: DocumentSnapshot,
                newItem: DocumentSnapshot
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class OrdineHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nordinetitolocard: TextView
        var textprod1: TextView
        var textprod2: TextView
        var textprod3: TextView
        var textprod4: TextView
        var textprod5: TextView
        var textprod6: TextView
        var textprod7: TextView


        init {
            nordinetitolocard = itemView.findViewById(R.id.nordinecard)
            textprod1 = itemView.findViewById(R.id.qtordineProd1)
            textprod2 = itemView.findViewById(R.id.qtordineProd2)
            textprod3 = itemView.findViewById(R.id.qtordineProd3)
            textprod4 = itemView.findViewById(R.id.qtordineProd4)
            textprod5 = itemView.findViewById(R.id.qtordineProd5)
            textprod6 = itemView.findViewById(R.id.qtordineProd6)
            textprod7 = itemView.findViewById(R.id.qtordineProd7)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdineHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.ordine_card_item, parent, false)
        return OrdineHolder((itemView))
    }

    override fun onBindViewHolder(holder: OrdineHolder, position: Int) {
        val currentDocumentSnapshot: DocumentSnapshot = getItem(position)
        holder.nordinetitolocard.text = currentDocumentSnapshot["nordine"].toString()
        holder.textprod1.text = currentDocumentSnapshot["prod1"].toString()
        holder.textprod2.text = currentDocumentSnapshot["prod2"].toString()
        holder.textprod3.text = currentDocumentSnapshot["prod3"].toString()
        holder.textprod4.text = currentDocumentSnapshot["prod4"].toString()
        holder.textprod5.text = currentDocumentSnapshot["prod5"].toString()
        holder.textprod6.text = currentDocumentSnapshot["prod6"].toString()
        holder.textprod7.text = currentDocumentSnapshot["prod7"].toString()



    }

    fun getDocumentoSnapshotAt(position: Int): DocumentSnapshot {
        return getItem(position)
    }
    
}