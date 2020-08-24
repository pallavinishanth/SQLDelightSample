package com.example.sqldelightsample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import com.squareup.sqldelight.android.AndroidSqliteDriver
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val DB_NAME = "item.db"
    val DB_VERSION = 2

    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val databaseHelper: SupportSQLiteOpenHelper by lazy {
            val config = SupportSQLiteOpenHelper.Configuration.builder(this)
                .name(DB_NAME)
                .callback(SterlingSQLCallback(DB_VERSION))
                .build()
            FrameworkSQLiteOpenHelperFactory().create(config)
        }

        val database: Database by lazy {
            Database(AndroidSqliteDriver(databaseHelper))
        }

        fun provideDatabase(): Database {
            return database
        }

        val itemsBefore: List<ItemInCart> = provideDatabase().shoppingCartQueries.selectAll().executeAsList()
        Log.d("ItemDatabase", "Items Before: $itemsBefore")

        for (i in 1..3) {
            provideDatabase().shoppingCartQueries.insertOrReplace(
                label = "Item $i",
                image = "https://localhost/item$i.png",
                quantity = i.toLong(),
                link = null,
                brand = "Brand $i"
            )
        }

        val itemsAfter: List<ItemInCart> = provideDatabase().shoppingCartQueries.selectAll().executeAsList()
        Log.d("ItemDatabase", "Items After: $itemsAfter")

        //Populating items list
        recyclerView.apply {
            linearLayoutManager = LinearLayoutManager(context)
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.adapter = CustomAdapter(itemsAfter)
        }

    }
}

class SterlingSQLCallback(version: Int) : SupportSQLiteOpenHelper.Callback(version) {
    override fun onCreate(db: SupportSQLiteDatabase) {
        Log.d("ItemDatabase", "Database onCreate")
        val driver = AndroidSqliteDriver(db)
        Database.Schema.create(driver)
    }

    override fun onUpgrade(db: SupportSQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d("ItemDatabase", "Database onUpgrade")
        val driver = AndroidSqliteDriver(db)
        Database.Schema.migrate(driver, oldVersion, newVersion)
    }

}