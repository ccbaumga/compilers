package FinalProject;

import FinalProject.lexer.*;
import FinalProject.node.*;
import FinalProject.parser.*;
import java.io.*;
import java.util.*;

public class Main{
	//public static List symboltable;
    //public static SymbolTable st;

   public static void main(String[] arguments){
      try{
            Lexer lexer = new Lexer(new PushbackReader
                  (new InputStreamReader(System.in), 1024));


            Parser parser = new Parser(lexer);

            Start ast = parser.parse();

			//st = new SymbolTable();
            ast.apply(new SymbolTraverse());  //this is what gets the depth first search going
            //SymbolTraverse.st.addVar(new Variable("x", "int"));

            /*Method m = new Method("func", "int");
            SymbolTraverse.st.addMethod(m);
            m.addParam(new Variable("y", "boolean"));
            m.addParam(new Variable("ya", "boolean"));
            m.addParam(new Variable("ys", "boolean"));
            m.addParam(new Variable("yd", "boolean"));
            m.addParam(new Variable("yf", "boolean"));
            m.addParam(new Variable("ydghsgnsnrg", "ldkfhslkajs"));
            Method n = new Method("n", "String");
            SymbolTraverse.st.addMethod(n);
            m.addVar(new Variable("yf", "boolean"));
            m.addVar(new Variable("ds", "int"));
            m.addVar(new Variable("d", "int"));
            n.addVar(new Variable("dh", "int"));
            n.addVar(new Variable("df", "int"));
            n.addVar(new Variable("hds", "int"));*/
            ast.apply(new Translate());
            SymbolTraverse.st.print();
      }
      catch(Exception e){ System.out.println("Error: " + e.getMessage()); }
   }
}
