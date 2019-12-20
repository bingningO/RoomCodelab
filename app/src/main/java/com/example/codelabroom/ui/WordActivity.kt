package com.example.codelabroom.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codelabroom.R
import com.example.codelabroom.data.Word
import com.example.codelabroom.viewmodel.WordViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class WordActivity : AppCompatActivity() {

    private lateinit var wordViewModel: WordViewModel
    private lateinit var adapter: WordListAdapter

    companion object {
        private val NewWordActivityRequestCode = 1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NewWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let {
                val word = Word(it)
                wordViewModel.insert(word)
            }?: showEmptyWordToast()
        } else {
            showEmptyWordToast()
        }
    }

    private fun showEmptyWordToast() {
        toast(R.string.empty_not_saved)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setList()
        observeViewModel()
        setClick()
    }

    private fun setList() {
        adapter = WordListAdapter(this)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)
    }

    private fun observeViewModel() {
        wordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)
        wordViewModel.allWords.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter.setWords(it) }
        })
    }

    private fun setClick() {
        fab.setOnClickListener {
            val intent = Intent(this, NewWordActivity::class.java)
            startActivityForResult(intent, NewWordActivityRequestCode)
        }
    }
}
