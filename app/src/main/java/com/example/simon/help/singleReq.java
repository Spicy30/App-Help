package com.example.simon.help;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Simon on 2016/6/17.
 */
public class singleReq {
    String nickname;
    String cellphone;
    String body;
    String title;
    String accepted;

    public singleReq(String nick, String cell, String bo, String ti)
    {
        this(nick, cell, bo, ti, "F");
    }

    public singleReq(String nick, String cell, String bo, String ti, String accepted)
    {
        this.title = ti;
        this.body = bo;
        this.cellphone = cell;
        this.nickname = nick;
        this.accepted = accepted;
    }

    public JSONObject toJSONObject(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("nickname", nickname);
            obj.put("cellphone", cellphone);
            obj.put("body", body);
            obj.put("title", title);
            obj.put("accepted", accepted);
        } catch (JSONException e){
            e.printStackTrace();
        }

        return obj;
    }
}
