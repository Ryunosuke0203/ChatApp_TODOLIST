package xyz.ryujpn.mymessenger

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.chat_item.view.*
import kotlinx.android.synthetic.main.chat_item_mine.view.*
import xyz.ryujpn.mymessenger.Fragment.TalkFragment

class ChatLogActivity : AppCompatActivity() {

    val adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        recyclerview_chatlog.adapter = adapter
        // 相手の要素
        val user = intent.getParcelableExtra<User>(TalkFragment.USER_KEY)
        supportActionBar?.title = user.username

        listenForMessage()


        sendButton.setOnClickListener {
            Log.d("ChatLogActivity","tap sendButton")
            performSendMessage()
        }
    }

    private fun listenForMessage(){
        val ref = FirebaseDatabase.getInstance().getReference("/messages")
        ref.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java)
                Log.d("ChatLogActivity",chatMessage?.text)

                if (chatMessage != null) {
                    if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
                        adapter.add(ChatItemMine(chatMessage.text))
                    }else{
                        adapter.add(ChatItemFriends(chatMessage.text))
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        })
    }

    private fun performSendMessage(){
        val text = sendMessageText.text.toString()

        val fromId = FirebaseAuth.getInstance().uid
        val user = intent.getParcelableExtra<User>(TalkFragment.USER_KEY)
        val toId = user.uid

        if(fromId == null) return

        val ref = FirebaseDatabase.getInstance().getReference("/messages").push()
        Log.d("ChatLogActivity","performSendMessage")
        val chatMessage = ChatMessage(
            ref.key!!, text, fromId!!, toId, System
                .currentTimeMillis() / 1000
        )
        Log.d("ChatLogActivity", chatMessage.text)
        ref.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d("ChatLogActivity", ref.key)
            }
    }
}

class ChatItemFriends(val text: String): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textview_from_row.text = text
    }
    override fun getLayout(): Int {
        return R.layout.chat_item
    }
}

class ChatItemMine(val text:String): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textview_to_row.text = text
    }
    override fun getLayout(): Int {
        return R.layout.chat_item_mine
    }
}
