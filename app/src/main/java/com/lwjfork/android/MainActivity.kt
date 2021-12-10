package com.lwjfork.android

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.lwjfork.android.model.Response
import com.lwjfork.android.repository.callback.DataResourceFlowObserver
import com.lwjfork.android.repository.callback.DataResourceObserver
import com.lwjfork.android.repository.collectResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking

class MainActivity : FragmentActivity() {

    val test = Test()
    val testFlow = TestFlow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        findViewById<TextView>(R.id.testFetchNetWork).setOnClickListener { testFetchNetWork() }
        findViewById<TextView>(R.id.testFetchCache).setOnClickListener {
            runBlocking {
                testFetchCache()
            }
        }


    }

    private fun testFetchNetWork() {
        test.testOnlyFetchFromNetWork()
            .observe(this, object : DataResourceObserver<String>() {
                override fun onStart() {
                    Toast.makeText(this@MainActivity, "开始加载", Toast.LENGTH_LONG).show()

                }

                override fun onSuccess(t: Response<String>?) {
                    Toast.makeText(this@MainActivity, t?.data, Toast.LENGTH_LONG).show()
                }

                override fun onFail(t: Response<String?>?) {
                    Toast.makeText(this@MainActivity, "加载失败", Toast.LENGTH_LONG).show()
                }
            })
    }

    private suspend fun testFetchCache() {
//        test.firstReadCacheAndFetch()
//            .observe(this, object : DataResourceObserver<String>() {
//                override fun onStart() {
//                    Toast.makeText(this@MainActivity, "开始加载", Toast.LENGTH_LONG).show()
//                }
//
//                override fun onFail(t: Response<String?>?) {
//                    Toast.makeText(this@MainActivity, t?.msg, Toast.LENGTH_LONG).show()
//                }
//
//                override fun onSuccess(t: Response<String>?) {
//                    Toast.makeText(this@MainActivity, t?.data, Toast.LENGTH_LONG).show()
//                }
//            })

        testFlow.testOnlyFetchFromNetWork().flowOn(Dispatchers.IO)
            .collectResult(object : DataResourceFlowObserver<String>() {
                override fun onStart() {
                    Toast.makeText(this@MainActivity, "开始加载", Toast.LENGTH_LONG).show()
                }

                override fun onFail(t: Response<String?>?) {
                    Toast.makeText(this@MainActivity, t?.msg, Toast.LENGTH_LONG).show()
                }

                override fun onSuccess(t: Response<String>?) {
                    Toast.makeText(this@MainActivity, t?.data, Toast.LENGTH_LONG).show()
                }
            })
    }


}