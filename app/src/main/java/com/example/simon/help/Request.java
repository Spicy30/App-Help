package com.example.simon.help;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Request extends AppCompatActivity {

    private Button AcceptButton;
    private Button BackButton;

    private TextView TitleTextView;
    private TextView NicknameTextView;
    private TextView CellphoneTextView;
    private TextView ContentTextView;

    String title;
    String nickname;
    String cellphone;
    String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        getViews();
        getRequest();
        processControllers();
    }

    private void getViews() {
        AcceptButton = (Button)findViewById(R.id.accept);
        BackButton = (Button)findViewById(R.id.back);

        TitleTextView = (TextView)findViewById(R.id.title_content);
        NicknameTextView = (TextView)findViewById(R.id.nickname_content);
        CellphoneTextView = (TextView)findViewById(R.id.cellphone_content);
        ContentTextView = (TextView)findViewById(R.id.content_content);
    }

    private void getRequest() {

        // TODO get data from server via ID from extras

        // get ID
        // Intent intent = getIntent();
        // Bundle extras = intent.getExtras();

        // get data via ID
        getDataFromServer();







        TitleTextView.setText(title);
        NicknameTextView.setText(nickname);
        CellphoneTextView.setText(cellphone);
        ContentTextView.setText(content);

    }

    private void getDataFromServer() {

        // TODO

    }

    private void processControllers() {

        //add Accept button listener
        View.OnClickListener AcceptListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // checkIfNotAccepted() : to showAlert if accepted
                if (checkIfNotAccepted()) {

                    Intent i = new Intent(Request.this,Reply.class);
                    startActivity(i);
                }


            }
        };
        AcceptButton.setOnClickListener(AcceptListener);

        //add Back Button listener
        View.OnClickListener BackListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        };
        BackButton.setOnClickListener(BackListener);
    }

    private boolean checkIfNotAccepted() {

        // TODO : check from server (maybe from ID)
        // if accepted
        //      showAlert(R.string.warning,R.string.accepted);
        //      return false;
        // else
        //      return true;

        // temp
        return true;
    }

    private void showAlert(int titleId, int messageId){
        new AlertDialog.Builder(Request.this)
                .setTitle(titleId)
                .setMessage(messageId)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
}
