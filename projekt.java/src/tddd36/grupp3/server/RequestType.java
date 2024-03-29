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
	EVENT(){
		public String toString(){
			return "EVENT";
		}
	},
	ALL_CONTACTS(){
		public String toString(){
			return "ALL_CONTACTS";
		}
	},
	CONTACT(){
		public String toString(){
			return "CONTACT";
		}
	},
	LOG_OUT(){
		public String toString(){
			return "LOG_OUT";
		}
	},

	UPDATE_EVENT(){
		public String toString(){
			return "UPDATE_EVENT";
		}
	},
	JOURNAL(){
		public String toString(){
			return "JOURNAL";
		}
	},
}
