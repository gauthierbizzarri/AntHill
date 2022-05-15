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
import java.util.concurrent.ThreadLocalRandom;

public class Case {
	int x ;
	int y ;
	ArrayList<Resource> resources;
	boolean is_anthill = false;
	boolean is_worker = false;
	boolean is_officer = false;
	String color= "";
	final Object lock = new Object();

	// IMAGES
	Image grass_tile;

	Case(int x, int y){
        this.x = x;
        this.y = y;
		resources = new ArrayList<>();
		for (int k = 0; k < ThreadLocalRandom.current().nextInt(0, 50); k++) {
			Resource resource = new Resource(ResourceType.FOOD);
			resources.add(resource);
		}
		this.color = "";


		// LOAD GRASS FILE AND SET IT UP
		FileInputStream gras_tile_file = null;
		try {
			gras_tile_file = new FileInputStream("src/main/resources/ant/ants/grass_alt1.png");
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		Image grass_tile = new Image(gras_tile_file);

		this.grass_tile= grass_tile;
    }

	public void  set_anthill ( ) {
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

	public void set_color(String color){
		if (this.color!="" && this.is_anthill==true){
			return;
		}
		this.color = color;}

	public void draw(GraphicsContext gc) {

		///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////            DRAW MAIN FUNCTION //////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////      1/ WE DRAW THE GRASS TILE ON EACH TILE ////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////       2/  WE DRAW THE AMOUNT OF RESOURCES ON THE TILE /////////
		//////////////////////////////////////////             AS A YELLOW CIRCLE OF VARIABLE RADIUS ////////////
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////        3/ WE DRAW THE ANT/OFFICER/ANTHILL ///////////////////
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////        4/ WE DRAW A COLORED DOT TO REPRESENTS THE COLOR //
		//////////////////////////////////////////////////////////////////////////////////////////////////

		// DRAW GRID
		gc.setLineWidth(0.5);
		gc.setStroke(Color.BLACK);
		gc.strokeRect(this.x * 30, this.y * 30, 30, 30);
		// Draw grass Tile

		gc.drawImage(this.grass_tile, this.x * 30, this.y * 30, 30, 30);

		// Draw the amount of resources in the tile as a yellow circle of variable radius : radius(resources) = 4/10 * resources

		gc.setStroke(Color.YELLOW);

		gc.setLineWidth(2);

		gc.fillText(String.valueOf(getResources().size()),this.x*30+30/2,this.y*30+30/2);
		//gc.strokeOval(this.x*30 +30/4 ,this.y*30 + 30/4,4*getResources().size()/5,4*getResources().size()/5);
		gc.setStroke(Color.TRANSPARENT);


		// Drawing the anthill


			if (this.is_officer) {
				//gc.setLineWidth(7);
				//gc.setStroke(Color.RED);
				//gc.strokeOval(this.x*30 +30/24 +10,this.y*30 + 30/24 +10,5,5);
				// Draw worker image


				// Draw Officer image
				FileInputStream officer_tile_file = null;
				try {
					officer_tile_file = new FileInputStream("src/main/resources/ant/ants/officer.png");
				} catch (FileNotFoundException e) {
					throw new RuntimeException(e);
				}
				Image officer_tile = new Image(officer_tile_file);
				gc.drawImage(officer_tile, this.x * 30, this.y * 30, 25, 25);
				if(this.color.equals("Red")){gc.setStroke(Color.RED);}
				if(this.color.equals("Green")){gc.setStroke(Color.GREEN);}
				if(this.color.equals("Blue")){gc.setStroke(Color.BLUE);}


				gc.setLineWidth(7);
				gc.strokeOval(this.x*30 +30/4 ,this.y*30 + 30/4,4,4);
			}

			// DRAWING IMAGE OF THE ANTHILL

		if (this.is_anthill) {
			FileInputStream anthill_tile_file = null;
			try {
				anthill_tile_file = new FileInputStream("src/main/resources/ant/ants/red_anthill.png");
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
			Image anthill_tile = new Image(anthill_tile_file);
			gc.drawImage(anthill_tile, this.x * 30, this.y * 30, 30, 30);


			// DRAWING COLOR OF THE ANTHILL AS A DOT CENTERED ON IT
			if(this.color.equals("Red")){gc.setStroke(Color.RED);}
			if(this.color.equals("Green")){gc.setStroke(Color.GREEN);}
			if(this.color.equals("Blue")){gc.setStroke(Color.BLUE);}
			gc.setLineWidth(7);

			gc.strokeOval(this.x*30 +30/4 ,this.y*30 + 30/4,4,4);
		}

		////////////////////////////////
		//////// DRAWING WORKER IMAGE /////////
		///////////////////////////////////////

			if (this.is_worker) {

				// Draw worker image
				FileInputStream ant_tile_file = null;
				try {
					ant_tile_file = new FileInputStream("src/main/resources/ant/ants/ant_blue.png");
				} catch (FileNotFoundException e) {
					throw new RuntimeException(e);
				}
				Image ant_tile = new Image(ant_tile_file);
				gc.drawImage(ant_tile, this.x * 30, this.y * 30, 15, 15);
				if(this.color.equals("Red")){gc.setStroke(Color.RED);}
				if(this.color.equals("Green")){gc.setStroke(Color.GREEN);}
				if(this.color.equals("Blue")){gc.setStroke(Color.BLUE);}


				gc.setLineWidth(5);
				gc.strokeOval(this.x*30 +30/4 ,this.y*30 + 30/4,1,1);



			}

		}



	public ArrayList<Resource> getResources() {
		return resources;
	}

	// SET RESOURCES
	public void setResources(ArrayList<Resource> resources) {
		this.resources = resources;
	}

	// GET 1ST ELEMENT OF THE LIST OF THE RESOURCES LIST IE 1ST RESOURCE
	public Resource getFirstResource(){
		synchronized(lock){
			if(!resources.isEmpty())
				return resources.get(0);
			else
				return null;
		}
	}

	// REMOVE RESSOURCES
	public   void  removeResource(Resource myResource) {
		synchronized(lock){
			if(!resources.isEmpty())
				resources.remove(myResource);
		}
	}



}
