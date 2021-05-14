package FinalProject;

import java.util.*;

public class Method extends SymbolTE{
	public List<Variable> parameters;
	public HashMap<String, Variable> localvars;
	
	Method(String name, String returnType){
		super.name = name;
		super.type = returnType;
        localvars = new HashMap<String, Variable>();
        parameters = new ArrayList<Variable>();
	}
	
	boolean containsParam(String id){
        for (int i = 0; i < parameters.size(); i++){
            if (id.equals(parameters.get(i).name)){
                return true;
            }
        }
		return false;
	}
    
    boolean addParam(Variable v){
		if (!containsParam(v.name)){
            return parameters.add(v);
        }
        return false;
	}
    
    Variable getParam(String id){
        for (int i = 0; i < parameters.size(); i++){
            if (id.equals(parameters.get(i).name)){
                return parameters.get(i);
            }
        }
		return null;
    }
	
	boolean containsVar(String id){
		return localvars.containsKey(id);
	}
	
	boolean addVar(Variable v){
		if (!containsVar(v.name)){
            localvars.put(v.name, v);
            return true;
        }
		return false;
	}
    
    Variable getVar(String id){
        return localvars.get(id);
    }
    
    String print(){
        Variable[] arr = localvars.values().toArray(new Variable[0]);
        int numparam = parameters.size();
        String str =  "[" + super.name + ", " + super.type + ", (" ;
        for (int i = 0; i < numparam; i++){
            if (numparam > 5) {
                str += "\n\t";
            }
            str += parameters.get(i).print();
        }
        if (numparam > 5) {
            str += "\n\t";
        }        
        str += "), {";
        for (int i = 0; i < arr.length; i++){
            if (arr.length > 5) {
                str += "\n\t";
            }
            str += arr[i].print();
        }
        if (arr.length > 5) {
            str += "\n\t";
        }
        str += "}]";
        return str;
    }
}