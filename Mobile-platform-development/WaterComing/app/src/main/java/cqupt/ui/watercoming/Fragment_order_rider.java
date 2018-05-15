package cqupt.ui.watercoming;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import tool.MyOpenHelper;
/**
 * Created by 78914 on 2017/6/13.
 */
public class Fragment_order_rider extends Fragment {
    private Cursor cursor;
    private ListView list_order;
    private MyOpenHelper dbHelper;
    Dialog dialog;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStated) {
        return inflater.inflate(R.layout.fragment_order_rider, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        display_Order();//显示所有的待办订单，见页底
        //订单列表的点击事件
        list_order.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //创建对话框进一步操作
                final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                //获得反射器
                LayoutInflater factory=LayoutInflater.from(getActivity());
                //得到解析的View
                final View textEntryView=factory.inflate(R.layout.rider_dispose_order,null);
                //设置对话框布局
                builder.setView(textEntryView);
                //绑定布局控件
                final Button finish= (Button) textEntryView.findViewById(R.id.rider_order_finish);
                final Button unpaid= (Button) textEntryView.findViewById(R.id.rider_order_unpaid);
                //已完成按钮点击事件
                finish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //用cursor.getSring（）方法从数据库获取当前item指向的行的id
                        String id=cursor.getString(cursor.getColumnIndex("_id"));
                        //获取寝室号RoomNum
                        String num=cursor.getString(cursor.getColumnIndex("RoomNum"));
                        //获取月month
                        String mon=cursor.getString(cursor.getColumnIndex("month"));
                        //获取日day
                        String day=cursor.getString(cursor.getColumnIndex("day"));
                        //打开数据库
                        SQLiteDatabase db=dbHelper.getWritableDatabase();
                        //向orderlistPaid表中插入当前行的数据
                        //从orderlistPaid表中删除当前行并返回删除的行数
                        db.execSQL("insert into orderlistPaid(RoomNum,month,day)values(?,?,?)",new String[]{num,mon,day});
                        //返回删除的行数
                        int line=db.delete("orderlist","_id=?",new String[]{id});
                        if (line>0){
                            Toast.makeText(getActivity(),"已成功处理该订单",Toast.LENGTH_LONG).show();
                            //若删除成功则放出吐司
                        }
                        //从数据库中获取数据并显示的函数，见本页底
                        display_Order();
                        //使用完数据库，关闭
                        db.close();
                        //关闭对话框
                        dialog.dismiss();
                    }
                });
                dialog=builder.create();//创建对话框
                dialog.show();//显示对话框
                //待支付按钮的点击事件监听
                unpaid.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id=cursor.getString(cursor.getColumnIndex("_id"));
                        String num=cursor.getString(cursor.getColumnIndex("RoomNum"));
                        String mon=cursor.getString(cursor.getColumnIndex("month"));
                        String day=cursor.getString(cursor.getColumnIndex("day"));
                        //以上获取当前行在表中的对应内容并用变量存储
                        SQLiteDatabase db=dbHelper.getWritableDatabase();
                        //打开数据库
                        //向orderlistUnPaid表中插入当前行的数据
                        //从orderlistUnPaid表中删除当前行并返回删除的行数
                        db.execSQL("insert into orderlistUnPaid(RoomNum,month,day)values(?,?,?)",new String[]{num,mon,day});
                        //返回删除的行数
                        int line=db.delete("orderlist","_id=?",new String[]{id});
                        if (line>0){
                            Toast.makeText(getActivity(),"已成功处理该订单",Toast.LENGTH_LONG).show();
                            //若删除成功则放出吐司
                        }
                        display_Order();//从数据库中获取数据并显示的函数，见本页底
                        db.close();//使用完数据库，关闭
                        dialog.dismiss();//关闭对话框
                    }
                });
            }
        });
    }
    private void display_Order() {
        //获取listview
        list_order = (ListView) getActivity().findViewById(R.id.all_order_list);
        //引用数据库
        dbHelper = MyOpenHelper.getInstance(getActivity());
        cursor = dbHelper.getReadableDatabase().query("orderlist", null, null, null, null, null, null, null);
        //打开数据库并读取orderlist表中的所有内容存入cursor
        if (cursor.moveToNext()) {
            //用SimpleCursorAdapter适配器将cursor的内容一条一条的显示在listview的item上
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.order_listitem, cursor,
                    new String[]{"RoomNum", "month", "day"}, new int[]{R.id.order_num, R.id.order_month, R.id.order_day});
            list_order.setAdapter(adapter);//绑定
        }
        dbHelper.close();//关闭数据库
    }
}
