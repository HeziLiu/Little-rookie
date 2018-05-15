package cqupt.ui.watercoming;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import tool.MyOpenHelper;
import static cqupt.ui.watercoming.R.*;

/**
 * Created by 78914 on 2017/6/13.
 */

public class Fragment_home_rider extends Fragment {
    private Cursor cursor;//游标
    private ListView list_info;//公告显示列表
    private MyOpenHelper dbHelper;//用于数据库操作
    Dialog dialog;//用于对话框返回Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStated) {
        return inflater.inflate(layout.fragment_home_rider, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        display_Info();

        list_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //创建对话框
                final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                //解析布局文件
                LayoutInflater factory=LayoutInflater.from(getActivity());
                final View textEntryView=factory.inflate(R.layout.rider_info_delete,null);
                //设置对话框布局
                builder.setView(textEntryView);
                //获取对话框布局中的按钮控件
                final Button delete= (Button) textEntryView.findViewById(R.id.rider_info_delete);
                //删除按钮点击事件
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //通过游标获得当前点击的项Id
                        String id=cursor.getString(cursor.getColumnIndex("_id"));
                        //获得数据库写操作
                        SQLiteDatabase db=dbHelper.getWritableDatabase();
                        //返回删除的行数
                        int line=db.delete("info","_id=?",new String[]{id});
                        //判断是否删除成功
                        if (line>0){
                            Toast.makeText(getActivity(),"数据删除成功",Toast.LENGTH_LONG).show();
                        }
                        display_Info();//重新显示公告表项
                        db.close();//关闭数据库
                        dialog.dismiss();//关闭对话框
                    }
                });
                dialog=builder.create();//创建对话框
                dialog.show();//对话框显示
            }
        });
    }

    private void display_Info() {
        list_info= (ListView) getActivity().findViewById(id.rider_info_list);
        dbHelper = MyOpenHelper.getInstance(getActivity());
        cursor=dbHelper.getReadableDatabase().query("info",null,null,null,null,null,null,null);
        if (cursor.moveToNext()){
            SimpleCursorAdapter adapter=new SimpleCursorAdapter(getActivity(),R.layout.rider_info_layout,cursor,
                    new String[]{"title","content","time"},new int[]{R.id.title_list,R.id.content_list,R.id.time_list});
            list_info.setAdapter(adapter);
        }
        dbHelper.close();
    }
}


