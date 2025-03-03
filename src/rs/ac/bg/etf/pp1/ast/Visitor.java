// generated with ast extension for cup
// version 0.8
// 14/1/2025 17:3:36


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(MethodDecl MethodDecl);
    public void visit(LocalVarList LocalVarList);
    public void visit(MoreVars MoreVars);
    public void visit(Decl_List Decl_List);
    public void visit(LocalVarDecls LocalVarDecls);
    public void visit(ActParams ActParams);
    public void visit(StatementList StatementList);
    public void visit(MethodFormParams MethodFormParams);
    public void visit(OneVar OneVar);
    public void visit(Factor Factor);
    public void visit(IsArray IsArray);
    public void visit(Designator Designator);
    public void visit(MoreConsts MoreConsts);
    public void visit(Term Term);
    public void visit(ArrayDesignatorMore ArrayDesignatorMore);
    public void visit(Terms Terms);
    public void visit(ClassMethodList ClassMethodList);
    public void visit(MulOp MulOp);
    public void visit(WhileCondition WhileCondition);
    public void visit(IfCondition IfCondition);
    public void visit(RelOp RelOp);
    public void visit(CondTerms CondTerms);
    public void visit(LocalVarDecl LocalVarDecl);
    public void visit(Do Do);
    public void visit(Conditions Conditions);
    public void visit(Expr Expr);
    public void visit(LocalVarType LocalVarType);
    public void visit(ActPars ActPars);
    public void visit(AddOp AddOp);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(VarDecls VarDecls);
    public void visit(Const Const);
    public void visit(MethodReturnType MethodReturnType);
    public void visit(FormParamList FormParamList);
    public void visit(Decl Decl);
    public void visit(Statement Statement);
    public void visit(VarDecl VarDecl);
    public void visit(ConstNameDecl ConstNameDecl);
    public void visit(ClassDecl ClassDecl);
    public void visit(ConstDecl ConstDecl);
    public void visit(CondFact CondFact);
    public void visit(MethodDeclList MethodDeclList);
    public void visit(VarType VarType);
    public void visit(DesignatorMore DesignatorMore);
    public void visit(MethodVars MethodVars);
    public void visit(ExtendsClass ExtendsClass);
    public void visit(FormParam FormParam);
    public void visit(ClassMethods ClassMethods);
    public void visit(FuncDesign FuncDesign);
    public void visit(RelOpEq RelOpEq);
    public void visit(RelOpNeq RelOpNeq);
    public void visit(RelOpLe RelOpLe);
    public void visit(RelOpLow RelOpLow);
    public void visit(RelOpGre RelOpGre);
    public void visit(RelOpGr RelOpGr);
    public void visit(SetOp SetOp);
    public void visit(AssignOp AssignOp);
    public void visit(Mod Mod);
    public void visit(Div Div);
    public void visit(Mul Mul);
    public void visit(Sub Sub);
    public void visit(Add Add);
    public void visit(Label Label);
    public void visit(TermMulOp TermMulOp);
    public void visit(TermFactor TermFactor);
    public void visit(TermsAddOp TermsAddOp);
    public void visit(OnlyTerm OnlyTerm);
    public void visit(FinalCondition FinalCondition);
    public void visit(ConditionsDerived2 ConditionsDerived2);
    public void visit(ConditionsDerived1 ConditionsDerived1);
    public void visit(IfCondError IfCondError);
    public void visit(IfCond IfCond);
    public void visit(CondTerm CondTerm);
    public void visit(CondFactAnd CondFactAnd);
    public void visit(OneCondFact OneCondFact);
    public void visit(CondFactRelOp CondFactRelOp);
    public void visit(CondFactExpr CondFactExpr);
    public void visit(FactorExpr FactorExpr);
    public void visit(FactorNewArray FactorNewArray);
    public void visit(FactorNew FactorNew);
    public void visit(FactorBoolConst FactorBoolConst);
    public void visit(FactorCharConst FactorCharConst);
    public void visit(FactorNumConst FactorNumConst);
    public void visit(FactorFunction FactorFunction);
    public void visit(FactorVar FactorVar);
    public void visit(FunctionDesignator FunctionDesignator);
    public void visit(ArrayDesignator ArrayDesignator);
    public void visit(DesignatorArray DesignatorArray);
    public void visit(ClassDesignator ClassDesignator);
    public void visit(OneDesignator OneDesignator);
    public void visit(NoActPars NoActPars);
    public void visit(ActParam ActParam);
    public void visit(ActPar ActPar);
    public void visit(ActParamsList ActParamsList);
    public void visit(DesignatorSetOp DesignatorSetOp);
    public void visit(DesignatorDec DesignatorDec);
    public void visit(DesignatorInc DesignatorInc);
    public void visit(DesignatorFunction DesignatorFunction);
    public void visit(AssignError AssignError);
    public void visit(DesignatorAssign DesignatorAssign);
    public void visit(ExprMap ExprMap);
    public void visit(NegativeExpr NegativeExpr);
    public void visit(Expression Expression);
    public void visit(NoWhileCondition NoWhileCondition);
    public void visit(WhileConditionStmt WhileConditionStmt);
    public void visit(WhileConditionOnly WhileConditionOnly);
    public void visit(NoStatements NoStatements);
    public void visit(HasStatements HasStatements);
    public void visit(DoStmt DoStmt);
    public void visit(StmtList StmtList);
    public void visit(DoWhileStmt DoWhileStmt);
    public void visit(PrintStmtExprConst PrintStmtExprConst);
    public void visit(PrintStmtExpr PrintStmtExpr);
    public void visit(PrintEmptyStmt PrintEmptyStmt);
    public void visit(ReadDesignatorStatement ReadDesignatorStatement);
    public void visit(ReturnExpr ReturnExpr);
    public void visit(ReturnStmt ReturnStmt);
    public void visit(ContinueStmt ContinueStmt);
    public void visit(BreakStmt BreakStmt);
    public void visit(IfElseStmt IfElseStmt);
    public void visit(IfStmt IfStmt);
    public void visit(DesignatorStmt DesignatorStmt);
    public void visit(StartElse StartElse);
    public void visit(EndOfIf EndOfIf);
    public void visit(ReturnVoid ReturnVoid);
    public void visit(ReturnType ReturnType);
    public void visit(MethodName MethodName);
    public void visit(NoMethods NoMethods);
    public void visit(MethodList MethodList);
    public void visit(NoMethodVariables NoMethodVariables);
    public void visit(MethodHasVariables MethodHasVariables);
    public void visit(MethodDeclaration MethodDeclaration);
    public void visit(NoMethodFormParams NoMethodFormParams);
    public void visit(MethodFormParamList MethodFormParamList);
    public void visit(FormParamsErrorParen FormParamsErrorParen);
    public void visit(FormParamsErrorComa FormParamsErrorComa);
    public void visit(OneFormParam OneFormParam);
    public void visit(MoreFormParams MoreFormParams);
    public void visit(FormParamDef FormParamDef);
    public void visit(ClassName ClassName);
    public void visit(ClassDeclaration ClassDeclaration);
    public void visit(ClassNameDecl ClassNameDecl);
    public void visit(NoClassMethods NoClassMethods);
    public void visit(ClassMethodsDecl ClassMethodsDecl);
    public void visit(OneClassMethod OneClassMethod);
    public void visit(MoreClassMethods MoreClassMethods);
    public void visit(NoLocalVars NoLocalVars);
    public void visit(LocalVars LocalVars);
    public void visit(LocalVarDeclType LocalVarDeclType);
    public void visit(LocalVarDeclarationError LocalVarDeclarationError);
    public void visit(LocalVarDeclaration LocalVarDeclaration);
    public void visit(OneLocalVar OneLocalVar);
    public void visit(MoreLocalVars MoreLocalVars);
    public void visit(LocalVarIdent LocalVarIdent);
    public void visit(ExtendsError ExtendsError);
    public void visit(NotExtendingClass NotExtendingClass);
    public void visit(IsExtendingClass IsExtendingClass);
    public void visit(VarNotArray VarNotArray);
    public void visit(VarIsArray VarIsArray);
    public void visit(VarDeclType VarDeclType);
    public void visit(VarDeclaration VarDeclaration);
    public void visit(OneVarDeclaration OneVarDeclaration);
    public void visit(MultipleVarsDeclaration MultipleVarsDeclaration);
    public void visit(MoreVarsDeclError MoreVarsDeclError);
    public void visit(MoreVarsDecl MoreVarsDecl);
    public void visit(OneVarDeclError OneVarDeclError);
    public void visit(OneVarDecl OneVarDecl);
    public void visit(ConstType ConstType);
    public void visit(ConstError ConstError);
    public void visit(ConstDeclaration ConstDeclaration);
    public void visit(MoreConstError MoreConstError);
    public void visit(NoMoreConstatns NoMoreConstatns);
    public void visit(MoreConstants MoreConstants);
    public void visit(BoolConstValue BoolConstValue);
    public void visit(CharConstValue CharConstValue);
    public void visit(NumConstValue NumConstValue);
    public void visit(Bool_Const Bool_Const);
    public void visit(Char_Const Char_Const);
    public void visit(Num_Const Num_Const);
    public void visit(Type Type);
    public void visit(NoDeclList NoDeclList);
    public void visit(ClassDeclList ClassDeclList);
    public void visit(VarDeclList VarDeclList);
    public void visit(ConstDeclList ConstDeclList);
    public void visit(ProgName ProgName);
    public void visit(Program Program);

}
