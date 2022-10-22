package com.example.chatting_toy.Adapter

import com.example.chatting_toy.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.message_list_row.view.*

class UserItem(val name : String) : Item<GroupieViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.message_list_row
    }
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.name.text = name
    }
}