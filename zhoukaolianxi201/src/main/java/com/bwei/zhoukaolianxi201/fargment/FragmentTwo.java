package com.bwei.zhoukaolianxi201.fargment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bwei.zhoukaolianxi201.R;
import com.bwei.zhoukaolianxi201.activity.LoginActivity;
import com.bwei.zhoukaolianxi201.activity.MainActivity;

import java.lang.ref.WeakReference;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

public class FragmentTwo extends Fragment {

    private ImageView imageView;
    private Button button;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmenttwo,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button = view.findViewById(R.id.back);
        imageView = view.findViewById(R.id.imageview);

        sharedPreferences = getActivity().getSharedPreferences("User", Context.MODE_MULTI_PROCESS);

        editor = sharedPreferences.edit();
        shengcheng();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
                getActivity().finish();
                editor.clear();
                editor.commit();
            }
        });

    }
    private void shengcheng() {
        String name = ((LoginActivity) getActivity()).nAme();
        QRTask qrTask = new QRTask(getActivity(), imageView, name);
        qrTask.execute(name);

    }
    static class QRTask extends AsyncTask<String,Void,Bitmap> {
        private WeakReference<Context> mcontext;
        private WeakReference<ImageView> mimageview;

        public QRTask(Context context, ImageView imageView, String name) {
            mcontext=new WeakReference<>(context);
            mimageview=new WeakReference<>(imageView);
        }

        @Override protected Bitmap doInBackground(String... params) {
            String str = params[0];
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            int size = mcontext.get().getResources().getDimensionPixelOffset(R.dimen.size);
            return QRCodeEncoder.syncEncodeQRCode(str, size);
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap!=null){
                mimageview.get().setImageBitmap(bitmap);
            }else{
                Toast.makeText(mcontext.get(), "生成失败", Toast.LENGTH_SHORT).show();

            }

        }
    }
}
