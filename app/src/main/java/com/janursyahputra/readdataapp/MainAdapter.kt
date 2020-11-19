package com.janursyahputra.readdataapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_item.view.*

class MainAdapter(private val activity: Activity, val context: Context) : RecyclerView.Adapter<MainAdapter.NoteViewholder>() {
    var listTempatWisata = ArrayList<TempatWisata>()
        set(listTempatWisata) {
            this.listTempatWisata.clear()
            this.listTempatWisata.addAll(listTempatWisata)
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item, parent, false)
        return NoteViewholder(view)
    }
    override fun onBindViewHolder(holder: NoteViewholder, position: Int) {
        holder.bind(listTempatWisata[position])
        val twisata = listTempatWisata[position]
        holder.itemView.setOnClickListener {
            val moveWithObjectIntent = Intent(context,DetailActivity::class.java)
            moveWithObjectIntent.putExtra(DetailActivity.TWisata, twisata)
            context.startActivity(moveWithObjectIntent)
        }

    }
    override fun getItemCount(): Int {
        return this.listTempatWisata.size
    }
    inner class NoteViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: TempatWisata) {
            with(itemView){
                Glide.with(itemView.context)
                    .load(note.gambar)
                    .apply(RequestOptions().override(350, 550))
                    .into(imgPhoto)
                tv_item_name.text = note.nama
                tv_item_address.text = note.alamat
            }
        }
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
    }
}