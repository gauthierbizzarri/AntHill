package ant.ants;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;


public class Officer  extends Ant  implements Observer{
    
    
    protected Anthill queen;
    
    public Officer(Anthill queen, int id)
    {
    	// This officer belongs to a queen(anthill)
        this.queen = queen;
        // THis officer is set at queen position
        this.x = this.queen.x;
        this.y = this.queen.y;
        // Officer id 
        this.id = id;

		this.thread = new Thread(this);
        // 
       
        
    }

    public void move() {


        // Remove old worker position from the map
        Case tile_old_officer;
        tile_old_officer = this.queen.map.get_tile_with_coord(this.x,this.y);
        tile_old_officer.unset_officer();
        // Choosing a random value in Array
        Integer[] directions = {-1, 0,1,};
        int random_x_dir = new Random().nextInt(directions.length);
        int random_y_dir = new Random().nextInt(directions.length);
        this.x = this.x +random_x_dir;
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
        Case tile_officer;
        tile_officer = this.queen.map.get_tile_with_coord(this.x,this.y);
        tile_officer.set_officer();
    }
      
    public void run(){
        try {
            System.out.println(
                "Officer" + this.id+" de anthill "+this.queen.id+" called"+"\n"
                );
            this.move();
                Thread.sleep(50);  
            
        } catch (InterruptedException exc) {
            exc.printStackTrace();
        }
    }


	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
    
}