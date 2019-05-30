package xyz.ryujpn.mymessenger

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_newpost.*
import xyz.ryujpn.mymessenger.Adapter.RecyclerAdapter

class NewpostActivity : AppCompatActivity() {
    var auth: FirebaseAuth? = null
    var recyclerList: ArrayList<PostToDo>? = null
    //
    private var database = FirebaseDatabase.getInstance()
    private var reference: DatabaseReference = database.reference
    private var user: FirebaseUser? = null
    private var uid: String? = null
    private lateinit var mRecyclerView: RecyclerView

    private lateinit  var titleEditText: EditText
    private lateinit var mm: ArrayList<ToDoData>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newpost)

        user = FirebaseAuth.getInstance().currentUser
        uid = user?.uid
        titleEditText = findViewById(R.id.taskName)


        mm = ArrayList()
        val adater = RecyclerAdapter(mm,this)

        mRecyclerView = findViewById(R.id.checkboxRecyclerView)
        checkboxRecyclerView.layoutManager = LinearLayoutManager(this)


        addCheckbox.setOnClickListener {view ->
            if( !TextUtils.isEmpty(todoName.text.toString())){
                val task = ToDoData(title = todoName.text.toString())
                val ss = todoName.text.toString()
                adater.addItem(task)
                Snackbar.make(view,"追加しました！",Snackbar.LENGTH_SHORT).show()
            }else{

                Snackbar.make(view,"todoを入力してください",Snackbar.LENGTH_SHORT).show()
            }
            todoName.setText("")
        }

        mRecyclerView.adapter = adater
        val postButton: Button = findViewById(R.id.finished_newpost)
        postButton.setOnClickListener {

            //＋で追加したリストを保存する

            val intent = Intent(this,MyListActivity::class.java)

            startActivity(intent)

        }


    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        val postButton: Button = findViewById(R.id.finished_newpost)

    }

    fun save(){
        val todoname: String = todoName.text.toString()

        val postToDo = PostToDo(todoname)

        reference.child("post").child(todoname).setValue(postToDo)
            .addOnSuccessListener {
                finish()
            }
        }


    }