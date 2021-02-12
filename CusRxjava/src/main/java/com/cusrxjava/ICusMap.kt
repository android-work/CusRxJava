package com.cusrxjava

/**
 * 转换规则
 */
interface ICusMap<T,R> {

    /**
     * 转换方法
     *
     * @param item 上游发送的数据
     */
    fun transform(item:T):R
}