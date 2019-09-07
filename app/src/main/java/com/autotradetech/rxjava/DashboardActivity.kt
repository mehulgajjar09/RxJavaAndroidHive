package com.autotradetech.rxjava

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.autotradetech.rxjava.Adapter.AdapterDashboard
import com.autotradetech.rxjava.Listener.ListenerDashboard
import kotlinx.android.synthetic.main.activity_main.*

class DashboardActivity : AppCompatActivity(), ListenerDashboard {
    override fun OnDashboardItemClick(position: Int) {
        when(position){
            0 -> startActivity(Intent(this,BasicStepsActivity::class.java).putExtra("title","Basic Steps"))
            1 -> startActivity(Intent(this,DisposableActivity::class.java).putExtra("title","Disposable"))
            2 -> startActivity(Intent(this,IntroducingOperatorActivity::class.java).putExtra("title","Introducing Operator - Filter()"))
            3 -> startActivity(Intent(this,MultipleObserverAndCompositeDisposableActivity::class.java).putExtra("title","Multiple Observer & Composite Disposable"))
            4 -> startActivity(Intent(this,CustomDataTypeOperatorActivity::class.java).putExtra("title","Custom Data Type & Operators"))
        }
    }

    var titleList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        titleList.add("Basic Steps")
        titleList.add("Disposable")
        titleList.add("Introducing Operator - Filter()")
        titleList.add("Multiple Observer & Composite Disposable")
        titleList.add("Custom Data Type & Operators")

        val linearLayoutManager = LinearLayoutManager(this)
        recyclerViewAllExampleList.layoutManager = linearLayoutManager
        recyclerViewAllExampleList.itemAnimator = DefaultItemAnimator()
        recyclerViewAllExampleList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerViewAllExampleList.adapter = AdapterDashboard(this, titleList, this)

    }
}
