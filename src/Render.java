import glm.Vec2;
import ogl.Shape;

public class Render{
	public static void drawCell(Shape q, Vec2 p, float s) {
		s+=0.01;
		q.add(p.add(new Vec2(-s,-s)));
		q.add(p.add(new Vec2( s,-s)));
		q.add(p.add(new Vec2(-s, s)));
		
		q.add(p.add(new Vec2(-s, s)));
		q.add(p.add(new Vec2( s,-s)));
		q.add(p.add(new Vec2( s, s)));
	}
}
