import ogl.Shape;

public class CellRecord {
	public int max;
	private Base[] cellsRecord;
	private Boolean[] emptyRecord;
	public CellRecord(int max){
		this.max = max;
		cellsRecord = new Base[this.max];
		emptyRecord = new Boolean[this.max];
		this.reset();
	}
	
	public void reset(){
		for(int i=0; i<this.max; i++){
			emptyRecord[i]=true;
		}
	}
	
	public int giveEmpty(){
		boolean found = false;
		int r = -1;
		for(int i=0; i<this.max && !found; i++){
			if(emptyRecord[i]){
				found = true;
				r = i;
				emptyRecord[i]=false;
			}
		}
		return r;
	}
	
	public void setRecord(int i, Base cell){
		if(i>=0&&i<this.max){
			cellsRecord[i] = new Base(cell);
		}
	}
	
	public Base getRecord(int i){
		if(i>=0&&i<this.max&&!emptyRecord[i]){
			return cellsRecord[i];
		}
		return null;
	}
	
	public void draw(Shape shape){
		for(int i=0;i<this.max; i++){
			if(!emptyRecord[i]){
				cellsRecord[i].draw(shape); 
			}
		}
	}
}
