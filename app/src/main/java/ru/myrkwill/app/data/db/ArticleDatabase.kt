package ru.myrkwill.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.myrkwill.app.models.Article

@Database(entities = [Article::class], version = 2, exportSchema = false)
abstract class ArticleDatabase: RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

}