package com.example.asyntask;

public interface MyAsyncCallback {
    void onPreExecute();
    void onPostExecute(String text);
}
