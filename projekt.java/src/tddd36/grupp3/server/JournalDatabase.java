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

import org.json.JSONException;
import org.json.JSONObject;


public class JournalDatabase {
	
	private static Connection con 	= null;
	private static ResultSet rs 	= null;
	private static Statement st 	= null;

	public static void connect(){
		String url = "jdbc:mysql://130.236.226.197:3306/journal_db";
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
		String journal = null;
		try{
			connect();
			System.out.println("conncet klar");
			st=con.createStatement();
			String query = "SELECT * from journals";
			rs = st.executeQuery(query);
			System.out.println("query klar");
			while(rs.next()){
				System.out.println("while");
				if(rs.getString(2).equals(Identifier)){
					System.out.println("inne");
					journal = rs.getString(3);
					System.out.println(rs.getString(3));
					JSONObject content = new JSONObject();
					String [] field = journal.split(":");
					try {
						content.put(field[0],field[1]);
						content.put(field[2],field[3]);
						content.put(field[4],field[5]);
						content.put(field[6],field[7]);
						content.put(field[8],field[9]);
						content.put(field[10],field[11]);
					} catch (JSONException e) {
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
	 * Kalla aldrig på denna metod. /Roger that.
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
			System.out.println("hej");
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
