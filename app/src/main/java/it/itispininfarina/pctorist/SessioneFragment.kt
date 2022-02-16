package it.itispininfarina.pctorist

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SessioneFragment : Fragment() {
    private lateinit var appViewModel: PctoRistViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appViewModel = ViewModelProvider(this).get(PctoRistViewModel::class.java)
        appViewModel.getUserMutableLiveData().observe(this, object : Observer<FirebaseUser>{
            override fun onChanged(firebaseUser: FirebaseUser?) {
                if(firebaseUser != null){
                    Toast.makeText(context, "Login effettuato", Toast.LENGTH_SHORT).show()
                }
            }

        })



        if (appViewModel.getUserMutableLiveData().value != null) {
            findNavController().navigate(R.id.ordinitestFragment)
        } else {
            val startDestination = findNavController().graph.startDestinationId
            val navOptions = NavOptions.Builder()
                .setPopUpTo(startDestination, false)
                .build()
            findNavController().navigate(R.id.registraFragment, null, navOptions)
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