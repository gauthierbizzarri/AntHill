package ant.ants;

import java.util.Observable;

public class OrderQueen extends Observable{

	public void orderqueen(String order) {
		
		if (order =="retreat")
		{
			System.out.println("Queen is asking to  ants to go back to the anthill");
            this.setChanged();
            this.notifyObservers("retreat");
		}
	}
			
}
