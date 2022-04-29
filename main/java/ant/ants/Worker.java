package ant.ants;

import java.util.Observable;
import java.util.Observer;

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
	    	this.x = this.x +1;
			System.out.println(this.queen.map);
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
	                Thread.sleep(150);  
	            
	        } catch (InterruptedException exc) {
	            exc.printStackTrace();
	        }
	    }
}
