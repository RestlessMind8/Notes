package com.notes.notes


import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.lifecycleScope
import com.notes.notes.data.NoteSingleton
import com.notes.notes.databinding.ActivityEditNoteBinding
import com.notes.notes.model.AppDatabase
import com.notes.notes.model.BottomSheetInterface
import com.notes.notes.model.Note
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class EditNote : AppCompatActivity(), BottomSheetInterface {
    private lateinit var binding: ActivityEditNoteBinding
    private var editing : Boolean = true
    private lateinit var bottomSheetFragment : BottomSheetFragment
    private lateinit var callback: BottomSheetInterface
    private lateinit var dateTime: String
    private lateinit var formatter: SimpleDateFormat
    private var color: String = "#3F51B5" //default color
    private var code = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)
        setSupportActionBar(findViewById(R.id.toolbar))
        this.binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //BottomSheetInterface callback
        callback = this
        this.bottomSheetFragment = BottomSheetFragment(callback)

        //get date and time
        val time = Calendar.getInstance().time
        formatter = SimpleDateFormat("dd/MM/yy HH:mm")
        this.dateTime = formatter.format(time).toString()

        //Note code
        this.code = intent.getIntExtra("noteCode", -1)

        if(code == -1){
            //set the date and time of the last update
            binding.lastEditInfo.text = this.dateTime
            setResult(1)
        }else{
            edit()
            val note = NoteSingleton.getNote(this.code)
            binding.title.text = note.title
            setToolbarColor(note.color)
            binding.multiAutoCompleteTextView.setText(note.text)
            binding.lastEditInfo.text = note.lastUpdate
            setResult(0)
        }

        //click events
        binding.editTitle.setOnClickListener {
            edit()
        }
        binding.title.setOnClickListener {
            edit()
        }

        //color picker
        binding.colorPicker.setOnClickListener{
            bottomSheetFragment.show(supportFragmentManager, "BottomSheetDialog")
        }

        //multiAutoCompleteTextView
        binding.multiAutoCompleteTextView.setOnClickListener{
            if(!editing){
                edit()
            }
        }
    }


    override fun onBackPressed() {
        if(editing){
            edit()
            if(this.code == -1){
                NoteSingleton.addNote(
                    binding.inputTitle.text.toString(),
                    binding.multiAutoCompleteTextView.text.toString(),
                    this.dateTime,
                    this.color
                )
                addNote(NoteSingleton.getLast())
            }else{
                NoteSingleton.updateNote(
                    this.code,
                    binding.inputTitle.text.toString(),
                    binding.multiAutoCompleteTextView.text.toString(),
                    this.dateTime,
                    this.color
                )
                updateNote(NoteSingleton.getNote(this.code))
            }
            Toast.makeText(this, "Salvo", Toast.LENGTH_SHORT).show()
        }else{
            super.onBackPressed()
        }
    }


    override fun callbackMethod(color: String) {
        setToolbarColor(color)
    }


    private fun setToolbarColor(color: String){
        this.color = color
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
                binding.inputTitle.setText(strTitle)
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
            binding.editing.text = "Editando"
            binding.colorPicker.visibility = View.VISIBLE
            binding.title.visibility = View.INVISIBLE
            binding.editTitle.visibility = View.INVISIBLE
            binding.inputTitle.visibility = View.VISIBLE
            binding.inputTitle.setText(strTitle)
            binding.inputTitle.setSelection(binding.inputTitle.text.length)
            binding.multiAutoCompleteTextView.isCursorVisible = true
        }
    }

    private fun addNote(note : Note){
        lifecycleScope.launch{
            AppDatabase(this@EditNote).getNoteDao().addNote(note)
        }
    }

    private fun updateNote(note: Note){
        lifecycleScope.launch{
            AppDatabase(this@EditNote).getNoteDao().updateNote(note)
        }
    }

}