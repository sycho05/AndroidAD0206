package com.janursyahputra.readdataapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val TWisata = "twisata"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val twisata = intent.getParcelableExtra(TWisata) as TempatWisata
        supportActionBar?.title = twisata.nama.toString()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        tv_alamat.text = twisata.alamat.toString()
        tv_desk.text = twisata.deskripsi.toString()
        Glide.with(this)
            .load(twisata.gambar.toString())
            .apply(RequestOptions().override(700, 700))
            .into(iv_detail_photo)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
