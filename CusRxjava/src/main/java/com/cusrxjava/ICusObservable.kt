package com.cusrxjava

/**
 * 被观察者（上游）
 */
interface ICusObservable<T> {

    /**
     * 绑定下游
     *
     * @param iCusObserver 下游对象
     */
    fun subscribe(iCusObserver: ICusObserver<T>)
}