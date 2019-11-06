package com.ijk.paralaxemample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class RecyclerViewAdapter(context: Context, private val data: List<String>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {


    private var mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.recyclerview_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val animal = data[position]
        holder.myTextView?.text = animal
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var myTextView: TextView? = null

        init {
            try {
                myTextView = itemView.findViewById(R.id.tvAnimalName)
                itemView.setOnClickListener {
                    loget("onClick")
                }
            } catch (e: Exception) {
            }

        }


    }

    fun getItem(id: Int): String {
        return data[id]
    }
}