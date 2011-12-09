package misc;

import java.util.Observable;

import tddd36.grupp3.server.CommandThread;

public class EventTimer extends Observable implements Runnable {

		private int countdown = 10000;
		private boolean isRunning;
		private CommandThread ct;
		
		public EventTimer(CommandThread ct){
			this.ct = ct;
			this.addObserver(this.ct);
		}
		
		public void run() {
			startRunning();
			try {

				while(countdown>0 && isRunning){
					setChanged();
					Thread.sleep(1000);
					countdown -= 1000;
					notifyObservers(""+(countdown/1000));
				}
				stopRunning();
				this.deleteObserver(ct);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void stopRunning(){
			isRunning = false;
		}
		public void startRunning(){
			isRunning = true;
		}

	}
