package com.janursyahputra.readdataapp

import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MainAdapter

    val CONTENT_URI: Uri = Uri.Builder().scheme("content")
        .authority("com.JANURSYAHPUTRA.janur178.datawisata")
        .appendPath("tempat_wisata_table")
        .build()

    private var list = ArrayList<TempatWisata>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = MainAdapter(this,this@MainActivity)
        rv_notes.adapter = adapter
        rv_notes.layoutManager = LinearLayoutManager(this)
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadNotesAsync()
            }
        }
        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)
        loadNotesAsync()

    }

    fun mapCursorToArrayList(notesCursor: Cursor): java.util.ArrayList<TempatWisata> {
        val notesList = java.util.ArrayList<TempatWisata>()
        while (notesCursor.moveToNext()) {
            val id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow("id"))
            val nama = notesCursor.getString(notesCursor.getColumnIndexOrThrow("nama"))
            val alamat = notesCursor.getString(notesCursor.getColumnIndexOrThrow("alamat"))
            val deskripsi = notesCursor.getString(notesCursor.getColumnIndexOrThrow("deskripsi"))
            val gambar = notesCursor.getString(notesCursor.getColumnIndexOrThrow("gambar"))
            notesList.add(TempatWisata(id, nama, alamat, deskripsi, gambar))
        }
        return notesList
    }
    private fun loadNotesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null) as Cursor
                mapCursorToArrayList(cursor)
            }
            val notesList = deferredNotes.await()
            if (notesList.size > 0) {
                adapter.listTempatWisata = notesList
            } else {
                adapter.listTempatWisata = ArrayList()
            }
        }
    }
}
