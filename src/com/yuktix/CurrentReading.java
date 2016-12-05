package com.yuktix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class HourlyReading
 */

public class CurrentReading extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String temperature="";
	private String rh="";
	private String rain="";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CurrentReading() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public static void main(String args[]){
    	CurrentReading update=new CurrentReading();
    	try {
    //	  update.sendPost("yuktix1");
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
	  for(int i=0;i<DataHolder.stationdata.size();i++) {
		  getCurrentConditions(DataHolder.stationdata.get(i).id); 
		  DataHolder.stationdata.get(i).temp=temperature;
		  DataHolder.stationdata.get(i).rh=rh;
		  DataHolder.stationdata.get(i).rainfall=rain;
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	
	public void getCurrentConditions(String stationid) throws Exception {

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
	    String request="{\"map\": {\"module\" : \"AWS\", \"serialNumber\" : \""+stationid+"\",\"interval\" : \"900\",\"start\":\""+starttime+"\"}}";
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
		parseJSON(response.toString(),stationid);
	}
	
	void parseJSON(String response,String stationid){
		JSONObject obj = JSONObject.fromObject(response);
		JSONArray result=obj.getJSONArray("result");
		JSONObject temporary=result.getJSONObject(0);
		temperature=temporary.getString("T");
		rh=temporary.getString("RH");  
		long time=Long.parseLong(temporary.getString("tsUnix"));
		Calendar mydate = Calendar.getInstance();
		mydate.setTimeInMillis(time);
		DataHolder.lastupdated=mydate.getTime().toString();
		rain=temporary.getString("Rain");
		System.out.println(temperature+" "+rain+" "+mydate.getTime());
				
	}

}
