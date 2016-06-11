package com.example.simon.help;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewRequest extends AppCompatActivity {

    private Button OKButton;
    private Button CancelButton;

    private EditText titleEditText;
    private EditText nicknameEditText;
    private EditText cellphoneEditText;
    private EditText contentEditText;

    String title;
    String nickname;
    String cellphone;
    String content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_request);

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

                    // TODO: get requestID from server
                    getIDFromServer();


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

    private void showAlert(int titleId, int messageId){
        new AlertDialog.Builder(NewRequest.this)
                .setTitle(titleId)
                .setMessage(messageId)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    private void sendDataToServer() {

        // TODO

    }

    private void getIDFromServer() {

        // TODO

        // temp
        int id = 12345;




        showRequestID(id, R.string.your_id);
    }

    private void showRequestID(int RequestID, int messageId){
        new AlertDialog.Builder(NewRequest.this)
                .setTitle(String.valueOf(RequestID))
                .setMessage(messageId)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = new Intent(NewRequest.this, MainListActivity.class);
                        startActivity(i);
                    }
                })
                .show();
    }
}
