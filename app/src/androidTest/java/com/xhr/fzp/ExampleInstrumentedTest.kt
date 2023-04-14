package com.xhr.fzp

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.fzp.android", appContext.packageName)
    }

    @Test
    fun databaseTest() {
//        val db = AppDatabase.getDatabase(FzpApplication.context)
//        val save = Save(0, 0, "article", "title", byteArrayOf(1, 2, 3), "hh", 1, "position", "category")
//        db.saveDao().insertSave(save)
//        db.saveDao().queryAll().forEach {
//            println(it)
//        }
    }
}
