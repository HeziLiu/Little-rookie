package cqupt.ui.watercoming;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import tool.MyOpenHelper;
/**
 * Created by 78914 on 2017/6/12.
 */
public class Fragment_order extends Fragment {
    /* 声明全局变量data,
    num,month,day,cursor,list_order,dbHelper
	 *
	 */
    private String data;
    private String num;
    private String month;
    private String day;
    private Cursor cursor;
    private ListView list_order;
    private MyOpenHelper dbHelper;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStated){
        //data,num的数据来源
        Bundle bundle = getArguments();
        data=bundle.getString("DATA");
        num=bundle.getString("DATA2");//得到相应数据
        //关联布局界面
        return inflater.inflate(R.layout.student_order_layout,container,false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        display_order();
        //声明关联Button
        Button order= (Button) getActivity().findViewById(R.id.order_bt);
        //设置监听器
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Time time = new Time("GMT+8");
                //获取当前时间
                time.setToNow();
                //将获取到的时间插入到订单数据库中
                month = String.valueOf(time.month);
                day = String.valueOf(time.monthDay);
                //执行插入数据
                db.execSQL("insert into orderlist(RoomNum,month,day)values(?,?,?)", new String[]{num, month, day});
                display_order();
            }
        });
    }
    private void display_order() {
        //声明关联ListView
        list_order= (ListView) getActivity().findViewById(R.id.order_list);
        dbHelper = MyOpenHelper.getInstance(getActivity());
        //利用cursor查询插入的数据的时间，寝室号，并在ListView中显示出来
        cursor=dbHelper.getReadableDatabase().query("orderlist",null,"RoomNum=?",new String[]{num},null,null,null,null);
        if (cursor.moveToNext()){
            SimpleCursorAdapter adapter=new SimpleCursorAdapter(getActivity(),R.layout.order_listitem,cursor,
                    new String[]{"RoomNum","month","day"},new int[]{R.id.order_num,R.id.order_month,R.id.order_day});
            list_order.setAdapter(adapter);
        }
        dbHelper.close();//关闭表
    }
}
