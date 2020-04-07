package com.example.atonomous_driving;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.disposables.CompositeDisposable;

public class SignUpActivity extends AppCompatActivity {

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    private TextView tvData;

    EditText editText_id, editText_password, editText_name, editText_tel;
    AutoCompleteTextView editText_univ;
    Button button_login, button_signup;

    // 자동 완성을 위한 스트링 리스트
    String[] arWords = new String[] {
            "경북대학교", "경운대학교", "경일대학교", "계명대학교 대명캠퍼스", "대구교육대학교", "대구대학교",
            "대구가톨릭대학교 효성캠퍼스", "영남대학교 경산캠퍼스", "영진전문대학교 복현캠퍼스", "한동대학교"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // 어댑터를 만들고 자동완성 스트링 리스트와 연결해줌
        ArrayAdapter<String> adWord = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, arWords);
        editText_univ = (AutoCompleteTextView)findViewById(R.id.editText_univ);

        // 어댑터 세팅
        editText_univ.setAdapter(adWord);


        editText_id = findViewById(R.id.editText_id);
        editText_password = findViewById(R.id.editText_password);
        editText_name = findViewById(R.id.editText_name);
        editText_tel = findViewById(R.id.editText_tel);
        // editText_univ = findViewById(R.id.editText_univ);

        button_login = findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button_signup = findViewById(R.id.button_signup);
        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new JSONTask().execute("http://192.168.0.95:8005/auth/join"); //AsyncTask 시작 시킴
            }
        });
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    public class JSONTask extends AsyncTask<String, String, String> {

        String user_id = editText_id.getText().toString().trim();
        String user_password = editText_password.getText().toString().trim();
        String user_name = editText_name.getText().toString().trim();
        String user_phone_number = editText_tel.getText().toString().trim();
        String user_univ = editText_univ.getText().toString().trim();

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("user_id", user_id);
                jsonObject.accumulate("password", user_password);
                jsonObject.accumulate("name", user_name);
                jsonObject.accumulate("phone_number", user_phone_number);
                jsonObject.accumulate("univ", user_univ);
                HttpURLConnection con = null;
                BufferedReader reader = null;

                try {
                    //URL url = new URL("http://192.168.35.39:8005/auth/join");
                    URL url = new URL(urls[0]);
                    //연결을 함
                    con = (HttpURLConnection)url.openConnection();
                    con.setRequestMethod("POST"); //POST 방식으로 보냄
                    con.setRequestProperty("Cache-Control", "no-cache"); //캐시 설정
                    con.setRequestProperty("Content-Type", "application/json"); //application JSON 형식으로 전송
                    con.setRequestProperty("Accept", "text/html"); //서버에 response 데이터를 html로 받음
                    con.setDoOutput(true); //Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true); //Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();
                    OutputStream outStream = con.getOutputStream(); //서버로 보내기 위해 스트림 만듦

                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream)); //버퍼를 생성하고 넣음

                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close(); //버퍼를 받아줌

                    //서버로부터 데이터를 받음
                    InputStream stream = con.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream)); //속도 향상시키고 부하 줄이기 위한 버퍼 선언
                    StringBuffer buffer = new StringBuffer();
                    String line = ""; //line별 String을 받기 위한 temp 변수
                    while ((line = reader.readLine()) != null) { //실제 reader에서 데이터를 가져오는 부분. 즉, node.js 서버로부터 데이터 가져옴
                        buffer.append(line);
                    }

                    return buffer.toString(); //서버로부터 받은 값을 리턴해줌 아마 OK!!가 들어올 것임
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (con != null) {
                        con.disconnect();
                    }
                    try {
                        if (reader != null) {
                            reader.close(); //버퍼를 닫아줌
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.equals("ok")) {
                Toast.makeText(getApplicationContext(), "회원가입에 성공했습니다", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}

