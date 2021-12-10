package com.lwjfork.android.repository.executor

import com.lwjfork.mvvm.jetpack.executor.BaseApiExecutor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DefaultApiExecutor private constructor() : BaseApiExecutor {

    companion object {
        private val executor: DefaultApiExecutor by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { DefaultApiExecutor() }

        @JvmStatic
        fun getInstance(): DefaultApiExecutor {
            return executor
        }
    }


    override fun runOnDiskIO(block: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            block.invoke()
        }
    }

    override fun runOnMainThread(block: () -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            block.invoke()
        }
    }

    override fun runOnNetWorkIO(block: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            block.invoke()
        }
    }

}