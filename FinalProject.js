Package FinalProject;

Helpers
    sp  = ' ' | 10 | 13 | 9;
    digit = ['0'..'9'];
    letter = ['a'..'z'] | ['A'..'Z'];
    alphanumeric = letter | digit;
    anychar = [35..255] | ' ';
    
Tokens
    semicolon = ';';
    lparen = '(';
    rparen = ')';
    lbrace = '{';
    rbrace = '}';
    lbracket = '[';
    rbracket = ']';
    comma = ',';
    dot = '.';
    quote = '"';
    
    begin = 'BEGIN';
    end = 'END';
    classs = 'CLASS';
    if = 'IF';
    then = 'THEN';
    else = 'ELSE';
    while = 'WHILE';
    for = 'FOR';
    get = 'GET';
    put = 'PUT';
    new = 'NEW';
    return = 'RETURN';
    switch = 'SWITCH';
    case = 'CASE';
    break = 'BREAK';
    default = 'DEFAULT';
    true = 'TRUE';
    false = 'FALSE';
    
    intdecl = 'INT';
    realdecl = 'REAL';
    stringdecl = 'STRING';
    booleandecl = 'BOOLEAN';
    voiddecl = 'VOID';
    
    colon = ':';
    assign = ':=';
    
    equalequal = '==';
    notequal = '!=';
    geq = '>=';
    leq = '<=';
    gt = '>';
    lt = '<';
    
    minus = '-';
    plus = '+';
    times = '*';
    divide = '/';
    
    
    int = digit+;
    real = digit* '.' digit+;
    id = letter (letter | digit | '_')*;
    whitespace = sp+;
    anychars = '"' anychar* '"';
    
Ignored Tokens
    whitespace;

Productions
    prog = begin classmethodstmts end;
    type = {first} intdecl |
      {second} realdecl |
      {third} voiddecl |
      {fourth} booleandecl |
      {fifth} stringdecl |
      {sixth} id;
    multop = {first} times |
      {second} divide;
    addop = {first} plus |
      {second} minus;
    cond = {first} equalequal |
      {second} notequal |
      {third} leq |
      {fourth} geq |
      {fifth} lt |
      {sixth} gt;
    factor = {first} minus factor |
      {second} int |
      {third} real |
      {fourth} id |
      {fifth} id lbracket int rbracket |
      {sixth} id lparen varlisttwo rparen |
      {seventh} [object]:id dot [field]:id lparen varlisttwo rparen |
      {eighth} [object]:id lbracket int rbracket dot [field]:id lparen varlisttwo rparen |
      {ninth} [object]:id dot [field]:id |
      {tenth} lparen expr rparen;
      // cause of ambiguous grammar{eleventh} boolean;
    boolean = {first} true |
      {second} false |
      {third} id |
      {fourth} [one]:expr cond [two]:expr |
      {fifth} [object]:id dot [field]:id |
      {sixth} [object]:id lbracket int rbracket dot [field]:id;
    term = {first} term multop factor |
      {second} factor;
    expr = {first} expr addop term |
      {second} term;
    varlisttwo = {first} |
      {second} expr |
      {third} expr comma varlisttwo;
    varlist = {first} |
      {second} id colon type |
      {third} id colon type lbracket int rbracket |
      {fourth} id colon type comma varlist |
      {fifth} id colon type lbracket int rbracket comma varlist;
    idlist = {first} id |
      {second} id comma idlist;
    stmtseq = {first} |
      {second} stmt stmtseq;
    idorarray = {first} id |
      {second} id lbracket int rbracket;
    stmt = {first} idorarray assign expr semicolon |
      {third} idorarray assign anychars semicolon |
      {fifth} idlist colon type semicolon |
      {sixth} idlist colon type lbracket int rbracket semicolon |
      {seventh} if lparen boolean rparen then lbrace stmtseq rbrace | 
      {eighth} if lparen boolean rparen then [one]:lbrace [five]:stmtseq [three]:rbrace else [two]:lbrace [six]:stmtseq [four]:rbrace |
      {ninth} while lparen boolean rparen lbrace stmtseq rbrace |
      {tenth} for lparen [five]:id assign expr [one]:semicolon boolean [two]:semicolon [six]:id [three]:plus [four]:plus rparen lbrace stmtseq rbrace |      
      {eleventh} for lparen type [five]:id assign expr [one]:semicolon boolean [two]:semicolon [six]:id [three]:plus [four]:plus rparen lbrace stmtseq rbrace |
      {twelveth} for lparen [five]:id assign expr [one]:semicolon boolean [two]:semicolon [six]:id [three]:minus [four]:minus rparen lbrace stmtseq rbrace |      
      {thirteenth} for lparen type [five]:id assign expr [one]:semicolon boolean [two]:semicolon [six]:id [three]:minus [four]:minus rparen lbrace stmtseq rbrace |
      {fourteenth} for lparen [five]:id [aba]:assign [aca]:expr [one]:semicolon boolean [two]:semicolon [six]:id [gay]:assign [aaa]:expr rparen lbrace stmtseq rbrace |     
      {fifteenth} for lparen type [five]:id [aba]:assign [aca]:expr [one]:semicolon boolean [two]:semicolon [six]:id [gay]:assign [aaa]:expr rparen lbrace stmtseq rbrace |
      {sixteenth} idorarray assign get lparen rparen semicolon |
      {seventeenth} put lparen idorarray rparen semicolon |
      {eighteenth} idorarray [first]:plus [second]:plus semicolon |
      {nineteenth} idorarray [first]:minus [second]:minus semicolon |
      {twenty} idorarray assign new id lparen rparen semicolon |
      {twentyone} id lparen varlisttwo rparen semicolon |
      {twentytwo} idorarray dot id lparen varlisttwo rparen dotmethod semicolon |
      {twentythree} return expr semicolon ;
    dotmethod = {first} dot id lparen varlisttwo rparen dotmethod |
      {second} ;
    methodstmtseq = {first} type id lparen varlist rparen lbrace stmtseq rbrace |
      {second} idlist colon type semicolon;
    methodstmtseqs = {first} methodstmtseq methodstmtseqs |
      {second} ;
    classmethodstmt = {first} classs id lbrace methodstmtseqs rbrace |
      {second} type id lparen varlist rparen lbrace stmtseq rbrace |
      {third} idlist colon type semicolon;
    classmethodstmts = {first} classmethodstmt classmethodstmts |
      {second} ;
   
    
    
    
    