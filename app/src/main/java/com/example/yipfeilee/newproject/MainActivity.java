package com.example.yipfeilee.newproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /*
    String s_whatever, s_password
    int    i_
    bool   is_
    double/long/float(normally only double) f_
    datetime dt_
    array arr_  ,   suffix _info for one dimension
    _list 2+ dimension array
    any suffix with _id is a unique id
    */

    EditText getcode;
    Spinner spinner;
    String uname,userid,users,id;
    TextView txt;
    String []name;
    String []user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner =(Spinner)findViewById(R.id.spinner);
        loadNameList();

        txt = (TextView)findViewById(R.id.txt);

    }

    public void loadNameList() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://cloudsub04.trio-mobile.com/curl/mobile/user/get_user.php"
                , new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        uname=new String(responseBody);
                        JSONArray jobject = null;
                        try {
                            jobject = new JSONArray(uname);
                            name=new String[jobject.length()];
                            user_id=new String[jobject.length()];
                            for (int x = 0; x < jobject.length(); x++){
                                name[x]=jobject.getJSONObject(x).getString("s_first_name");
                                user_id[x]=jobject.getJSONObject(x).getString("user_id");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        populateSpinner(spinner, name, user_id);//populate the spinner with given data
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int i, long l)
                            {
                                System.out.println(name[i]+user_id[i]);
                                id=user_id[i];
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });


                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });

        Button btn_login = (Button)findViewById(R.id.btn2);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PassCode.class);
                startActivity(intent);
            }
        });
    }

    public void populateSpinner(Spinner spinner, String[] name, String[] id){
        ArrayAdapter<String > adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_activated_1,name);
        spinner.setAdapter(adapter);
    }

    public void http() throws IOException {
        HttpURLConnection urlConnection = null;
        URL url = new URL("http://cloudsub04.trio-mobile.com/curl/mobile/user/get_user.php");
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.connect();


        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();

        String jsonString = sb.toString();
        System.out.println("JSON: " + jsonString);
    }

    public void json(){
        System.out.println("gt call json");
        try {
            String lin, newjson = "";
            URL urls = new URL("http://cloudsub04.trio-mobile.com/curl/mobile/user/get_user.php");
            System.out.println("gt url");

            BufferedReader br = new BufferedReader(new InputStreamReader(urls.openStream()));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();

            String jsonString = sb.toString();
            System.out.println("JSON: " + jsonString);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("Error");
        }
    }

    public static void main() {

        URL oracle = null;
        try {
            oracle = new URL("http://cloudsub04.trio-mobile.com/curl/mobile/user/get_user.php");
            BufferedReader in = null;
            try {
                in = new BufferedReader(
                        new InputStreamReader(oracle.openStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            String inputLine;
            try {
                while ((inputLine = in.readLine()) != null)
                    System.out.println(inputLine);
            } catch (IOException e) {
                e.printStackTrace();
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public String checkLogin(String userID, String passCode){

        String message = "";
        System.out.println("userName:"+userID+",passCode:"+passCode);

        return message;
    }

    public void login_click(View v){

        String message;
        //String id = "";
        String userPassCode = "";
        String userName= spinner.getSelectedItem().toString();
        //String userPassCode = getcode.getText().toString();
        checkLogin(id, userPassCode);
    }
    @Override
    public void onClick(View v) {

        System.out.println("click");
    }
}
