import java.util.Random;

import com.jogamp.opengl.GL4;

import glm.Vec2;
import glm.Vec4;
import ogl.App;
import ogl.BadShaderException;
import ogl.DrawMode;
import ogl.OpenGL;
import ogl.Shader;
import ogl.Shape;

public class Main extends App{
	private Shader testShader;
	private Shape screenQuad;
	private float time=0;
	private Vec2[] points; 
	public static void main(String[] args){
		new OpenGL("Cell game",(App)new Main());
	}

	public int startup(GL4 gl) {
		try {
			this.testShader = new Shader(gl, "test", new int[]{2}, new String[]{"xy"});
		} catch (BadShaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		float ratio = (float)OpenGL.screenX/OpenGL.screenY;
		this.testShader.setUniformVec4(gl, "resolution", new Vec4(OpenGL.screenX, OpenGL.screenY, 0.0f, 0.0f));
		this.screenQuad = new Shape(gl);
		this.screenQuad.begin();
		this.screenQuad.add(new Vec2(-1.0f * ratio, -1.0f));
		this.screenQuad.add(new Vec2( 1.0f * ratio, -1.0f));
		this.screenQuad.add(new Vec2(-1.0f * ratio,  1.0f));
		this.screenQuad.add(new Vec2( 1.0f * ratio,  1.0f));
		Random random = new Random();
		int n = 30;
		points = new Vec2[n];
		for(int i=0;i<n;i++)
			points[i] = new Vec2(random.nextFloat(),random.nextFloat());
		return 0;
	}

	public void cleanup(GL4 gl) {
	}

	public void draw(GL4 gl) {
		for(int i=0;i<30;i++){
			this.testShader.setUniformVec2(gl, "points[" + i + "]", points[i]);
		}
		this.testShader.setUniformFloat(gl, "time", time);
		gl.glClearColor(0.5f, 0.0f, 0.0f, 0.0f);
		gl.glClear(gl.GL_COLOR_BUFFER_BIT);
		this.screenQuad.draw(gl, testShader, DrawMode.triangleStrip);
	}
	
	public int update(GL4 gl) {
		time+=0.01;
		return 0;
	}
}
