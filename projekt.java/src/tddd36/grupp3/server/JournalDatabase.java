package tddd36.grupp3.server;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;


public class JournalDatabase {
	
	private static Connection con 	= null;
	private static ResultSet rs 	= null;
	private static Statement st 	= null;

	public static void connect(){
		String url = "jdbc:mysql://127.0.0.1:3306/journal_db";
		String user = "server";
		String password = "starwars";

		try {
			con = DriverManager.getConnection(url,user,password);
		}catch (SQLException e) {e.printStackTrace();}
	}
	
	public static void disconnect(){
		try {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(JournalDatabase.class.getName());
			lgr.log(Level.WARNING, ex.getMessage(), ex);
		}
	}
	
	public static JSONObject getJournal(String Identifier){
		Identifier = Identifier+".txt";
		String link = null;
		try{
			connect();
			st=con.createStatement();
			String query = "SELECT * from journals";
			rs = st.executeQuery(query);
			while(rs.next()){
				if(rs.getString(2).equals(Identifier)){
					link = rs.getString(3);
					JSONObject content = null;
					try {
						content = getContent(link);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					disconnect();
					return content;
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		disconnect();
		return null;
	}
	
	/**
	 * Kalla aldrig p� denna metod.
	 * @param link
	 * @return
	 * @throws IOException
	 */
	public static JSONObject getContent(String link) throws IOException{
		FileInputStream fstream 	= new FileInputStream(link);
		DataInputStream in 			= new DataInputStream(fstream);
		BufferedReader br 			= new BufferedReader(new InputStreamReader(in));
		JSONObject content			= new JSONObject();
		String strLine;
		
		while ((strLine = br.readLine()) != null)   {
			String [] field = strLine.split(":");
			try {
				content.put(field[0], field[1]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return content;
	}
	
}
