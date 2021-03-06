import java.util.Random;

import glm.Vec2;

public class Food extends Base implements Runnable {
	protected static double yum = 15;

	public Food(Vec2 p) {
		Random rnd = new Random();
		position = new Vec2(p.v[0], p.v[1]);
		this.angle = (float) (rnd.nextFloat()*Math.PI*2.0);
		this.health = rnd.nextFloat()*15+15;
		alive = true;
		this.r = 0.3;
		this.g = 1.0;
		this.b = 0.3;
		this.a = 1.0;
		this.radius=0.0f;
		cellType = CellType.FOOD;
		this.speed = 0.001;
	}
	
	public void update(){
		Random rnd = new Random();
		float radiusTarget = 0.1f - ((100.0f-this.health)/100.0f)*0.08f; 
		this.radius += (radiusTarget-radius)*0.01;
		position.v[0] += rnd.nextDouble()*speed-speed/2;
		position.v[1] += rnd.nextDouble()*speed-speed/2;
	}
	
	public synchronized boolean canEat(Base eater){
		if(this.alive)
		if(this.collide(eater)){
			this.alive=false;
			eater.giveHealth(Food.yum);
			return true;
		}
		return false;
	}
}
