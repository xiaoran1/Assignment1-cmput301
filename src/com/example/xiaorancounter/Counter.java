package com.example.xiaorancounter;



import java.util.ArrayList;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.xiaorancounter.NewCounterCreate;

//this basically is the activity page for the use to to modification to the counter
//the user can add the counter's count number or minus it or rest it to zero
//the user also allowed to modify the counter name
//each time the user make a modify to the counter, the counter's detailed information will be shown on the screen
//and all of the modification will be saved in the file that contains all the user's counters
//the user can go back by click the goback button

/**
 * @author  xiaoran1
 */
public class Counter extends NewCounterCreate
{

	int count = 0;
	public ArrayList<SAVECOUNTER> savelist;
	/**
	 * @uml.property  name="s"
	 * @uml.associationEnd  
	 */
	SAVECOUNTER s;
	TextView oldname;
	EditText namee;
	TextView text;
	/**
	 * @uml.property  name="c"
	 * @uml.associationEnd  
	 */
	CounterManager c ;
	public static String FILENAME = "file.sav"; 
//	private static final String FILENAME = "file.sav";
	public void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);

		c = new CounterManager(this);
		savelist = new ArrayList<SAVECOUNTER>();
		oldname = (TextView)findViewById(R.id.oldname);
		namee = (EditText)findViewById(R.id.countername);

		Bundle bundle = getIntent().getExtras();
		final String myname = bundle.getString("key");
		s = new SAVECOUNTER(myname,0,new Date());
	
		//set the sentence to show the current count number======================================
		text = (TextView)findViewById(R.id.textView2);
		text.setText("\nright now the count is " + count );
		//add the count by 1-----------------------------------------------		
		final Button add = (Button) findViewById(R.id.add);
		add.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
			  count ++;
			  text.setText("\nright now the count is " + count );
			  s.setCount_value(count);
			  s.setDate_value(new Date());
			  savelist = c.updateCounter(s,s.getName_value(),s.getCount_value());
			  savelist = c.loadCounter();
			  int d = getCountByName(s.getName_value());
			  String shown = s.getName_value()+"\n"+s.getDate_value()+":"+d;
			  oldname.setText(shown);	
			}
		});
		//minus the counter by 1-----------------------------------------------	
		final Button minus = (Button) findViewById(R.id.minus);
		minus.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
			  count --;
			  text.setText("\nright now the count is " + count );
			  s.setCount_value(count);
			  s.setDate_value(new Date());
			  savelist = c.updateCounter(s,s.getName_value(),s.getCount_value());
			  savelist = c.loadCounter();
			  int d = getCountByName(s.getName_value());
			  String shown = s.getName_value()+"\n"+s.getDate_value()+":"+d;
			  oldname.setText(shown);			
			}
		});
		//reset the counter to zero-----------------------------------------------
		final Button reset = (Button) findViewById(R.id.reset);
		reset.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
			  count = 0;
			  text.setText("\nright now the count is " + count );
			  s.setCount_value(count);
			  s.setDate_value(new Date());
			  savelist = c.updateCounter(s,s.getName_value(),s.getCount_value());
			  savelist = c.loadCounter();
			  int d = getCountByName(s.getName_value());
			  String shown = s.getName_value()+"\n"+s.getDate_value()+":"+d;
			  oldname.setText(shown);			  
			}
		});		

		//save the counter---------------------------------------------------
		final Button back = (Button) findViewById(R.id.back);
		back.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				Intent startcounter = new Intent(getApplicationContext(),NewCounterCreate.class);
				startActivity(startcounter);
			}
		});	
		//rename the counter---------------------------------------------------
		final Button rename = (Button) findViewById(R.id.rename);
		rename.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				final String newname = namee.getText().toString(); 
				savelist = c.updateCounter(s,newname,s.getCount_value());
				savelist = c.loadCounter();
				s.setName_value(newname);
				int d = getCountByName(s.getName_value());
				String shown = s.getName_value()+"\n"+s.getDate_value()+":"+d;
				oldname.setText(shown);
			}
		});	
		//=========================================end of button===========================
	}	
	
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		savelist = c.loadCounter();
		int previous = getCountByName(s.getName_value());
		s.setCount_value(previous);
		System.out.println(previous+"yayayayayayayayayayayaya");
		savelist = c.updateCounter(s,s.getName_value(),previous);
		savelist = c.loadCounter();
		System.out.println(s.getCount_value()+"ddddddddddd");
		String shown = s.getName_value()+"\n"+s.getDate_value()+":"+s.getCount_value();
		oldname.setText(shown);	
		count = s.getCount_value();
		text.setText("\nright now the count is " + count );
	}
	
	
//==================================================================================	
	public int getCountByName(String name)
	{
		for (SAVECOUNTER s : savelist)
		{
			if (s.getName_value().equals(name))
			{
				return s.getCount_value();
			}
		}
		return 0;
	}
//===================================================================================================================	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

