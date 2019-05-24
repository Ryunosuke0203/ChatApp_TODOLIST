package xyz.ryujpn.mymessenger

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_newpost.*
import xyz.ryujpn.mymessenger.Adapter.RecyclerAdapter

class NewpostActivity : AppCompatActivity() {
    var auth: FirebaseAuth? = null
    var recyclerList: MutableList<String>? = null
    //
    private lateinit var database: DatabaseReference
    private var user: FirebaseUser? = null
    private var uid: String? = null
    private lateinit var mRecyclerView: RecyclerView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newpost)

        user = FirebaseAuth.getInstance().currentUser
        uid = user?.uid
        database =  FirebaseDatabase.getInstance().reference

        val adater = RecyclerAdapter(ArrayList(),this)

        mRecyclerView = findViewById(R.id.checkboxRecyclerView)
        checkboxRecyclerView.layoutManager = LinearLayoutManager(this)
//        mCustomAdapter = NewpostAdapter(NewpostActivity(),ArrayList())
//        mRecyclerView.adapter = mCustomAdapter

        addCheckbox.setOnClickListener {
            if( !TextUtils.isEmpty(todoName.text.toString())){
                val task = ToDoData(title = todoName.text.toString())
                adater.addItem(task)
            }else{

                Snackbar.make(container,"Task is Empty",Snackbar.LENGTH_SHORT)
            }

            todoName.setText("")
        }

        mRecyclerView.adapter = adater



//        val addCheckButton: Button = findViewById(R.id.addCheckbox)
//        checkboxRecyclerView.layoutManager = LinearLayoutManager(this)
//        addCheckButton.setOnClickListener { view->
//            Snackbar.make(view, "Todoを追加しよう！", Snackbar.LENGTH_LONG).show()
//            // taskListに要素を格納
//            val dialog = AlertDialog.Builder(this)
//            val v =layoutInflater.inflate(R.layout.dialog,null)
//            val taskN = v.findViewById<EditText>(R.id.ev_todo)
//            dialog.setView(v)
//            dialog.setPositiveButton("Todoを追加") { _: DialogInterface, _: Int ->
//                if (taskN.text.isNotEmpty()) {
//                    val item = Listitem()
//
//                    item.taskList?.add(taskN.text.toString())
//                    item.registerTime = Date()
//                    item.userId = auth.currentUser!!.uid
//                    refreshList()
//                }
//            }
//            dialog.setNegativeButton("やめる") { _: DialogInterface, _: Int ->
//
//            }
//            dialog.show()
//        }
//        val postButton: Button = findViewById(R.id.finished_newpost)
//        postButton.setOnClickListener {
//            val intent = Intent(this,MyListActivity::class.java)
//            startActivity(intent)
//            val item = Listitem()
//            val v =layoutInflater.inflate(R.layout.dialog,null)
//            val taskN = v.findViewById<EditText>(R.id.taskName)
//            item.taskName = taskN.text.toString()
//            refreshList()
//        }
//    }
//
//    private fun refreshList(){
//        recyclerList = Listitem().taskList
//        newpostAdapter = NewpostAdapter(this,recyclerList!!)
//        checkboxRecyclerView.adapter = newpostAdapter

    }

}
//class NewpostAdapter(val nowActivity: ArrayList<Any>, val recyclerlist: NewpostActivity) :
//        RecyclerView.Adapter<NewpostAdapter.NewpostViewHolder>(){
//
//    override fun onCreateViewHolder(p0: ViewGroup, p1: Int):
//            NewpostViewHolder {
//       return NewpostViewHolder(LayoutInflater.from(nowActivity).inflate(R.layout.check_task_child,p0,false))
//    }
//
//    override fun getItemCount(): Int {
//        return recyclerlist.size
//    }
//
//    override fun onBindViewHolder(p0: NewpostViewHolder, p1: Int) {
//        p0.checkbox.text = recyclerlist[p1].
//    }
//
//    class NewpostViewHolder(view: View) : RecyclerView.ViewHolder(view){
//        val checkbox = view.findViewById<CheckBox>(R.id.checkbox_todo)
//    }
//}