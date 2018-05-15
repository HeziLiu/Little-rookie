package com.ifly.shendong.smarthomelayout;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private PackageManager pm;
    private static final String PACKAGE_NAME = "com.smartcontrol.sample";
    private List<String[]> scenesList;//所有的场景名字
    private Map<String, List<String[]>> romHashMap;
    private Map<String, List<String[]>> datas = new LinkedHashMap<>();
    private List<String[]> deviceList;
    public static Map<Integer, Integer> mRomTypeToImageId = new HashMap<>();


    private ViewUtils viewUtils;
    private LinearLayout layout;


    static {
//        mHashMap.put(DeviceItem.DEVICE_LIGHT,R.drawable.tv_);
        mRomTypeToImageId.put(DeviceItem.DEVICE_AIR_CONDITIONING, R.drawable.air_condition);
        mRomTypeToImageId.put(DeviceItem.DEVICE_AIR_PURIFIER, R.drawable.air_purifier);
//        mRomTypeToImageId.put(DeviceItem.DEVICE_DIMMER, "调光灯");
        mRomTypeToImageId.put(DeviceItem.DEVICE_FAN, R.drawable.fan);
        mRomTypeToImageId.put(DeviceItem.DEVICE_HUMIDIFIER, R.drawable.humidifier);
//        mRomTypeToImageId.put(DeviceItem.DEVICE_HEATING, "暖气");
//        mRomTypeToImageId.put(DeviceItem.DEVICE_WINDOWCURTAIN, "窗帘");
//       mRomTypeToImageId.put(DeviceItem.DEVICE_SHUTTER, "百叶窗");
//        mRomTypeToImageId.put(DeviceItem.DEVICE_CURTAIN, "幕布");
//        mRomTypeToImageId.put(DeviceItem.DEVICE_PROJECTOR, "投影机");
//        mRomTypeToImageId.put(DeviceItem.DEVICE_CLOTHES_RACK, "晾衣架");
//        mRomTypeToImageId.put(DeviceItem.DEVICE_DOOR, "门");

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pm = getPackageManager();
        layout = (LinearLayout) findViewById(R.id.activity_main);
        viewUtils = new ViewUtils();

        scenesList = new ArrayList<>();
        romHashMap = new LinkedHashMap();
        deviceList = new ArrayList<>();
        deviceList.add(new String[]{PACKAGE_NAME});
        deviceList.add(new String[]{PACKAGE_NAME});
        deviceList.add(new String[]{PACKAGE_NAME});
        deviceList.add(new String[]{PACKAGE_NAME});
        deviceList.add(new String[]{PACKAGE_NAME});
        deviceList.add(new String[]{PACKAGE_NAME});
        deviceList.add(new String[]{PACKAGE_NAME});
        deviceList.add(new String[]{PACKAGE_NAME});

        saveDatas();
//        initDatas();
        initDatass();

        datas.put("场景模式",scenesList);

        Log.d(TAG, "onCreate: Scenes"+scenesList.size());

        for (String romName:romHashMap.keySet()){
            List<String[]> romList=romHashMap.get(romName);
            datas.put(romName,romList);
        }
        datas.put("设备管理", deviceList);
        View view = viewUtils.initSmartHome(this, datas);
        layout.addView(view);
    }


    private void saveDatas() {
        SharedPreferences.Editor editor = getSharedPreferences("SmartControl", MODE_MULTI_PROCESS).edit();

        editor.putString("SmartControlJSON", "{\n" +
                "    \"devices\": [\n" +
                "        {\n" +
                "            \"type\": 4,\n" +
                "            \"name\": \"花花\",\n" +
                "            \"room\": \"客厅\"\n" +
                "        },\n" +
                "{\n" +
                "            \"type\": 4,\n" +
                "            \"name\": \"花花\",\n" +
                "            \"room\": \"客厅\"\n" +
                "        },\n" +
                "{\n" +
                "            \"type\": 4,\n" +
                "            \"name\": \"花花\",\n" +
                "            \"room\": \"客厅\"\n" +
                "        },\n" +" {\n" +
                        "            \"type\": 4,\n" +
                        "            \"name\": \"花花\",\n" +
                        "            \"room\": \"客厅\"\n" +
                        "        },"+
                "{\n" +
                "            \"type\": 4,\n" +
                "            \"name\": \"花花\",\n" +
                "            \"room\": \"客厅\"\n" +
                "        },\n" +
                "{\n" +
                "            \"type\": 4,\n" +
                "            \"name\": \"花花\",\n" +
                "            \"room\": \"客厅\"\n" +
                "        },\n" +
                "{\n" +
                "            \"type\": 4,\n" +
                "            \"name\": \"花花\",\n" +
                "            \"room\": \"客厅\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": 6,\n" +
                "            \"name\": \"黄黄\",\n" +
                "            \"room\": \"卧室\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"type\": 3,\n" +
                "            \"name\": \"可爱\",\n" +
                "            \"room\": \"过道\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"scenes\": [\n" +
                "        \"回家\",\n" +
                "        \"晚餐\",\n" +
                "        \"娱乐\",\n" +
                "        \"休息\",\n" +
                "        \"起床\",\n" +
                "        \"离家\",\n" +
                "        \"度假\",\n" +
                "        \"会客\",\n" +
                "        \"欢庆\",\n" +
                "        \"灯光全开\",\n" +
                "        \"灯光全关\",\n" +
                "        \"家庭影院\",\n" +
                "        \"瑜伽\"\n" +
                "    ]\n" +
                "}");

        editor.commit();
    }


    private void initDatas() {

        SharedPreferences sp = getSharedPreferences("SmartControl",
                MODE_MULTI_PROCESS);
        HashMap<String, String> maps = (HashMap<String, String>) sp.getAll();
        if (maps.size() > 0) {
            for (String key : maps.keySet()) {
                scenesList.clear();
                try {
                    JSONObject jo = new JSONObject(maps.get(key));
                    JSONArray scenesja = (JSONArray) jo.get("scenes");


                    if (scenesja != null && scenesja.length() > 0) {
                        for (int i = 0; i < scenesja.length(); i++) {
                            if (!TextUtils.isEmpty(scenesja.getString(i))) {
                                scenesList.add(new String[]{scenesja.getString(i)});
                            }
                        }
                    }

                    JSONArray devicesja = (JSONArray) jo.get("devices");
                    if (devicesja != null && devicesja.length() > 0) {
                        for (int i = 0; i < devicesja.length(); i++) {
                            JSONObject device = devicesja.getJSONObject(i);
                            String name = device.getString("name");
                            int type = device.optInt("type", -1);
                            String room = device.getString("room");
//                            DeviceItem deviceItem = new DeviceItem();
//                            deviceItem.name = name;
//                            deviceItem.room = room;
//                            deviceItem.type = type;

                            if (romHashMap.get(room) != null) {
                                List<String[]> temp = romHashMap.get(room);
                                temp.add(new String[]{"" + mRomTypeToImageId.get(type), name});


                                romHashMap.put(room, temp);
                            } else {
                                List<String[]> l = new ArrayList<>();
                                l.add(new String[]{"" + mRomTypeToImageId.get(type), name});
                                romHashMap.put(room, l);
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initDatass(){
        SharedPreferences sp = getSharedPreferences("SmartControl",
                MODE_MULTI_PROCESS);
                try {
                    String key=sp.getString("SmartControlJSON",null);
                    if (key==null){
                        throw  new RuntimeException("null");
                    }
                    JSONObject jo = new JSONObject(key);
                    JSONArray scenesja = (JSONArray) jo.get("scenes");
                    if (scenesja != null && scenesja.length() > 0) {
                        for (int i = 0; i < scenesja.length(); i++) {
                            if (!TextUtils.isEmpty(scenesja.getString(i))) {
                                scenesList.add(new String[]{scenesja.getString(i)});
                            }
                        }
                    }

                    JSONArray devicesja = (JSONArray) jo.get("devices");
                    if (devicesja != null && devicesja.length() > 0) {
                        for (int i = 0; i < devicesja.length(); i++) {
                            JSONObject device = devicesja.getJSONObject(i);
                            String name = device.getString("name");
                            int type = device.optInt("type", -1);
                            String room = device.getString("room");
//                            DeviceItem deviceItem = new DeviceItem();
//                            deviceItem.name = name;
//                            deviceItem.room = room;
//                            deviceItem.type = type;

                            if (romHashMap.get(room) != null) {
                                List<String[]> temp = romHashMap.get(room);
                                temp.add(new String[]{"" + mRomTypeToImageId.get(type), name});
                                romHashMap.put(room, temp);
                            } else {
                                List<String[]> l = new ArrayList<>();
                                l.add(new String[]{"" + mRomTypeToImageId.get(type), name});
                                romHashMap.put(room, l);
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


    }



}
