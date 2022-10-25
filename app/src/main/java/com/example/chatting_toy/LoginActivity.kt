package com.example.chatting_toy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private val TAG : String = "[TOY_TEST]-LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth


        login_button.setOnClickListener {
            val email: String = login_email.text.toString()
            val password: String = login_pwd.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG , "success")
                        Log.d(TAG, "$email $password")

                        //데이터베이스에 유저정보 넣어줘야 합니다


                        val intent = Intent(this, ChatListActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK //전 액티비티 지워줌
                        startActivity(intent)

                    } else {
                        Log.e(TAG , "Fail")
                    }
                }
        }
    }
}