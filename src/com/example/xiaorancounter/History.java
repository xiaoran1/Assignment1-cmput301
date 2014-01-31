package com.example.xiaorancounter;

//This is aimed for show the history counter that the user have created,
//and the user is allow to view the histories by hour,day,month or week
//the user also allowed to remove the specific counter and open the counter and edit it
//if the user want to quit,can click the go back button to go back to the main page
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


/**
 * @author  xiaoran1
 */
public class History extends MainActivity
{
	ListView oldcountsList;
	public ArrayList<SAVECOUNTER> savelist ;
	public static String FILENAME = "file.sav"; 
	TextView welcome;
	/**
	 * @uml.property  name="c"
	 * @uml.associationEnd  
	 */
	CounterManager c ;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.historysave);
		c = new CounterManager(this);
		welcome = (TextView)findViewById(R.id.welcome);
		savelist = new ArrayList<SAVECOUNTER>();
		welcome.setText("My history count list");
		oldcountsList = (ListView) findViewById(R.id.countList);
		//back to the home page=======================================	
		final EditText searchname = (EditText)findViewById(R.id.thename);
		final Button rename = (Button) findViewById(R.id.hback);
		rename.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				Intent startcounter = new Intent(getApplicationContext(),MainActivity.class);
				startActivity(startcounter);
			}
		});	
		//show the counter information with the name that the user typed in=======================================			
		final Button open = (Button) findViewById(R.id.open);
		open.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				int find = 0;
				final String openname = searchname.getText().toString();
				//check if the name exits,if it is  open else show a text view to announce the user================
				if(savelist.size()>0){
					for (SAVECOUNTER s : savelist) {
						if(s.getName_value().equals(openname)){
							find = 1;
						}
					}
				}
				if(find == 0){searchname.setText("the name is not exist");}else
				//change to the counter page
				{Intent startcounter = new Intent(getApplicationContext(),Counter.class);
				startcounter.putExtra("key", openname);
				setResult(RESULT_OK, startcounter); 
				startActivity(startcounter);
				}
			}
		});	
		//remove the counter form the file=======================================		
		final Button remove = (Button) findViewById(R.id.remove);
		remove.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//remove the counter form the file
				int exist = 0;
				final String rmcounter = searchname.getText().toString();
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
				if(savelist.size()>0){
					for (SAVECOUNTER s : savelist) {
						if(!s.getName_value().equals(rmcounter)){
							c.storeCounter(s);
						}else{exist = 1;}
					}
				}
				if(exist == 0){searchname.setText("the name is not exist");}
				savelist.clear();
				savelist = c.loadCounter();
				ShowTheList();
			}
		});		
		//show the counter information within today=======================================			
		final Button day = (Button) findViewById(R.id.day);
		day.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				savelist = datelist(savelist,1);
				ShowTheList();
			}
		});	
		//show the counter information within this month=======================================			
		final Button month = (Button) findViewById(R.id.month);
		month.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				savelist = datelist(savelist,2);
				ShowTheList();
			}
		});	
		//show the counter information within the recent hour=======================================			
		final Button hour = (Button) findViewById(R.id.hour);
		hour.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				savelist = datelist(savelist,3);
				ShowTheList();
			}
		});	
		//show the counter information within the recent week=======================================		
		final Button weekn = (Button) findViewById(R.id.week);
		weekn.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				savelist = datelist(savelist,4);
				ShowTheList();
			}
		});	
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		//show all the old counter names line by line on the screen=======================================	
		savelist = c.loadCounter();
		ShowTheList();
	}
	//show by dates
	@SuppressWarnings("deprecation")
	protected ArrayList<SAVECOUNTER> datelist(ArrayList<SAVECOUNTER> mylist,int datetype) {
		// TODO Auto-generated method stub
		ArrayList<SAVECOUNTER> today = new ArrayList<SAVECOUNTER>();
		ArrayList<SAVECOUNTER> month = new ArrayList<SAVECOUNTER>();
		ArrayList<SAVECOUNTER> hour = new ArrayList<SAVECOUNTER>();
		ArrayList<SAVECOUNTER> week = new ArrayList<SAVECOUNTER>();
		Date rightnow = new Date();
		long currentweek = rightnow.getTime();
		long checkweek = 0;
		for(SAVECOUNTER s : mylist){
			if(s.getDate_value().getYear() == rightnow.getYear()){
				if(s.getDate_value().getMonth() == rightnow.getMonth()){
					month.add(s);
					checkweek = s.getDate_value().getTime();
					if((checkweek - currentweek)<=604800){
						week.add(s);
						if(s.getDate_value().getDay() == rightnow.getDay()){
							today.add(s);
							if(s.getDate_value().getHours() == rightnow.getHours()){
								hour.add(s);
							}
						}
					}
				}
			}
		}
		if(datetype == 1){return today;}
		if(datetype == 2){return month;}
		if(datetype == 3){return hour;}
		if(datetype == 4){return week;}
		return null;
	}
	//show the history counters on the screen======================================		
	public void ShowTheList()
	{
		ArrayList<String> cnames = new ArrayList<String>();
		savelist = sortlist(savelist);
		if(savelist.size()>0){
			for (SAVECOUNTER s : savelist) {
				cnames.add("NAME: "+s.getName_value()+"\n"+"COUNT: "+s.getCount_value()+"\n"+"DATE: "+s.getDate_value());
			}
		}else{
			welcome.setText("My history count list is empty");
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.roll, cnames);
		oldcountsList.setAdapter(adapter);
		savelist = c.loadCounter();
	}
	//sort the history counters by their count numbers======================================	
	public ArrayList<SAVECOUNTER> sortlist(ArrayList<SAVECOUNTER> sorted)
	{
		ArrayList<SAVECOUNTER> a = new ArrayList<SAVECOUNTER>();
		int i= 0;
		int smallid;
		int smallest;
		while(i<sorted.size()){
			int j= 0;
			smallid = 0;
			smallest = sorted.get(0).getCount_value();
			while(j<sorted.size()){
				if(smallest <= sorted.get(j).getCount_value()){
					smallid = j;
					smallest = sorted.get(j).getCount_value();
				}
				j++;
			}
			a.add(sorted.get(smallid));
			sorted.remove(smallid);
		}
		return a;
	}
//==========================================save and load(counter manager)============================	
	
	public SAVECOUNTER Getcounter(String name)
	{
		for (SAVECOUNTER s : savelist)
		{
			if (s.getName_value().equals(name))
			{
				return s;
			}
		}
		return null;
	}
	
}
