package com.example.project2

import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project2.NewNotepad
import com.example.project2.DatabaseHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    var dbHelper: DatabaseHelper? = null
    var database: SQLiteDatabase? = null
    var tableName: String? = null
    var dbName: String? = null
    var intent_toolbar: Intent? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbName = "notepad.db"
        tableName = "noteData"
        val toolbar =
            findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
        createDatabase() // 데이터베이스 생성 함수
        createTable(tableName!!) // 테이블 생성 함수
        executeQuery() // 리사이클러뷰 어댑터와 아이템 설정

//        플로팅버튼 터치시 메모작성 클래스 호출
        val fab =
            findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewNotepad::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val mSearch = menu.findItem(R.id.menu_search)
        val mSearchView =
            mSearch.actionView as SearchView
        mSearchView.queryHint = "Search"

//        서치뷰 x버튼 클릭했을때
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
        return true
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

    private fun searchQuery(query: String) {
        val sql = "select * from noteData " +
                "where content Like " + "'%" + query + "%'" + "or title Like " + "'%" + query + "%'" + "or location Like " + "'%" + query + "%'" + "order by time DESC"
        //        sql문
//        select * from noteData where content Like '%query%' or title Like '%query%' order by time DESC"
//        select 컬럼 from 테이블 | *는 모든 컬럼을 의미함
//        where 조건
//        content 컬럼내에서 Like(포함하는것) | title 컬럼도 동일함
//        입력값이 사과일때
//        %query면 썩은사과, 파인사과 등
//        query%면 사과가격, 사과하세요 등
//        %query%면 황금사과가격, 빨리사과하세요 등
//        order by 정렬 | time 컬럼을 기준으로 DESC 내림차순
//        order by를 사용하지 않거나 order by time ASC로 하면 오름차순
        val intent = Intent(this@MainActivity, NoteSearchAdapter::class.java)
        intent.putExtra("sql", sql)
        //        NoteSearchAdapter에 sql문을 전달.
        val cursor = database!!.rawQuery(
            "select * from noteData " +
                    "where content Like " + "'%" + query + "%'" + "or title Like " + "'%" + query + "%'" + "or location Like " + "'%" + query + "%'" + "order by time DESC",
            null
        )
        //        위 문자열 sql과 쿼리 sql문은 같음
        val recordCount = cursor.count
        val adapter = NoteSearchAdapter(intent)

//         어댑터 설정
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        //         리싸이클뷰 설정
        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        for (i in 0 until recordCount) {
            cursor.moveToNext()
            val title = cursor.getString(1)
            val content = cursor.getString(2)
            adapter.addItem(NoteList(title, content))
            //             어댑터에 아이템 추가
        }
        recyclerView.adapter = adapter
        //         리사이클뷰 어댑터 설정
        cursor.close()
    }

    private fun createDatabase() {
        dbHelper = DatabaseHelper(this)
        database = dbHelper!!.writableDatabase
        // 데이터베이스 생성 쓰기 가능한 상태로 설정
    }

    private fun createTable(name: String) {
        database!!.execSQL(
            "create table if not exists " + name + "("
                    + " _id integer PRIMARY KEY autoincrement, "
                    + " title text, "
                    + " content text, "
                    + " location text, "
                    + " time text)"
        )
        //        메모내용을 담을 테이블 생성
    }

    fun executeQuery() {
        val cursor =
            database!!.rawQuery("select _id, title, content from noteData order by time DESC", null)
        //        select 컬럼 from 테이블 order by 컬럼 내림차순
//        noteData 테이블에서 _id, title, content 컬럼을 time 컬럼을 기준으로 내림차순으로
        val recordCount = cursor.count
        val adapter = NoteAdapter()
        //         어댑터 설정
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        //         리싸이클뷰 설정
        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        for (i in 0 until recordCount) {
            cursor.moveToNext()
            val title = cursor.getString(1)
            val content = cursor.getString(2)
            adapter.addItem(NoteList(title, content))
            //             어댑터에 아이템 추가
        }
        val spaceDecoration = RecyclerDecoration(20)
        recyclerView.addItemDecoration(spaceDecoration)
        //         아이템간의 간격 설정
        recyclerView.adapter = adapter
        //         리사이클뷰 어댑터 설정
        cursor.close()
    }

    fun executeQueryNotSpace() {
        val cursor =
            database!!.rawQuery("select _id, title, content from noteData order by time DESC", null)
        //        select 컬럼 from 테이블 order by 컬럼 내림차순
//        noteData 테이블에서 _id, title, content 컬럼을 time 컬럼을 기준으로 내림차순으로
        val recordCount = cursor.count
        val adapter = NoteAdapter()
        //         어댑터 설정
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        //         리싸이클뷰 설정
        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        for (i in 0 until recordCount) {
            cursor.moveToNext()
            val title = cursor.getString(1)
            val content = cursor.getString(2)
            adapter.addItem(NoteList(title, content))
            //             어댑터에 아이템 추가
        }
        recyclerView.adapter = adapter
        //         리사이클뷰 어댑터 설정
        cursor.close()
    }
}