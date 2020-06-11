package com.example.taskManager


import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.taskManager.MainActivity.ListTache.comboValue
import com.example.taskManager.entité.Tache
import com.example.taskManager.fragment.ListTachesFragment
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    object ListTache {
        var TotalListTaches=  ArrayList<Tache>()
        var listTaches=  ArrayList<Tache>()
        var comboValue =0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE && resources.getBoolean(
                R.bool.isTablet
            )){
            if(comboValue ==0){
                getTodayList()
                replaceFragment(ListTachesFragment())
            }
            if(comboValue == 1){
                getWeekList()
                replaceFragment(ListTachesFragment())
            }
            if(comboValue ==2){
                getTotalList()
                replaceFragment(ListTachesFragment())
            }
        }else{
            if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT && resources.getBoolean(
                    R.bool.isTablet
                )){
                val spinner: Spinner = findViewById(R.id.combobox)
                val adapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.combo_value,
                    R.layout.support_simple_spinner_dropdown_item
                )
                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                spinner.setAdapter(adapter)
                spinner.setOnItemSelectedListener(this)
            }else{
                getTodayList()
                replaceFragment(ListTachesFragment())
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val text = parent?.getItemAtPosition(position).toString()
        if(position ==0){
            ListTache.comboValue = 0
            getTodayList()
            replaceFragment(ListTachesFragment())
        }
        if(position == 1){
            ListTache.comboValue = 1
            getWeekList()
            replaceFragment(ListTachesFragment())
        }
        if(position == 2){
            ListTache.comboValue = 2
            getTotalList()
            replaceFragment(ListTachesFragment())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater :MenuInflater = menuInflater
        inflater.inflate(R.menu.left_menu,menu)
        if(resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE || !resources.getBoolean(
                R.bool.isTablet
            )){
            for (i in 0 until menu!!.size()) menu.getItem(i).isVisible = false
        }
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {//A changer plus tard
        return when (item.itemId) {
            R.id.today -> {
                ListTache.comboValue = 0
                getTodayList()
                replaceFragment(ListTachesFragment())
                true
            }
            R.id.week -> {
                ListTache.comboValue = 1
                getWeekList()
                replaceFragment(ListTachesFragment())
                true
            }
            R.id.all -> {
                ListTache.comboValue = 2
                getTotalList()
                replaceFragment(ListTachesFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    private fun replaceFragment (fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.navigation_fragment, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
    private fun getTodayList(){
        //get today date
        val sdf = SimpleDateFormat("MM/dd/yyyy")
        val currentDate = sdf.format(Date())
        ListTache.listTaches.clear()
        for (i in ListTache.TotalListTaches) {
            if (currentDate.toString().equals(i.date)) {
                ListTache.listTaches.add(i)
            }
        }
    }


    private fun getWeekList(){
        val cal = Calendar.getInstance()
        val format1 = SimpleDateFormat("MM/dd/yyyy")
        cal.set(Calendar.DAY_OF_WEEK, cal.firstDayOfWeek);
        val date1 = cal.time
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        val date2 = cal.time
        var thisMonday: String? = null
        var nextMonday: String? = null
        thisMonday = format1.format(date1)
        nextMonday = format1.format(date2)
        ListTache.listTaches.clear()
        for (i in ListTache.TotalListTaches) {
            if(i.date>=thisMonday && i.date<=nextMonday){
                ListTache.listTaches.add(i)
            }
        }
    }
    private fun getTotalList(){
        ListTache.listTaches.clear()
        for (i in ListTache.TotalListTaches) {
            ListTache.listTaches.add(i)
        }
    }

}
