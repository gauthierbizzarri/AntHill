package ant.ants;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


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