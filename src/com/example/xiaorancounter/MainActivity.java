package com.example.xiaorancounter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//Main activity provide the general selection for the user include create new counter and view the history counters
//user can go to both activities by click the responding buttons 
//I use the intent to help the activity jump to other activities


public class MainActivity extends Activity
{
	public static String FILENAME = "file.sav"; 
	
	public void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final TextView text = (TextView)findViewById(R.id.textView1);
		text.setText("\nwelcome to counter tool");
		final Button start = (Button) findViewById(R.id.create);
		start.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				Intent startcounter = new Intent(getApplicationContext(),NewCounterCreate.class);
				startActivity(startcounter);
			}
		});	
		//take a look at all the history counters============================================ 
		final Button hstory = (Button) findViewById(R.id.history);
		hstory.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				Intent startcounter = new Intent(getApplicationContext(),History.class);
				startActivity(startcounter);
			}
		});	
		//clear all of the history counters============================================ 		
		final Button clearall = (Button) findViewById(R.id.empty);
		clearall.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				try
				{
					FileOutputStream fos = openFileOutput(FILENAME,
							Context.MODE_PRIVATE);
					fos.write((new String()).getBytes());
					fos.close();
				} catch (FileNotFoundException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});	
		//==============================================================================
	}
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
