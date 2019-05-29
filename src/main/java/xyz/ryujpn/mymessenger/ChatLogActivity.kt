package xyz.ryujpn.mymessenger

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*

class ChatLogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        supportActionBar?.title = "チャットルーム"

        val adapter = GroupAdapter<ViewHolder>()

        adapter.add(ChatItem())

        recyclerview_chatlog.adapter = adapter
    }
}

class ChatItem: Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {

    }
    override fun getLayout(): Int {
        return R.layout.chat_item
    }
}
