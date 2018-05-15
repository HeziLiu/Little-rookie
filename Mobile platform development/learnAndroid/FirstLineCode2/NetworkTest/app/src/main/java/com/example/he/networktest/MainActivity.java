package com.example.he.networktest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final static int SHOW_RESPONSE = 0;
    private TextView responseText;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    String response = (String) msg.obj;
                    responseText.setText(response);//将结果显示出来
                    break;
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        responseText = (TextView) findViewById(R.id.response_text);
        findViewById(R.id.send_request).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_request:
                sendRequestWithHttpURLConnection();//开启线程发送网络请求
                break;
        }
    }

    private void sendRequestWithHttpURLConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                HttpURLConnection jsonConnection = null;
                try {
                    URL url = new URL("http://10.0.2.2:8088/get_data.xml");//xml数据
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);//设置连接超时秒数
                    connection.setReadTimeout(8000);//设置读取超时秒数
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder builder = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                    String response = builder.toString();
//                    parseXMLWithPull(response);//pull解析数据
                    parseXMLWithSAX(response);//SAX解析数据


                    //解析JSON数据
                    URL jsonUrl = new URL("http://10.0.2.2:8088/get_data.json");//json数据
                    jsonConnection = (HttpURLConnection) jsonUrl.openConnection();
                    jsonConnection.setRequestMethod("GET");
                    InputStream inputStream2 = jsonConnection.getInputStream();
                    BufferedReader reader2 = new BufferedReader(new InputStreamReader(inputStream2));
                    StringBuilder builder2 = new StringBuilder();
                    String line2 = null;
                    while ((line2 = reader2.readLine()) != null) {
                        builder2.append(line2);
                    }
                    String response2 = builder2.toString();
                    parseJSONWithJSONObject(response2);

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();//关闭连接
                    }
                }

            }
        }).start();

    }

    //解析XML
    private void parseXMLWithPull(String xmlData) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser pullParser = factory.newPullParser();
            pullParser.setInput(new StringReader(xmlData));
            int eventType = pullParser.getEventType();
            String id = "";
            String name = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeNmae = pullParser.getName();
                switch (eventType) {
                    //开始解析某个结点
                    case XmlPullParser.START_TAG:
                        if ("id".equals(nodeNmae)) {
                            id = pullParser.nextText();
                        } else if ("name".equals(nodeNmae)) {
                            name = pullParser.nextText();
                        }
                        break;
                    //完成解析
                    case XmlPullParser.END_TAG:
                        if ("app".equals(nodeNmae)) {
                            Log.i("MainActivity", "id is" + id);
                            Log.i("MainActivity", "name is" + name);
                        }
                        break;
                }
                eventType = pullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    private void parseXMLWithSAX(String xmlData) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        XMLReader xmlReader = factory.newSAXParser().getXMLReader();
        ContentHandler handler = new ContentHandler();
        //将ContentHandler的实例设置到XMLReader中
        xmlReader.setContentHandler(handler);
        //开始执行解析
        xmlReader.parse(new InputSource(new StringReader(xmlData)));
    }

    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONArray jsonArray=new JSONArray(jsonData);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject object=jsonArray.getJSONObject(i);
                String id=object.getString("id");
                String name=object.getString("name");
                Log.i("MainActivity JSON","id is"+id);
                Log.i("MainActivity JSON","name is"+name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
