package ant.ants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

import java.util.ArrayList;



// Singleton class
public class Map {
    private static class LoadMap {
        static final Map INSTANCE = new Map();
    }
    protected Map() {}

    public static Map getInstance() {
        return LoadMap.INSTANCE;
    }
	
	
	
	// Map 
	public ArrayList <Case> tiles;
	public ArrayList <Anthill> anthills;

	
	// Create Map 

	public void create_map()
    {
        //Create the Map ,  an array of Case object
        tiles = new ArrayList<Case>();

        int i = 0 ;
        int j = 0 ;

        for ( i = 0; i < 25 ;i++)
        {
            for ( j =0; j < 20; j++)
            {
                // Setting a random position of the anthill
                Random rand = new Random(); //instance of random class
                // Resources go from 0 to 50
                int upperbound = 50;
                //generate random value
                int random_resources = rand.nextInt(upperbound);
                Case case_created = new Case(i, j,random_resources);
                this.tiles.add(case_created);
            }

        }
    }
	public void add_anthill() {
		anthills = new ArrayList <Anthill>();
		String[] colors = {"Red","Green","Blue"};
		// Create 3 anthill on a random position , set it in a Case and in the Anthill list
	for (int x = 0;x<3;x++)
	{
		// Setting a random position of the anthill
		Random rand = new Random(); //instance of random class
	      int upperbound = 50*10;
	        //generate random postition
	      int random_position = rand.nextInt(upperbound);

          // Random index in order to generate random color
        //int rnd_color = new Random().nextInt(colors.length);


	      // Setting  anthill randomly on the Map
            Case random_case =  tiles.get(random_position);
            random_case.set_anthill();
            random_case.set_color(colors[x]);

	      // Creating a Anthill and setting up to the case position
        System.out.println(colors[x]);
	      Anthill anthill = new Anthill(x,random_case.x,random_case.y,this,colors[x]);
	      // Adding it to the Anthill list of the map
	      this.anthills.add(anthill);
	}
	}
	public Case get_tile_with_coord(int x , int y) {
		Case tile = this.tiles.get(0);
		for (Case tile_to_get : this.tiles) {
			if (tile_to_get.x ==x){

			if (tile_to_get.y ==y) {
				return tile_to_get;
								}
									}
		}
		return tile;
		}


        // Method to draw in console
public synchronized  void draw()
{
    int i = 0;
    System.out.println("\n");    
    for(Case c :this.tiles)
    {
    	String content = null;
        if ( i == 50)
        {
            System.out.print("*\n");
            i = 0;
        }
        
        if ( i == 0)
             System.out.print("*");
        
        // Print ants on tile 
        if(c.is_worker == true)
        {
            System.out.print(" W ");
            i++;
        }
        if(c.is_officer == true)
        {
            System.out.print(" O ");
            i++;
        }
        if(c.is_anthill == true)
        {
            System.out.print(" F ");
            i++;
        }
        
        
        
        else
        {
            System.out.print(" - ");
            i++;
        }
    }
    System.out.print("*\n");
    
    
}


public void draw_graphic(GraphicsContext gc) throws FileNotFoundException {
    // Draw the content in the tile

    for (Case tile : this.tiles) {
        tile.draw(gc);
    }
    // Draw the Score interface

    gc.fillText("Score",25*32,20);

    // For each anthill we will display a picture of it (with his color)
    // We will print the score(the amount of gathered resources & the content of the hill (number of Workers & Officers)

    int separator = 0;
    for (Anthill anthill : this.anthills) {
        FileInputStream anthill_tile_file = null;
        try {
            anthill_tile_file = new FileInputStream("/home/bizzarri/eclipse-workspace/ants/src/main/resources/ant/ants/red_anthill.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Image anthill_tile = new Image(anthill_tile_file);
        gc.drawImage(anthill_tile, 25 * 30, 50+separator, 30, 30);
        gc.fillText(String.valueOf(anthill.resources),25 * 30 + 40,50+separator + 15);

        
        if(anthill.color =="Red"){gc.setStroke(Color.RED);}
        if(anthill.color =="Green"){gc.setStroke(Color.GREEN);}
        if(anthill.color =="Blue"){gc.setStroke(Color.BLUE);}


        gc.setLineWidth(7);
        gc.strokeOval(25 * 30+12  ,50+separator+10,4,4);
        separator= separator+200;
    }
}

}
