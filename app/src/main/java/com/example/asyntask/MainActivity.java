package com.example.asyntask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements MyAsyncCallback {

    TextView textStatus;
    TextView textDesc;
    String INPUT_STRING= "Halo Ini Demo AsynTask";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textStatus= findViewById(R.id.text_status);
        textDesc= findViewById(R.id.text_desc);
        DemoAsync demoAsync= new DemoAsync(this);
        // input text to proses asyntask
        demoAsync.execute(INPUT_STRING);
    }

    @Override
    public void onPreExecute() {
        // betrjalan ketika preexecute
        textStatus.setText(R.string.status_pre);
        textDesc.setText(INPUT_STRING);
    }


    // setelah selesai proses asyntask
    @Override
    public void onPostExecute(String text) {
        // berjalan ketika do  selesai dilakukan
        textStatus.setText(R.string.status_post);
        if(text !=null){
            textDesc.setText(text);
        }
    }
    // berfungsi mengelola data secara asyncrounnus
    private static class DemoAsync extends AsyncTask<String, Void, String> {
        static  final String LOG_ASYNC="DemoAsync";
        // WakReference digunakan untuk menghubungkan interface dengan activity agar tidak terdapat kebocoran memory
        WeakReference<MyAsyncCallback> myListener;

        // ketika preexecute
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MyAsyncCallback myListener= this.myListener.get();
            if(myListener!= null){
                myListener.onPreExecute();
            }
        }

        public DemoAsync(MyAsyncCallback myListenert) {
            this.myListener = new WeakReference<>(myListenert);
        }

        // ketika sedang berjalan
        @Override
        protected String doInBackground(String... strings) {
            String output= null;
            try {
                String input= strings[0];
                output= input + "Selamat Belajar!!";
                Thread.sleep(5000);
            }catch (Exception e){
                Log.d(LOG_ASYNC, e.getMessage());
            }
            return output;
        }

        // ketika sudah selesai berjalan
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // mengeksekusi s yang nantinya akan dipanggil lagi di interface
            MyAsyncCallback myListener= this.myListener.get();
            if(myListener!=null){
                // mengeksekusi hasil do background melalui interface
                myListener.onPostExecute(s);
            }
        }
    }
}
