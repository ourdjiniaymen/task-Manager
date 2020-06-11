package com.example.taskManager.fragment

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.example.taskManager.MainActivity

import com.example.taskManager.R
import com.example.taskManager.adapter.ListTacheAdapter
import kotlinx.android.synthetic.main.list_taches_fragment.*

class ListTachesFragment : Fragment() {

    companion object {
        fun newInstance() = ListTachesFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_taches_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter= ListTacheAdapter(
            context!!,
            MainActivity.ListTache.listTaches
        )
        list_taches.adapter=adapter
        setHasOptionsMenu(true)
        list_taches.onItemClickListener =
            AdapterView.OnItemClickListener { arg0, arg1, position, arg3 ->
                dropTacheParCarac( MainActivity.ListTache.listTaches[position].name, MainActivity.ListTache.listTaches[position].date)
                MainActivity.ListTache.listTaches.removeAt(position)
                activity?.supportFragmentManager?.beginTransaction()?.detach(this)?.attach(this)?.commit();
            }
    }
    private fun replaceFragment (fragment: Fragment){
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.navigation_fragment , fragment)
        fragmentTransaction?.addToBackStack(null)
        fragmentTransaction?.commit()
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_nav,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {//A changer plus tard
        return when (item.itemId) {
            R.id.add_tache -> {
                replaceFragment(AddTacheFragment())
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun dropTacheParCarac(nom:String,date:String){
        for (i in MainActivity.ListTache.TotalListTaches){
            if(i.name.equals(nom) && i.date.equals(date)) MainActivity.ListTache.TotalListTaches.remove(i)
        }

    }

}
