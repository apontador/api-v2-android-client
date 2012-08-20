package com.apontador.api.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

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
			
			EditText username = (EditText) findViewById(R.id.editTextUsername);
			EditText password = (EditText) findViewById(R.id.editTextPassword);
			
			Intent i = new Intent(this, ResourceActivity.class);
			i.putExtra("grantType", "password");
			i.putExtra("username", username.getText().toString());
			i.putExtra("password", password.getText().toString());
			startActivity(i);
			break;			
		}
	}

}
