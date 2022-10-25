package com.example.chatting_toy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.example.chatting_toy.Adapter.ChatLeftYou
import com.example.chatting_toy.Adapter.ChatRightMe
import com.example.chatting_toy.Model.ChatModel
import com.example.chatting_toy.Model.ChatNewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_chat_room.*
import kotlinx.android.synthetic.main.activity_chat_room.view.*


class ChatRoomActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val TAG: String = "[TEST]-ChatRoomActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        auth = FirebaseAuth.getInstance()
        val myUid = auth.uid

        val yourUid: String? = intent.getStringExtra("yourUid")
        val name: String? = intent.getStringExtra("name")

        val adapter = GroupAdapter<GroupieViewHolder>()

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        //데이터 불러오기 (파이어스토어)
//        db.collection("message")
//            .orderBy("time")
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    Log.d(TAG, document.toString())
//
//                    val senderUid = document.get("myUid")
//                    val msg = document.get("message").toString()
//
//                    if (senderUid == myUid) {   //if 내가 보낸 메세지일때
//                        adapter.add(ChatRightMe(msg))
//                    } else {                    //if 내가 보낸 메세지가 아닐때
//                        adapter.add(ChatLeftYou(msg))
//                    }
//                }
//                recyclerView_chat.adapter = adapter
//
//            }
//            .addOnFailureListener {
//
//            }


        val database = Firebase.database("https://chattingtoy-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val myRef = database.getReference("message")
        val readRef = database.getReference("message").child(myUid.toString()).child(yourUid.toString())

//        val childEventListener = object : ChildEventListener {
//            override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
//                val model : String? = snapshot.getValue(ChatNewModel::class.java)?.message
//            }
//
//            override fun onChildChanged(snapshot: DataSnapshot, p1: String?) {
//            }
//
//            override fun onChildRemoved(snapshot: DataSnapshot) {
//            }
//
//            override fun onChildMoved(snapshot: DataSnapshot, p1: String?) {
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//            }
//
//        }
        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
//                Log.d(TAG, "add : $p0")
                val model = p0.getValue(ChatNewModel::class.java)
                val msg = model?.message.toString()
                val who = model?.who

                if (who == "Me") {
                    adapter.add(ChatRightMe(msg))
                } else {
                    adapter.add(ChatLeftYou(msg))
                }
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }

        }

        recyclerView_chat.adapter = adapter
        readRef.addChildEventListener(childEventListener)

        val myRef_list = database.getReference("message-user-list")

        //메세지를 보내는 버튼튼
        send_button.setOnClickListener {
            val massage: String = send_text.text.toString()

            val chat = ChatNewModel(myUid.toString(), yourUid.toString(), massage, System.currentTimeMillis(), "Me")
            myRef.child(myUid.toString()).child(yourUid.toString()).push().setValue(chat)

            val chat_you = ChatNewModel(yourUid.toString(), myUid.toString(), massage, System.currentTimeMillis(), "You")
            myRef.child(yourUid.toString()).child(myUid.toString()).push().setValue(chat_you)

            myRef_list.child(myUid.toString()).child(yourUid.toString()).setValue(chat)

            send_text.setText("")   //보내고나면 텍스트필드에 있는 문자열을 초기화


        }
    }
}