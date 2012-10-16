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
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class FileStorage {

	static String FILENAME = "songlist.txt";

	public boolean SaveToFile(String line) {
		File file = new File(getPath());

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				Log.e("Test", e.getMessage(), e);
			}
		}

		// write the bytes in file
		if (file.exists()) {
			try {
				String text = this.ReadFile();
				
				String songTitle = this.extractSongTitle(line);
				if(text.contains(songTitle))
				{
					return false;
				}
				
				text = text + "\n" + line;
				text = text.replaceAll("(?m)^[ \t]*\r?\n", "");
				OutputStream fo = new FileOutputStream(file);
				OutputStreamWriter osw = new OutputStreamWriter(fo);
				osw.write(text);
				osw.flush();
				osw.close();
				return true;
			} catch (Exception e) {
				Log.e("Test", e.getMessage(), e);
			}
		}
		
		return false;
	}

	private String extractSongTitle(String line) {
		String returnVal = line;
		if (line.contains(";")) {
			returnVal = line.substring(0, line.lastIndexOf(";"));
		}
		
		return returnVal;
	}

	public String ReadFile() {

		String str = "";
		try {
			File file = new File(getPath());
			StringBuffer buf = new StringBuffer();
			InputStream is = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			if (is != null) {
				while ((str = reader.readLine()) != null) {
					buf.append(str + "\n");
				}
			}
			is.close();
			str = buf.toString();
		} catch (Exception e) {
			Log.e("Test", e.getMessage(), e);
		}

		return str;
	}

	public LinkedList<String> ReadFileLines() {
		LinkedList<String> songs = new LinkedList<String>();

		try {
			File file = new File(getPath());
			InputStream is = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			if (is != null) {
				String str = "";
				while ((str = reader.readLine()) != null) {
					if (str == null || str.trim().equals("")) {
						continue;
					}

					songs.add(this.extractSongTitle(str));

				}
			}
			is.close();
		} catch (Exception e) {
			Log.e("Test", e.getMessage(), e);
		}

		return songs;
	}

	public void DeleteFile() {
		File file = new File(getPath());
		if (file.exists()) {
			try {
				file.delete();
			} catch (Exception e) {
				Log.e("Test", e.getMessage(), e);
			}
		}

	}

	public static String getPath() {
		return Environment.getExternalStorageDirectory()+ File.separator + FILENAME;
	}

}
