package ru.vlmor.diaryhealth

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import ru.vlmor.diaryhealth.ViewModel.DairiesViewModel
import ru.vlmor.diaryhealth.data.model.Dairy

//https://developer.android.com/topic/libraries/architecture
class MainActivity : AppCompatActivity() {

    private val dairyViewModel: DairiesViewModel by lazy{
        ViewModelProviders.of(this).get(DairiesViewModel::class.java)
    }

    private val populatorData: PopulatorTestData = PopulatorTestData(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        dairyViewModel.getAll().observe(this, object: Observer<List<Dairy>>{
            override fun onChanged(t: List<Dairy>?) {
                Toast.makeText(this@MainActivity, "Size " + t?.size.toString(), Toast.LENGTH_LONG).show()
            }
        })
        lifecycle.addObserver(populatorData)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
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
}
