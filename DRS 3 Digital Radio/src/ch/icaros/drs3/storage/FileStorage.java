package ch.icaros.drs3.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class FileStorage {

	private static String FILENAME = "songlist.txt";
	
	public void SaveToFile(String line)
	{
		File file = new File(Environment.getExternalStorageDirectory() + File.separator + FILENAME);
		
		if(!file.exists())
		{
			try {
				file.createNewFile();
			} catch (Exception e) {
				Log.e("Test", e.getMessage(), e);
			}	
		}
		
		
		//write the bytes in file
		if(file.exists())
		{
		     try {
		    	 String text = this.ReadFile();
		    	 text = text +"\n"+line;
				OutputStream fo = new FileOutputStream(file);
				 OutputStreamWriter osw = new OutputStreamWriter(fo);
				 osw.write(text);
				 osw.flush();
				    osw.close();
			} catch (Exception e) {
				Log.e("Test", e.getMessage(), e);
			} 
		    
		}               
	}
	
	public String ReadFile() 
	{
		
		String str = "";
		try {
			File file = new File(Environment.getExternalStorageDirectory() + File.separator + FILENAME);
			StringBuffer buf = new StringBuffer();			
			InputStream is = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			if (is!=null) {							
				while ((str = reader.readLine()) != null) {	
					buf.append(str + "\n" );
				}				
			}		
			is.close();
			str = buf.toString();
		}  catch (Exception e) {
			Log.e("Test", e.getMessage(), e);
		} 
		
		
		return str;
	}
	
	public void DeleteFile()
	{
		File file = new File(Environment.getExternalStorageDirectory() + File.separator + FILENAME);
		if(!file.exists())
		{
			try {
				file.delete();
			} catch (Exception e) {
				Log.e("Test", e.getMessage(), e);
			}	
		}
		
	}
	
}
