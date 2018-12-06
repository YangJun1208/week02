package com.bwei.zhoukaolianxi201.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwei.zhoukaolianxi201.R;
import com.bwei.zhoukaolianxi201.bean.ZhanBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends BaseAdapter {

    private Context context;
    private List<ZhanBean.DataBean> data;

    public NewsAdapter(Context context) {
        this.context = context;
        data=new ArrayList<>();
    }

    public void setList(List<ZhanBean.DataBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public ZhanBean.DataBean getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.item,parent,false);
            holder=new ViewHolder(convertView);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.getdata(getItem(position));
        return convertView;
    }
    class ViewHolder{
        private TextView title;
        private ImageView imageView;

        public ViewHolder(View convertView) {
            title=convertView.findViewById(R.id.title);
            imageView=convertView.findViewById(R.id.image);
            convertView.setTag(this);
        }

        public void getdata(ZhanBean.DataBean item) {
            title.setText(item.getTitle());
            ImageLoader.getInstance().displayImage(item.getThumbnail_pic_s(),imageView);
        }
    }
}
