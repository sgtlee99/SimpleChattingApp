package com.example.chatting_toy.Adapter

import com.example.chatting_toy.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class ChatRightMe() : Item<GroupieViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chat_right_me
    }
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
    }
}