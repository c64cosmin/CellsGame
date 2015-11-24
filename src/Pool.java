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
		for(int i=0;i<1;i++){
			cells.add(new Food(new Vec2((random.nextFloat() * 2.0f - 1.0f) ,random.nextFloat() * 2.0f - 1.0f)));
		}
		for(int i=0;i<1;i++){
			cells.add(new Cell(new Vec2((random.nextFloat() * 2.0f - 1.0f) ,random.nextFloat() * 2.0f - 1.0f)));
		}
		for(int i=0;i<cells.size();i++){
			(new Thread(cells.get(i))).start();
		}
	}
	
	public void run() {
		int counter=0;
		while(cells.size()!=0){
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized(this){
				cellClones.clear();
				for(int i=0;i<cells.size();i++){
					cellClones.add(cells.get(i).getClone());
				}
				
				for(int i=0;i<cellClones.size();i++){
					ArrayList<Message> messages = new ArrayList<Message>();
					for(int j=0;j<cellClones.size();j++){
						if(i!=j){
							if(Base.distance(cellClones.get(i),cellClones.get(j))<3.0){
								messages.add(new Message(cells.get(j).getClone()));
							}
						}
					}
					cells.get(i).inputMessage(messages);
				}
				
				for(int i=0;i<cellClones.size();i++)
				if(cellClones.get(i).outputMessage!=null){
					Message msg = cellClones.get(i).outputMessage;
					if(msg.messageType == Message.type.EAT){
						int closest = findClosest(msg.cell);
						if(closest!=-1)
						if(cellClones.get(i).alive&&
						   cellClones.get(closest).alive&&
						   cellClones.get(i).collide(cellClones.get(closest))){
							cellClones.get(closest).alive=false;
							cells.get(i).health+=cellClones.get(closest).health;
						}
					}
					if(msg.messageType == Message.type.SPLIT){
						if(cellClones.get(i).alive&&
						   cellClones.get(i).health>80){
							cells.get(i).alive=false;
							cellClones.get(i).alive=false;
							addNewCell(new Cell(cellClones.get(i).position));
							addNewCell(new Cell(cellClones.get(i).position));
						}
					}
				}
				counter++;
				if(counter==100){//new Random().nextInt(120)==0){
					counter=0;
					addNewCell(new Food(new Vec2((new Random().nextFloat() * 2.0f - 1.0f) ,new Random().nextFloat() * 2.0f - 1.0f)));
				}
				
				for(int i=0;i<cellClones.size();i++){
					if(!cellClones.get(i).alive){
						if(cellClones.get(i).cellType==Base.CellType.CELL){
							addNewCell(new Food(cellClones.get(i).position));
						}
						cellClones.remove(i);
						cells.remove(i);
						i--;
					}
				}
			}
		}
	}

	private void addNewCell(Base cell) {
		if(cells.size()<Main.maxN){
			cells.add(cell);
			new Thread(cell).start();
		}
	}

	private int findClosest(Base cell) {
		Base result = null;
		float minDist = 100;
		int n = -1;
		for(int i=0;i<cellClones.size();i++){
			float dist = (float) Base.distance(cellClones.get(i), cell);
			if(minDist>dist){
				result = cellClones.get(i);
				minDist = dist;
				n=i;
			}
		}
		if(Base.distance(cell, result)>0.01)
			n = -1;
		return n;
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
