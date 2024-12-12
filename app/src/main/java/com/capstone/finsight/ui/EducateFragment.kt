package com.capstone.finsight.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.capstone.finsight.R
import com.capstone.finsight.databinding.FragmentEducateBinding
import com.capstone.finsight.dataclass.FAQ


class EducateFragment : DialogFragment() {
    private lateinit var binding : FragmentEducateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEducateBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val faq : FAQ? =  arguments?.getParcelable("FAQ")
        binding.textView19.text = faq?.question
        binding.textView20.text = faq?.answer
        dialog?.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog)
        val marginInDp = 16
        val marginInPx = resources.displayMetrics.density * marginInDp
        dialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels - (marginInPx * 2)).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    companion object {
        @JvmStatic
        fun newInstance(faq : FAQ) =
            EducateFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("FAQ", faq)
                }
            }
    }
}