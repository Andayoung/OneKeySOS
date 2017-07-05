package com.gg.onekeysos;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.gg.onekeysos.db.DBManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btn_add)
    ImageView btnAdd;
    @BindView(R.id.lv_number)
    ListView lvNumber;
    PeopleAdapter peopleAdapter = null;
    DBManager mgr = null;
    private List<Map<String, String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mgr=new DBManager(this);
        initView();
    }

    private void initView() {
        list = new ArrayList<>();
        List<People> l = new ArrayList<>();
        l=mgr.query();
        for (int i = 0; i < l.size(); i++) {
            Map<String, String> zp = new HashMap<>();
            zp.put("name", l.get(i).getName());
            zp.put("number", l.get(i).getNumber());
            Log.e("MainActivity", "title=" + l.get(i).getName() + ",time=" + l.get(i).getNumber());
            list.add(zp);
        }
        peopleAdapter = new PeopleAdapter(MainActivity.this, list);
        lvNumber.setAdapter(peopleAdapter);
        lvNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String number = ((PeopleAdapter) parent.getAdapter()).getData().get(position).get("number");
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                } else {
                    startActivity(intent);
                }

            }
        });
    }

    @OnClick({R.id.btn_add})
    public void add(ImageView btn) {
        inputTitleDialog();
    }
    private void inputTitleDialog() {
        final EditText inputNumber = new EditText(this);
        inputNumber.setFocusable(true);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请输入号码").setView(inputNumber)
                .setNegativeButton("取消", null);
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String input = inputNumber.getText().toString();
                        Log.e("MainActivity","input="+input);
                        mgr.addForOne(new People("cc",input));
                        list .clear();
                        List<People> l = mgr.query();
                        for (int i = 0; i < l.size(); i++) {
                            Map<String, String> zp = new HashMap<>();
                            zp.put("name", l.get(i).getName());
                            zp.put("number", l.get(i).getNumber());
                            Log.e("MainActivity", "title=" + l.get(i).getName() + ",time=" + l.get(i).getNumber());
                            list.add(zp);
                        }
                        peopleAdapter.setData(list);
                        peopleAdapter.notifyDataSetChanged();
                    }
                });
        builder.show();
    }
}
