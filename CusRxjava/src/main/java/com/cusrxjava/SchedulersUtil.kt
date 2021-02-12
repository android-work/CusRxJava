package com.cusrxjava

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import java.util.concurrent.Executors

/**
 * 线程池工具类
 */
class SchedulersUtil {
    private val TAG = "${Contant.RX_JAVA} SchedulersUtil"

    /**
     * 创建主线程Handler
     */
    private val mSchedulerHandler = SchedulerHandler()

    /**
     * 创建线程池
     */
    private val mThreadPool = Executors.newCachedThreadPool()

    companion object{
        val INSTANCE: SchedulersUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            SchedulersUtil()
        }

        class SchedulerHandler : Handler(Looper.getMainLooper())
    }

    val IO = 1
    val MAIN = 0

    /**
     * 上游发消息所在线程
     */
    fun <T> submitObservableSchedulers(scheduler:Int, observer: ICusObserver<T>, observable: ICusObservable<T>){
        Log.i(TAG, "submitObservableSchedulers currentThread:${Thread.currentThread().name}  scheduler：$scheduler")
        when(scheduler){
            IO ->{
                mThreadPool.submit(Runnable {
                    Log.i(TAG, "${Thread.currentThread().name} run runnable")
                    observable.subscribe(observer)
                })
            }
            MAIN ->{
                mSchedulerHandler.post {
                    Log.i(TAG,"${Thread.currentThread().name} run runnable")
                    observable.subscribe(observer)
                }
            }
        }
    }

    /**
     * xia游接收消息所在线程
     */
    fun <T> submitObserverSchedulers(scheduler:Int,observer: ICusObserver<T>,item:T){
        Log.i(TAG, "submitObserverSchedulers currentThread:${Thread.currentThread().name}  scheduler：$scheduler")
        when(scheduler){
            IO ->{
                mThreadPool.submit(Runnable {
                    Log.i(TAG, "${Thread.currentThread().name} run runnable")
                    observer.onNext(item)
                })
            }
            MAIN ->{
                mSchedulerHandler.post {
                    Log.i(TAG,"${Thread.currentThread().name} run runnable")
                    observer.onNext(item)
                }
            }
        }
    }

}