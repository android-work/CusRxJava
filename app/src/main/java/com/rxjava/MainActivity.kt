package com.rxjava

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cusrxjava.*

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    var i = 0

    fun onClick(view: View) {
        FalseObservable.create(object : ICusObservable<String> {
            override fun subscribe(iCusObserver: ICusObserver<String>) {
                Log.i(TAG, "subscribe currentThread${Thread.currentThread().name}      i = ${i++}")
                iCusObserver.onNext("自定义Rxjava初版")
            }
        })
                .observableOn(SchedulersUtil.INSTANCE.IO)
                .map(object : ICusMap<String, Int> {
                    override fun transform(item: String): Int {
                        Log.i(TAG, "Map currentThread${Thread.currentThread().name}  transform item:$item")
                        return 100000
                    }
                })
                .subscribeOn(SchedulersUtil.INSTANCE.MAIN)
                .onSubscribe(object : ICusObserver<Int> {
                    override fun onNext(item: Int) {
                        Log.i(TAG, "subscribeOn currentThread${Thread.currentThread().name}")
                        Toast.makeText(this@MainActivity, "item :$item", 0).show()
                    }

                })
    }
}