package com.materialsearchview


import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val searchView =
            handleIntent(intent)


        removeSingleHistory()


    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        val mysuggestions = SearchRecentSuggestions(
            this,
            MySuggestionProvider.AUTHORITY,
            MySuggestionProvider.MODE
        )
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                doMySearch(query)
                mysuggestions.saveRecentQuery(query, null)

            }
        }

        if (intent.action == Intent.ACTION_SEARCH_LONG_PRESS) {
            mysuggestions.clearHistory()
        }

    }


    private fun removeSingleHistory() {
        button.setOnClickListener {
            SearchRecentSuggestions(this, MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE)
                .clearHistory()
        }


    }


    private fun doMySearch(query: String) {
        Toast.makeText(this, query, Toast.LENGTH_SHORT).show()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        //val searchView = menu.findItem(R.id.action_search)


        searchView.isSubmitButtonEnabled = true


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                handleIntent(intent)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //   Toast.makeText(this@MainActivity,"$newText",Toast.LENGTH_SHORT).show()
                return false
            }
        }


        )


        // Get the SearchView and set the searchable configuration
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.action_search).actionView as SearchView).apply {
            // Assumes current activity is the searchable activity
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false) // Do not iconify the widget; expand it by default
        }

        return true
    }




}