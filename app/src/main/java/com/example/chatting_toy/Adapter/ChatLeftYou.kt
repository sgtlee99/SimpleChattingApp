package com.example.chatting_toy.Adapter

import com.example.chatting_toy.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.chat_left_you.view.*

class ChatLeftYou(val msg : String) : Item<GroupieViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chat_left_you
    }
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.left_chat.text = msg
    }
}