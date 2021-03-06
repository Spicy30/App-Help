package com.example.simon.help;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class NewRequest extends AppCompatActivity {

    private Button OKButton;
    private Button CancelButton;

    private EditText titleEditText;
    private EditText nicknameEditText;
    private EditText cellphoneEditText;
    private EditText contentEditText;

    private String title;
    private String nickname;
    private String cellphone;
    private String content;
    private String ServerIP = config.server_ip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_request);

        Typeface Mias = Typeface.createFromAsset(getAssets(), "fonts/MiasScribblings.ttf");
        TextView custom1 = (TextView)findViewById(R.id.newtitle);
        custom1.setTypeface(Mias);

        getViews();
        processControllers();
    }

    private void getViews() {
        OKButton = (Button)findViewById(R.id.ok);
        CancelButton = (Button)findViewById(R.id.cancel);

        titleEditText = (EditText)findViewById(R.id.title_content);
        nicknameEditText = (EditText)findViewById(R.id.nickname_content);
        cellphoneEditText = (EditText)findViewById(R.id.cellphone_content);
        contentEditText = (EditText)findViewById(R.id.content_content);

    }

    private void processControllers() {

        //add OK button listener
        View.OnClickListener OKListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getEditText();

                // checkIfFilled() : to showAlert if no information
                if (checkIfFilled()) {

                    // TODO: send data to server
                    // send request information
                    sendDataToServer();
                }


            }
        };
        OKButton.setOnClickListener(OKListener);

        //add Cancel Button listener
        View.OnClickListener CancelListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        };
        CancelButton.setOnClickListener(CancelListener);
    }

    private void getEditText() {
        title = titleEditText.getText().toString();
        nickname = nicknameEditText.getText().toString();
        cellphone = cellphoneEditText.getText().toString();
        content = contentEditText.getText().toString();
    }

    private boolean checkIfFilled() {
        if(title.matches(""))
            showAlert(R.string.warning,R.string.no_title);
        else if(nickname.matches(""))
            showAlert(R.string.warning,R.string.no_nickname);
        else if(cellphone.matches(""))
            showAlert(R.string.warning,R.string.no_cellphone);
        else if(content.matches(""))
            showAlert(R.string.warning,R.string.no_content);
        else
            return true;
        return false;
    }

    // for backward compatibility, accept only two parameters with "finish" set to false
    private void showAlert(int titleId, int messageId){
        showAlert(titleId, messageId, false);
    }

    private void showAlert(int titleId, int messageId, final boolean finish){
        new AlertDialog.Builder(NewRequest.this)
                .setTitle(titleId)
                .setMessage(messageId)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(finish){
                            setResult(RESULT_OK);
                            finish();
                        }
                    }
                })
                .show();
    }

    private void sendDataToServer() {
        // TODO
        try {
            JSONObject reqq = new singleReq(nickname, cellphone, content, title).toJSONObject();
            sendRequestThread std = new sendRequestThread(ServerIP, 3000, reqq);
            std.start();
            std.join();

            showAlert(R.string.notice, R.string.successful, true);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }



}
