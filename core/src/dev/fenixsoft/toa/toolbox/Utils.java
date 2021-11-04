package dev.fenixsoft.toa.toolbox;

import dev.fenixsoft.toa.physics.ResultPair;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class Utils {
	
	public static String loadFileAsString(String path){
		StringBuilder builder = new StringBuilder();
		
		try{
			
			FileInputStream br = new FileInputStream(path);
			ObjectInputStream ou = new ObjectInputStream(br);
			
			while(ou.available() > 0)
				builder.append(ou.readInt() + "_");

			
			ou.close();
		}catch(EOFException e){
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return builder.toString();
	}
	
	public static int parseInt(String number){
		try{
			return Integer.parseInt(number);
		}catch(NumberFormatException e){
			e.printStackTrace();
			return 0;
		}
	}

	public static void sort(List<ResultPair> list){
		ResultPair temp = null;

		for (int i = 0; i < list.size(); i++)
		{
			for (int j = 0; j < list.size(); j++)
			{
				float a = list.get(i).contactTime;

				float b = list.get(j).contactTime;

				if (a < b)
				{
					temp = list.get(i);

					list.set(i, list.get(j));

					list.set(j, temp);
				}
			}
		}

	}

}
