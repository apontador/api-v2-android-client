package com.apontador.api.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        View passwordButton = findViewById(R.id.button_passwordflow);
        passwordButton.setOnClickListener(this);
        
        View clientCredentialsButton = findViewById(R.id.button_clientcredentialsflow);
        clientCredentialsButton.setOnClickListener(this);
        
    }
    
	@Override
	public void onClick(View v) {
		Intent i = null;
		switch (v.getId()) {
		case R.id.button_passwordflow:
			i = new Intent(this, PasswordActivity.class);
			startActivity(i);
			break;
		case R.id.button_clientcredentialsflow:
			i = new Intent(this, ResourceActivity.class);
			i.putExtra("grantType", "client_credentials");
			startActivity(i);
			break;			
		}
	}

}
