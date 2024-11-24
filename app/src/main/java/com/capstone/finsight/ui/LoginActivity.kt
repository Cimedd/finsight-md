package com.capstone.finsight.ui

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.capstone.finsight.R
import com.capstone.finsight.databinding.ActivityLoginBinding
import com.capstone.finsight.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        animation()

        binding.txtLogToReg.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.textView22.setOnClickListener{
            val intent = Intent(this, RiskActivity::class.java)
            startActivity(intent)
        }
    }

    private fun animation(){
        ObjectAnimator.ofFloat(binding.cardLogin, View.TRANSLATION_Y, 1000f, 0f).setDuration(650).start()
    }
}