package com.foticc.iot.api.data

data class CommonResult<T>(
    val code:Int,
    val msg:String,
    var data:T?
);
