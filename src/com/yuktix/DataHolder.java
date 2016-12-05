package com.yuktix;

import java.util.ArrayList;
import java.util.HashMap;

public class DataHolder {

//	public static StringBuilder display=new StringBuilder();
	public static HashMap<String,String> stationlist=new HashMap<String,String>();
	public static ArrayList<StationObject> stationdata=new ArrayList<StationObject>();
	public static String lastupdated="";
	
	static {
		stationlist.put("yuktix1","HSR Layout") ;
	//	stationlist.put("ainvvy0","Girinagar") ;
		stationlist.put("gkvk001", "GKVK") ;
		stationlist.put("iisc001", "IISC") ;
		  
		stationlist.put("paws002","Jayanagar") ;
		stationlist.put("rainbow","Sarjapura") ;
		stationlist.put("ramkris","Indira Nagar") ;
		stationlist.put("sandesh", "Uttarahalli") ;
		//  stations.put("thejesh", "Electronic City") ;
		stationlist.put("zenrain", "Vidyarayanapura") ;
		  
		stationlist.put("iihs001", "Sadashivnagar") ;
		stationlist.put("atree01", "Jakkur") ;
		//  stations.put("itc0001", "HUNSUR") ;
		  
	//	 DataHolder.stations.put("itc0002","Kothegala Mysore") ;
		stationlist.put("iihs002", "Kengeri") ;
		stationlist.put("wipro01","Wipro Electronic City") ;
	}
}
