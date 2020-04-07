package com.example.atonomous_driving;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jgabrielfreitas.core.BlurImageView;

import org.json.JSONException;
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


public class LoginActivity extends AppCompatActivity {

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    EditText editText_id, editText_password;
    Button button_login, button_signup;

    BlurImageView myBlurImage;

    String cookies;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myBlurImage = (BlurImageView) findViewById(R.id.myBlurImage);
        myBlurImage.setBlur(5);

        //init view
        editText_id = findViewById(R.id.editText_id);
        editText_password = findViewById(R.id.editText_password);

        button_signup = findViewById(R.id.button_signup);
        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button_login = findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new JSONTask().execute("http://192.168.0.95:8005/auth/login");
            }
        });
    }

    public class JSONTask extends AsyncTask<String, String, String> {
        String user_id = editText_id.getText().toString().trim();
        String user_password = editText_password.getText().toString().trim();
        String name, phone_number, univ;


        @Override
        protected String doInBackground(String... urls) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("user_id", user_id);
                jsonObject.accumulate("password", user_password);
                HttpURLConnection con = null;
                BufferedReader reader = null;
                //
                //String cookieString = CookieManager.getInstance().getCookie("http://192.168.35.39:8005/auth/login");

                try {
                    URL url = new URL(urls[0]);
                    con = (HttpURLConnection)url.openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Cache-Control", "no-cache");
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setRequestProperty("Accept", "text/html");
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.connect();

                    OutputStream outStream = con.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));

                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();

                    InputStream stream=con.getInputStream();
                    reader=new BufferedReader(new InputStreamReader(stream));
                    StringBuffer buffer=new StringBuffer();
                    String line="";
                    while((line=reader.readLine())!=null){
                        buffer.append(line);
                    }

                    return buffer.toString();

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
                            reader.close();
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

            if (result.equals("no")) {
                Toast.makeText(getApplicationContext(), "아이디나 비밀번호를 다시 확인해주세요!", Toast.LENGTH_SHORT).show();
                editText_id.setText("");
                editText_password.setText("");
            } else {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                Log.d("soyoung_result", result);

                try {
                    JSONObject obj = new JSONObject(result);
                    name = obj.getString("name");
                    phone_number = obj.getString("phone_number");
                    univ = obj.getString("univ");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("soyoung_testeee", name + ", " + phone_number + " , " + univ);

                intent.putExtra("user_id", user_id);
                intent.putExtra("name", name);
                intent.putExtra("phone_number", phone_number);
                intent.putExtra("univ", univ);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }
    }
}
