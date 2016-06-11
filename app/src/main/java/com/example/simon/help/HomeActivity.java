package com.example.simon.help;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    private Button SeeRequestButton;
    private Button AddRequestButton;
    private Button SearchRequestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getViews();
        processControllers();
    }

    private void getViews() {

        SeeRequestButton = (Button)findViewById(R.id.see_request);
        AddRequestButton = (Button)findViewById(R.id.add_request);
        SearchRequestButton = (Button)findViewById(R.id.search_request);
    }

    private void processControllers() {

        //add SeeRequest button listener
        View.OnClickListener SeeRequestListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(HomeActivity.this,MainListActivity.class);
                startActivity(i);
            }
        };
        SeeRequestButton.setOnClickListener(SeeRequestListener);

        //add AddRequest button listener
        View.OnClickListener AddRequestListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(HomeActivity.this,NewRequest.class);
                startActivity(i);
            }
        };
        AddRequestButton.setOnClickListener(AddRequestListener);

        //add SearchRequest button listener
        View.OnClickListener SearchRequestListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(HomeActivity.this,Search.class);
                startActivity(i);
            }
        };
        SearchRequestButton.setOnClickListener(SearchRequestListener);
    }
}