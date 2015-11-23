import ogl.Shape;

public class CellRecord {
	public static int CELLMAX = 40;
	private Cell[] cellsRecord;
	private Boolean[] emptyRecord;
	public CellRecord(){
		cellsRecord = new Cell[CELLMAX];
		emptyRecord = new Boolean[CELLMAX];
		this.reset();
	}
	
	public void reset(){
		for(int i=0; i<CELLMAX; i++){
			emptyRecord[i]=true;
		}
	}
	
	public int giveEmpty(){
		boolean found = false;
		int r = -1;
		for(int i=0; i<CELLMAX && !found; i++){
			if(emptyRecord[i]){
				found = true;
				r = i;
				emptyRecord[i]=false;
			}
		}
		return r;
	}
	
	public void setRecord(int i, Cell cell){
		if(i>=0&&i<CELLMAX){
			cellsRecord[i] = new Cell(cell);
		}
	}
	
	public Cell getRecord(int i){
		if(i>=0&&i<CELLMAX&&!emptyRecord[i]){
			return cellsRecord[i];
		}
		return null;
	}
	
	public void draw(Shape shape){
		for(int i=0;i<CELLMAX; i++){
			if(emptyRecord[i] == true){
				cellsRecord[i].draw(shape);
			}
		}
	}
}
