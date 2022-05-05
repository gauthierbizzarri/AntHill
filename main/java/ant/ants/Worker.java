package ant.ants;

import java.util.Observable;
import java.util.Observer;
import java.lang.Math;

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
			this.color = this.queen.color;
	    }
	 
	 public void move() {


		 // Remove old worker position from the map
		 Case tile_old_worker;
		 tile_old_worker = this.queen.map.get_tile_with_coord(this.x,this.y);
		 tile_old_worker.unset_worker();
		 tile_old_worker.set_color("");
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
			// Setting the color
		 	tile_worker.set_color(this.color);


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


		public void go_back_home(){
		 // Drawing a direct line (Pythagore) to the home
			int x_home = this.queen.x ;
			int y_home = this.queen.y;
			int delta_x = x_home - this.x ;
			int delta_y = y_home - this.y;
			double delta_x_sqr = delta_x*delta_x;
			double delta_y_sqr = delta_y*delta_y;
			double sqrt_delta = delta_x_sqr-delta_y_sqr;
			double length = Math.pow(sqrt_delta,1/2);

		// Remove old worker position from the map
		Case tile_old_worker;
		tile_old_worker = this.queen.map.get_tile_with_coord(this.x,this.y);
		tile_old_worker.unset_worker();
		tile_old_worker.set_color("");


		this.x = (int) (this.x + delta_x/length);
		this.y = (int) (this.y + delta_y/length);

		// Update worker position on the map
		Case tile_worker;
		tile_worker = this.queen.map.get_tile_with_coord(this.x,this.y);
		tile_worker.set_worker();
			tile_old_worker.set_color(this.color);
		}
	@Override
	public void update(Observable arg0, Object arg1) {
		
	}

	   public void run(){
		   System.out.println("Worker MOVE"+this.resources);
		   //System.out.println("Worker called"+"\n");

		   // If the worker has gathered resources , and the worker is at his anthill , drop the resources.


		   if (this.x == this.queen.x && this.y ==this.queen.y && this.resources>0)
		   {
			   this.queen.resources = this.queen.resources+ this.resources;
			   this.resources = 0;
			   return;
		   }


		   //If the worker has not a full inventory (<5 resources it moves using move() method (ie the ant will move x,y randomly(-1,0,+1)
		   if (this.resources<5) {
			   System.out.println("Worker MOVE"+"\n");
			   this.move();
			   return;
		   }

		   // If the worker has 5 or more resources it will go back home by taking a direct path using the
		   if (this.resources>=5) {

			   System.out.println("Worker HOME"+"\n");
			   this.go_back_home();
			   return;
		   }








	   }
}
