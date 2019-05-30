package xyz.ryujpn.mymessenger.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home.*
import xyz.ryujpn.mymessenger.NewpostActivity
import xyz.ryujpn.mymessenger.R


class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onResume() {
        super.onResume()
        fab_item.setOnClickListener {
            val intent= Intent(activity, NewpostActivity::class.java)
            startActivity(intent)
        }

    }

//    fun onCreateView(inflater: LayoutInflater, savedInstanceState: Bundle?): View{
//        super.onCreateView(inflater, container, savedInstanceState)
//        MenuActivity().setTitle("Home")
//        var rootView: View = inflater.inflate(R.layout.fragment_home, container , false)
//        return rootView
//    }


    companion object {
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            return fragment
        }
    }


}