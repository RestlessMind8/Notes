package com.notes.notes


import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.notes.notes.databinding.ActivityEditNoteBinding
import com.notes.notes.model.BottomSheetInterface
import com.notes.notes.model.Note
import java.text.SimpleDateFormat
import java.util.*


class EditNote : AppCompatActivity(), BottomSheetInterface {
    private lateinit var binding: ActivityEditNoteBinding
    private var editing : Boolean = true
    private lateinit var bottomSheetFragment : BottomSheetFragment
    private lateinit var callback: BottomSheetInterface
    private lateinit var dateTime: String
    private lateinit var formatter: SimpleDateFormat


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)
        setSupportActionBar(findViewById(R.id.toolbar))
        this.binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //note code
        val code = intent.getIntExtra("noteCode", -1)

        //BottomSheetInterface callback
        callback = this
        this.bottomSheetFragment = BottomSheetFragment(callback)

        //date and time
        val time = Calendar.getInstance().time
        formatter = SimpleDateFormat("dd/MM/yy HH:mm")
        this.dateTime = formatter.format(time).toString()

        //set the date and time of the last edit
        if(code == -1){
            binding.lastEditInfo.text = this.dateTime
        }

        //click events
        binding.editTitle.setOnClickListener {
            edit()
        }
        binding.title.setOnClickListener {
            edit()
        }

        binding.colorPicker.setOnClickListener{
            bottomSheetFragment.show(supportFragmentManager, "BottomSheetDialog")
        }

        binding.multiAutoCompleteTextView.setOnClickListener{
            if(!editing){
                edit()
            }
        }
    }


    override fun onBackPressed() {
        if(editing){
            Toast.makeText(this, "Salvo", Toast.LENGTH_SHORT).show()
            edit()
        }else{
            super.onBackPressed()
        }
    }


    override fun callbackMethod(color: String) {
        binding.colorPicker.setBackgroundColor(Color.parseColor(color))
        if(color.length == 7)
             binding.toolbar.setBackgroundColor(Color.parseColor("#A1" + color.substring(1, color.length)))
        else
             binding.toolbar.setBackgroundColor(Color.parseColor("#A1" + color.substring(3, color.length)))

    }


    private fun edit() {
        var strTitle: String
        if(editing){
            editing = false
            binding.editing.text = "Há 0 seg"
            strTitle = binding.inputTitle.text.toString()
            if(strTitle.isBlank()){
                strTitle = this.dateTime.substring(0, this.dateTime.indexOf(" "))
            }
            binding.colorPicker.visibility = View.INVISIBLE
            binding.editTitle.visibility = View.VISIBLE
            binding.inputTitle.visibility = View.INVISIBLE
            binding.title.visibility = View.VISIBLE
            binding.title.text = strTitle
            binding.multiAutoCompleteTextView.isCursorVisible = false
        }else{
            editing = true
            strTitle = binding.title.text.toString()
            binding.editing.text = R.string.editing.toString()
            binding.colorPicker.visibility = View.VISIBLE
            binding.title.visibility = View.INVISIBLE
            binding.editTitle.visibility = View.INVISIBLE
            binding.inputTitle.visibility = View.VISIBLE
            binding.inputTitle.setText(strTitle)
            binding.inputTitle.setSelection(binding.inputTitle.text.length)
            binding.multiAutoCompleteTextView.isCursorVisible = true
        }
    }
}