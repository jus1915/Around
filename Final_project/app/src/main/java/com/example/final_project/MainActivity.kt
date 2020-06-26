package com.example.final_project

import android.app.Activity
import android.content.Intent
import android.icu.text.IDNA
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.navigation.findNavController
import com.example.final_project.R
import com.example.final_project.dialog.NoteCreateDialog
import com.google.android.material.bottomappbar.BottomAppBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var intent_toolbar: Intent? = null
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
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
    override fun onDestroy() {
        super.onDestroy()
    }
}