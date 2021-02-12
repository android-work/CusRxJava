package com.cusrxjava

import android.util.Log

/**
 * 切换线程的被观察者
 */
class CusSubscribeOnObservable<T>(val cusObservable:ICusObservable<T>,val scheduler:Int): ICusObservable<T> {
    private val TAG = "${Contant.RX_JAVA} CusSubscribeOnObservable"
    /**
     * 绑定下游
     */
    override fun subscribe(iCusObserver: ICusObserver<T>) {
        Log.i(TAG, "subscribe currentThread:${Thread.currentThread().name}")
        val cusSubscribeOnObserver = CusSubscribeOnObserver(iCusObserver,scheduler)
        SchedulersUtil.INSTANCE.submitObservableSchedulers(scheduler,cusSubscribeOnObserver,cusObservable)
    }

    /**
     * 切换线程的观察者
     */
    class CusSubscribeOnObserver<T>(val iCusObserver: ICusObserver<T>,val scheduler:Int): ICusObserver<T>{
        private val TAG = "${Contant.RX_JAVA} CusSubscribeOnObserver"
        override fun onNext(item: T) {
            Log.i(TAG,"onNext...")
            SchedulersUtil.INSTANCE.submitObserverSchedulers(scheduler,iCusObserver,item)
        }
    }
}