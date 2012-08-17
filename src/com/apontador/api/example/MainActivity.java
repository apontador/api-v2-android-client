package com.apontador.api.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
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
		switch (v.getId()) {
		case R.id.button_passwordflow:
			
			break;
		case R.id.button_clientcredentialsflow:
			
			break;			
		}
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

}
