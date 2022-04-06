package internproject1;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class ConDB_jsonmultiple {

	public static void main(String[] args) throws SQLException, StreamWriteException, DatabindException, IOException {
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ProjectOne","root","root");
		Statement stmt=con.createStatement();
		String s="Select * from `analyitics_houly_load_mobile_source`";
       ResultSet rs =stmt.executeQuery(s);
       ArrayList<HourlyLoad> ar=new ArrayList<HourlyLoad>();
       HourlyLoad hl=new HourlyLoad();
       
       while(rs.next())
       {
    	 String meterno=  rs.getString("meter_serial_no");
    	 String commdate=  rs.getString("comm_date");
    	 hl.setMeterno(meterno);
    	 hl.setCommdate(commdate);
  
    	 ar.add(hl);
       }
       JSONArray jsonr=new JSONArray();
      
       for(int i=0;i<ar.size();i++)
       {
        Gson g=new Gson();
       String jsonString= g.toJson(ar.get(i));
       jsonr.add(jsonString);
       
       }
       JSONObject jo=new JSONObject();
       jo.put("data", jsonr);
       //System.out.println(jo.toJSONString());
       String jsonformattedString=jo.toJSONString().replace("\\\"" , "\"");
       //System.out.println(jsonformattedString);
       String finalJson=jsonformattedString.replace("\"{","{").replace("}\"","}");
       System.out.print(finalJson);
       con.close();
       //System.out.println("Done!");
	}

}
