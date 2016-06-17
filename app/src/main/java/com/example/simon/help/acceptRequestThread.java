package com.example.simon.help;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Iterator;

public class acceptRequestThread extends  Thread {
    private int port;
    private String ip;
    private String req_name;
    private String reply_name;
    private String req_cell;
    private String reply_cell;

    public acceptRequestThread(String ipp, int portt, String nick, String cell, String nick_reply, String cell_reply)
    {
        ip = ipp;
        port = portt;
        req_name = nick;
        req_cell = cell;
        reply_name = nick_reply;
        reply_cell = cell_reply;
    }

    public void run()
    {
        try {
            Socket socket = new Socket(ip, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            out.write("3\n");
            out.write(req_cell);
            out.newLine();
            out.write(req_name);
            out.newLine();
            out.write(reply_cell);
            out.newLine();
            out.write(reply_name);
            out.newLine();
            out.flush();

            String retCode = in.readLine();
            if(retCode.equals("Successful")){
                System.out.println("successfully accepted");
            }
            else if(retCode.equals("Unsuccessful")){
                System.out.println("accept unsuccessfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
