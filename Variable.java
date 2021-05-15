package FinalProject;

public class Variable extends SymbolTE{
    public int stack_offset;
    public boolean isarray;
	Variable(String name, String type){
		super.name = name;
		super.type = type;
        isarray = false;
	}

    Variable(String name, String type, String array){
		super.name = name;
		super.type = type;
        isarray = true;
	}
    
    String print(){
        String str = "[" + super.name + ", " + super.type;
        if (isarray){
            str+= "[]";
        }
        return str + "]";
    }
}
