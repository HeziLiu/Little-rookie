package cqupt.ui.watercoming;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.R.attr.name;

/**
 * Created by 78914 on 2017/6/12.
 */

public class Fragment_my extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStated){
        Bundle bundle = getArguments();
        data=bundle.getString("DATA");
        num=bundle.getString("DATA2");//获取Intent数据
        return inflater.inflate(R.layout.student_info,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView tv_name= (TextView) getActivity().findViewById(R.id.my_name);
        tv_name.setText(data);
    }
    private String data;
    private String num;
}
