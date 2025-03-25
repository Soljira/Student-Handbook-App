package com.example.studenthandbookapp.scholarships
import MyAdapter
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studenthandbookapp.R
import com.example.studenthandbookapp.helpers.BottomNavigationHelper
import com.example.studenthandbookapp.helpers.BottomNavigationHelper.unselectBottomNavIcon
import com.example.studenthandbookapp.helpers.DrawerNavigationHelper
import com.example.studenthandbookapp.helpers.TopAppBarHelper
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class ScholarshipPage2 : AppCompatActivity() {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<ScholarshipsDataClass>
    private lateinit var heading: Array<String>
    lateinit var scholarshipDescriptions : Array<String>

    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var topAppBar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scholarship_page2)
        initializeNavigationStuff()

        val menuItem = bottomNavigationView.menu.findItem(R.id.nav_scholarship)
        menuItem?.isChecked = true  // ensures that scholarship button thingy stays checked

        heading = arrayOf(
            "1. PHINMA Scholarship (formerly Presidential Scholarship)",
            "2. Student Assistance Scholarship",
            "3. Valedictorian & Salutatorian Scholarship",
            "4. Dean's List Scholarship",
            "5. With Highest Honor",
            "6. PHINMA UPang Direct Dependent Scholarship",
            "7. Hawak Kamay Scholarship",
            "8. Victory Liner, 5-Star, and Transasia Oil Employee & Direct Dependent Scholarship",
            "9. The Medical City Employee & Direct Dependent Scholarship",
            "10. Kapamilya @ PHINMA UPang Scholarship",
            "11. PHINMA UPang Alumni Scholarship"
        )

        scholarshipDescriptions = arrayOf(
            getString(R.string.scholarship_1),
            getString(R.string.scholarship_2),
            getString(R.string.scholarship_3),
            getString(R.string.scholarship_4),
            getString(R.string.scholarship_5),
            getString(R.string.scholarship_6),
            getString(R.string.scholarship_7),
            getString(R.string.scholarship_8),
            getString(R.string.scholarship_9),
            getString(R.string.scholarship_10),
            getString(R.string.scholarship_11),
        )

        newRecyclerView = findViewById(R.id.recyclerview)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)
        newArrayList = arrayListOf<ScholarshipsDataClass>()
        getUserData()
    }

    private fun getUserData() {
        for (i in heading.indices){
            val scholarshipsDataClass = ScholarshipsDataClass(heading[i])
            newArrayList.add(scholarshipsDataClass)
        }

        val adapter = MyAdapter(newArrayList)
        newRecyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : MyAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
//                Toast.makeText(this@MainActivity, "Clicked item $position", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@ScholarshipPage2, SchDescriptionsActivity::class.java)
                intent.putExtra("heading", newArrayList[position].title)



                intent.putExtra("description", scholarshipDescriptions[position])
                startActivity(intent)
            }
        })
    }

    fun initializeNavigationStuff() {
        drawerLayout = findViewById(R.id.drawer_layout)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        navigationView = findViewById(R.id.navigation_view)
        topAppBar = findViewById(R.id.topAppBar)

        TopAppBarHelper.setupTopAppBar(this, topAppBar, drawerLayout, "Scholarships")
        BottomNavigationHelper.setupBottomNavigation(this, bottomNavigationView)
        DrawerNavigationHelper.setupDrawerNavigation(this, drawerLayout, navigationView)

//        bottomNavigationView.selectedItemId = R.id.nav_home
        unselectBottomNavIcon(bottomNavigationView)
    }
}