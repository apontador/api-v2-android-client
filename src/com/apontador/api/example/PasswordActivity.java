package com.apontador.api.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class PasswordActivity extends Activity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        
        View submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);
        
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.submitButton:
			Intent i = new Intent(this, ResourceActivity.class);
			startActivity(i);
			break;			
		}
	}

}
