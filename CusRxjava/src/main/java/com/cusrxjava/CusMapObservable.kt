package com.cusrxjava

import android.util.Log

/**
 * 用于承接上游的消息，转换后发送给下游
 */
class CusMapObservable<T,R>(val cusMap:ICusMap<T,R>,val observable: ICusObservable<T>): ICusObservable<R>{
    private val TAG = "${Contant.RX_JAVA} CusMapObservable"
    /**
     * 用于关联下游
     */
    override fun subscribe(iCusObserver: ICusObserver<R>) {
        Log.i(TAG,"subscribe...")
        val cusMapObserver = CusMapObserver(cusMap, iCusObserver)
        observable.subscribe(cusMapObserver)
    }

    /**
     * 用于观察上游的动静，方便进行转换后，通知给下游
     */
    class CusMapObserver<T,R>(val cusMap: ICusMap<T,R>,val observer: ICusObserver<R>):ICusObserver<T>{
        val TAG = "${Contant.RX_JAVA} CusMapObserver"
        /**
         * 上游通知map进行转换后，再通知下游
         */
        override fun onNext(item: T) {
            Log.i(TAG,"onNext...")
            val transform = cusMap.transform(item)
            observer.onNext(transform)
        }

    }
}