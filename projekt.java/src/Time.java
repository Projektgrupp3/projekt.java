import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Time {
	private String time;
	
	public Time(){
		this.time = time();
	}
	
	public Time(String time){
		this.time = time;
	}

	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

	public static String time() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		String now = (String)sdf.format(cal.getTime());
		return now; 

	}

	public String getTime() {
		return time;
	}

	public static void main(String arg[]) {
		System.out.println("Now : " + Time.time());
	}
}
