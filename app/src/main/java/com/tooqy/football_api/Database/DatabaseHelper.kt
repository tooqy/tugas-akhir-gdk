package com.tooqy.football_api.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.tooqy.football_api.Favorite.FavoriteEvent
import com.tooqy.football_api.Favorite.FavouriteTeam
import org.jetbrains.anko.db.*

class DBHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "Favorite.db", null, 1) {
    companion object {
        private var instance: DBHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DBHelper {
            if (instance == null) {
                instance = DBHelper(ctx.applicationContext)
            }
            return instance as DBHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(FavoriteEvent.TABLE, true,
                FavoriteEvent.ID to INTEGER + PRIMARY_KEY,
                FavoriteEvent.DATE to TEXT,
                FavoriteEvent.HOMEID to TEXT,
                FavoriteEvent.HOMENAME to TEXT,
                FavoriteEvent.HOMESCORE to TEXT,
                FavoriteEvent.AWAYID to TEXT,
                FavoriteEvent.AWAYNAME to TEXT,
                FavoriteEvent.AWAYSCORE to TEXT
        )


        db.createTable(FavouriteTeam.TABLE_NAME, true,
                FavouriteTeam.TEAM_ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavouriteTeam.TEAM_NAME to TEXT,
                FavouriteTeam.TEAM_BADGE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(FavoriteEvent.TABLE, true)
        db.dropTable(FavouriteTeam.TABLE_NAME, true)
    }
}

// Access property for Context
val Context.database: DBHelper
    get() = DBHelper.getInstance(applicationContext)