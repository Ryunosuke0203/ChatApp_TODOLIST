
package xyz.ryujpn.mymessenger.Adapter

//import kotlinx.android.synthetic.main.item.view.*
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.android.synthetic.main.check_task_child.view.*
import xyz.ryujpn.mymessenger.R
import xyz.ryujpn.mymessenger.ToDoData

class RecyclerAdapter(
    val mItems:   ArrayList<ToDoData>,
    private val mContext: Context
): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    override fun getItemCount(): Int = mItems.size

    // Itemを追加する
    fun addItem(item: ToDoData) {
        mItems.add(item)
        notifyDataSetChanged() // これを忘れるとRecyclerViewにItemが反映されない
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(mContext).inflate(
        R.layout.check_task_child, parent, false))

    // Itemを削除する
    private fun removeItem(position: Int) {
        mItems.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged() // これを忘れるとRecyclerViewにItemが反映されない
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = mItems[position].title
        holder.btRemoveItem.setOnClickListener {
            removeItem(position)
        }

    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvName: TextView  = view.tv_name
        val btRemoveItem: ImageButton = view.bt_remove_item
    }
}