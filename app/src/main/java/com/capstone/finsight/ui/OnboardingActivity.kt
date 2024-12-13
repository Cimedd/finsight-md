package com.capstone.finsight.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.capstone.finsight.R
import com.capstone.finsight.data.SettingVMF
import com.capstone.finsight.data.SettingViewModel
import com.capstone.finsight.databinding.ActivityOnboardingActivtyBinding
import kotlinx.coroutines.launch

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOnboardingActivtyBinding
    private var index = 0
    private var title: Array<String> = arrayOf()
    private var desc: Array<String> = arrayOf()
    private var image: Array<Int> = arrayOf()


    private val settingVM by viewModels<SettingViewModel>{
        SettingVMF.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnboardingActivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide()
        lifecycleScope.launch {
            if(settingVM.getTheme()){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        image = arrayOf(R.drawable.onboard_1, R.drawable.onboard_2, R.drawable.onboard_3)
        title =resources.getStringArray(R.array.onboardTitle)
        desc = resources.getStringArray(R.array.onboardDesc)

        binding.txtOnboardTitle.text = title[index]
        binding.txtOnboadDesc.text = desc[index]
        binding.imageView8.setImageResource(image[index])

        start()

        binding.txtSkip.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnNextBoard.setOnClickListener {
            if(index < 2){
                index++
                dotChecker()
                if(index == 2) binding.btnNextBoard.text = "Start"
                nextAnim()
            }
            else{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.btnPrev.setOnClickListener {
            if(index > 0){
                dotChecker()
                index--
                prevAnim()
            }
        }
    }

    private fun nextAnim(){
        val move1 = ObjectAnimator.ofFloat(binding.txtOnboardTitle, View.TRANSLATION_X,0f, -1000f)
        val move2 = ObjectAnimator.ofFloat(binding.txtOnboadDesc, View.TRANSLATION_X,0f, -1000f)
        val move3 = ObjectAnimator.ofFloat(binding.imageView8, View.TRANSLATION_X,0f, -1000f)
        val anim1 = ObjectAnimator.ofFloat(binding.txtOnboardTitle, View.ALPHA,1f, 0f)
        val anim2 = ObjectAnimator.ofFloat(binding.txtOnboadDesc, View.ALPHA,1f, 0f)
        val anim3 = ObjectAnimator.ofFloat(binding.imageView8, View.ALPHA,1f, 0f)

        AnimatorSet().apply {
            playTogether(move1,move2,move3,anim1,anim2,anim3)
            setDuration(400)
            addListener(object : AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    binding.txtOnboardTitle.translationX = 1000f
                    binding.txtOnboadDesc.translationX = 1000f
                    binding.imageView8.translationX = 1000f

                    binding.txtOnboardTitle.text = title[index]
                    binding.txtOnboadDesc.text = desc[index]
                    binding.imageView8.setImageResource(image[index])
                    AnimatorSet().apply {
                        playTogether(
                            ObjectAnimator.ofFloat(binding.txtOnboardTitle, View.TRANSLATION_X,1000f, 0f),
                            ObjectAnimator.ofFloat(binding.txtOnboadDesc, View.TRANSLATION_X,1000f, 0f),
                            ObjectAnimator.ofFloat(binding.imageView8, View.TRANSLATION_X,1000f, 0f),
                            ObjectAnimator.ofFloat(binding.txtOnboardTitle, View.ALPHA,0f, 1f),
                            ObjectAnimator.ofFloat(binding.txtOnboadDesc, View.ALPHA,0f, 1f),
                            ObjectAnimator.ofFloat(binding.imageView8, View.ALPHA,0f, 1f)
                        )
                        setDuration(300)
                        start()
                    }


                }
            })
            start()
        }

    }

    private fun prevAnim(){
        val move1 = ObjectAnimator.ofFloat(binding.txtOnboardTitle, View.TRANSLATION_X,0f, 1000f)
        val move2 = ObjectAnimator.ofFloat(binding.txtOnboadDesc, View.TRANSLATION_X,0f, 1000f)
        val move3 = ObjectAnimator.ofFloat(binding.imageView8, View.TRANSLATION_X,0f, 1000f)
        val anim1 = ObjectAnimator.ofFloat(binding.txtOnboardTitle, View.ALPHA,1f, 0f)
        val anim2 = ObjectAnimator.ofFloat(binding.txtOnboadDesc, View.ALPHA,1f, 0f)
        val anim3 = ObjectAnimator.ofFloat(binding.imageView8, View.ALPHA,1f, 0f)

        AnimatorSet().apply {
            playTogether(move1,move2,move3,anim1,anim2,anim3)
            setDuration(400)
            addListener(object : AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    binding.txtOnboardTitle.translationX = -1000f
                    binding.txtOnboadDesc.translationX = -1000f
                    binding.imageView8.translationX = -1000f

                    binding.txtOnboardTitle.text = title[index]
                    binding.txtOnboadDesc.text = desc[index]
                    binding.imageView8.setImageResource(image[index])
                    AnimatorSet().apply {
                        playTogether(
                            ObjectAnimator.ofFloat(binding.txtOnboardTitle, View.TRANSLATION_X,-1000f, 0f),
                            ObjectAnimator.ofFloat(binding.txtOnboadDesc, View.TRANSLATION_X,-1000f, 0f),
                            ObjectAnimator.ofFloat(binding.imageView8, View.TRANSLATION_X,-1000f, 0f),
                            ObjectAnimator.ofFloat(binding.txtOnboardTitle, View.ALPHA,0f, 1f),
                            ObjectAnimator.ofFloat(binding.txtOnboadDesc, View.ALPHA,0f, 1f),
                            ObjectAnimator.ofFloat(binding.imageView8, View.ALPHA,0f, 1f)
                        )
                        setDuration(300)
                        start()
                    }


                }
            })
            start()
        }

    }

    private fun start() {
        val anim1 = ObjectAnimator.ofFloat(binding.txtOnboardTitle, View.ALPHA, 0f, 1f)
        val anim2 = ObjectAnimator.ofFloat(binding.txtOnboadDesc, View.ALPHA, 0f, 1f)
        val anim3 = ObjectAnimator.ofFloat(binding.imageView8, View.ALPHA, 0f, 1f)
        AnimatorSet().apply {
            playTogether(anim1, anim2, anim3)
            setDuration(400)
            start()
        }
    }

    private fun dotChecker(){
        val dot1 = if(index == 0) R.drawable.dot_active else R.drawable.dot_inactive
        binding.dot1.setImageResource(dot1)

        val dot2 = if(index == 1) R.drawable.dot_active else R.drawable.dot_inactive
        binding.dot2.setImageResource(dot2)

        val dot3 = if(index == 2) R.drawable.dot_active else R.drawable.dot_inactive
        binding.dot3.setImageResource(dot3)
    }
}