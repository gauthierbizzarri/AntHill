package ant.ants;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
public class Case {
	// Position of the case 
	int x ;
	int y ;
	int ressources ;
	boolean is_anthill = false;
	boolean is_worker = false;
	boolean is_officer = false;
	
	Case(int x, int y , int ressource){
        this.x = x;
        this.y = y;
        this.ressources = ressource;
        
    }

	public void  set_anthill () {
		this.is_anthill = true;
	}
	
	public void  set_worker () {
		this.is_worker = true;
	}
	public void set_officer() {
		this.is_officer = true;
	}


	public void draw(GraphicsContext gc)  {
		gc.setLineWidth(0.5);
		gc.setStroke(Color.BLACK);
		gc.strokeRect(this.x*30,this.y*30,30,30);
		if(this.is_anthill){
			gc.setLineWidth(7);
			gc.setStroke(Color.GREEN);
			gc.strokeOval(this.x*30 +30/24,this.y*30 + 30/24,10,10);
		}
		if(this.is_officer){
			gc.setLineWidth(7);
			gc.setStroke(Color.RED);
			gc.strokeOval(this.x*30 +30/24 +10,this.y*30 + 30/24 +10,5,5);
		}
		if(this.is_worker){
			gc.setLineWidth(7);
			gc.setStroke(Color.BLUE);
			gc.strokeOval(this.x*30 +30/24 ,this.y*30 + 30/24 +11,1,1);
		}
	}
	public void drawBackground (GraphicsContext gc)  {

	}



}
