package com.bwei.zhoukaolianxi201.model;

import android.os.AsyncTask;

import com.bwei.zhoukaolianxi201.callback.MyCallBack;
import com.bwei.zhoukaolianxi201.util.NetUtils;
import com.google.gson.Gson;

public class IModelImpl implements IModel {

    public <T> T getRequest(String dataUrl,String param,Class clazz){

        return (T) new Gson().fromJson(NetUtils.getRequest(dataUrl),clazz);
    }

    @Override
    public void getRequest(final String dataUrl, final String param, final Class clazz, final MyCallBack callBack) {
        new AsyncTask<String,Void,Object>(){
            @Override
            protected Object doInBackground(String... strings) {
                return getRequest(dataUrl,param,clazz);
            }

            @Override
            protected void onPostExecute(Object o) {
                callBack.onSuccess(o);
            }
        }.execute(dataUrl);
    }
}
