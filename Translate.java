package FinalProject;

import FinalProject.analysis.*;
import FinalProject.node.*;
import java.util.*;


class Translate extends DepthFirstAdapter
{
    public static boolean[] registers = {true, true, true, true, true, true, true, true, true, true};
    public static int offset;
    public static Stack<Integer> exprstack;
    public static String lastnum;
    public static String lastid;
    public static Variable[] globalvars; 
    Variable[] localparams;
    Variable[] localvars;
    public static int arrayoffset;
    int stacksize = 0;
    Method curmeth = null;
    String laststring = null;
    public static int ifcount = 0;
    public static int whilecount = 0;

 	public Translate() {
		//System.out.println("Start of the Translating Action");
        arrayoffset = -1;
        exprstack = new Stack<Integer>();
        System.out.println("\t.data");
        globalvars = SymbolTraverse.st.globalvars.values().toArray(new Variable[0]);
        for (int i = 0; i < globalvars.length;i++){
            System.out.println(globalvars[i].name + ":");
            switch(globalvars[i].type)
            {
                case "INT": 
                System.out.println("\t.space 4");
                break;

                case "STRING":
                System.out.println("\t.space 4");
                break;

                case "REAL":
                System.out.println("\t.space 4");
                break;

                case "BOOLEAN":
                System.out.println("\t.space 4");
                break;

                default:
                    System.out.println("Mission failed");
                break;
            }  
        }
        System.out.println("\t.text");
        System.out.println("\tjal main");
        
	}

	//this gets called if the production is prog --> id digit
    public void caseAProg(AProg node){
        //System.out.println("\tGot a first prog!");
        node.getBegin().apply(this);
        node.getClassmethodstmts().apply(this);
        node.getEnd().apply(this);
    }

//Type --> {first} intdecl
public void caseAFirstType(AFirstType node){
	//System.out.println("\tGot a First Type!");
	node.getIntdecl().apply(this);
}

//Type --> {second} realdecl
public void caseASecondType(ASecondType node){
	//System.out.println("\tGot a Second Type!");
	node.getRealdecl().apply(this);
}

//Type --> {third} voiddecl
public void caseAThirdType(AThirdType node){
	//System.out.println("\tGot a third Type!");
	node.getVoiddecl().apply(this);
}

//Type --> {fourth} booleandecl
public void caseAFourthType(AFourthType node){
	//System.out.println("\tGot a fourth Type!");
	node.getBooleandecl().apply(this);
}

//Type --> {fifth} stringdecl
public void caseAFifthType(AFifthType node){
	//System.out.println("\tGot a fifth Type!");
	node.getStringdecl().apply(this);
}

//Type --> {sixth} iddecl
public void caseASixthType(ASixthType node){
	//System.out.println("\tGot a sixth Type!");
	node.getId().apply(this);
}



//multop --> {first} times
public void caseAFirstMultop(AFirstMultop node){
	//System.out.println("\tGot a first Multop!");
	node.getTimes().apply(this);
    int second = exprstack.pop();
    int first = exprstack.pop();
    System.out.println("\tmul $t" + first + ", $t" + first + ", $t" + second);
    registers[second] = true;
    exprstack.push(first);
    
}

public void caseASecondMultop(ASecondMultop node){
	//System.out.println("\tGot a second Multop!");
	node.getDivide().apply(this);
    int second = exprstack.pop();
    int first = exprstack.pop();
    System.out.println("\tdiv $t" + first + ", $t" + first + ", $t" + second);
    registers[second] = true;
    exprstack.push(first);
}

//addop --> {first} plus
public void caseAFirstAddop(AFirstAddop node){
	//System.out.println("\tGot a first Addop!");
	node.getPlus().apply(this);
    int second = exprstack.pop();
    int first = exprstack.pop();
    System.out.println("\tadd $t" + first + ", $t" + first + ", $t" + second);
    registers[second] = true;
    exprstack.push(first);
}

public void caseASecondAddop(ASecondAddop node){
	//System.out.println("\tGot a second Addop!");
	node.getMinus().apply(this);
    int second = exprstack.pop();
    int first = exprstack.pop();
    System.out.println("\tsub $t" + first + ", $t" + first + ", $t" + second);
    registers[second] = true;
    exprstack.push(first);
}


//cond --> {first} equalequal
public void caseAFirstCond(AFirstCond node){
	//System.out.println("\tGot a First Cond!");
	node.getEqualequal().apply(this);
    int second = exprstack.pop();
    int first = exprstack.pop();
    System.out.println("\tslt $t2, $t1, $t0");
    System.out.println("\tslt $t3, $t0, $t1");
    System.out.println("\tor $t0, $t2, $t3");
    System.out.println("\tslti $t0, $t0, 1");
    registers[1] = true;
    exprstack.pop();
}
//{second} notequal
public void caseASecondCond(ASecondCond node){
	//System.out.println("\tGot a Second Cond!");
	node.getNotequal().apply(this);
    int second = exprstack.pop();
    int first = exprstack.pop();
    System.out.println("\tslt $t2, $t1, $t0");
    System.out.println("\tslt $t3, $t0, $t1");
    System.out.println("\tor $t0, $t2, $t3");
    registers[1] = true;
    exprstack.pop();
}
//{third} leq
public void caseAThirdCond(AThirdCond node){
	//System.out.println("\tGot a Third Cond!");
	node.getLeq().apply(this);
    int second = exprstack.pop();
    int first = exprstack.pop();
    System.out.println("\tslt $t0, $t1, $t0");
    System.out.println("\tslti $t0, $t0, 1");
    registers[1] = true;
    exprstack.pop();
}
//{fourth} geq
public void caseAFourthCond(AFourthCond node){
	//System.out.println("\tGot a Fourth Cond!");
	node.getGeq().apply(this);
    System.out.println("\tslt $t0, $t0, $t1");
    System.out.println("\tslt $t0, $t0, 1");
    registers[1] = true;
    exprstack.pop();
}
//{fifth} lt
public void caseAFifthCond(AFifthCond node){
	//System.out.println("\tGot a Fifth Cond!");
	node.getLt().apply(this);
    System.out.println("\tslt $t0, $t0, $t1");
    registers[1] = true;
    exprstack.pop();
}
//{sixth} gt
public void caseASixthCond(ASixthCond node){
	//System.out.println("\tGot a Sixth Cond!");
	node.getGt().apply(this);
    System.out.println("\tslt $t0, $t1, $t0");
    registers[1] = true;
    exprstack.pop();
}


public void caseAFirstFactor(AFirstFactor node){
	//System.out.println("\tGet a first factor!");
	node.getMinus().apply(this);
	node.getFactor().apply(this);
    
}

public void caseASecondFactor(ASecondFactor node){
	//System.out.println("\tGet a second factor!");
	node.getInt().apply(this);
    int reg = -1;
    for (int i = 0; i < registers.length;i++){
        if (registers[i] == true && reg == -1){
            reg = i;
            registers[i] = false;
            System.out.println("\tli $t" + i + ", " + lastnum);
            exprstack.push(i);
        }
    }
    
}

public void caseAThirdFactor(AThirdFactor node){
	//System.out.println("\tGet a Third factor!");
	node.getReal().apply(this);
}

public void caseAFourthFactor(AFourthFactor node){
	//System.out.println("\tGet a fourth factor!");
	node.getId().apply(this);
    getIdValue();
}

public void caseAFifthFactor(AFifthFactor node){
	//System.out.println("\tGet a fifth factor!");
	node.getId().apply(this);
	node.getLbracket().apply(this);
	node.getInt().apply(this);
	node.getRbracket().apply(this);
}

public void caseASixthFactor(ASixthFactor node){
	//System.out.println("\tGet a sixth factor!");
	node.getId().apply(this);
	node.getLparen().apply(this);
	node.getVarlisttwo().apply(this);
	node.getRparen().apply(this);
}

public void caseASeventhFactor(ASeventhFactor node){
	//System.out.println("\tGet a seventh factor!");
	node.getObject().apply(this);
	node.getDot().apply(this);
	node.getField().apply(this);
	node.getLparen().apply(this);
	node.getVarlisttwo().apply(this);
	node.getRparen().apply(this);
}

public void caseAEighthFactor(AEighthFactor node){
	//System.out.println("\tGet an Eighth factor!");
	node.getObject().apply(this);
	node.getLbracket().apply(this);
	node.getInt().apply(this);
	node.getRbracket().apply(this);
	node.getDot().apply(this);
	node.getField().apply(this);
	node.getLparen().apply(this);
	node.getVarlisttwo().apply(this);
	node.getRparen().apply(this);
}

public void caseANinthFactor(ANinthFactor node){
	//System.out.println("\tGet a Ninth factor!");
	node.getObject().apply(this);
	node.getDot().apply(this);
	node.getField().apply(this);
}

public void caseATenthFactor(ATenthFactor node){
	//System.out.println("\tGet a Tenth factor!");
	node.getLparen().apply(this);
	node.getExpr().apply(this);
	node.getRparen().apply(this);
}


public void caseAFirstBoolean(AFirstBoolean node){
	//System.out.println("\tGot a First Boolean!");
	node.getTrue().apply(this);
    int reg = -1;
    for (int i = 0; i < registers.length;i++){
        if (registers[i] == true && reg == -1){
            reg = i;
            registers[i] = false;
            exprstack.push(i);
        }
    }
    System.out.println("\tli $t" + reg + ", 1");    
}

public void caseASecondBoolean(ASecondBoolean node){
	//System.out.println("\tGot a Second Boolean!");
	node.getFalse().apply(this);
    int reg = -1;
    for (int i = 0; i < registers.length;i++){
        if (registers[i] == true && reg == -1){
            reg = i;
            registers[i] = false;
            exprstack.push(i);
        }
    }
    System.out.println("\tli $t" + reg + ", 0"); 
}

public void caseAThirdBoolean(AThirdBoolean node){
	//System.out.println("\tGot a Third Boolean!");
	node.getId().apply(this);
    getIdValue();
}

public void caseAFourthBoolean(AFourthBoolean node){
	//System.out.println("\tGot a Fourth Boolean!");
	node.getOne().apply(this);
	node.getTwo().apply(this);
	node.getCond().apply(this);
}

public void caseAFifthBoolean(AFifthBoolean node){
	//System.out.println("\tGot a Fifth Boolean!");
	node.getObject().apply(this);
	node.getDot().apply(this);
	node.getField().apply(this);
}

public void caseASixthBoolean(ASixthBoolean node){
	//System.out.println("\tGot a Sixth Boolean!");
	node.getObject().apply(this);
	node.getLbracket().apply(this);
	node.getInt().apply(this);
	node.getRbracket().apply(this);
	node.getDot().apply(this);
	node.getField().apply(this);
}

	//term --> {first} term multop factor
    public void caseAFirstTerm(AFirstTerm node){
        //System.out.println("\tGot a first term!");
        node.getTerm().apply(this);
        node.getFactor().apply(this);
        node.getMultop().apply(this);
    }

    //term --> {second} factor
    public void caseASecondTerm(ASecondTerm node){
        //System.out.println("\tGot a second term!");
        node.getFactor().apply(this);
    }

	//expr --> {first} expr addop term
    public void caseAFirstExpr(AFirstExpr node){
        //System.out.println("\tGot a first expr!");
        node.getExpr().apply(this);
        node.getTerm().apply(this);
        node.getAddop().apply(this);
    }

	//expr --> {second} term 
    public void caseASecondExpr(ASecondExpr node){
        //System.out.println("\tGot a second expr!");
        node.getTerm().apply(this);
    }

	//varlisttwo --> {first} 
    public void caseAFirstVarlisttwo(AFirstVarlisttwo node){
        //System.out.println("\tGot a first varlisttwo!");
    }

	//varlisttwo --> {second} expr
    public void caseASecondVarlisttwo(ASecondVarlisttwo node){
        //System.out.println("\tGot a second varlisttwo!");
        node.getExpr().apply(this);
    }

	//varlisttwo --> {third} expr comma varlisttwo
    public void caseAThirdVarlisttwo(AThirdVarlisttwo node){
        //System.out.println("\tGot a third varlisttwo!");
        node.getExpr().apply(this);
        node.getComma().apply(this);
        node.getVarlisttwo().apply(this);
    }

	//varlist --> {first} 
    public void caseAFirstVarlist(AFirstVarlist node){
        //System.out.println("\tGot a first varlist!");
    }

    //varlist --> {second} id colon type
    public void caseASecondVarlist(ASecondVarlist node){
        //System.out.println("\tGot a second varlist!");
        node.getId().apply(this);
        node.getColon().apply(this);
        node.getType().apply(this);
    }

    //varlist --> {third} id colon type lbracket int rbracket
    public void caseAThirdVarlist(AThirdVarlist node){
        //System.out.println("\tGot a third varlist!");
        node.getId().apply(this);
        node.getColon().apply(this);
        node.getType().apply(this);
        node.getLbracket().apply(this);
        node.getInt().apply(this);
        node.getRbracket().apply(this);
    }

    //varlist --> {fourth} id colon type comma varlist
    public void caseAFourthVarlist(AFourthVarlist node){
        //System.out.println("\tGot a fourth varlist!");
        node.getId().apply(this);
        node.getColon().apply(this);
        node.getType().apply(this);
        node.getComma().apply(this);
        node.getVarlist().apply(this);
    }

    //varlist --> {fifth} id colon type lbracket int rbracket comma varlist
    public void caseAFifthVarlist(AFifthVarlist node){
        //System.out.println("\tGot a fifth varlist!");
        node.getId().apply(this);
        node.getColon().apply(this);
        node.getType().apply(this);
        node.getLbracket().apply(this);
        node.getInt().apply(this);
        node.getRbracket().apply(this);
        node.getComma().apply(this);
        node.getVarlist().apply(this);
    }

    //idlist --> {first} id 
    public void caseAFirstIdlist(AFirstIdlist node){
        //System.out.println("\tGot a first idlist!");
        node.getId().apply(this);
    }

    //idlist --> {second} id comma idlist
    public void caseASecondIdlist(ASecondIdlist node){
        //System.out.println("\tGot a first idlist!");
        node.getId().apply(this);
        node.getComma().apply(this);
        node.getIdlist().apply(this);
    }

    //stmtseq --> {first} 
    public void caseAFirstStmtseq(AFirstStmtseq node){
        //System.out.println("\tGot a first stmtseq!");
    }

    //stmtseq --> {second} stmt stmtseq
    public void caseASecondStmtseq(ASecondStmtseq node){
        //System.out.println("\tGot a second stmtseq!");
        node.getStmt().apply(this);
        node.getStmtseq().apply(this);
    }

    //idorarray --> {first} id
    public void caseAFirstIdorarray(AFirstIdorarray node){
        //System.out.println("\tGot a first idorarray!");
        node.getId().apply(this);
    }

    //idorarray --> {second} id lbracket int rbracket
    public void caseASecondIdorarray(ASecondIdorarray node){
        //System.out.println("\tGot a second idorarray!");
        node.getId().apply(this);
        node.getLbracket().apply(this);
        node.getInt().apply(this);
        node.getRbracket().apply(this);
    }

    //stmt --> {first} idorarray assign expr semicolon
    public void caseAFirstStmt(AFirstStmt node){
        //System.out.println("\tGot a first stmt!");
        node.getExpr().apply(this);
        node.getIdorarray().apply(this);
        assign();
        node.getAssign().apply(this);
        node.getSemicolon().apply(this);
    }

    //stmt --> {third} idorarray assign anychars semicolon
    public void caseAThirdStmt(AThirdStmt node){
        //System.out.println("\tGot a third stmt!");
        node.getAssign().apply(this);
        node.getAnychars().apply(this);
        node.getIdorarray().apply(this);
        int stringlength = laststring.length();
        System.out.println("\tli $a0, " + (stringlength + 1));
        System.out.println("\tli $v0, 9\n\tsyscall");
        String str = laststring + "    ";
        int i;
        for (i = 0; i < stringlength; i+= 4){
            int ascii1 = str.charAt(i);
            int ascii2 = str.charAt(i + 1);
            int ascii3 = str.charAt(i + 2);
            int ascii4 = str.charAt(i + 3);
            int ascii = ascii1 + ascii2 * 256 + ascii3 * 256 * 256 + ascii4 * 256 * 256 * 256;
            System.out.println("\tli $t0, " + ascii);
            System.out.println("\tsw $t0, " + i + "($v0)");
        }
        int ascii = '\0';
        System.out.println("\tli $t0, " + ascii);
        System.out.println("\tsw $t0, " + i + "($v0)");
        System.out.println("\tmove $t0, $v0");
        assign();
        node.getSemicolon().apply(this);
    }

    //stmt --> {fifth} idlist colon type semicolon
    public void caseAFifthStmt(AFifthStmt node){
        //System.out.println("\tGot a fifth stmt!");
        node.getIdlist().apply(this);
        node.getColon().apply(this);
        node.getType().apply(this);
        node.getSemicolon().apply(this);
    }

    //stmt --> {sixth} idlist colon type lbracket int rbracket semicolon
    public void caseASixthStmt(ASixthStmt node){
        //System.out.println("\tGot a sixth stmt!");
        node.getIdlist().apply(this);
        node.getColon().apply(this);
        node.getType().apply(this);
        node.getLbracket().apply(this);
        node.getInt().apply(this);
        node.getRbracket().apply(this);
        node.getSemicolon().apply(this);
    }

    //stmt --> {seventh} if lparen boolean rparen then lbrace stmtseq rbrace
    public void caseASeventhStmt(ASeventhStmt node){
        //System.out.println("\tGot a seventh stmt!");
        node.getIf().apply(this);
        node.getLparen().apply(this);
        node.getBoolean().apply(this);
        node.getRparen().apply(this);
        ifcount++;
        int count = ifcount;//make sure they match
        System.out.println("\tbeq $t0, $zero, after" + count);
        registers[0] = true;
        exprstack.pop();
        node.getThen().apply(this);
        node.getLbrace().apply(this);
        node.getStmtseq().apply(this);
        node.getRbrace().apply(this);
        System.out.println("after" + count + ":");
    }

    //stmt --> {eighth} if lparen boolean rparen then [one]:lbrace [five]:stmtseq [three]:rbrace else [two]:lbrace [six]:stmtseq [four]:rbrace
    public void caseAEighthStmt(AEighthStmt node){
        //System.out.println("\tGot a eighth stmt!");
        node.getIf().apply(this);
        node.getLparen().apply(this);
        node.getBoolean().apply(this);
        ifcount++;
        int count = ifcount;
        registers[0] = true;
        exprstack.pop();
        System.out.println("\tbeq $t0, $zero, else" + count);
        node.getRparen().apply(this);
        node.getThen().apply(this);
        node.getOne().apply(this);
        node.getFive().apply(this);
        node.getThree().apply(this);
        System.out.println("\tj after" + count);
        System.out.println("else" + count + ":");
        node.getElse().apply(this);
        node.getTwo().apply(this);
        node.getSix().apply(this);
        node.getFour().apply(this);
        System.out.println("after" + count + ":");
    }

    //stmt --> {ninth} while lparen boolean rparen lbrace stmtseq rbrace
    public void caseANinthStmt(ANinthStmt node){
        //System.out.println("\tGot a ninth stmt!");
        node.getWhile().apply(this);
        whilecount++;
        int count = whilecount;
        System.out.println("while" + count + ":");
        node.getLparen().apply(this);
        node.getBoolean().apply(this);
        registers[0] = true;
        exprstack.pop();
        System.out.println("\tbeq $t0, $zero, done" + count);
        node.getRparen().apply(this);
        node.getLbrace().apply(this);
        node.getStmtseq().apply(this);
        System.out.println("\tj while" + count);
        System.out.println("done" + count + ":");
        node.getRbrace().apply(this);
    }

    //stmt --> {tenth} for lparen [five]:id assign expr [one]:semicolon boolean [two]:semicolon [six]:id [three]:plus [four]:plus rparen lbrace stmtseq rbrace
    public void caseATenthStmt(ATenthStmt node){
        //System.out.println("\tGot a tenth stmt!");
        node.getFor().apply(this);
        node.getLparen().apply(this);
        node.getExpr().apply(this);
        node.getFive().apply(this);
        node.getAssign().apply(this);
        assign();
        node.getOne().apply(this);
        whilecount++;
        int count = whilecount;
        System.out.println("while" + count + ":");
        node.getBoolean().apply(this);
        registers[0] = true;
        exprstack.pop();
        System.out.println("\tbeq $t0, $zero, done" + count);
        node.getTwo().apply(this);
        node.getRparen().apply(this);
        node.getLbrace().apply(this);
        node.getStmtseq().apply(this);
        node.getRbrace().apply(this);
        node.getSix().apply(this);
        node.getThree().apply(this);
        node.getFour().apply(this);
        idplusplus();
        System.out.println("\tj while" + count);
        System.out.println("done" + count + ":");
    }

    //stmt --> {eleventh} for lparen type [five]:id assign expr [one]:semicolon boolean [two]:semicolon [six]:id [three]:plus [four]:plus rparen lbrace stmtseq rbrace
    public void caseAEleventhStmt(AEleventhStmt node){
       // System.out.println("\tGot a eleventh stmt!");
        node.getFor().apply(this);
        node.getLparen().apply(this);
        node.getExpr().apply(this);
        node.getFive().apply(this);
        node.getAssign().apply(this);
        assign();
        node.getOne().apply(this);
        whilecount++;
        int count = whilecount;
        System.out.println("while" + count + ":");
        node.getBoolean().apply(this);
        registers[0] = true;
        exprstack.pop();
        System.out.println("\tbeq $t0, $zero, done" + count);
        node.getTwo().apply(this);
        node.getRparen().apply(this);
        node.getLbrace().apply(this);
        node.getStmtseq().apply(this);
        node.getRbrace().apply(this);
        node.getSix().apply(this);
        node.getThree().apply(this);
        node.getFour().apply(this);
        idplusplus();
        System.out.println("\tj while" + count);
        System.out.println("done" + count + ":");
    }

    //stmt --> {twelveth} for lparen [five]:id assign expr [one]:semicolon boolean [two]:semicolon [six]:id [three]:minus [four]:minus rparen lbrace stmtseq rbrace
    public void caseATwelvethStmt(ATwelvethStmt node){
        //System.out.println("\tGot a twelveth stmt!");
        node.getFor().apply(this);
        node.getLparen().apply(this);
        node.getExpr().apply(this);
        node.getFive().apply(this);
        node.getAssign().apply(this);
        assign();
        node.getOne().apply(this);
        whilecount++;
        int count = whilecount;
        System.out.println("while" + count + ":");
        node.getBoolean().apply(this);
        registers[0] = true;
        exprstack.pop();
        System.out.println("\tbeq $t0, $zero, done" + count);
        node.getTwo().apply(this);
        node.getRparen().apply(this);
        node.getLbrace().apply(this);
        node.getStmtseq().apply(this);
        node.getRbrace().apply(this);
        node.getSix().apply(this);
        node.getThree().apply(this);
        node.getFour().apply(this);
        idminusminus();
        System.out.println("\tj while" + count);
        System.out.println("done" + count + ":");
    }

    //stmt --> {thirteenth} for lparen type [five]:id assign expr [one]:semicolon boolean [two]:semicolon [six]:id [three]:minus [four]:minus rparen lbrace stmtseq rbrace
    public void caseAThirteenthStmt(AThirteenthStmt node){
        //System.out.println("\tGot a thirteenth stmt!");
        node.getFor().apply(this);
        node.getLparen().apply(this);
        node.getExpr().apply(this);
        node.getFive().apply(this);
        node.getAssign().apply(this);
        assign();
        node.getOne().apply(this);
        whilecount++;
        int count = whilecount;
        System.out.println("while" + count + ":");
        node.getBoolean().apply(this);
        registers[0] = true;
        exprstack.pop();
        System.out.println("\tbeq $t0, $zero, done" + count);
        node.getTwo().apply(this);
        node.getRparen().apply(this);
        node.getLbrace().apply(this);
        node.getStmtseq().apply(this);
        node.getRbrace().apply(this);
        node.getSix().apply(this);
        node.getThree().apply(this);
        node.getFour().apply(this);
        idminusminus();
        System.out.println("\tj while" + count);
        System.out.println("done" + count + ":");
    }

    //stmt --> {fourteenth} for lparen [five]:id [aba]:assign [aca]:expr [one]:semicolon boolean [two]:semicolon [six]:id [gay]:assign [aaa]:expr rparen lbrace stmtseq rbrace
    public void caseAFourteenthStmt(AFourteenthStmt node){
        //System.out.println("\tGot a fourteenth stmt!");
        node.getFor().apply(this);
        node.getLparen().apply(this);
        node.getAca().apply(this);
        node.getFive().apply(this);
        node.getAba().apply(this);
        assign();
        node.getOne().apply(this);
        whilecount++;
        int count = whilecount;
        System.out.println("while" + count + ":");
        node.getBoolean().apply(this);
        registers[0] = true;
        exprstack.pop();
        System.out.println("\tbeq $t0, $zero, done" + count);
        node.getTwo().apply(this);
        node.getRparen().apply(this);
        node.getLbrace().apply(this);
        node.getStmtseq().apply(this);
        node.getRbrace().apply(this);
        node.getAaa().apply(this);
        node.getSix().apply(this);
        assign();
        System.out.println("\tj while" + count);
        System.out.println("done" + count + ":");
    }

    //stmt --> {fifteenth} for type lparen [five]:id [aba]:assign [aca]:expr [one]:semicolon boolean [two]:semicolon [six]:id  [gay]:assign [aaa]:expr rparen lbrace stmtseq rbrace
    public void caseAFifteenthStmt(AFifteenthStmt node){
        //System.out.println("\tGot a fifteenth stmt!");
        node.getFor().apply(this);
        node.getLparen().apply(this);
        node.getAca().apply(this);
        node.getFive().apply(this);
        node.getAba().apply(this);
        assign();
        node.getOne().apply(this);
        whilecount++;
        int count = whilecount;
        System.out.println("while" + count + ":");
        node.getBoolean().apply(this);
        registers[0] = true;
        exprstack.pop();
        System.out.println("\tbeq $t0, $zero, done" + count);
        node.getTwo().apply(this);
        node.getRparen().apply(this);
        node.getLbrace().apply(this);
        node.getStmtseq().apply(this);
        node.getRbrace().apply(this);
        node.getAaa().apply(this);
        node.getSix().apply(this);
        assign();
        System.out.println("\tj while" + count);
        System.out.println("done" + count + ":");
    }

    //stmt --> {sixteenth} idorarray assign get lparen rparen semicolon
    public void caseASixteenthStmt(ASixteenthStmt node){
        //System.out.println("\tGot a sixteenth stmt!");
        node.getAssign().apply(this);
        node.getGet().apply(this);
        System.out.println("\tli $v0, 5");
        System.out.println("\tsyscall");
        System.out.println("\tmove $t0, $v0");
        node.getIdorarray().apply(this);
        assign();
        node.getLparen().apply(this);
        node.getRparen().apply(this);
        node.getSemicolon().apply(this);
    }

    //stmt --> {seventeenth} put lparen idorarray rparen semicolon
    public void caseASeventeenthStmt(ASeventeenthStmt node){
        //System.out.println("\tGot a seventeenth stmt!");
        node.getPut().apply(this);
        node.getLparen().apply(this);
        node.getIdorarray().apply(this);
        if (arrayoffset != -1){//if its to an array index
            System.out.println("\tla $t0, " + lastid);
            System.out.println("\tlw $a0, " + arrayoffset + "($t0)");
            System.out.println("\tli $v0, 1\n\tsyscall");
        } else if (curmeth.containsParam(lastid)){//if its a parameter
            if (curmeth.getParam(lastid).type.equals("STRING")){
                System.out.println("\tlw $a0, " + curmeth.getParam(lastid).stack_offset + "($sp)");
                System.out.println("\tli $v0, 4\n\tsyscall");
            }else if (curmeth.getParam(lastid).type.equals("INT")){
                System.out.println("\tlw $a0, " + curmeth.getParam(lastid).stack_offset + "($sp)");
                System.out.println("\tli $v0, 1\n\tsyscall");
            }
        } else if (curmeth.containsVar(lastid)){ //if it's a local variable
            if (curmeth.getVar(lastid).type.equals("STRING")){
                System.out.println("\tlw $a0, " + curmeth.getVar(lastid).stack_offset + "($sp)");
                System.out.println("\tli $v0, 4\n\tsyscall");
            }else if (curmeth.getVar(lastid).type.equals("INT")){
                System.out.println("\tlw $a0, " + curmeth.getVar(lastid).stack_offset + "($sp)");
                System.out.println("\tli $v0, 1\n\tsyscall");
            }
        } else { //if its a global variable
            if (SymbolTraverse.st.getVar(lastid).type.equals("STRING")){
                System.out.println("\tlw $a0, " + SymbolTraverse.st.getVar(lastid).name);
                System.out.println("\tli $v0, 4\n\tsyscall");
            }else if (SymbolTraverse.st.getVar(lastid).type.equals("INT")){
                System.out.println("\tlw $a0, " + lastid);
                System.out.println("\tli $v0, 1\n\tsyscall");
            }
        }
        node.getRparen().apply(this);
        node.getSemicolon().apply(this);
    }

    //stmt --> {eighteenth} idorarray [first]:plus [second]:plus semicolon
    public void caseAEighteenthStmt(AEighteenthStmt node){
        //System.out.println("\tGot a eightteenth stmt!");
        node.getIdorarray().apply(this);
        idplusplus();
        node.getFirst().apply(this);
        node.getSecond().apply(this);
        node.getSemicolon().apply(this);
    }

    //stmt --> {nineteenth} idorarray [first]:minus [second]:minus semicolon
    public void caseANineteenthStmt(ANineteenthStmt node){
        //System.out.println("\tGot a nineteenth stmt!");
        node.getIdorarray().apply(this);
        idminusminus();
        node.getFirst().apply(this);
        node.getSecond().apply(this);
        node.getSemicolon().apply(this);
    }

    //stmt --> {twenty} idorarray assign new id lparen rparen semicolon
    public void caseATwentyStmt(ATwentyStmt node){
        //System.out.println("\tGot a twenty stmt!");
        node.getIdorarray().apply(this);
        node.getAssign().apply(this);
        node.getNew().apply(this);
        node.getId().apply(this);
        node.getLparen().apply(this);
        node.getRparen().apply(this);
        node.getSemicolon().apply(this);
    }

    //stmt --> {twentyone} id lparen varlisttwo rparen semicolon |
    public void caseATwentyoneStmt(ATwentyoneStmt node){
        //System.out.println("\tGot a twentyone stmt!");
        node.getId().apply(this);
        node.getLparen().apply(this);
        node.getVarlisttwo().apply(this);
        node.getRparen().apply(this);
        node.getSemicolon().apply(this);
    }

    //stmt --> {twentytwo} idorarray dot id lparen varlisttwo rparen dotmethod semicolon
    public void caseATwentytwoStmt(ATwentytwoStmt node){
       // System.out.println("\tGot a twentytwo stmt!");
        node.getIdorarray().apply(this);
        node.getDot().apply(this);
        node.getId().apply(this);
        node.getLparen().apply(this);
        node.getVarlisttwo().apply(this);
        node.getRparen().apply(this);
        node.getDotmethod().apply(this);
        node.getSemicolon().apply(this);
    }

    //stmt --> {twentythree} return expr semicolon
    public void caseATwentythreeStmt(ATwentythreeStmt node){
        //System.out.println("\tGot a twentythree stmt!");
        node.getReturn().apply(this);
        node.getExpr().apply(this);
        node.getSemicolon().apply(this);
    }

public void caseAFirstDotmethod(AFirstDotmethod node){
	//System.out.println("\tGot a first Dotmethod!");
	node.getDot().apply(this);
	node.getId().apply(this);
	node.getLparen().apply(this);
	node.getVarlisttwo().apply(this);
	node.getRparen().apply(this);
	node.getDotmethod().apply(this);
}

public void caseASecondDotmethod(ASecondDotmethod node){
	//System.out.println("\tGot a second Dotmethod!");
}


public void caseAFirstMethodstmtseq(AFirstMethodstmtseq node){
	//System.out.println("\tGot a first Methodstmtseq!");
	node.getType().apply(this);
	node.getId().apply(this);
    methodIn();
	node.getLparen().apply(this);
	node.getVarlist().apply(this);
	node.getRparen().apply(this);
	node.getLbrace().apply(this);
	node.getStmtseq().apply(this);
	node.getRbrace().apply(this);
    methodOut();
}

public void caseASecondMethodstmtseq(ASecondMethodstmtseq node){
	//System.out.println("\tGot a Second Methodstmtseq!");
	node.getIdlist().apply(this);
	node.getColon().apply(this);
	node.getType().apply(this);
	node.getSemicolon().apply(this);
}

public void caseAFirstMethodstmtseqs(AFirstMethodstmtseqs node){
	//System.out.println("\tGot a First methodstmtseqs!");
	node.getMethodstmtseq().apply(this);
	node.getMethodstmtseqs().apply(this);
}

public void caseASecondMethodstmtseqs(ASecondMethodstmtseqs node){
	//System.out.println("\tGot a Second methodstmtseqs!");
}

public void caseAFirstClassmethodstmt(AFirstClassmethodstmt node){
	//System.out.println("\tGot a First Classmethodstmt!");
	node.getClasss().apply(this);
	node.getId().apply(this);
	node.getLbrace().apply(this);
	node.getMethodstmtseqs().apply(this);
	node.getRbrace().apply(this);
}

public void caseASecondClassmethodstmt(ASecondClassmethodstmt node){
	//System.out.println("\tGot a Second Classmethodstmt!");
	node.getType().apply(this);
	node.getId().apply(this);
    methodIn();
	node.getLparen().apply(this);
	node.getVarlist().apply(this);
	node.getRparen().apply(this);
	node.getLbrace().apply(this);
	node.getStmtseq().apply(this);
	node.getRbrace().apply(this);
    methodOut();
}

public void caseAThirdClassmethodstmt(AThirdClassmethodstmt node){
	//System.out.println("\tGot a Third Classmethodstmt!");
	node.getIdlist().apply(this);
	node.getColon().apply(this);
	node.getType().apply(this);
	node.getSemicolon().apply(this);
}


public void caseAFirstClassmethodstmts(AFirstClassmethodstmts node){
	//System.out.println("\tGot a First Classmethodstmts!");
	node.getClassmethodstmt().apply(this);
	node.getClassmethodstmts().apply(this);
}

public void caseASecondClassmethodstmts(ASecondClassmethodstmts node){
	//System.out.println("\tGot a Second Classmethodstmts!");
}

	//if it reaches an id, print it off
    public void caseTId(TId node){
		 //System.out.println("\tGot myself an id: <"+node.getText()+">");
         lastid = node.getText();
	}

	//if it reaches an int, print it off
    public void caseTInt(TInt node){
		 //System.out.println("\tGot myself an int: <"+node.getText()+">");
         lastnum = node.getText();
	}

	//if it reaches an real, print it off
    public void caseTReal(TReal node){
		 System.out.println("\tGot myself an real: <"+node.getText()+">");
	}

	//if it reaches an string, print it off
    public void caseTAnychars(TAnychars node){
		 //System.out.println("\tGot myself an anychars: <"+node.getText()+">");
         laststring = node.getText();
	}

	//if it reaches an semi, clear stuff
    public void caseTSemicolon(TSemicolon node){
		 //System.out.println("\tGot myself an semicolon: <"+node.getText()+">");
         for (int i = 0; i < registers.length; i++){
             registers[i] = true;
         }
         arrayoffset = -1;
	}



public void methodIn(){
    curmeth =  SymbolTraverse.st.getMethod(lastid);
    System.out.println(curmeth.name + ":");
    localparams = curmeth.parameters.toArray(new Variable[0]);
    localvars = curmeth.localvars.values().toArray(new Variable[0]);
    int num = localparams.length + localvars.length;
    stacksize = num * 4 + 8;
    System.out.println("\taddi $sp, $sp, " + -1 * stacksize);
    System.out.println("\tsw $s0, 0($sp)");
    System.out.println("\tsw $ra, 4($sp)");
    for (int i = 0; i < localparams.length; i++){
        localparams[i].stack_offset = 8 + 4 * i;
    }
    for (int i = 0; i < localvars.length; i++){
        localvars[i].stack_offset = 8 + 4 * (i + localparams.length);
    }
}

public void methodOut(){
    if (curmeth.name.equals("main")){
        System.out.println("\tli $v0, 10 \n\tsyscall");
    } else {
        System.out.println("\tlw $s0, 0($sp)");
        System.out.println("\tlw $ra, 4($sp)");
        System.out.println("\taddi $sp, $sp, " + stacksize);
        System.out.println("\tjr $ra");
    }
    curmeth = null;
}

public void assign(){
        if (arrayoffset != -1){//if its to an array index
            System.out.println("\tla $t0, " + lastid);
            System.out.println("\tsw $t0, " + arrayoffset + "($t0)");
        } else if (curmeth.containsParam(lastid)){//if its a parameter
            System.out.println("\tsw $t0, " + curmeth.getParam(lastid).stack_offset + "($sp)");
        } else if (curmeth.containsVar(lastid)){ //if it's a local variable
            System.out.println("\tsw $t0, " + curmeth.getVar(lastid).stack_offset + "($sp)");
        } else { //if its a global variable
            System.out.print("\tsw $t0, ");
            System.out.println(lastid);
        }
}

public void getIdValue(){
    int reg = -1;
    for (int i = 0; i < registers.length;i++){
        if (registers[i] == true && reg == -1){
            reg = i;
            registers[i] = false;
            exprstack.push(i);
        }
    }
    if (curmeth.containsParam(lastid)){
        System.out.println("\tlw $t" + reg + ", " + curmeth.getParam(lastid).stack_offset + "($sp)");
    } else if (curmeth.containsVar(lastid)){
        System.out.println("\tlw $t" + reg + ", " + curmeth.getVar(lastid).stack_offset + "($sp)");
    } else if (SymbolTraverse.st.containsVar(lastid)){
        System.out.println("\tlw $t" + reg + ", " + lastid);
    }
}

public void idplusplus(){
    int reg = -1;
    for (int i = 0; i < registers.length;i++){
        if (registers[i] == true && reg == -1){
            reg = i;
            registers[i] = false;
            exprstack.push(i);
        }
    }
    if (curmeth.containsParam(lastid)){
        System.out.println("\tlw $t" + reg + ", " + curmeth.getParam(lastid).stack_offset + "($sp)");
        System.out.println("\taddi $t" + reg + ", $t" + reg + ", 1");
        System.out.println("\tsw $t" + reg + ", " + curmeth.getParam(lastid).stack_offset + "($sp)");
    } else if (curmeth.containsVar(lastid)){
        System.out.println("\tlw $t" + reg + ", " + curmeth.getVar(lastid).stack_offset + "($sp)");
        System.out.println("\taddi $t" + reg + ", $t" + reg + ", 1");
        System.out.println("\tsw $t" + reg + ", " + curmeth.getVar(lastid).stack_offset + "($sp)");
    } else if (SymbolTraverse.st.containsVar(lastid)){
        System.out.println("\tlw $t" + reg + ", " + lastid);
        System.out.println("\taddi $t" + reg + ", $t" + reg + ", 1");
        System.out.println("\tsw $t" + reg + ", " + lastid);
    }
}

public void idminusminus(){
    int reg = -1;
    for (int i = 0; i < registers.length;i++){
        if (registers[i] == true && reg == -1){
            reg = i;
            registers[i] = false;
            exprstack.push(i);
        }
    }
    if (curmeth.containsParam(lastid)){
        System.out.println("\tlw $t" + reg + ", " + curmeth.getParam(lastid).stack_offset + "($sp)");
        System.out.println("\taddi $t" + reg + ", $t" + reg + ", -1");
        System.out.println("\tsw $t" + reg + ", " + curmeth.getParam(lastid).stack_offset + "($sp)");
    } else if (curmeth.containsVar(lastid)){
        System.out.println("\tlw $t" + reg + ", " + curmeth.getVar(lastid).stack_offset + "($sp)");
        System.out.println("\taddi $t" + reg + ", $t" + reg + ", -1");
        System.out.println("\tsw $t" + reg + ", " + curmeth.getVar(lastid).stack_offset + "($sp)");
    } else if (SymbolTraverse.st.containsVar(lastid)){
        System.out.println("\tlw $t" + reg + ", " + lastid);
        System.out.println("\taddi $t" + reg + ", $t" + reg + ", -1");
        System.out.println("\tsw $t" + reg + ", " + lastid);
    }
}

}
