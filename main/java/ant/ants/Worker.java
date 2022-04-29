package ant.ants;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Worker extends Ant implements Observer {
	protected Anthill queen;
	private boolean find_ressource ;
	
	// Constructor 
	 public Worker(Anthill queen , int id)
	    {
	        this.queen = queen;
	        this.x = this.queen.x;
	        this.y = this.queen.y;
	        this.id = id;

	        this.thread = new Thread();
	    }
	 
	 public void move() {


		 // Remove old worker position from the map
		 Case tile_old_worker;
		 tile_old_worker = this.queen.map.get_tile_with_coord(this.x,this.y);
		 tile_old_worker.unset_worker();
		 // Choosing a random value
		 int random_x_dir = ThreadLocalRandom.current().nextInt(-1, 1 + 1);
		 int random_y_dir = ThreadLocalRandom.current().nextInt(-1, 1 + 1);
		 System.out.println("SELECTED "+random_x_dir+","+random_y_dir);
		 	// Update the ant position
	    	this.x = this.x + random_x_dir;
			this.y = this.y +random_y_dir;
		 // If x or y overflow a border of the map . The ant will be on the opposite position (It works as a spherical

		 if (this.x>25){
			 this.x = 1;
		 }
		 if (this.y>20)
		 {
			 this.y = 1;

		 }
		 if (this.x<0)
		 {
			 this.x = 25;
		 }
		 if (this.y<0)
		 {
			 this.y = 20;
		 }


			// Update worker position on the map
	    	Case tile_worker;
			tile_worker = this.queen.map.get_tile_with_coord(this.x,this.y);
			tile_worker.set_worker();
	    }
	 
	@Override
	public void update(Observable arg0, Object arg1) {
		
	}
	
	   public void run(){
	        try {
	            System.out.println(
	            		"Worker" + this.id+" de anthill "+this.queen.id+" called"+"\n"
	                );
	            this.move();
	                Thread.sleep(500);
	            
	        } catch (InterruptedException exc) {
	            exc.printStackTrace();
	        }
	    }
}
