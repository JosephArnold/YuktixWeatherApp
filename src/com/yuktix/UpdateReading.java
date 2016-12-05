package com.yuktix;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yuktix.api.YuktixAPI;

@SuppressWarnings("serial")
public class UpdateReading extends HttpServlet {
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			YuktixAPI api_call=new YuktixAPI();
			DataHolder.stationdata=new ArrayList<StationObject>();
			for(String stationid:DataHolder.stationlist.keySet()){
				System.err.print("inside for loop");
				StationObject station=api_call.sendPost(stationid); 
				DataHolder.stationdata.add(station);
			}
		}
			catch(Exception e){
				e.printStackTrace();
			} 
	}

}
