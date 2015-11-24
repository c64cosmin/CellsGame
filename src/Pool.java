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
			cells.add(new Base(new Vec2((random.nextFloat() * 2.0f - 1.0f) ,random.nextFloat() * 2.0f - 1.0f), 0.1f, 50));
		}
		for(int i=0;i<cells.size();i++){
			(new Thread(cells.get(i))).start();
		}
	}
	
	public void run() {
		int refreshClonesCounter = 0;
		while(cellClones.size()!=0){
			refreshClonesCounter++;
			if(refreshClonesCounter == 300){
				refreshClonesCounter = 0;
				cellClones.clear();
				for(int i=0;i<cells.size();i++){
					synchronized(cells.get(i)){
						cellClones.add(cells.get(i).myClone);
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
			for(int i=0;i<cells.size();i++){
				synchronized(cells.get(i)){
					c.add(cells.get(i).myClone);
				}
			}
		}
		return c;
	}
}
