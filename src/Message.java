
public class Message {
	public enum type{
		EMPTY,
		EAT,
		MATE,
		SPLIT,
		DIE
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
	
	public static Message Split(){
		return new Message(type.SPLIT, null);
	}

	public static Message Die() {
		return new Message(type.DIE, null);
	}
	
	public Message clone(){
		return new Message(this.messageType, this.cell);
	}
}
