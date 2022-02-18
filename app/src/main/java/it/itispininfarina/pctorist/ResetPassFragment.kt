package it.itispininfarina.pctorist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels


class ResetPassFragment : Fragment() {
    private val appViewModel: PctoRistViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_reset_pass, container, false)

        var resetbtn = layout.findViewById<Button>(R.id.resetButton)
        val textMailReset = layout.findViewById<TextView>(R.id.editMailReset)




        return layout
    }


}