package com.example.simon.help;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by Simon on 2016/6/17.
 */
public class acceptThread extends  Thread {
    private int port;
    private String ip;
    private String req_name;
    private String req_cell;
    private JSONObject obj;



    public acceptThread(String ipp, int portt, String nick, String cell, JSONObject objj)
    {
        ip = ipp;
        port = portt;
        req_name = nick;
        req_cell = cell;
        obj = objj;
    }

    public void run()
    {
        try {
            Socket socket = new Socket(ip, port); // doesn't work QAQ
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            out.write("4\n");
            out.write(req_cell);
            out.newLine();
            out.write(req_name);
            out.newLine();
            out.flush();

            String climsg = in.readLine();
            if (climsg.equals("1")) // accepted
            {
                String jsonstr = in.readLine();
                obj = new JSONObject(jsonstr);
                obj.put("tf", "T");
            }
            else if (climsg.equals("2")) // not accepted
            {
                String jsonstr = in.readLine();
                obj = new JSONObject(jsonstr);
                obj.put("tf", "F");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
