package FinalProject;

import FinalProject.analysis.*;
import FinalProject.node.*;
import java.util.*;


class CheckType extends DepthFirstAdapter
{
    Method curmeth = null;
    String lhsid = null;
    String lhstype = null;
    String rhstype = null;
    String rhslastid = null;
    String lastid = null;
 	public CheckType() {
		//System.out.println("Start of the Printing Action");
	}

	//this gets called if the production is prog --> id digit
    public void caseAProg(AProg node){
        //System.out.println("\tGot a first prog!");
        node.getBegin().apply(this);
        node.getClassmethodstmts().apply(this);
        node.getEnd().apply(this);
    }

	//term --> {first} term multop factor
    public void caseAFirstTerm(AFirstTerm node){
        //System.out.println("\tGot a first term!");
        node.getTerm().apply(this);
        node.getMultop().apply(this);
        node.getFactor().apply(this);
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
        node.getAddop().apply(this);
        node.getTerm().apply(this);
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
        node.getIdorarray().apply(this);
        if(curmeth.containsVar(lastid)){
            lhstype = curmeth.getVar(lastid).type;
        } else if (curmeth.containsParam(lastid)){
            lhstype = curmeth.getParam(lastid).type;
        }
        node.getAssign().apply(this);
        node.getExpr().apply(this);
        if (!lhstype.equals(rhstype)){
            System.out.println("attempting to store a value in the wrong type variable \n" + lastid + " is " + lhstype + " not " + rhstype);
            System.exit(1);
        }
        node.getSemicolon().apply(this);
    }

    //stmt --> {third} idorarray assign anychars semicolon
    public void caseAThirdStmt(AThirdStmt node){
        //System.out.println("\tGot a third stmt!");
        node.getIdorarray().apply(this);
        if(curmeth.containsVar(lastid)){
            lhstype = curmeth.getVar(lastid).type;
        } else if (curmeth.containsParam(lastid)){
            lhstype = curmeth.getParam(lastid).type;
        }
        if (!lhstype.equals("STRING")){
            System.out.println("types don't match. trying to assign STRING to " + lhstype);
            System.exit(1);
        }
        node.getAssign().apply(this);
        node.getAnychars().apply(this);
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
        node.getThen().apply(this);
        node.getLbrace().apply(this);
        node.getStmtseq().apply(this);
        node.getRbrace().apply(this);
    }

    //stmt --> {eighth} if lparen boolean rparen then [one]:lbrace [five]:stmtseq [three]:rbrace else [two]:lbrace [six]:stmtseq [four]:rbrace
    public void caseAEighthStmt(AEighthStmt node){
        //System.out.println("\tGot a eighth stmt!");
        node.getIf().apply(this);
        node.getLparen().apply(this);
        node.getBoolean().apply(this);
        node.getRparen().apply(this);
        node.getThen().apply(this);
        node.getOne().apply(this);
        node.getFive().apply(this);
        node.getThree().apply(this);
        node.getElse().apply(this);
        node.getTwo().apply(this);
        node.getSix().apply(this);
        node.getFour().apply(this);
    }

    //stmt --> {ninth} while lparen boolean rparen lbrace stmtseq rbrace
    public void caseANinthStmt(ANinthStmt node){
        //System.out.println("\tGot a ninth stmt!");
        node.getWhile().apply(this);
        node.getLparen().apply(this);
        node.getBoolean().apply(this);
        node.getRparen().apply(this);
        node.getLbrace().apply(this);
        node.getStmtseq().apply(this);
        node.getRbrace().apply(this);
    }

    //stmt --> {tenth} for lparen [five]:id assign expr [one]:semicolon boolean [two]:semicolon [six]:id [three]:plus [four]:plus rparen lbrace stmtseq rbrace
    public void caseATenthStmt(ATenthStmt node){
        //System.out.println("\tGot a tenth stmt!");
        node.getFor().apply(this);
        node.getLparen().apply(this);
        node.getFive().apply(this);
        if(curmeth.containsVar(lastid)){
            lhstype = curmeth.getVar(lastid).type;
        } else if (curmeth.containsParam(lastid)){
            lhstype = curmeth.getParam(lastid).type;
        }
        node.getAssign().apply(this);
        node.getExpr().apply(this);
        if (!lhstype.equals(rhstype)){
            System.out.println("attempting to store a value in the wrong type variable \n" + lastid + " is " + lhstype + " not " + rhstype);
            System.exit(1);
        }
        node.getOne().apply(this);
        node.getBoolean().apply(this);
        node.getTwo().apply(this);
        node.getSix().apply(this);
        if(curmeth.containsVar(lastid)){
            lhstype = curmeth.getVar(lastid).type;
        } else if (curmeth.containsParam(lastid)){
            lhstype = curmeth.getParam(lastid).type;
        }
        if (!lhstype.equals("INT")){
            System.out.println("you can only use ++ or -- on ant int, not a " + lhstype + " for variable " + lastid);
            System.exit(1);
        }
        node.getThree().apply(this);
        node.getFour().apply(this);
        node.getRparen().apply(this);
        node.getLbrace().apply(this);
        node.getStmtseq().apply(this);
        node.getRbrace().apply(this);
    }

    //stmt --> {eleventh} for lparen type [five]:id assign expr [one]:semicolon boolean [two]:semicolon [six]:id [three]:plus [four]:plus rparen lbrace stmtseq rbrace
    public void caseAEleventhStmt(AEleventhStmt node){
        //System.out.println("\tGot a eleventh stmt!");
        node.getFor().apply(this);
        node.getLparen().apply(this);
        node.getType().apply(this);
        node.getFive().apply(this);
        node.getAssign().apply(this);
        node.getExpr().apply(this);
        node.getOne().apply(this);
        node.getBoolean().apply(this);
        node.getTwo().apply(this);
        node.getSix().apply(this);
        if(curmeth.containsVar(lastid)){
            lhstype = curmeth.getVar(lastid).type;
        } else if (curmeth.containsParam(lastid)){
            lhstype = curmeth.getParam(lastid).type;
        }
        if (!lhstype.equals("INT")){
            System.out.println("you can only use ++ or -- on ant int, not a " + lhstype + " for variable " + lastid);
            System.exit(1);
        }
        node.getThree().apply(this);
        node.getFour().apply(this);
        node.getRparen().apply(this);
        node.getLbrace().apply(this);
        node.getStmtseq().apply(this);
        node.getRbrace().apply(this);
    }

    //stmt --> {twelveth} for lparen [five]:id assign expr [one]:semicolon boolean [two]:semicolon [six]:id [three]:minus [four]:minus rparen lbrace stmtseq rbrace
    public void caseATwelvethStmt(ATwelvethStmt node){
        //System.out.println("\tGot a twelveth stmt!");
        node.getFor().apply(this);
        node.getLparen().apply(this);
        node.getFive().apply(this);
        if(curmeth.containsVar(lastid)){
            lhstype = curmeth.getVar(lastid).type;
        } else if (curmeth.containsParam(lastid)){
            lhstype = curmeth.getParam(lastid).type;
        }
        node.getAssign().apply(this);
        node.getExpr().apply(this);
        if (!lhstype.equals(rhstype)){
            System.out.println("attempting to store a value in the wrong type variable \n" + lastid + " is " + lhstype + " not " + rhstype);
            System.exit(1);
        }
        node.getOne().apply(this);
        node.getBoolean().apply(this);
        node.getTwo().apply(this);
        node.getSix().apply(this);
        if(curmeth.containsVar(lastid)){
            lhstype = curmeth.getVar(lastid).type;
        } else if (curmeth.containsParam(lastid)){
            lhstype = curmeth.getParam(lastid).type;
        }
        if (!lhstype.equals("INT")){
            System.out.println("you can only use ++ or -- on ant int, not a " + lhstype + " for variable " + lastid);
            System.exit(1);
        }
        node.getThree().apply(this);
        node.getFour().apply(this);
        node.getRparen().apply(this);
        node.getLbrace().apply(this);
        node.getStmtseq().apply(this);
        node.getRbrace().apply(this);
    }

    //stmt --> {thirteenth} for lparen type [five]:id assign expr [one]:semicolon boolean [two]:semicolon [six]:id [three]:minus [four]:minus rparen lbrace stmtseq rbrace
    public void caseAThirteenthStmt(AThirteenthStmt node){
        //System.out.println("\tGot a thirteenth stmt!");
        node.getFor().apply(this);
        node.getLparen().apply(this);
        node.getType().apply(this);
        node.getFive().apply(this);
        node.getAssign().apply(this);
        node.getExpr().apply(this);
        node.getOne().apply(this);
        node.getBoolean().apply(this);
        node.getTwo().apply(this);
        node.getSix().apply(this);
        if(curmeth.containsVar(lastid)){
            lhstype = curmeth.getVar(lastid).type;
        } else if (curmeth.containsParam(lastid)){
            lhstype = curmeth.getParam(lastid).type;
        }
        if (!lhstype.equals("INT")){
            System.out.println("you can only use ++ or -- on ant int, not a " + lhstype + " for variable " + lastid);
            System.exit(1);
        }
        node.getThree().apply(this);
        node.getFour().apply(this);
        node.getRparen().apply(this);
        node.getLbrace().apply(this);
        node.getStmtseq().apply(this);
        node.getRbrace().apply(this);
    }

    //stmt --> {fourteenth} for lparen [five]:id [aba]:assign [aca]:expr [one]:semicolon boolean [two]:semicolon [six]:id [three]:plus [four]:plus [gay]:assign [aaa]:expr rparen lbrace stmtseq rbrace
    public void caseAFourteenthStmt(AFourteenthStmt node){
        //System.out.println("\tGot a fourteenth stmt!");
        node.getFor().apply(this);
        node.getLparen().apply(this);
        node.getFive().apply(this);
        if(curmeth.containsVar(lastid)){
            lhstype = curmeth.getVar(lastid).type;
        } else if (curmeth.containsParam(lastid)){
            lhstype = curmeth.getParam(lastid).type;
        }
        node.getAba().apply(this);
        node.getAca().apply(this);
        if (!lhstype.equals(rhstype)){
            System.out.println("attempting to store a value in the wrong type variable \n" + lastid + " is " + lhstype + " not " + rhstype);
            System.exit(1);
        }
        node.getOne().apply(this);
        node.getBoolean().apply(this);
        node.getTwo().apply(this);
        node.getSix().apply(this);
        if(curmeth.containsVar(lastid)){
            lhstype = curmeth.getVar(lastid).type;
        } else if (curmeth.containsParam(lastid)){
            lhstype = curmeth.getParam(lastid).type;
        }
        node.getGay().apply(this);
        node.getAaa().apply(this);
        if (!lhstype.equals(rhstype)){
            System.out.println("attempting to store a value in the wrong type variable \n" + lastid + " is " + lhstype + " not " + rhstype);
            System.exit(1);
        }
        node.getRparen().apply(this);
        node.getLbrace().apply(this);
        node.getStmtseq().apply(this);
        node.getRbrace().apply(this);
    }

    //stmt --> {fifteenth} for type lparen [five]:id [aba]:assign [aca]:expr [one]:semicolon boolean [two]:semicolon [six]:id [three]:plus [four]:plus [gay]:assign [aaa]:expr rparen lbrace stmtseq rbrace
    public void caseAFifteenthStmt(AFifteenthStmt node){
        //System.out.println("\tGot a fifteenth stmt!");
        node.getFor().apply(this);
        node.getType().apply(this);
        node.getLparen().apply(this);
        node.getFive().apply(this);
        node.getAba().apply(this);
        node.getAca().apply(this);
        node.getOne().apply(this);
        node.getBoolean().apply(this);
        node.getTwo().apply(this);
        node.getSix().apply(this);
        if(curmeth.containsVar(lastid)){
            lhstype = curmeth.getVar(lastid).type;
        } else if (curmeth.containsParam(lastid)){
            lhstype = curmeth.getParam(lastid).type;
        }
        node.getGay().apply(this);
        node.getAaa().apply(this);
        if (!lhstype.equals(rhstype)){
            System.out.println("attempting to store a value in the wrong type variable \n" + lastid + " is " + lhstype + " not " + rhstype);
            System.exit(1);
        }
        node.getRparen().apply(this);
        node.getLbrace().apply(this);
        node.getStmtseq().apply(this);
        node.getRbrace().apply(this);
    }

    //stmt --> {sixteenth} idorarray assign get lparen rparen semicolon
    public void caseASixteenthStmt(ASixteenthStmt node){
        //System.out.println("\tGot a sixteenth stmt!");
        node.getIdorarray().apply(this);
        node.getAssign().apply(this);
        node.getGet().apply(this);
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
        node.getRparen().apply(this);
        node.getSemicolon().apply(this);
    }

    //stmt --> {eighteenth} idorarray [first]:plus [second]:plus semicolon
    public void caseAEighteenthStmt(AEighteenthStmt node){
        //System.out.println("\tGot a eightteenth stmt!");
        node.getIdorarray().apply(this);
        if(curmeth.containsVar(lastid)){
            lhstype = curmeth.getVar(lastid).type;
        } else if (curmeth.containsParam(lastid)){
            lhstype = curmeth.getParam(lastid).type;
        }
        if (!lhstype.equals("INT")){
            System.out.println("you can only use ++ or -- on ant int, not a " + lhstype + " for variable " + lastid);
            System.exit(1);
        }
        node.getFirst().apply(this);
        node.getSecond().apply(this);
        node.getSemicolon().apply(this);
    }

    //stmt --> {nineteenth} idorarray [first]:minus [second]:minus semicolon
    public void caseANineteenthStmt(ANineteenthStmt node){
        //System.out.println("\tGot a nineteenth stmt!");
        node.getIdorarray().apply(this);
        if(curmeth.containsVar(lastid)){
            lhstype = curmeth.getVar(lastid).type;
        } else if (curmeth.containsParam(lastid)){
            lhstype = curmeth.getParam(lastid).type;
        }
        if (!lhstype.equals("INT")){
            System.out.println("you can only use ++ or -- on ant int, not a " + lhstype + " for variable " + lastid);
            System.exit(1);
        }
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
        //System.out.println("\tGot a twentytwo stmt!");
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
}

public void caseASecondMultop(ASecondMultop node){
	//System.out.println("\tGot a second Multop!");
	node.getDivide().apply(this);
}

//addop --> {first} plus
public void caseAFirstAddop(AFirstAddop node){
	//System.out.println("\tGot a first Addop!");
	node.getPlus().apply(this);
}

public void caseASecondAddop(ASecondAddop node){
	//System.out.println("\tGot a second Addop!");
	node.getMinus().apply(this);
}


//cond --> {first} equalequal
public void caseAFirstCond(AFirstCond node){
	//System.out.println("\tGot a First Cond!");
	node.getEqualequal().apply(this);
}

public void caseASecondCond(ASecondCond node){
	//System.out.println("\tGot a Second Cond!");
	node.getNotequal().apply(this);
}

public void caseAThirdCond(AThirdCond node){
	//System.out.println("\tGot a Third Cond!");
	node.getLeq().apply(this);
}

public void caseAFourthCond(AFourthCond node){
	//System.out.println("\tGot a Fourth Cond!");
	node.getGeq().apply(this);
}

public void caseAFifthCond(AFifthCond node){
	//System.out.println("\tGot a Fifth Cond!");
	node.getLt().apply(this);
}

public void caseASixthCond(ASixthCond node){
	//System.out.println("\tGot a Sixth Cond!");
	node.getGt().apply(this);
}


public void caseAFirstFactor(AFirstFactor node){
	//System.out.println("\tGet a first factor!");
	node.getMinus().apply(this);
	node.getFactor().apply(this);
    if ((!rhstype.equals("INT")) && (!rhstype.equals("REAL"))){
        System.out.println("you can only take the negative of a number");
        System.exit(1);
    }
}

public void caseASecondFactor(ASecondFactor node){
	//System.out.println("\tGet a second factor!");
	node.getInt().apply(this);
    rhstype = "INT";
}

public void caseAThirdFactor(AThirdFactor node){
	//System.out.println("\tGet a Third factor!");
	node.getReal().apply(this);
    rhstype = "REAL";
}

public void caseAFourthFactor(AFourthFactor node){
	//System.out.println("\tGet a fourth factor!");
	node.getId().apply(this);
    if(curmeth.containsVar(lastid)){
        rhstype = curmeth.getVar(lastid).type;
    } else if (curmeth.containsParam(lastid)){
        rhstype = curmeth.getParam(lastid).type;
    }
}

public void caseAFifthFactor(AFifthFactor node){
	//System.out.println("\tGet a fifth factor!");
	node.getId().apply(this);
    if(curmeth.containsVar(lastid)){
        rhstype = curmeth.getVar(lastid).type;
    } else if (curmeth.containsParam(lastid)){
        rhstype = curmeth.getParam(lastid).type;
    }
    if(rhstype.equals("STRING")){
        System.out.println("you cannot do math on a string");
        System.exit(1);
    }
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
}

public void caseASecondBoolean(ASecondBoolean node){
	//System.out.println("\tGot a Second Boolean!");
	node.getFalse().apply(this);
}

public void caseAThirdBoolean(AThirdBoolean node){
	//System.out.println("\tGot a Third Boolean!");
	node.getId().apply(this);
}

public void caseAFourthBoolean(AFourthBoolean node){
	//System.out.println("\tGot a Fourth Boolean!");
	node.getOne().apply(this);
	node.getCond().apply(this);
	node.getTwo().apply(this);
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
    curmeth = SymbolTraverse.st.getMethod(lastid);
	node.getLparen().apply(this);
	node.getVarlist().apply(this);
	node.getRparen().apply(this);
	node.getLbrace().apply(this);
	node.getStmtseq().apply(this);
	node.getRbrace().apply(this);
    curmeth = null;
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
    curmeth = SymbolTraverse.st.getMethod(lastid);
	node.getLparen().apply(this);
	node.getVarlist().apply(this);
	node.getRparen().apply(this);
	node.getLbrace().apply(this);
	node.getStmtseq().apply(this);
	node.getRbrace().apply(this);
    curmeth = null;
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
        
        
	}

	//if it reaches an real, print it off
    public void caseTReal(TReal node){
		 //System.out.println("\tGot myself an real: <"+node.getText()+">");
         System.out.println("Unfortunately, we do not support real numbers at this time. Your number : " + node.getText() + " cannot be evaluated");
         System.exit(0);
	}

	//if it reaches an string, print it off
    public void caseTAnychars(TAnychars node){
		 //System.out.println("\tGot myself an anychars: <"+node.getText()+">");
	}

    //if it reaches an semicolon, 
    public void caseTSemicolon(TSemicolon node){
        rhstype = null;
	}

}

