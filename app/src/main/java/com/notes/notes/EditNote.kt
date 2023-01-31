package com.notes.notes


import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.notes.notes.databinding.ActivityEditNoteBinding
import yuku.ambilwarna.AmbilWarnaDialog


class EditNote : AppCompatActivity() {
    private lateinit var binding: ActivityEditNoteBinding
    private var editing : Boolean = true
    private var mRootLayoutBgColor: Int = Color.WHITE


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)
        setSupportActionBar(findViewById(R.id.toolbar))

        this.binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editTitle.setOnClickListener {
            edit()
        }
        binding.title.setOnClickListener {
            edit()
        }

        binding.colorPicker.setOnClickListener{
            openColorPicker()
        }

        binding.multiAutoCompleteTextView.setOnClickListener{
            if(!editing){
                edit()
            }
        }

    }

    override fun onBackPressed() {
        if(editing){
            Toast.makeText(this, "Salvo.", Toast.LENGTH_SHORT).show()
            edit()
        }else{
            super.onBackPressed()
        }
    }

    private fun openColorPicker() {
        //Create color picker dialog
        val ambilWarnaDialog =
            AmbilWarnaDialog(this, mRootLayoutBgColor, object :
                AmbilWarnaDialog.OnAmbilWarnaListener {
                override fun onCancel(dialog: AmbilWarnaDialog) {}
                override fun onOk(dialog: AmbilWarnaDialog, color: Int) {
                    mRootLayoutBgColor = color
                    //Set root layout background color
                    binding.colorPicker.setBackgroundColor(mRootLayoutBgColor)
                }
            })
        //show color picker dialog
        ambilWarnaDialog.show()
    }

    private fun edit() {
        var str: String
        if(editing){
            editing = false
            binding.editing.text = "Há 0 seg"
            str = binding.inputTitle.text.toString()
            binding.colorPicker.visibility = View.INVISIBLE
            binding.editTitle.visibility = View.VISIBLE
            binding.inputTitle.visibility = View.INVISIBLE
            binding.title.visibility = View.VISIBLE
            binding.title.text = str
            binding.multiAutoCompleteTextView.isCursorVisible = false
        }else{
            editing = true
            str = binding.title.text.toString()
            binding.editing.text = "Editando"
            binding.colorPicker.visibility = View.VISIBLE
            binding.title.visibility = View.INVISIBLE
            binding.editTitle.visibility = View.INVISIBLE
            binding.inputTitle.visibility = View.VISIBLE
            binding.inputTitle.setText(str)
            binding.inputTitle.setSelection(binding.inputTitle.text.length)
            binding.multiAutoCompleteTextView.isCursorVisible = true
        }
    }
}