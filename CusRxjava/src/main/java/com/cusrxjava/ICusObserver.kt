package com.cusrxjava

/**
 * 观察者接口（下游）
 */
interface ICusObserver<T> {

    /**
     * 接收上游发送的事件
     *
     * @param item 上游传递的参数
     */
    fun onNext(item:T)
}