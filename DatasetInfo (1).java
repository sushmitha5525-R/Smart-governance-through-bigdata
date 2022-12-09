package com;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
public class DatasetInfo{
	static String columns;
	static int size;
	static ArrayList<String> workclass = new ArrayList<String>();
	static ArrayList<String> education = new ArrayList<String>();
	static ArrayList<String> occupation = new ArrayList<String>();
public static void getInfo(String path){
	try{
		size = 0;
		workclass.clear();
		education.clear();
		occupation.clear();
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = br.readLine().trim();
		columns = line;
		while((line = br.readLine())!=null){
			line = line.trim();
			if(line.length() > 0) {
				size = size + 1;
				String arr[] = line.split(",");
				String wc = arr[1].trim();
				String edu = arr[3].trim();
				String occ = arr[6].trim();
				if(!workclass.contains(wc))
					workclass.add(wc);
				if(!education.contains(edu))
					education.add(edu);
				if(!occupation.contains(occ))
					occupation.add(occ);
			}
		}
		br.close();
	}catch(Exception e){
		e.printStackTrace();
	}
}
}