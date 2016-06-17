package com.example.simon.help;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Simon on 2016/6/17.
 */
public class sendRequestThread extends Thread{
    private int port;
    private String ip;
    private Socket socket;
    private JSONObject req;

    public sendRequestThread(String ipp, int portt, JSONObject reqq)
    {
        ip = ipp;
        port = portt;
        req = reqq;
    }
    public void run()
    {
        try{
            Socket socket = new Socket(ip, port); // doesn't work QAQ
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            System.out.println("Write new request!");
            String jsonstr = req.toString();
            out.write("2\n");
            out.flush();
            out.write(jsonstr + "\n");
            out.flush();
            System.out.println("JOJO\n");
            out.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
