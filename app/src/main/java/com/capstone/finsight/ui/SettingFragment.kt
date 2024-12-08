package com.capstone.finsight.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
        if(binding.switchTheme.isChecked){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        binding.fabAdd.setOnClickListener{
            launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.btnSetProfile.setOnClickListener {
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
        fun newInstance(param1: String, param2: String) =
            SettingFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}