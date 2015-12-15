import java.util.ArrayList;
import java.util.Random;

import glm.Vec2;

public class Pool implements Runnable{
	private ArrayList<Base> cells;
	private static Pool instance = null;
	public synchronized static Pool get(){
		if(Pool.instance == null){
			Pool.instance = new Pool();
			(new Thread(Pool.instance)).start();
		}
		return Pool.instance;
	}
	private Pool(){
		cells = new ArrayList<Base>();
		Random random = new Random();
		for(int i=0;i<10;i++){
			addNewCell(new Food(new Vec2((random.nextFloat() * 2.0f - 1.0f) ,random.nextFloat() * 2.0f - 1.0f)));
		}
		for(int i=0;i<10;i++){
			//addNewCell(new Cell(new Vec2((random.nextFloat() * 2.0f - 1.0f) ,random.nextFloat() * 2.0f - 1.0f)));
		}
		for(int i=0;i<10;i++){
			if(random.nextBoolean()){
				addNewCell(new Male(new Vec2((random.nextFloat() * 2.0f - 1.0f) ,random.nextFloat() * 2.0f - 1.0f)));
			}
			else{
				addNewCell(new Female(new Vec2((random.nextFloat() * 2.0f - 1.0f) ,random.nextFloat() * 2.0f - 1.0f)));
			}
		}
	}
	
	public void run() {
		while(cells.size()!=0){
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Random random = new Random();
			if(random.nextInt(60)==0)addNewCell(new Food(new Vec2((random.nextFloat() * 2.0f - 1.0f) ,random.nextFloat() * 2.0f - 1.0f)));
			synchronized(this.cells){
				for(int i=0;i<cells.size();i++){
					if(!cells.get(i).isAlive()){
						cells.remove(i);
						i--;
					}
				}
			}
		}
	}

	public synchronized void addNewCell(Base cell) {
		synchronized(this.cells){
			if(cells.size()<Main.maxN){
				cells.add(cell);
				new Thread(cell).start();
			}
		}
	}

	public synchronized ArrayList<Base> getCells(){
		synchronized(this.cells){
			ArrayList<Base> ret = new ArrayList<Base>();
			for(int i=0;i<cells.size();i++){
				ret.add(cells.get(i));
			}
			return ret;
		}
	}
}
