package com.notes.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RelativeLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.notes.notes.data.NoteSingleton
import com.notes.notes.databinding.ActivityMainBinding
import com.notes.notes.model.AppDatabase
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var floatingButton: FloatingActionButton
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var binding: ActivityMainBinding

    private val getAction = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if(it.resultCode == 1){
            noteAdapter.notifyItemInserted(noteAdapter.itemCount - 1)
            binding.recyclerView.scrollToPosition(noteAdapter.itemCount - 1)
        }else if(it.resultCode == 0){
            noteAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        this.binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.floatingButton = findViewById(R.id.floatingButton)
        this.floatingButton.setOnClickListener{
            openEditNoteActivity()
        }
        initNoteList()
        initRecyclerView()
    }

    private fun initRecyclerView(){
        this.noteAdapter = NoteAdapter(NoteSingleton.getNotes()){ note ->
            val intent = Intent(this, EditNote::class.java)
            intent.putExtra("noteCode", note.code)
            getAction.launch(intent)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = noteAdapter
        }
    }

    private fun openEditNoteActivity(){
        val intent = Intent(this, EditNote::class.java)
        intent.putExtra("noteCode", -1)
        getAction.launch(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.three_dots, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itemSair -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initNoteList(){
        lifecycleScope.launch {
            val noteList = AppDatabase(this@MainActivity).getNoteDao().getAllNotes()
            NoteSingleton.init(noteList)
        }
    }
}