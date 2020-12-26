package com.example.piggybank;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainFragment extends Fragment {


     View view;

    ImageView pigimage;
    TextView output;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_main, container, false);

        //희선 2020-12-26 db 변수 선언: SQLite 데이터 InputForm -> MainFragment로 전달하기 위해
        DBHelper helper= new DBHelper(getContext(), "adddb.db", null, 1);
        final SQLiteDatabase db = helper.getWritableDatabase();

        // 세림 2020-12-22 res/drawble 폴더에 있는 이미지로 세팅하기
        pigimage = (ImageView) view.findViewById(R.id.pig);
        pigimage.setImageResource(R.drawable.pig);
        pigimage.setOnClickListener(new MyListener());


        //희선 2020-12-26
        //Select 쿼리 이용
        //변수를 통해 데이터 조작시 참고 :  https://m.blog.naver.com/PostView.nhn?blogId=qbxlvnf11&logNo=221406135285&proxyReferer=https:%2F%2Fwww.google.com%2F
        Cursor c = db.query("mytable11",null,"year=2016",null,null,null,null,null);

        String Result = "output"; //쿼리에 맞게 누적된 정보 저장

        //희선 2020-12-24 쿼리문 실행
        while(c.moveToNext()) {
            String cat = c.getString(c.getColumnIndex("category"));
            int y = c.getInt(c.getColumnIndex("year"));
            int m = c.getInt(c.getColumnIndex("month"));
            int d = c.getInt(c.getColumnIndex("day"));
            int p = c.getInt(c.getColumnIndex("price"));
            //String amount 가 필요!

            Result = "";
            Result += cat + "," + y + "," + m + "," + d+","+p;
        }

        Log.d("asdfResult값",Result);

        output = (TextView) view. findViewById(R.id.output);
        output.setText(Result);

        return view;
    }



        // 세림 2020-12-22 돼지 아이콘 클릭시 커스텀다이얼로그 표시
    class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            InputForm inputform = new InputForm(getActivity());
            inputform.callFunction();

        }
    }

}
