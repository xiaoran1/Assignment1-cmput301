package com.example.xiaorancounter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Context;

import com.example.xiaorancounter.SAVECOUNTER;
import com.google.gson.Gson;

//this class is used to manage the counter for the users which include the constructor 
//the save and load counter from or to file functions and user the counter name to get the
//Specific counter object from the list that contains all of the counters that use has saved

public class CounterManager
{
	
	private static final String FILENAME = "file.sav";
	static Context context;
	private ArrayList<SAVECOUNTER> savelist; 
	public CounterManager(Context newcontext) {
		savelist = new ArrayList<SAVECOUNTER>();
		context = newcontext;
	}
	//==================================load and save================================================================		
	public ArrayList<SAVECOUNTER> updateCounter(SAVECOUNTER s, String name, int countt)
		{
			int check = 0;
			int ifnewcounter = 1;
			try
			{
				//empty the file first===================
				FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
				fos.write((new String()).getBytes());
				fos.close();
				//find out the item you want to modify and modify that=============
				if(savelist.size()>0){
					for (SAVECOUNTER ss : savelist)
					{
						check = 0;
						if (ss.getName_value().equals(s.getName_value())){
							// update			
							ss.setName_value(name);
							ss.setCount_value(countt);
							ss.setDate_value(s.getDate_value());
							check = 1;
							ifnewcounter = 0;
							storeCounter(ss);
						}
						if(check == 0){
							storeCounter(ss);
						}
					}
					if(ifnewcounter==1){
						storeCounter(s);
						savelist.add(s);
					}
				}else{
					savelist.add(s);
					storeCounter(s);
				}
			}	
			catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return savelist;
	}	
		
	public void storeCounter(SAVECOUNTER s)
	{
				Gson gson = new Gson();
				System.out.println(s.getName_value()+"  store"+s.getCount_value());
				String mycounter = gson.toJson(s);
				try {
					FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_APPEND);
					fos.write(mycounter.getBytes());
					fos.write("\n".getBytes());
					fos.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}	 
	
	public ArrayList<SAVECOUNTER> loadCounter()
	{
			String s = "";
			Gson gson = new Gson();
			SAVECOUNTER realcount;	
			savelist.clear();
			try {
				FileInputStream fis  = context.openFileInput(FILENAME);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				s = br.readLine();		
				while (s != null) {
					realcount = gson.fromJson(s, SAVECOUNTER.class);
					savelist.add(realcount);
					s = br.readLine();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return savelist;
	}
		
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
	//==================================load and save================================================================		
}
