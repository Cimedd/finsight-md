package com.capstone.finsight.ui

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.capstone.finsight.data.SettingVMF
import com.capstone.finsight.data.SettingViewModel
import com.capstone.finsight.databinding.ActivityLoginBinding
import com.capstone.finsight.network.Result
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private val settingVM by viewModels<SettingViewModel>{
        SettingVMF.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        animation()

        lifecycleScope.launch {
            if(settingVM.checkUser()){
                moveToMain()
            }
        }

        binding.txtLogToReg.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener{
            val uname =binding.editTextText.text.toString()
            val pass = binding.editTextTextPassword.text.toString()
            settingVM.login(uname, pass).observe(this){
                when(it){
                    is Result.Error -> {
                        Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                        binding.btnLogin.isActivated = true
                    }
                    is Result.Loading -> binding.btnLogin.isActivated = false
                    is Result.Success -> {
                        val intent = Intent(this, RiskActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }

    private fun moveToMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun animation(){
        ObjectAnimator.ofFloat(binding.cardLogin, View.TRANSLATION_Y, 1000f, 0f).setDuration(650).start()
    }
}