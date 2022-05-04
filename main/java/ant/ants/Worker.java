package ant.ants;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Worker extends Ant implements Observer {

	// The worker knows to which anthill she belongs to
	protected Anthill queen;

	// The worker knows to which officer she belongs to
	protected Officer officer;


	// The resources' var represents the amount of gathered resources ( it will be reset to 0 when the worker will drop it in the anthill)

	private int resources;

	// Constructor 
	 public Worker(Anthill queen , int id)
	    {
	        this.queen = queen;
	        this.x = this.queen.x;
	        this.y = this.queen.y;
	        this.id = id;
			this.resources = 0;

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
		 	// Update the ant position
	    	this.x = this.x + random_x_dir;
			this.y = this.y +random_y_dir;
		 // If x or y overflow a border of the map . The ant will be on the opposite position (It works as a spherical

		 // Upper bound is 25-1 = 24
		 // If the ant overflow the right corner of the grid it will be replaced to the 1st , on the left border
		 if (this.x>24){
			 this.x = 0;
		 }
		 // Upper bound is 21-1 = 20
		 // If the ant overflow the bottom corner of the grid it will be replaced to the 1st , on the top border
		 if (this.y>19)
		 {
			 this.y = 0;

		 }
		 if (this.x<0)
		 {
			 this.x = 24;
		 }
		 if (this.y<0)
		 {
			 this.y = 19;
		 }


			// Update worker position on the map
	    	Case tile_worker;
			tile_worker = this.queen.map.get_tile_with_coord(this.x,this.y);
			tile_worker.set_worker();


			// Check if there are resources on this tile .
		 if(tile_worker.ressources>0)
		 {
			 // A worker can gather a maximum of 5 resources
			 if (tile_worker.ressources<=5){
				 tile_worker.ressources=0;
				 this.resources =tile_worker.ressources;
			 }
			 if (tile_worker.ressources>5)
			 {
				 tile_worker.ressources = tile_worker.ressources-5;
				 this.resources = 5;
			 }
		 }
	    }
	 
	@Override
	public void update(Observable arg0, Object arg1) {
		
	}
	
	   public void run(){
		   //System.out.println("Worker called"+"\n");

		   //If the worker has no resources it moves using move() method (ie the ant will move randomly(-1,0,+1)

		   this.move();

		   // If the worker has gathered resources , it will go back to the anthill to drop the resources.

	   }
}
