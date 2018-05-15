package com.example.he.fragmenttest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

/**
 * Created by he on 2016/4/3.
 */
//public class mainActivity extends Activity implements View.OnClickListener {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main_layout);
//        Button button = (Button) findViewById(R.id.button);
//        button.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.button:
//                AnotherRightFragment fragment = new AnotherRightFragment();
//                FragmentManager manager = getFragmentManager();
//                FragmentTransaction transaction = manager.beginTransaction();
//                transaction.replace(R.id.right_layout, fragment);
//                transaction.commit();
//                break;
//        }
//    }
//}
public class mainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        Button button = (Button) findViewById(R.id.button);
//        从布局文件中获取碎片实例
//        RightFragment rightFragment= (RightFragment) getFragmentManager().findFragmentById(R.id.right_fragment);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AnotherRightFragment fragment = new AnotherRightFragment();//碎片实例
//                FragmentManager manager = getFragmentManager();//得到FragmentManager
//                FragmentTransaction transaction = manager.beginTransaction();//开启事务
//                transaction.replace(R.id.right_layout, fragment);//替换碎片
//                transaction.addToBackStack(null);//将事务添加到返回栈
//                transaction.commit();//提交
//            }
//        });
    }
}
