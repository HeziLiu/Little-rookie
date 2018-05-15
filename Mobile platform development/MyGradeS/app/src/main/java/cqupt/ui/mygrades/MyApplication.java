package cqupt.ui.mygrades;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by 78914 on 2017/6/5.
 */
/*该类用于按两次Back时finish所有的Activity*/
public class MyApplication extends Application {
    private List<Activity> activityList=new LinkedList<Activity>();
    private static MyApplication instance;
    private MyApplication(){}

    public static MyApplication getInstance(){
        if (null==instance){
            instance=new MyApplication();
        }
        return instance;
    }
    //加每次生成的Activity加入到list中
    public void addActivity(Activity activity){
        activityList.add(activity);
    }
    //循环退出所有的Activity
    public void exit(){
        for(Activity activity:activityList){
            activity.finish();
        }
        System.exit(0);
    }
}
