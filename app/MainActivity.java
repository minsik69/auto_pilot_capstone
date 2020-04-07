package com.example.atonomous_driving;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Cookie;

import android.content.Intent;
import android.os.Bundle;
import android.text.BoringLayout;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView_userName;

    ImageButton imgButton_set;

    Button button_process, button_receive, button_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView_userName = findViewById(R.id.textView_userName);

        Intent intent = getIntent();
        String user_id = intent.getStringExtra("user_id");
        String user_name = intent.getStringExtra("name");
        String user_phone_number = intent.getStringExtra("phone_number");
        String user_univ = intent.getStringExtra("univ");


        textView_userName.setText(user_name + "님, 환영합니다!");

        imgButton_set = findViewById(R.id.imgButton_set);
        imgButton_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
                intent.putExtra("user_id", user_id);
                intent.putExtra("name", user_name);
                intent.putExtra("phone_number", user_phone_number);
                intent.putExtra("univ", user_univ);
                startActivity(intent);
            }
        });

        button_call = findViewById(R.id.button_call);
        button_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent call_intent = new Intent(getApplicationContext(), CallActivity.class);
                startActivity(call_intent);
            }
        });

        button_receive = findViewById(R.id.button_receive);
        button_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "받기 미완성", Toast.LENGTH_SHORT).show();
            }
        });

        button_process = findViewById(R.id.button_process);
        button_process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "실시간 현황 미완성", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
