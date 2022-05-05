package ant.ants;
import java.util.ArrayList; 


public class Anthill extends Thread {
	int id;
	int x;
	int y;
	String color;

	int resources;

	// Content is a list of all content in the tile , due to the fact that a tile may contain more than 1 ant
	public ArrayList <String> content;

	public Map map;
	// List of all workers on the map
	protected ArrayList<Worker> workers;
	// List of all Officers on the map
	protected ArrayList<Officer> officers;

	protected Thread thread;
	private OrderQueen order;

	// public ArrayList <OrderQueen> queenorders;

	Anthill(int id, int x, int y, Map map, String color) {


		// order = new OrderQueen();
		this.id = id;
		this.x = x;
		this.y = y;
		this.map = map;
		this.color = color;
		// Create List of All officers and  
		officers = new ArrayList<Officer>();


		// Create 3 officers

		officers = new ArrayList<Officer>();

		for (int i = 0; i < 0; i++) {

			Officer officer = new Officer(this, i);
			officers.add(officer);

			Case tile_officer;
			tile_officer = this.map.get_tile_with_coord(this.x, this.y);
			tile_officer.set_officer();
		}






		// Create 5 workers

		workers = new ArrayList<Worker>();

		for (int i = 0; i < 0; i++) {

			Worker worker = new Worker(this, i);
			workers.add(worker);
			Case tile_worker;
			tile_worker = this.map.get_tile_with_coord(this.x, this.y);
			tile_worker.set_worker();
		}


		this.thread = new Thread(this);
		this.thread.start();

	}

	public void run() {
		// While true to not stop drawing
		while (true) {

			System.out.println(
					"Anthill" + this.id + "is called "+"\n"

			);
			for (Officer officer : officers) {
				if ((officer.thread == null) || (!officer.thread.isAlive())) {
					officer.thread = new Thread(officer);
					officer.thread.start();

					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}

				}
			}
			for (Worker worker : workers) {
				if ((worker.thread == null) || (!worker.thread.isAlive())) {
					worker.thread = new Thread(worker);
					worker.thread.start();

					try {
						Thread.sleep(1200);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
			}


		}


	}
}
