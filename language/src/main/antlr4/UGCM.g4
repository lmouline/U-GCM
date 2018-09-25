grammar UGCM;

fragment ESC :   '\\' (["\\/bfnrt] | UNICODE) ;
fragment UNICODE : 'u' HEX HEX HEX HEX ;
fragment HEX : [0-9a-fA-F] ;
STRING :  '"' (ESC | ~["\\])* '"' | '\'' (ESC | ~["\\])* '\'' ;
IDENT : [a-zA-Z_][a-zA-Z_0-9]*;
NUMBER : [\-]?[0-9]+'.'?[0-9]*;
WS : ([ \t\r\n]+ | SL_COMMENT | ML_COMMENT) -> skip ; // skip spaces, tabs, newlines
SL_COMMENT :  '//' ~('\r' | '\n')* ;
ML_COMMENT : '/*' .*? '*/' -> skip;


modelDcl: (classDcl)*;
fullIdentDcl: IDENT ('.' IDENT)*;
classDcl: 'class' name=fullIdentDcl parent=parentDcl? '{' (attributeDcl|relationDcl)* '}';
parentDcl: ':' fullIdentDcl (',' fullIdentDcl)*;
attributeDcl: ('att' | 'id') name=IDENT ':' type=IDENT;
relationDcl: ('ref' | 'contained') name=IDENT ':' type=fullIdentDcl multiplicity=multiplicityDcl? opposite=oppisteDcl?;
multiplicityDcl: '[' lower=('*'|NUMBER) ',' upper=('*'|NUMBER) ']';
oppisteDcl: 'oppositeOf' opp=IDENT;