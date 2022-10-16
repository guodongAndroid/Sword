package com.guodong.android.sword.app

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.guodong.android.sword.api.kt.Proxy

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val test = Test()

        val btn = findViewById<Button>(R.id.btn)
        btn.setOnClickListener {
            val text = getText("1")
            Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()
        }

        val textNoArg = test.getTextNoArg()
        Log.e(TAG, "onCreate: textNoArg = ${textNoArg.length}")

        val textArg = test.getTextArg(
            1,
            false,
            'A',
            1,
            1,
            1,
            1F,
            1.0,
            "test",
            intArrayOf(),
            Array(0) { "" },
            listOf(),
            listOf(),
            mapOf(),
            User("1", 1),
            object : Callback {
                override fun onSuccess() {
                }

                override fun onFailure(cause: Throwable) {
                }
            })
        Log.e(TAG, "onCreate: textArg = $textArg")

        val user = test.testHandler(
            1,
            false,
            'A',
            1
        )
        Log.e(TAG, "onCreate: user = $user")

        val testInt = test.testInt()
        Log.e(TAG, "onCreate: testInt = $testInt")

        val userTopLevel = getUser()
        Log.e(TAG, "onCreate: TopLevel user = $userTopLevel")

        val userObject = TopLevel.getUser()
        Log.e(TAG, "onCreate: Object user = $userObject")

        val innerTest = test.TestInner()
        val userInner = innerTest.getUser()
        Log.e(TAG, "onCreate: Inner user = $userInner")

        innerTest.anonymousFun()

        val staticClass = Test.TestStatic()
        val userStatic = staticClass.getUser()
        Log.e(TAG, "onCreate: StaticInner user = $userStatic")
    }

    @Proxy(
        enable = true,
        handler = HandlerFqName.GET_TEXT_NO_ARG
    )
    private fun getText(text: String): String {
        return text
    }
}