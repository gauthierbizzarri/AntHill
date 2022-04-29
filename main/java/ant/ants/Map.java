package ant.ants;
import javafx.scene.canvas.GraphicsContext;

import java.util.Random;

import java.util.ArrayList;



public class Map {
	
	
	
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
                Case case_created = new Case(i, j,10);
                this.tiles.add(case_created);
            }
        }
    }
	public void add_anthill() {
		anthills = new ArrayList <Anthill>();
		String[] colors = {"Red","Blue","Green"};
		// Create 3 anthill on a random position , set it in a Case and in the Anthill list
	for (int x = 0;x<3;x++)
	{
		// Setting a random position of the anthill
		Random rand = new Random(); //instance of random class
	      int upperbound = 50*10;
	        //generate random value from 0-50*50
	      int random_position = rand.nextInt(upperbound);


	      // Setting 1 anthill on the Map
	      Case random_case =  tiles.get(random_position);
	      random_case.set_anthill();

	      // Creating a Anthill and setting up to the case position
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


public synchronized  void draw()
{
    int i = 0;
    System.out.println("Map \n");
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


public void draw_graphic(GraphicsContext gc){
    for (Case tile : this.tiles) {
        tile.drawBackground(gc);
    }
    for (Case tile : this.tiles) {
        tile.draw(gc);
    }
}

}
