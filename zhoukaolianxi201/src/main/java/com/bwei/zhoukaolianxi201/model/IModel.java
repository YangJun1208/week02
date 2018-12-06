package com.bwei.zhoukaolianxi201.model;

import com.bwei.zhoukaolianxi201.callback.MyCallBack;

public interface IModel {
    void getRequest(String dataUrl, String param, Class clazz, MyCallBack callBack);
}
