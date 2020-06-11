package com.example.taskManager.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.taskManager.MainActivity
import com.example.taskManager.R
import com.example.taskManager.date.DatePickerFragment
import com.example.taskManager.entité.Tache
import java.text.SimpleDateFormat
import java.util.*


class AddTacheFragment : Fragment() {

    lateinit var name_tache :EditText
    companion object {
        const val REQUEST_CODE_DATE_PICKER = 100
        fun newInstance() = AddTacheFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_tache_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        name_tache = view?.findViewById<EditText>(R.id.edit_name_tache)!!
        name_tache.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_GO){
                val fm: FragmentManager = (activity as AppCompatActivity?)!!.supportFragmentManager
                val newFragment: AppCompatDialogFragment =
                    DatePickerFragment()
                newFragment.setTargetFragment(this,
                    REQUEST_CODE_DATE_PICKER
                )
                newFragment.show(fm, "datePicker")
                true
            } else {
                false
            }
        }
    }
    private fun addTache(nom:String, date: String){
        MainActivity.ListTache.TotalListTaches.add(
            Tache(nom, date)
        )
        if(MainActivity.ListTache.comboValue ==2) {
            MainActivity.ListTache.listTaches.add(
                Tache(nom, date)
            )
        }else{
            if(MainActivity.ListTache.comboValue ==1) {
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
                if(date>=thisMonday && date<=nextMonday){
                    MainActivity.ListTache.listTaches.add(
                        Tache(nom, date)
                    )
                }
            }else{
                val sdf = SimpleDateFormat("MM/dd/yyyy")
                val currentDate = sdf.format(Date())
                if (currentDate.toString().equals(date)) {
                    MainActivity.ListTache.listTaches.add(
                        Tache(nom, date)
                    )
                }

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_DATE_PICKER) {
                val selectedDate = data?.getStringExtra("selectedDate").toString()
                addTache(name_tache.text.toString(),selectedDate)
                //hide keyboard
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, 0)
                replaceFragment(ListTachesFragment())
            }
        }
    }
    private fun replaceFragment (fragment: Fragment){
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.navigation_fragment , fragment)
        fragmentTransaction?.addToBackStack(null)
        fragmentTransaction?.commit()
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.retour_nav,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {//A changer plus tard
        return when (item.itemId) {
            R.id.retour -> {
                replaceFragment(ListTachesFragment())
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}
