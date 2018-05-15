package cqupt.ui.watercoming;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import tool.MyOpenHelper;

/**
 * Created by 78914 on 2017/6/12.
 */

public class Fragment_home extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStated){
        return inflater.inflate(R.layout.order_layout,container,false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        list_info= (ListView) getActivity().findViewById(R.id.student_info_layout);
        dbHelper = MyOpenHelper.getInstance(getActivity());
        cursor=dbHelper.getReadableDatabase().query("info",null,null,null,null,null,null,null);
        if (cursor.moveToNext()){
            SimpleCursorAdapter adapter=new SimpleCursorAdapter(getActivity(),R.layout.rider_info_layout,cursor,
                    new String[]{"title","content","time"},new int[]{R.id.title_list,R.id.content_list,R.id.time_list});
            list_info.setAdapter(adapter);
        }
        dbHelper.close();
    }
    private Cursor cursor;
    private ListView list_info;
    private MyOpenHelper dbHelper;
}
