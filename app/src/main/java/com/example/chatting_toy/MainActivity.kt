package com.example.chatting_toy

import com.example.chatting_toy.Model.User
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

//import kotlinx.android.synthetic.main.activity_login.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private val TAG : String = "[TOY_TEST]-MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        join_button.setOnClickListener {

            val email : String = email_area.text.toString()
            val password : String = password_area.text.toString()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "success")
                        Log.d(TAG, "$email $password")

                        val uid = FirebaseAuth.getInstance().uid ?: ""
                        //uid값을 아래 인자로 집어넣음
                        val user = User(uid, username.text.toString())

                        //데이터베이스에 넣음
                        val db = Firebase.firestore.collection("users")
                        db.document(uid)
                            .set(user)
                            .addOnSuccessListener {
                                Log.d(TAG,"DB Success")
                            }
                            .addOnFailureListener {
                                Log.d(TAG,"DB Fail")
                            }

                        val intent = Intent(this, ChatListActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK //전 액티비티 지워줌
                        startActivity(intent)

                    } else {
                        Log.d(TAG, "fail")
                    }
                }
        }


        login_button_main.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}