package com.example.simon.help;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Reply extends AppCompatActivity {

    private Button OKButton;
    private Button CancelButton;

    private EditText nicknameEditText;
    private EditText cellphoneEditText;

    private String nickname;
    private String cellphone;

    private String ServerIP = config.server_ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        getViews();
        processControllers();
    }

    private void getViews() {
        OKButton = (Button)findViewById(R.id.ok);
        CancelButton = (Button)findViewById(R.id.cancel);

        nicknameEditText = (EditText)findViewById(R.id.nickname_content);
        cellphoneEditText = (EditText)findViewById(R.id.cellphone_content);
    }

    private void processControllers() {

        //add OK button listener
        View.OnClickListener OKListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getEditText();

                // checkIfFilled() : to showAlert if no information or accepted
                if (checkIfNotAccepted() && checkIfFilled() ) {

                    // TODO: send data to server
                    // tell this request was accepted (by ID?)
                    // send replier information
                    sendDataToServer();

                    Intent i = new Intent(Reply.this, MainListActivity.class);
                    startActivity(i);

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
        nickname = nicknameEditText.getText().toString();
        cellphone = cellphoneEditText.getText().toString();
    }

    private boolean checkIfFilled() {

        if(nickname.matches(""))
            showAlert(R.string.warning,R.string.no_nickname);
        else if(cellphone.matches(""))
            showAlert(R.string.warning,R.string.no_cellphone);
        else
            return true;
        return false;
    }

    private void showAlert(int titleId, int messageId){
        new AlertDialog.Builder(Reply.this)
                .setTitle(titleId)
                .setMessage(messageId)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    private boolean checkIfNotAccepted() {

        // TODO : check from server (maybe via all data)
        // if accepted
        //      showAlert(R.string.warning,R.string.accepted);
        //      return false;
        // else
        //      return true;

        // temp

        return true;
    }

    private void sendDataToServer() {

        // TODO

    }




}
