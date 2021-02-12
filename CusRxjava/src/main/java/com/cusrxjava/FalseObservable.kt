package com.cusrxjava

import android.util.Log

/**
 * 虚假被观察者，仅持有真正被观察者的引用
 */
class FalseObservable<T>(val iCusObservable: ICusObservable<T>) {
    val TAG = "${Contant.RX_JAVA} FalseObservable"

    /**
     * 通过静态方法接收一个真正被观察者，并返回一个虚假被观察者
     */
    companion object{
        fun<T> create(iCusObservable: ICusObservable<T>):FalseObservable<T>{
            return FalseObservable(iCusObservable)
        }
    }

    /**
     * 用于订阅下游
     *
     * @param iCusObserver 观察者，下游对象
     */
    fun onSubscribe(iCusObserver: ICusObserver<T>){
        Log.i(TAG,"onSubscribe...")
        iCusObservable.subscribe(iCusObserver)
    }

    /**
     * map转换
     */
    fun<R> map(cusMap:ICusMap<T,R>):FalseObservable<R>{
        Log.i(TAG, "map...")
        // 将map观察上游的动静
        val cusMapObservable = CusMapObservable(cusMap, iCusObservable)
        return FalseObservable(cusMapObservable)
    }

    /**
     * 切换上游线程
     *
     * @param scheduler 线程类型 0：main线程，1：IO线程
     */
    fun observableOn(scheduler:Int):FalseObservable<T>{
        Log.i(TAG, "observableOn...")
        val cusSubscribeOnObservable = CusSubscribeOnObservable(iCusObservable,scheduler)
        return FalseObservable(cusSubscribeOnObservable)
    }

    /**
     * 切换下游线程
     */
    fun subscribeOn(scheduler: Int):FalseObservable<T>{
        Log.i(TAG, "subscribeOn...")
        val cusSubscribeOnObservable = CusSubscribeOnObservable(iCusObservable,scheduler)
        return FalseObservable(cusSubscribeOnObservable)
    }
}
