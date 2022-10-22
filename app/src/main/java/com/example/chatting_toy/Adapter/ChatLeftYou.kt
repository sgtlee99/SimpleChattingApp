package com.example.chatting_toy.Adapter

import com.example.chatting_toy.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class ChatLeftYou() : Item<GroupieViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chat_left_you
    }
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
    }
}