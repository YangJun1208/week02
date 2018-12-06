package com.bwei.zhoukaolianxi201.persenter;

import com.bwei.zhoukaolianxi201.callback.MyCallBack;
import com.bwei.zhoukaolianxi201.model.IModelImpl;
import com.bwei.zhoukaolianxi201.view.IView;

public class IPersenterImpl implements IPersenter {

    private IView mIview;
    private IModelImpl iPersenter;

    public IPersenterImpl(IView iView){
        this.mIview=iView;
        iPersenter=new IModelImpl();
    }
    @Override
    public void getRequest(String dataUrl, String param, Class clazz) {
        iPersenter.getRequest(dataUrl,param,clazz,new MyCallBack(){
            @Override
            public void onSuccess(Object data) {
                mIview.getRequest(data);
            }
        });
    }
    //解绑
    public void detach(){
        if(mIview!=null) {
            mIview = null;
        }
        if(iPersenter!=null) {
            iPersenter = null;
        }
    }
}
