package tddd36.grupp3.server;

public enum RequestType {
	ALL_UNITS(){
		public String toString(){
			return "ALL_UNITS";
		}
	},
	
	ACKNOWLEDGE(){
		public String toString(){
			return "ACKNOWLEDGE";
		}
	},
	
	MAP_OBJECTS(){
		public String toString(){
			return "MAP_OBJECTS";
		}
	},
}