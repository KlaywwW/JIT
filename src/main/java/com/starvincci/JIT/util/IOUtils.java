package com.starvincci.JIT.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class IOUtils {//将数据暂时保存在文件中--没有配置双数据源前

	
	
	public static String  ReadLink(){
		//路径
		         File file = new File("E:\\wip\\link.txt");
		         if(!file.exists() || file.length() == 0) {  
		        	    System.out.println("文件为空！");  
		        	    return null;
		        	}  else{
			        	BufferedReader bf;
						try {
							bf = new BufferedReader(new FileReader(file));
							String content = "";
							StringBuilder sb = new StringBuilder();
							while(content != null){
							    content = bf.readLine();
							    if(content == null){
							       break;
							   }
							     sb.append(content.trim());
							 }
							bf.close();
						
							return sb.toString();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							
							return e.toString();
						}
						
						
			        }
				
				}
	
	
	public static void  WriteLink(String link)throws IOException {
		   File file = new File("E:\\wip\\link.txt");
		   if(!file.exists()){
		       file.createNewFile();
		      }
		    FileWriter fileWriter =new FileWriter(file);
	        fileWriter.write(link.toString());
	        fileWriter.flush();
	        fileWriter.close();
	}
}
