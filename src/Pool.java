import java.util.ArrayList;
import java.util.Random;

import glm.Vec2;

public class Pool implements Runnable{
	private ArrayList<Base> cells;
	private ArrayList<Base> cellClones;
	public Pool(int amount){
		cells = new ArrayList<Base>();
		cellClones = new ArrayList<Base>();
		Random random = new Random();
		for(int i=0;i<amount;i++){
			cells.add(new Base(new Vec2((random.nextFloat() * 2.0f - 1.0f) ,random.nextFloat() * 2.0f - 1.0f), 0.1f, 100));
		}
		for(int i=0;i<cells.size();i++){
			(new Thread(cells.get(i))).start();
		}
	}
	
	public void run() {
		int counter = 0;
		while(cells.size()!=0){
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			synchronized(this){
				counter = 0;
				cellClones.clear();
				for(int i=0;i<cells.size();i++){
					cellClones.add(cells.get(i).getClone());
				}
				
				for(int i=0;i<cellClones.size();i++){
					ArrayList<Message> messages = new ArrayList<Message>();
					for(int j=0;j<cellClones.size();j++){
						if(i!=j){
							
						}
					}
				}
				
				for(int i=0;i<cellClones.size();i++){
					if(!cellClones.get(i).alive){
						cellClones.remove(i);
						cells.remove(i);
						i--;
					}
				}
			}
		}
	}
	
	public ArrayList<Base> getCells(){
		ArrayList<Base> c = new ArrayList<Base>();
		synchronized(this){
			for(int i=0;i<cellClones.size();i++){
				c.add(cellClones.get(i));
			}
		}
		return c;
	}
}
