package edu.ewubd.cse4892rbr;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.webkit.WebView;
import android.widget.Button;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class myattendence extends AppCompatActivity {

    private Button btnExit;
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());
    private String URL = "https://www.muthosoft.com/univ/attendance/report.php";
    // Reference objects for handling event lists
    private WebView web;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myattendence);
        Button btnExit = findViewById(R.id.exit);
        btnExit.setOnClickListener(view -> finish());

        web = findViewById(R.id.webview);
        String[] keys = {"CSE489-Lab","year","semester","course","section","sid"};
        String[] values = {"true","2022","1","CSE489","2","2018360088"};
        httpRequest(keys,values);
    }

    @SuppressLint("StaticFieldLeak")
    private void httpRequest(final String keys[],final String values[]){
        executorService.execute( ()->{
            try{
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                for (int i =0;i<keys.length;i++){
                    params.add( new BasicNameValuePair(keys[i],values[i]));
                }
                data = JSONParser.getInstance().makeHttpRequest(URL,"POST",params);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
            handler.post(()->{
                if(data != null){
                    try{
                        System.out.println("data"+data);
                        web.loadDataWithBaseURL(null,data,"text/html","UTF-8",null);
                        //web.loadData(data,"text/html","UTF-8");
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
        });
    }
}