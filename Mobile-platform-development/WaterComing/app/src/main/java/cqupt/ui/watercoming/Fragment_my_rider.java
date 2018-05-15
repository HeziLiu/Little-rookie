package cqupt.ui.watercoming;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import tool.MyOpenHelper;

/**
 * Created by 78914 on 2017/6/13.
 */
//显示公告，在Fragment添加之后不能及时显示在主页，因为此时Activity已经创建，不能再重新刷新Listview
public class Fragment_my_rider extends Fragment {
    private MyOpenHelper dbHelper;//获取数据库
    Dialog dialog;//用于对话框消失及显示的操作
    Dialog dialog2;
    Dialog dialog3;
    Dialog dialog4;
    Dialog dialog5;//对话框对象
    private Cursor cursor;//数据库查找结果存放对象
    private int i=0; //存放结果集的个数
    private String S1=null;//sipnner空间选中“月”中item的值
    private String S2=null; //sipnner空间选中“日”中item的值
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStated) {

        return inflater.inflate(R.layout.fragment_my_rider, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayout ll = (LinearLayout)getActivity().findViewById(R.id.rider_my_info);//发布公告信息选项
        LinearLayout ll1= (LinearLayout) getActivity().findViewById(R.id.rider_my_paid);//查看已完成订单选项
        LinearLayout ll2= (LinearLayout) getActivity().findViewById(R.id.rider_my_unpaid);//查看未完成订单选项
        LinearLayout ll3= (LinearLayout) getActivity().findViewById(R.id.rider_my_income);//查询收益
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获得数据库当前版本
                dbHelper = MyOpenHelper.getInstance(getActivity());
                //创建对话框进行进一步操作
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                //获得反射器
                LayoutInflater factory = LayoutInflater.from(getActivity());
                //解析对话框布局View
                final View textEntryView = factory.inflate(R.layout.rider_info_editor, null);
                //设置对话框布局
                builder.setView(textEntryView);
                //绑定对话框相应控件
                final EditText title = (EditText) textEntryView.findViewById(R.id.rider_info_title);
                final EditText content = (EditText) textEntryView.findViewById(R.id.rider_info_content);
                final Button positive = (Button) textEntryView.findViewById(R.id.rider_info_postive);
                final Button negative = (Button) textEntryView.findViewById(R.id.rider_info_negative);
                //取消按钮点击事件
                negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                //确定按钮点击事件
                positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //得到编辑框输入的标题
                        String out_title = title.getText().toString();
                        //得到编辑框输入的内容
                        String out_content = content.getText().toString();
                        //获取数据库写权限
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        //两个编辑框不为空
                        if (out_content != null && out_title != null) {
                            //从系统获取当前时间
                            Time time = new Time("GMT+8");
                            time.setToNow();
                            int year = time.year;
                            int month = time.month;
                            int dat = time.monthDay;
                            int minute = time.minute;
                            int hour = time.hour;
                            //分别取出年月日时分秒方便公告栏显示
                            String show_time = year + "年" + month + "月" + dat + "日" + hour + "时" + minute + "分";
                            //将相应数据插入公告表
                            db.execSQL("insert into info(title,content,time)values(?,?,?)", new String[]{out_title, out_content, show_time});
                            //弹出操作成功提示
                            Toast.makeText(getActivity(), "发布成功！", Toast.LENGTH_LONG).show();
                            //对话框消失
                            dialog.dismiss();
                        }
                    }
                });
                dialog = builder.create();//创建对话框
                dialog.show();//显示对话框
                dbHelper.close();//关闭数据库
            }
        });


        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = MyOpenHelper.getInstance(getActivity());
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                LayoutInflater factory = LayoutInflater.from(getActivity());
                final View textEntryView = factory.inflate(R.layout.rider_paid_layout, null);
                builder1.setView(textEntryView);

                final ListView lv= (ListView) textEntryView.findViewById(R.id.order_paid_list);
                dbHelper = MyOpenHelper.getInstance(getActivity());
                cursor = dbHelper.getReadableDatabase().query("orderlistPaid", null, null, null, null, null, null, null);
                if (cursor.moveToNext()) {
                    SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.order_listitem, cursor,
                            new String[]{"RoomNum", "month", "day"}, new int[]{R.id.order_num, R.id.order_month, R.id.order_day});
                    lv.setAdapter(adapter);
                }
                dialog2=builder1.create();
                dialog2.show();
                dbHelper.close();
            }
        });

        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = MyOpenHelper.getInstance(getActivity());
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                LayoutInflater factory = LayoutInflater.from(getActivity());
                final View textEntryView = factory.inflate(R.layout.rider_unpaid_layout, null);
                builder1.setView(textEntryView);

                final ListView lv= (ListView) textEntryView.findViewById(R.id.order_unpaid_list);
                //待付款项的点击事件
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        AlertDialog.Builder builder3=new AlertDialog.Builder(getActivity());
                        LayoutInflater factory=LayoutInflater.from(getActivity());
                        final View view1=factory.inflate(R.layout.rider_dispose_unpaid_order,null);
                        builder3.setView(view1);
                        final Button btn= (Button) view1.findViewById(R.id.rider_dispose_unpaid_btn);
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String id=cursor.getString(cursor.getColumnIndex("_id"));
                                SQLiteDatabase db=dbHelper.getWritableDatabase();
                                //返回删除的行数
                                int line=db.delete("orderlistUnPaid","_id=?",new String[]{id});
                                if (line>0){
                                    Toast.makeText(getActivity(),"已成功处理该订单",Toast.LENGTH_LONG).show();
                                    dialog4.dismiss();
                                    //刷新当前未支付列表
                                    dbHelper = MyOpenHelper.getInstance(getActivity());
                                    cursor = dbHelper.getReadableDatabase().query("orderlistUnPaid", null, null, null, null, null, null, null);
                                    if (cursor.moveToNext()) {
                                        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.order_listitem, cursor,
                                                new String[]{"RoomNum", "month", "day"}, new int[]{R.id.order_num, R.id.order_month, R.id.order_day});
                                        lv.setAdapter(adapter);
                                    }
                                    dbHelper.close();
                                    cursor.close();

                                }
                            }
                        });
                        dialog4=builder3.create();
                        dialog4.show();
                    }
                });
                dbHelper = MyOpenHelper.getInstance(getActivity());
                cursor = dbHelper.getReadableDatabase().query("orderlistUnPaid", null, null, null, null, null, null, null);
                if (cursor.moveToNext()) {
                    SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.order_listitem, cursor,
                            new String[]{"RoomNum", "month", "day"}, new int[]{R.id.order_num, R.id.order_month, R.id.order_day});
                    lv.setAdapter(adapter);
                }
                dialog3=builder1.create();
                dialog3.show();
                dbHelper.close();
            }
        });
        //按键的监听事件
        ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //当点击按键时回调的函数
                AlertDialog.Builder builder4 = new AlertDialog.Builder(getActivity());
                LayoutInflater factory = LayoutInflater.from(getActivity());
                //搭建对话框，用于显示收益板块内容
                final View textEntryView = factory.inflate(R.layout.rider_income, null);
                builder4.setView(textEntryView);
                dialog5=builder4.create();//构建对话框
                dialog5.show();   //显示对话框
                //将个控件加入，包括：两个spinner、两个button、一个textview
                final Spinner sp1= (Spinner) textEntryView.findViewById(R.id.sp1);
                final Spinner sp2= (Spinner) textEntryView.findViewById(R.id.sp2);
                final TextView tv= (TextView) textEntryView.findViewById(R.id.money);
                final Button btn_sure= (Button) textEntryView.findViewById(R.id.rider_income_postive);
                final Button btn_not= (Button) textEntryView.findViewById(R.id.rider_income_negative);
                //将xml数据转换为具体的数据数组
                final String[] months=getResources().getStringArray(R.array.month);
                final String[] days=getResources().getStringArray(R.array.day);

                ArrayAdapter<String> adapter1=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,months);
                ArrayAdapter<String>adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,days);
                //将适配器数据加载到控件
                sp1.setAdapter(adapter1);
                sp2.setAdapter(adapter2);
                //spinner控件的监听
                sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            S1= (String) sp1.getSelectedItem();
                        //获取spinner中被点击的item的内容
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        S2= (String) sp2.getSelectedItem();
                        //获取spinner中被点击的item的内容
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                //button按键的监听
                btn_sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dbHelper = MyOpenHelper.getInstance(getActivity());
                        cursor = dbHelper.getReadableDatabase().query("orderlistPaid", null, "month=? and day=?", new String[]{S1,S2}, null, null, null, null);
                        //根据括号内的要求查找数据库信息，并将结果放在结果集中
                        if (cursor!=null&&cursor.moveToFirst())
                            do {
                                i++; //通过循环求出结果集的数据个数
                            }while (cursor.moveToNext());
                        //由cursor中结果个数乘于水的单价9元得到收益
                        String money= String.valueOf(i*9);
                        tv.setText(money);
                        dbHelper.close();
                        cursor.close();
                        i=0;
                    }

                });
                btn_not.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog5.dismiss();
                    }
                });
            }
        });

    }

}
