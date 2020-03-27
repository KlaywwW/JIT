package com.starvincci.JIT.util;

import java.util.HashMap;
import java.util.Map;

import com.sun.org.apache.bcel.internal.generic.SWITCH;

public class SwitchLanguageUtils {//简繁切换
private static final String jianti="带夹制";
private static final String fanti="帶夾製";

public static  String findMes(String mes) {
	Map<Character, Character> map=new HashMap<Character, Character>();
	for(int i=0;i<jianti.length();i++){
		map.put(fanti.charAt(i),jianti.charAt(i));
	}

	
	String jiantiMes="";
	for(int i=0;i<mes.length();i++) {
		char tempChar=mes.charAt(i);
		Character character=map.get(tempChar);
		char jiantichar;
		if(character==null) {
			jiantichar=tempChar;
		}else {
			jiantichar=character;
		}
		jiantiMes=jiantiMes+String.valueOf(jiantichar);
	}
	return jiantiMes;
}
	
public static  String JianToFan(String mes) {
	Map<Character, Character> map=new HashMap<Character, Character>();
	for(int i=0;i<jianti.length();i++){
		map.put(jianti.charAt(i),fanti.charAt(i));
	}
	String fMes="";
	for(int i=0;i<mes.length();i++) {
		char tempChar=mes.charAt(i);
		Character character=map.get(tempChar);
		char fchar;
		if(character==null) {
			fchar=tempChar;
		}else {
			fchar=character;
		}
		fMes=fMes+String.valueOf(fchar);
	}
	return fMes;
}

   public static String changeGuige(String Guige) {
	  return null;
   }


}
