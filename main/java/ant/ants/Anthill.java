package ant.ants;
import java.util.ArrayList; 


public class Anthill extends Thread {
	int id;
	int x;
	int y;
	String color;

	public Map map;
	protected ArrayList<Worker> workers;
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


		// Create an officer
		/*
		Officer officer = new Officer(this, 1);
		officers.add(officer);

		Case tile_officer;
		tile_officer = this.map.get_tile_with_coord(this.x, this.y);
		tile_officer.set_officer();

		 */


		// Create 2 workers 

		workers = new ArrayList<Worker>();

		for (int i = 0; i < 1; i++) {

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
		while (true) {
			try {
				System.out.println(
						"Anthill" + this.id + " pos:" + this.x + "," + this.y + "\n"

				);
				Thread.sleep(50);

				for (Officer officer : officers) {
					System.out.println("Fourmiliere " + officer.queen.id + " Officer" + officer.id + " pos:" + officer.x + "," + officer.y);
					if ((officer.thread == null) || (!officer.thread.isAlive())) {
						officer.thread = new Thread(officer);
						officer.thread.start();

						Thread.sleep(300);
					}
				}

				for (Worker worker : workers) {
					System.out.println("Fourmiliere " + worker.queen.id + " worker" + worker.id + " pos:" + worker.x + "," + worker.y);
					if ((worker.thread == null) || (!worker.thread.isAlive())) {
						worker.thread = new Thread(worker);
						worker.thread.start();

						Thread.sleep(1500);
					}
				}


			} catch (InterruptedException exc) {
				exc.printStackTrace();
			}

		}


	}
}
