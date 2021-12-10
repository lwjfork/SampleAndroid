package com.lwjfork.mvvm.jetpack.executor

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class DefaultApiExecutor private constructor() : BaseApiExecutor {

    private val diskIO: Executor = Executors.newSingleThreadExecutor()
    private val networkIO: Executor = Executors.newFixedThreadPool(3)
    private val mainThread: Executor = MainThreadExecutor()


    companion object {
        private val executor: DefaultApiExecutor by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { DefaultApiExecutor() }

        @JvmStatic
        fun getInstance(): DefaultApiExecutor {
            return executor
        }
    }


    override fun runOnDiskIO(block: () -> Unit) {
        diskIO.execute {
            block()
        }
    }

    override fun runOnMainThread(block: () -> Unit) {
        mainThread.execute {
            block()
        }
    }

    override fun runOnNetWorkIO(block: () -> Unit) {
        networkIO.execute {
            block()
        }
    }


    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}