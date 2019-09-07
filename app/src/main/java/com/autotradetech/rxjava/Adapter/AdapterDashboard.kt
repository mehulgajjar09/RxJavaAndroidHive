package com.autotradetech.rxjava.Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.autotradetech.rxjava.Listener.ListenerDashboard
import com.autotradetech.rxjava.R

class AdapterDashboard(private val activity: Activity, private val titleList: ArrayList<String>, private val listnerDashboard : ListenerDashboard) :
    RecyclerView.Adapter<AdapterDashboard.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.row_single_dashboard, parent, false))
    }

    override fun getItemCount(): Int {
        return titleList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val title = titleList[position]

        holder.txtTitle.setText(title)
        holder.ll_row_single_dashbord.setOnClickListener {
            listnerDashboard.OnDashboardItemClick(position)
        }
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtTitle: TextView
        val ll_row_single_dashbord: LinearLayout

        init {
            txtTitle = view.findViewById(R.id.txtTitle)
            ll_row_single_dashbord = view.findViewById(R.id.ll_row_single_dashbord)
        }
    }
}