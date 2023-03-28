package com.xhr.fzp

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class FzpApplication : Application(){

    //    private HttpProxyCacheServer proxy;
    //
    //    public static HttpProxyCacheServer getProxy(Context context) {
    //        App app = (App) context.getApplicationContext();
    //        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    //    }
    //
    //    private HttpProxyCacheServer newProxy() {
    //        return new HttpProxyCacheServer(this);
    //    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
//        lateinit var proxy : HttpProxyCacheServer


    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
//        proxy = HttpProxyCacheServer(context)
    }
}