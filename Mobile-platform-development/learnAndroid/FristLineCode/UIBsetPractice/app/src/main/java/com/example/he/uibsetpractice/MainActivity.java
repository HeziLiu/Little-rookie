package com.example.he.uibsetpractice;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private ListView msgListView;
    private EditText input;
    private Button send;
    private MsgAdapter adapter;
    private List<Msg> msgList = new ArrayList<Msg>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_layout);
        initMsg();//初始化消息数据
        adapter = new MsgAdapter(MainActivity.this, R.layout.msg_item_layout, msgList);
        input = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        msgListView = (ListView) findViewById(R.id.msg_list_view);
        msgListView.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果editText不为空就发送出去
                String content = input.getText().toString();
                if (!"".equals(content)) {
                    Msg msg = new Msg(content, Msg.TYPE_SENT);
                    msgList.add(msg);
                    adapter.notifyDataSetChanged();//刷新ListView中的显示
                    msgListView.setSelection(msgList.size());//将ListView定位到最后一行
                    input.setText("");//请空输入框
                }

            }
        });


    }

    void initMsg() {
        Msg msg1 = new Msg("Hello guy", Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("Hello ", Msg.TYPE_SENT);
        msgList.add(msg2);
        Msg msg3 = new Msg("This is a Test, thank you ", Msg.TYPE_RECEIVED);
        msgList.add(msg3);

    }

}
