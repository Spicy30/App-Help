package com.example.simon.help;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class Search extends AppCompatActivity {

    private TextView TitleTextView;
    private TextView NicknameRequesterTextView;
    private TextView CellphoneRequesterTextView;
    private TextView ContentTextView;
    private TextView NicknameReplierTextView;
    private TextView CellphoneReplierTextView;

    String title;
    String nickname_requester;
    String cellphone_requester;
    String content;
    String nickname_replier;
    String cellphone_replier;

    // temp
    String id;

    private EditText IDEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getViews();
    }

    // menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate option menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                search();
                return true;
            case R.id.action_home:
                backToHome();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void search() {

        getRequestID();

        if(checkIfFilled()){
            getDataFromServer();
            showRequest();
        }


    }

    private void backToHome() {

        Intent i = new Intent(Search.this,HomeActivity.class);
        startActivity(i);
    }


    private void getRequestID(){

        id =IDEditText.getText().toString();
    }

    private boolean checkIfFilled() {

        if(id.matches(""))
            showAlert(R.string.warning,R.string.no_id);
        else
            return true;
        return false;
    }

    private void showAlert(int titleId, int messageId){
        new AlertDialog.Builder(Search.this)
                .setTitle(titleId)
                .setMessage(messageId)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    private void getViews() {

        TitleTextView = (TextView)findViewById(R.id.title_content);
        NicknameRequesterTextView = (TextView)findViewById(R.id.nickname_requester);
        CellphoneRequesterTextView = (TextView)findViewById(R.id.cellphone_requester);
        ContentTextView = (TextView)findViewById(R.id.content_content);

        NicknameReplierTextView = (TextView)findViewById(R.id.nickname_replier);
        CellphoneReplierTextView = (TextView)findViewById(R.id.cellphone_replier);

        IDEditText = (EditText)findViewById(R.id.search_id);

    }


    private void getDataFromServer() {

        // TODO

    }

    private void showRequest() {


        TitleTextView.setText(title);
        NicknameRequesterTextView.setText(nickname_requester);
        CellphoneRequesterTextView.setText(cellphone_requester);
        ContentTextView.setText(content);

        NicknameReplierTextView.setText(nickname_replier);
        CellphoneReplierTextView.setText(cellphone_replier);

        // temp
        NicknameReplierTextView.setText("hello");
        CellphoneReplierTextView.setText(id);
    }
}
