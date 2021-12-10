package com.lwjfork.mvvm.jetpack.executor



interface BaseApiExecutor {

    fun runOnDiskIO(block: () -> Unit)

    fun runOnMainThread(block: () -> Unit)


    fun runOnNetWorkIO(block: () -> Unit)
}