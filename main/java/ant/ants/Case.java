package ant.ants;
import javafx.scene.canvas.GraphicsContext;
import java.io.FileInputStream;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

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
	public void  unset_worker () {
		this.is_worker = false;
	}
	public void set_officer() {
		this.is_officer = true;
	}
	public void unset_officer() {
		this.is_officer = false;
	}


	public void draw(GraphicsContext gc) {



		//gc.strokeOval(this.x*30,this.y*30 + 30/4,5,5);

		// Draw a grid
		gc.setLineWidth(0.5);
		gc.setStroke(Color.BLACK);
		gc.strokeRect(this.x * 30, this.y * 30, 30, 30);
		// Draw grass Tile
		FileInputStream gras_tile_file = null;
		try {
			gras_tile_file = new FileInputStream("/home/bizzarri/eclipse-workspace/ants/src/main/resources/ant/ants/grass_alt1.png");
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		Image grass_tile = new Image(gras_tile_file);
		gc.drawImage(grass_tile, this.x * 30, this.y * 30, 30, 30);

		// Draw the amount of resources in the tile
		//gc.fillText(String.valueOf(this.ressources),this.x*30,this.y*30);

		gc.setStroke(Color.YELLOW);

		gc.setLineWidth(2);
		gc.strokeOval(this.x*30 +30/4 ,this.y*30 + 30/4,4*this.ressources/10,4*this.ressources/10);


		if (this.is_anthill) {
				FileInputStream anthill_tile_file = null;
				try {
					anthill_tile_file = new FileInputStream("/home/bizzarri/eclipse-workspace/ants/src/main/resources/ant/ants/red_anthill.png");
				} catch (FileNotFoundException e) {
					throw new RuntimeException(e);
				}
				Image anthill_tile = new Image(anthill_tile_file);
				gc.drawImage(anthill_tile, this.x * 30, this.y * 30, 30, 30);

			}
			if (this.is_officer) {
				//gc.setLineWidth(7);
				//gc.setStroke(Color.RED);
				//gc.strokeOval(this.x*30 +30/24 +10,this.y*30 + 30/24 +10,5,5);
				// Draw worker image


				// Draw Officer image
				FileInputStream officer_tile_file = null;
				try {
					officer_tile_file = new FileInputStream("/home/bizzarri/eclipse-workspace/ants/src/main/resources/ant/ants/officer.png");
				} catch (FileNotFoundException e) {
					throw new RuntimeException(e);
				}
				Image officer_tile = new Image(officer_tile_file);
				gc.drawImage(officer_tile, this.x * 30, this.y * 30, 20, 20);
			}
			if (this.is_worker) {
				// Draw a blue circle to represents worker
			/*
			gc.setLineWidth(7);
			gc.setStroke(Color.BLUE);
			gc.strokeOval(this.x*30 +30/2 ,this.y*30 + 30/2 ,1,1);

			 */

				// Draw worker image
				FileInputStream ant_tile_file = null;
				try {
					ant_tile_file = new FileInputStream("/home/bizzarri/eclipse-workspace/ants/src/main/resources/ant/ants/ant_blue.png");
				} catch (FileNotFoundException e) {
					throw new RuntimeException(e);
				}
				Image ant_tile = new Image(ant_tile_file);
				gc.drawImage(ant_tile, this.x * 30, this.y * 30, 20, 20);
			}
		}

	public void drawBackground (GraphicsContext gc)  {





	}



}
