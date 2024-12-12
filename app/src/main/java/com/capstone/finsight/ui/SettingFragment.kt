package com.capstone.finsight.ui

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.capstone.finsight.R
import com.capstone.finsight.data.ProfileVMF
import com.capstone.finsight.data.ProfileViewModel
import com.capstone.finsight.data.SettingVMF
import com.capstone.finsight.data.SettingViewModel
import com.capstone.finsight.databinding.ActivityMainBinding
import com.capstone.finsight.databinding.FragmentSettingBinding
import com.capstone.finsight.network.Result
import com.capstone.finsight.utils.ImageUtils
import com.capstone.finsight.utils.ImageUtils.reduceFileImage
import kotlinx.coroutines.launch

class SettingFragment : Fragment() {
    private lateinit var binding : FragmentSettingBinding
    private val settingVM by viewModels<SettingViewModel>{
        SettingVMF.getInstance(requireActivity())
    }

    private var currentImageUri: Uri? = null
    @RequiresApi(Build.VERSION_CODES.Q)
    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        uri?.let {
            currentImageUri = uri
            addPic()
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
            } else {
                Toast.makeText(requireActivity(), "Notifications permission rejected", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            settingVM.getNotif()
            binding.switchTheme.isChecked = settingVM.getTheme()
            val image = if(settingVM.getProf() != "") settingVM.getProf() else R.drawable.example1
            Glide.with(requireActivity())
                .load(image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imgUser)
        }

        settingVM.notifEnabled.observe(viewLifecycleOwner){
            binding.switch2.isChecked = it
        }

        binding.fabAdd.setOnClickListener{
            launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.txtProfileRiskSet.setOnClickListener {
            val intent = Intent(requireActivity(),RiskActivity::class.java)
            requireActivity().startActivity(intent)
        }

        binding.btnLogout.setOnClickListener{
            lifecycleScope.launch{
                settingVM.logout()
            }
            val intent = Intent(requireActivity(),LoginActivity::class.java)
            requireActivity().startActivity(intent)
            requireActivity().finish()
        }

        binding.switch2.setOnCheckedChangeListener { _, isChecked ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
            settingVM.saveNotif(isChecked)
            Log.d("Setting", isChecked.toString())
        }

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            binding.switchTheme.isEnabled = false
            if(isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            settingVM.saveTheme(isChecked)
            binding.switchTheme.isEnabled = true

        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun addPic(){
        val imageFile = currentImageUri?.let { ImageUtils.uriToFile(it, requireActivity()).reduceFileImage() }
        if (imageFile != null) {
            settingVM.setImage(imageFile).observe(viewLifecycleOwner){
                when(it){
                    is Result.Error -> {}
                    Result.Loading -> {}
                    is Result.Success -> {binding.imgUser.setImageURI(currentImageUri)}
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SettingFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}