package ant.ants;

import java.util.Observable;
import java.util.Observer;


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
    
    public void move()  {
    	this.x = this.x +1;
    	this.y = this.y -1;
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