package com.example.atonomous_driving;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.daum.mf.map.api.MapView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class CallActivity extends AppCompatActivity {

    EditText send_place, receive_place, receiver_name;
    TextView estimated_time;
    Button receiver_check, button_rc_call;

    // send_place, receive_place => read-only
    // estimated_time, button_rc_call => visibility : gone

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        MapView mapView= new MapView(this);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        send_place = findViewById(R.id.send_place);
        receive_place = findViewById(R.id.receive_place);
        receiver_name = findViewById(R.id.receiver_name);
        estimated_time = findViewById(R.id.estimated_time);
        receiver_check = findViewById(R.id.receiver_check);
        button_rc_call = findViewById(R.id.button_rc_call);

        send_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CallActivity.this);
                alertDialogBuilder.setTitle("보내시는 장소를 선택 해 주세요."); // 제목

                final String[] buildinglist = new String[] {"본관", "공학관", "연서관", "청문관", "창조관", "정보관", "도서관", "유아교육관", "백호체육관", "교수회관", "영진생활관", "동문", "서문"};

                alertDialogBuilder.setItems(buildinglist, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        send_place.setText(buildinglist[which]); // 선택한 건물 텍스트로 입력..
                    }
                });
                alertDialogBuilder.show();
            }
        });

        receive_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CallActivity.this);
                alertDialogBuilder.setTitle("받으시는 장소를 선택 해 주세요."); // 제목

                final String[] buildinglist = new String[] {"본관", "공학관", "연서관", "청문관", "창조관", "정보관", "도서관", "유아교육관", "백호체육관", "교수회관", "영진생활관", "동문", "서문"};

                alertDialogBuilder.setItems(buildinglist, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        receive_place.setText(buildinglist[which]); // 선택한 건물 텍스트로 입력..
                    }
                });
                alertDialogBuilder.show();
            }
        });
    }
}
