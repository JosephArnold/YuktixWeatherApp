package com.yuktix.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import com.yuktix.DataHolder;
import com.yuktix.StationObject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



public class YuktixAPI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
		  new YuktixAPI().sendPost("yuktix1");
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}
	
	public StationObject sendPost(String stationid) throws Exception {

		String url = "http://api1.yuktix.com:8080/sensordb/v1//module/device/archive/latest";
	//	String url = "http://api1.yuktix.com:8080/sensordb/v1//module/device";
	    URL obj = new URL(url);
	    HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
	    conn.setDoOutput(true);
	    conn.setRequestMethod("POST");
	    conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	    conn.setRequestProperty("Accept", "application/json");
	    conn.addRequestProperty("Authorization", "Signature=df00ee61-cd77-4d37-a938-a522cd9014cf");
	    long starttime=System.currentTimeMillis()-24*3600*1000;
		Calendar mydate = Calendar.getInstance();
		mydate.setTimeInMillis(starttime);
	    String request="{\"map\": {\"module\" : \"AWS\", \"serialNumber\" : \""+stationid+"\",\"interval\" : \"86400\",\"start\":\""+starttime+"\"}}";
	    OutputStream writer = conn.getOutputStream();
	    writer.write(request.getBytes());
	    writer.flush();
	    writer.close();
	    StringBuffer response = new StringBuffer();
	    int respCode = conn.getResponseCode();  // New items get NOT_FOUND on PUT
	    System.out.println(respCode);
	    if (respCode == HttpURLConnection.HTTP_OK || respCode == HttpURLConnection.HTTP_NOT_FOUND) {
	        System.err.println(respCode);;
	      
	      String line;

	      BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	      while ((line = reader.readLine()) != null) {
	        response.append(line);
	      }
	      reader.close();
	     
	    } 
	//	System.out.println(response);
		System.out.println(DataHolder.stationlist.get(stationid));
		StationObject station=parseJSON(response.toString(),stationid);
	     return station;

	}
	
	StationObject parseJSON(String response,String stationid){
		double currtemp=0;
		double max=0;
		double min=100;
		String timeofmax="";
		String timeofmin="";
		String rain="";
		String unixtimestamp="";
		String humidity="";
		StationObject station=new StationObject();
		JSONObject obj = JSONObject.fromObject(response);
		JSONArray result=obj.getJSONArray("result");
		station.temp=result.getJSONObject(0).getString("T");
		station.rh=result.getJSONObject(0).getString("RH");
		unixtimestamp=result.getJSONObject(0).getString("tsUnix");
		long time=Long.parseLong(unixtimestamp);
		Calendar mydate = Calendar.getInstance();
		mydate.setTimeInMillis(time);
		DataHolder.lastupdated=mydate.getTime().toString();
		for(int i=0;i<result.size();i++){
			JSONObject temporary=result.getJSONObject(i);
			String temp=temporary.getString("T");
			currtemp=Double.parseDouble(temp);
			humidity=temporary.getString("RH"); 
			unixtimestamp=temporary.getString("tsUnix");
			time=Long.parseLong(unixtimestamp);
			mydate.setTimeInMillis(time);
			String currenttime=mydate.getTime().toString();
			if(currtemp>max){
				max=currtemp;
				timeofmax=currenttime;
			}
			if(currtemp<min){
				min=currtemp;
				timeofmin=currenttime;
			}
		//	String winddir=temporary.getString("wdc");
			rain=temporary.getString("Rain");
		
			System.out.println(temp+" "+rain+" "+mydate.getTime());
			
         
		}
		
		System.out.println("Max temperature is "+max+" recorded at "+timeofmax);
		System.out.println("Min temperature is "+min+" recorded at "+timeofmin);
		station.name=DataHolder.stationlist.get(stationid);
		station.id=stationid;
		station.max=max;
		station.min=min;
		station.timeOfMax=timeofmax;
		station.timeOfMin=timeofmin;
		station.rainfall=rain;
		station.rh=humidity;
		return station;
		
	
	}
}
