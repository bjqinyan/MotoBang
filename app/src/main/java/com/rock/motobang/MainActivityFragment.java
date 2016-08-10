package com.rock.motobang;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener {
    private int number;
    private int nextNumber;
    private int time;
    private String date;
    private TextView mTv_nextChange;
    private TextView mTv_beforeChange;
    private TextView mTv_time;
    private TextView mTv_dateChange;
    private SharedPreferences sp;

    public MainActivityFragment() {
    }


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mTv_nextChange = (TextView) rootView.findViewById(R.id.tv_nextChange);
        Button mBt_change = (Button) rootView.findViewById(R.id.bt_change);
        mTv_beforeChange = (TextView) rootView.findViewById(R.id.tv_beforeChange);
        mTv_time = (TextView) rootView.findViewById(R.id.tv_timeChange);
        mTv_dateChange = (TextView) rootView.findViewById(R.id.tv_dateChange);
        mBt_change.setOnClickListener(this);
        number = 0;
        sp = getActivity().getSharedPreferences("moto", Context.MODE_PRIVATE);
        number = sp.getInt("number", 0);
        nextNumber = sp.getInt("nextNumber", 0);
        time = sp.getInt("time", 0);
        date = sp.getString("date", "");
        mTv_nextChange.setText(nextNumber + "km");
        mTv_beforeChange.setText(number + "km");
        mTv_time.setText(date);
        mTv_time.setText(time + "");
        return rootView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_change:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("请输入当前总公里数");

                View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);

                builder.setView(view);

                final EditText editText = (EditText) view.findViewById(R.id.editText);
                editText.setHint(nextNumber + "");

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String num = editText.getText().toString().trim();
                        if (!num.equals(""))
                            number = Integer.parseInt(num);
                        SharedPreferences.Editor editor = sp.edit();

                        editor.putInt("number", number);
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
                        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                        String str = formatter.format(curDate);
                        mTv_dateChange.setText(str);
                        editor.putString("date", str);
                        mTv_beforeChange.setText(number + "km");
                        time++;
                        editor.putInt("time", time);
                        mTv_time.setText(time + "");
                        nextNumber = number + 1500;
                        editor.putInt("nextNumber", nextNumber);
                        mTv_nextChange.setText(nextNumber + "km");
                        editor.commit();
                        Toast.makeText(getActivity(), number + "", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                break;
        }
    }
}
