package FinalProject;

import FinalProject.lexer.*;
import FinalProject.node.*;
import FinalProject.parser.*;
import java.io.*;
import java.util.*;

public class Main{

   public static void main(String[] arguments){
      try{
            Lexer lexer = new Lexer(new PushbackReader
                  (new InputStreamReader(System.in), 1024));

            Parser parser = new Parser(lexer);
            Start ast = parser.parse();

            ast.apply(new SymbolTraverse());
            //System.out.println("traverse good");
            ast.apply(new CheckType());
            //System.out.println("checktype good");
            ast.apply(new Translate());
            //SymbolTraverse.st.print();
      }
      catch(Exception e){ System.out.println("Error: " + e.getMessage()); }
   }
}
