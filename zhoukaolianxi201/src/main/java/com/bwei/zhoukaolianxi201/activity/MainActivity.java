package com.bwei.zhoukaolianxi201.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.bwei.zhoukaolianxi201.R;
import com.bwei.zhoukaolianxi201.bean.DengBean;
import com.bwei.zhoukaolianxi201.persenter.IPersenterImpl;
import com.bwei.zhoukaolianxi201.view.IView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,IView {

    private EditText editText1,editText2;
    private CheckBox checkBox1,checkBox2;
    private Button button;
    private IPersenterImpl iPersenter;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText1=findViewById(R.id.edit_phone);
        editText2=findViewById(R.id.edit_pwd);
        checkBox1=findViewById(R.id.check_jz);
        checkBox2=findViewById(R.id.check_zd);
        button=findViewById(R.id.button_dl);

        iPersenter=new IPersenterImpl(this);
        button.setOnClickListener(this);

        sharedPreferences = getSharedPreferences("User",MODE_MULTI_PROCESS);
        editor = sharedPreferences.edit();

        boolean j_ischeck = sharedPreferences.getBoolean("j_ischeck", false);
        if(j_ischeck){
            String phone = sharedPreferences.getString("phone", null);
            String pwd = sharedPreferences.getString("pwd", null);
            editText1.setText(phone);
            editText2.setText(pwd);
        }

        boolean z_ischeck = sharedPreferences.getBoolean("z_ischeck", false);
        if(z_ischeck){
            String phone = editText1.getText().toString();
            String pwd = editText2.getText().toString();
            checkBox1.setChecked(true);
            iPersenter.getRequest("http://www.xieast.com/api/user/login.php?username="+phone+"&password="+pwd,null,DengBean.class);
        }
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkBox1.setChecked(true);
                }else{
                    editor.clear();
                    editor.commit();
                }
            }
        });
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button_dl:
                String phone = editText1.getText().toString();
                String pwd = editText2.getText().toString();

                if(checkBox1.isChecked()){
                    editor.putString("phone",phone);
                    editor.putString("pwd",pwd);

                    editor.putBoolean("j_ischeck",true);
                    editor.commit();
                }else{
                    editor.clear();
                    editor.commit();
                }

                if(checkBox2.isChecked()){
                    editor.putBoolean("z_ischeck",true);
                    editor.commit();
                }

                iPersenter.getRequest("http://www.xieast.com/api/user/login.php?username="+phone+"&password="+pwd,null,DengBean.class);
                break;
            default:
                break;

        }
    }

    @Override
    public void getRequest(Object data) {
        DengBean dengBean= (DengBean) data;
        if(dengBean.getCode()==100){
            Toast.makeText(MainActivity.this,dengBean.getMsg()+"",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.putExtra("name",dengBean.getData().getName());
            startActivity(intent);
        }else{
            Toast.makeText(MainActivity.this,dengBean.getMsg()+"",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPersenter.detach();

    }
}
