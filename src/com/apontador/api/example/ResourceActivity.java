package com.apontador.api.example;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ResourceActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);
        
        TextView oauthText = (TextView)findViewById(R.id.oauth_data_details);
        oauthText.setText("oauth details: ");
        
        TextView resourceText = (TextView)findViewById(R.id.resource_details);
        resourceText.setText("resource details: ");
        
    }

}
