
public class Message {
	public enum type{
		EMPTY,
		EAT,
		MATE,
		SPLIT
	};
	
	public type messageType;
	public Base cell;
	
	public Message(Base cell){
		this(type.EMPTY, cell);
	}
	
	public Message(type type, Base cell){
		this.messageType = type;
		this.cell = cell;
	}
	
	public static Message Eat(Base cell){
		return new Message(type.EAT, cell);
	}
	
	public static Message Mate(Base cell){
		return new Message(type.MATE, cell);
	}
	
	public static Message Split(Base cell){
		return new Message(type.SPLIT, cell);
	}
	
	public Message clone(){
		return new Message(this.messageType, this.cell);
	}
}
