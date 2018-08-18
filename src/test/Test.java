package test;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Properties;

import util.MD5;

public class Test {
	
	@org.junit.Test
	public void prop(){
		Properties ps=System.getProperties();
		Enumeration<?> enumeration=ps.propertyNames();;
		while(enumeration.hasMoreElements()){
			String item=(String)enumeration.nextElement();
			System.err.println(item+"------->"+System.getProperty(item));
		}
	}
	
	@org.junit.Test
	public void md5(){
		String md5=MD5.encrypt("hello");
		System.out.println(md5.length());
		System.out.println(md5);
		
	}
}
