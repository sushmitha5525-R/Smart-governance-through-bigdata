package com;
import java.io.IOException;
import java.util.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;
import java.math.BigInteger;
public class MapReduce extends MapReduceBase implements Mapper<LongWritable,Text,Text,Text> {
	IntWritable one = new IntWritable(1);
    Text locText = new Text();
	Text empty = new Text();
public void map(LongWritable key,Text value,OutputCollector<Text,Text> output,Reporter reporter) throws IOException {
	String line = value.toString();
	String arr[] = line.split(",");
	if(arr.length > 10) {
		String type = arr[BigData.type].trim();
		if(BigData.graph.containsKey(type)) {
			int count = BigData.graph.get(type);
			count = count + 1;
			BigData.graph.put(type,count);
		} else {
			BigData.graph.put(type,1);
		}
		if(type.equals(BigData.value)){
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<arr[8].length();i++){
				sb.append("*");
			}
			arr[8] = sb.toString();
			sb.delete(0,sb.length());
			for(int i=0;i<arr.length;i++) {
				sb.append(arr[i]+",");
			}
			sb.deleteCharAt(sb.length()-1);
			BigData.mapbuffer.append(sb.toString()+"\n");
		}
	}
}
}