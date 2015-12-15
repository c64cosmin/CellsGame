import java.util.Random;

import glm.Vec2;

public class Egg extends Base implements Runnable {
	protected static double yum = 15;
	private double hatch;

	public Egg(Vec2 p) {
		Random rnd = new Random();
		position = new Vec2(p.v[0], p.v[1]);
		this.angle = (float) (rnd.nextFloat()*Math.PI*2.0);
		this.health = 15;
		alive = true;
		this.r = 1.0;
		this.g = 0.2;
		this.b = 0.2;
		this.a = 1.0;
		this.radius=0.0f;
		cellType = CellType.CELL;
		this.hatch = 1;
		this.speed = 0.001;
	}
	
	public void update(){
		Random rnd = new Random();
		float radiusTarget = 0.1f - ((100.0f-this.health)/100.0f)*0.08f; 
		this.radius += (radiusTarget-radius)*0.01;
		position.v[0] += rnd.nextDouble()*speed-speed/2;
		position.v[1] += rnd.nextDouble()*speed-speed/2;
		this.hatch -= 0.001;
		this.a = 0.2 + (1.0-hatch)* 0.8;
		if(this.hatch<=0.0){
			this.alive=false;
			if(rnd.nextInt(100)>40){
				Pool.get().addNewCell(new Male(new Vec2(this.position.v[0], this.position.v[1])));
			}
			else{
				Pool.get().addNewCell(new Female(new Vec2(this.position.v[0], this.position.v[1])));
			}
		}
	}
}
