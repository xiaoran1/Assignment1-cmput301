package com.example.xiaorancounter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//this is created for the user to create a new counter by give a name to it
//if the user type in the same name that appears in the history counter then the system will automatically recognize 
//the user want to update the previous counter
//if the use want to b=go back he can click the go back button
public class NewCounterCreate extends MainActivity
{ 
	//public String key = "";
	public static String COUNTER_NAME = "";
	public void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.newcounterlayout);
		
		final TextView text = (TextView)findViewById(R.id.entername);
		text.setText("\nPlease type your counter name");
		
		final EditText countername = (EditText)findViewById(R.id.countername);
		final Button start = (Button) findViewById(R.id.createnewb);
		start.setOnClickListener(new View.OnClickListener(){	
		public void onClick(View v) {
			
			final String namevalue = countername.getText().toString(); 

			//change to the counter page
			Intent startcounter = new Intent(getApplicationContext(),Counter.class);
			startcounter.putExtra("key", namevalue);
			setResult(RESULT_OK, startcounter); 
			startActivity(startcounter);
		 }
		});

		final Button goback = (Button) findViewById(R.id.gobackb);
		goback.setOnClickListener(new View.OnClickListener(){			
		public void onClick(View v) {
			Intent gback = new Intent();
			gback.setClass(NewCounterCreate.this, MainActivity.class);
			NewCounterCreate.this.startActivity(gback);
		 }
	    });
	}			
}