package com.example.simon.help;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

public class getAllRequestThread extends Thread{
    private int port;
    private String ip;
    private Socket socket;
    private ArrayList<String> requestList_title = new ArrayList<String>();

    public getAllRequestThread(String ipp, int portt, ArrayList<String> req)
    {
        ip = ipp;
        port = portt;
        requestList_title = req;
    }

    public void run()
    {
        try {
            Log.i("Chat", "yoyoyo");
            Socket socket = new Socket(ip, port); // doesn't work QAQ
            Log.i("Chat", "yoyoyo");
            if (socket.isConnected()) Log.i("Chat", "DAMN");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            out.write("1\n");   // ask for all requests
            out.flush();
            //out.close();
            System.out.println("output done");
            String jsonstr = in.readLine();
            System.out.println(jsonstr);
            if(jsonstr != null){
                JSONArray arr = new JSONArray(jsonstr);

                JSONObject ob;
                for (int i = 0; i < arr.length(); i++) {
                    ob = (JSONObject) arr.get(i);
                    requestList_title.add((String) ob.get("title"));
                }
            }
            //in.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
