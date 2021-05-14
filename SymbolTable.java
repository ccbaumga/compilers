package FinalProject;

import java.util.*;

public class SymbolTable{
    HashMap<String, SymbolTE> globalvars;
	HashMap<String, SymbolTE> methods;
	HashMap<String, SymbolTE> classes;
	
	SymbolTable(){
		globalvars = new HashMap<String, SymbolTE>();
        methods = new HashMap<String, SymbolTE>();
        classes = new HashMap<String, SymbolTE>();
	}
    
    boolean addVar(Variable m){
        if (!containsVar(m.name)){
            globalvars.put(m.name, m);
            return true;
        }
		return false;
	}
	
	boolean containsVar(String id){
		return globalvars.containsKey(id);
	}
	
	Variable getVar(String id){
        return (Variable)(globalvars.get(id));
		//throw new Exception("method not yet created");
        //return new Variable("error", "error");
	}
	
	boolean addMethod(Method m){
		if (!containsMethod(m.name)){
            methods.put(m.name, m);
            return true;
        }
		return false;
	}
	
	boolean containsMethod(String id){
		return methods.containsKey(id);
	}
	
	Method getMethod(String id){
        return (Method)(methods.get(id));
		//throw new Exception("method not yet created");
        //return new Method("error", "error");
	}
    
    public void print(){
        System.out.println("globalvars");
        Variable[] arr = globalvars.values().toArray(new Variable[0]);
        System.out.println("{");
        for (int i = 0; i < arr.length; i++){
            System.out.println(arr[i].print());
        }
        System.out.println("}");
        
        System.out.println("methods");
        Method[] arr2 = methods.values().toArray(new Method[0]);
        System.out.println("{");
        for (int i = 0; i < arr2.length; i++){
            System.out.println(arr2[i].print());
        }
        System.out.println("}");
    }
}