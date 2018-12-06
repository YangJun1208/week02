package com.bwei.zhoukaolianxi201.fargment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwei.zhoukaolianxi201.R;
import com.bwei.zhoukaolianxi201.adapter.NewsAdapter;
import com.bwei.zhoukaolianxi201.bean.ZhanBean;
import com.bwei.zhoukaolianxi201.persenter.IPersenterImpl;
import com.bwei.zhoukaolianxi201.view.IView;

import me.maxwin.view.XListView;

public class FragmentOne extends Fragment implements IView {

    private XListView xListView;
    private IPersenterImpl iPersenter;
    private NewsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentone,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        xListView = view.findViewById(R.id.xListView);
        iPersenter=new IPersenterImpl(this);
        iPersenter.getRequest("http://www.xieast.com/api/news/news.php",null,ZhanBean.class);
        adapter = new NewsAdapter(getActivity());
        xListView.setAdapter(adapter);


    }

    @Override
    public void getRequest(Object data) {

        ZhanBean zhanBean= (ZhanBean) data;
        if(zhanBean.getCode()==1){
            adapter.setList(zhanBean.getData());
        }
    }
}
