package xyz.ryujpn.mymessenger

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import xyz.ryujpn.mymessenger.Fragment.HomeFragment
import xyz.ryujpn.mymessenger.Fragment.SearchFragment
import xyz.ryujpn.mymessenger.Fragment.SettingFragment
import xyz.ryujpn.mymessenger.Fragment.TalkFragment

class DrawerMainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, HomeFragment())
                    .commit()
                supportActionBar!!.setTitle("ホーム")
                return@OnNavigationItemSelectedListener true
            }R.id.navigation_search -> {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, SearchFragment())
                .commit()
            supportActionBar!!.setTitle("検索")
            return@OnNavigationItemSelectedListener true
        }
            R.id.navigation_talk -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, TalkFragment())
                    .commit()
                supportActionBar!!.setTitle("トーク")
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val drawView: NavigationView = findViewById(R.id.nav_view_drawer)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        supportActionBar!!.setTitle("ホーム")

        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, HomeFragment())
            .commit()

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
//        nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)


        drawView.setNavigationItemSelectedListener(this)

    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.drawer_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
            when (item.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, HomeFragment())
                        .commit()
                    supportActionBar!!.setTitle("ホーム")
                }
                R.id.navigation_setting -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, SettingFragment())
                        .commit()
                    supportActionBar!!.setTitle("設定")

                }
                R.id.mytodolist -> {
                    val intent = Intent(this,MyListActivity::class.java)
                    startActivity(intent)

                }

                R.id.nav_share -> {

                }
                R.id.nav_talk -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, TalkFragment())
                        .commit()
                    supportActionBar!!.setTitle("トーク")
                }
            }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}