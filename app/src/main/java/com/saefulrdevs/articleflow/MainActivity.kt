package com.saefulrdevs.articleflow

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saefulrdevs.articleflow.data.ProgrammingLanguage
import com.saefulrdevs.articleflow.databinding.ActivityMainBinding
import com.saefulrdevs.articleflow.view.AboutActivity
import com.saefulrdevs.articleflow.view.DetailActivity
import com.saefulrdevs.articleflow.viewmodel.ListProgrammingLanguage


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rvProgrammingLanguage: RecyclerView
    private val list = ArrayList<ProgrammingLanguage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val isLightTheme = when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> false
            Configuration.UI_MODE_NIGHT_NO -> true
            else -> false
        }

        setStatusBarIconColor(this, isLightTheme)

        setSupportActionBar(binding.toolbar)

        rvProgrammingLanguage = binding.rvPl
        rvProgrammingLanguage.setHasFixedSize(true)

        list.addAll(getListPL())
        showRecyclerList()
    }

    private fun getListPL(): ArrayList<ProgrammingLanguage> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val dataRelease = resources.getIntArray(R.array.data_release)
        val dataInventor = resources.getStringArray(R.array.data_inventor)
        val dataLink = resources.getStringArray(R.array.data_link)
        val listPL = ArrayList<ProgrammingLanguage>()
        for (i in dataName.indices) {
            val pl = ProgrammingLanguage(dataName[i], dataDescription[i], dataPhoto.getResourceId(i, -1),
                dataRelease[i], dataInventor[i], dataLink[i])
            listPL.add(pl)
        }
        return listPL
    }

    private fun showRecyclerList() {
        rvProgrammingLanguage.layoutManager = LinearLayoutManager(this)
        val listPLAdapter = ListProgrammingLanguage(list)
        rvProgrammingLanguage.adapter = listPLAdapter

        listPLAdapter.setOnItemClickCallback(object : ListProgrammingLanguage.OnItemClickCallback {
            override fun onItemClicked(data: ProgrammingLanguage) {
                val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
                intentToDetail.putExtra("DATA", data)
                startActivity(intentToDetail)
            }
        })
    }

    private fun setStatusBarIconColor(activity: Activity, isLightTheme: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = activity.window.decorView
            if (isLightTheme) {
                decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                decor.systemUiVisibility = 0
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about_page -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}