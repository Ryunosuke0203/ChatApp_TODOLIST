package xyz.ryujpn.mymessenger.Fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_talk.*
import kotlinx.android.synthetic.main.user_row_new_message.view.*
import xyz.ryujpn.mymessenger.ChatLogActivity
import xyz.ryujpn.mymessenger.R
import xyz.ryujpn.mymessenger.User


class TalkFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater!!.inflate(R.layout.fragment_talk, container, false)
    }

    override fun onResume() {
        super.onResume()


        fetchUsers()
    }

    private fun fetchUsers() {
        val ref = FirebaseDatabase.getInstance().getReference("/users")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()

                p0.children.forEach {
                    Log.d("TalkFragment", it.toString())

                    val user = it.getValue(User::class.java)
                    if (user != null) {
                        adapter.add(UserItem(user))
                    }
                }
                adapter.setOnItemClickListener{ item, view ->
                    val intent = Intent(view.context, ChatLogActivity::class.java)
                    startActivity(intent)
                }

                rv_newMessage.adapter = adapter
            }

            override fun onCancelled(p0: DatabaseError) {

            }

        })
    }

}
class UserItem(val user: User): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.username_textview_new_message.text = user.username
        //ImageView等を指定し、リクエストした画像を当て込む先を指定する。
        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.imageView_chat_to_row)
    }
    override fun getLayout(): Int {
        return R.layout.user_row_new_message
    }
}