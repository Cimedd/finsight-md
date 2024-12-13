package com.capstone.finsight.ui

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.capstone.finsight.R
import com.capstone.finsight.data.SettingVMF
import com.capstone.finsight.data.SettingViewModel
import com.capstone.finsight.databinding.ActivityMainBinding
import com.capstone.finsight.databinding.ActivitySignUpBinding
import com.capstone.finsight.network.Result

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignUpBinding
    private val settingVM by viewModels<SettingViewModel>{
        SettingVMF.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        animation()
        binding.btnRegister.setOnClickListener {
            val email = binding.txtRegEmail.text.toString()
            val uname = binding.txtRegUser.text.toString()
            val pass = binding.txtRegPass.text.toString()
            settingVM.register(email, uname, pass).observe(this){
                when(it){
                    is Result.Error ->{
                        Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                        binding.btnRegister.setBackgroundColor(getColor(R.color.blue_main_2))
                    }
                    is Result.Loading -> {
                        binding.btnRegister.isActivated = false
                        binding.btnRegister.setBackgroundColor(getColor(R.color.grayed))
                    }
                    is Result.Success ->{
                        binding.btnRegister.setBackgroundColor(getColor(R.color.blue_main_2))
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
    private fun animation(){
        ObjectAnimator.ofFloat(binding.cardRegister, View.TRANSLATION_Y, 1000f, 0f).setDuration(600).start()
    }
}