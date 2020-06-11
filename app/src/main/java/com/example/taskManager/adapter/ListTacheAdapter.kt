package com.example.taskManager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.taskManager.R
import com.example.taskManager.entité.Tache
import kotlinx.android.synthetic.main.taches.view.*

class ListTacheAdapter(context: Context, var listTacheAdapter: ArrayList<Tache>) :
    BaseAdapter() {

    var context: Context?= context

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        var view=inflater.inflate(R.layout.taches,null)
        val tache=listTacheAdapter[position]
        view.name_tache.text = tache.name
        view.date_tache.text = tache.date.toString()
        return view
    }

    override fun getItem(position: Int): Any {
        return listTacheAdapter[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listTacheAdapter.size
    }
}