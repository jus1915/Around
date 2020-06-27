package com.example.final_project

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.icu.text.IDNA
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R
import androidx.lifecycle.Observer
import androidx.room.Dao
import com.example.final_project.dialog.NoteCreateDialog
import com.example.final_project.list.NoteAdapter
import com.example.final_project.room.AppDatabase
import com.google.android.material.bottomappbar.BottomAppBar
import kotlinx.android.synthetic.main.activity_main.*
import androidx.room.RawQuery
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.final_project.list.noteSearchAdapter
import com.example.final_project.room.NoteEntity
import com.example.final_project.ui.ListFragment
import kotlinx.android.synthetic.main.fragment_list.view.*

class MainActivity : AppCompatActivity() {
    var intent_toolbar: Intent? = null
    var database: AppDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val controller = findNavController(R.id.my_nav_host_fragment)
        controller.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.listFragment -> {
                    bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                    fab_add_note.setImageResource(R.drawable.ic_plus)
                    fab_add_note.setOnClickListener {
                        NoteCreateDialog().show(
                            supportFragmentManager,
                            null
                        )
                    }
                }
                R.id.detailFragment -> {
                    bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                    fab_add_note.setImageResource(R.drawable.ic_back)
                    fab_add_note.setOnClickListener { controller.popBackStack() }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        val mSearch = menu.findItem(R.id.menu_search)
        val mSearchView =
            mSearch.actionView as SearchView
        mSearchView.queryHint = "Search"

        mSearchView.setOnCloseListener {
            val inputMethodManager =
                getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(
                InputMethodManager.HIDE_IMPLICIT_ONLY,
                0
            )
            //                자판패드 숨김
            mSearchView.clearFocus()
            //                포커스 제거
            false
        }
        mSearchView.setOnClickListener {
            mSearchView.isIconified = false
            //                위 소스가 없으면 돋보기 모양을 눌러야만 검색이 가능함.
        }
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
//                확인버튼 누르면 서치쿼리 메소드 호출
                searchQuery(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
//                    입력한 텍스트가 없으면 기본 쿼리 메소드 호출
                    executeQueryNotSpace()
                } else {
//                    입력한 텍스트가 있으면 서치쿼리 메소드 호출
                    searchQuery(newText)
                }
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val curId = item.itemId
        when (curId) {
            R.id.menu_delete -> {
                intent_toolbar = Intent(this@MainActivity, Info::class.java)
                startActivity(intent_toolbar)
            }
            R.id.menu_password -> {
                intent_toolbar = Intent(this@MainActivity, SettingPassword::class.java)
                startActivity(intent_toolbar)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun searchQuery(search: String) {
        val noteDao by lazy { AppDatabase.getDatabase(this).noteDao() }
        val intent = Intent(this@MainActivity, noteSearchAdapter::class.java)
        val text:String = "%$search%"
        intent.putExtra("search", text)
        val noteAdapter = noteSearchAdapter(intent)
        val recyclerView = findViewById<RecyclerView>(R.id.list_notes)
        //         리싸이클뷰 설정
        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        noteDao.searchNotes(text).observe(this, Observer {
            noteAdapter.notes = it//어댑터에 변경된 note 전달
            noteAdapter.notifyDataSetChanged()//어댑터에 변경 공지
        })
        recyclerView.adapter = noteAdapter

    }
    fun executeQueryNotSpace() {
        val noteDao by lazy { AppDatabase.getDatabase(this).noteDao() }

        val adapter = NoteAdapter()
        //         어댑터 설정
        val recyclerView = findViewById<RecyclerView>(R.id.list_notes)
        //         리싸이클뷰 설정
        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        noteDao.selectNotes().observe(this, Observer {
            adapter.notes = it//어댑터에 변경된 note 전달
            adapter.notifyDataSetChanged()//어댑터에 변경 공지
        })

        recyclerView.adapter = adapter
        //         리사이클뷰 어댑터 설정
    }
    override fun onDestroy() {
        super.onDestroy()
    }
}