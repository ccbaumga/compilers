package FinalProject;

public class Variable extends SymbolTE{
    public int stack_offset;
	Variable(String name, String type){
		super.name = name;
		super.type = type;
	}
    
    String print(){
        return "[" + super.name + ", " + super.type + "]";
    }
}
