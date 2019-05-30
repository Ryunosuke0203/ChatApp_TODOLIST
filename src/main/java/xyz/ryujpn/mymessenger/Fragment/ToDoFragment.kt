package xyz.ryujpn.mymessenger.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_todo.*
import xyz.ryujpn.mymessenger.NewpostActivity
import xyz.ryujpn.mymessenger.R

class ToDoFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_todo, container, false)

    }

    override fun onResume() {
        super.onResume()
        fab_item.setOnClickListener {
            val intent= Intent(activity, NewpostActivity::class.java)
            startActivity(intent)
        }
    }
    companion object {
        fun newInstance(): ToDoFragment {
            val fragment = ToDoFragment()
            return fragment
        }
    }

}