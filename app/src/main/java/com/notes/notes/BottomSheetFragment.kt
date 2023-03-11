package com.notes.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.notes.notes.databinding.BottomsheetFragmentBinding
import com.notes.notes.model.BottomSheetInterface

class BottomSheetFragment(val callback: BottomSheetInterface): BottomSheetDialogFragment() {
    private lateinit var binding: BottomsheetFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomsheetFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.blackColor.setOnClickListener{
            setColor("#000000")
        }

        binding.orangeColor.setOnClickListener{
            setColor("#FF6700")
        }

        binding.redColor.setOnClickListener{
            setColor("#F44336")
        }

        binding.greenColor.setOnClickListener{
            setColor("#4CAF50")
        }

        binding.blueColor.setOnClickListener{
            setColor("#3F51B5")
        }

        binding.yellowColor.setOnClickListener{
            setColor("#FFEB3B")
        }

        binding.grayColor.setOnClickListener{
            setColor("#918B8B")
        }

        binding.purpleColor.setOnClickListener{
            setColor("#FFBB86FC")
        }
    }

    private fun setColor(color: String) {
        callback.callbackMethod(color)
        dismiss()
    }

}