package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Stack;

import rs.ac.bg.etf.pp1.CounterVisitor.FormalParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.ast.PrintEmptyStmt;
import rs.ac.bg.etf.pp1.ast.PrintStmtExpr;
import rs.ac.bg.etf.pp1.ast.PrintStmtExprConst;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;



public class CodeGenerator extends VisitorAdaptor {
	private int mainPc;
	
	private boolean inDoWhile = false;
	private int ret = 0;
	
	//IF LOOP
	private Stack<Integer> jumpFixUp1 = new Stack<Integer>();
	private Stack<Integer> jumpAddress = new Stack<Integer>();
	private Stack<Integer> jumpFixUp2 = new Stack<Integer>();
  	private Stack<Integer> jumpElse = new Stack<Integer>();
  	private Stack<Integer> whileStart = new Stack<Integer>();
 	private Stack<ArrayList<Integer>> breakJumps = new Stack<ArrayList<Integer>>();
 
  	
	public int getMainPc() {
		return mainPc;
	}
	
	public void setMainPc(int x) {
		mainPc = x;
	}
	
	public CodeGenerator() {
		Obj chrMeth = Tab.find("chr");
		Obj ordMeth = Tab.find("ord");
		
		ordMeth.setAdr(Code.pc);
		chrMeth.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);
		
		Obj lenMeth = Tab.find("len");
	
		lenMeth.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n);
		Code.put(Code.arraylength);
		Code.put(Code.exit);
		Code.put(Code.return_);
		
		
	}
	
	
	
	// PRINT STATEMENTS
	public void visit(PrintStmtExpr stmt) {
		if((stmt.getExpr().struct == Tab.intType) || (stmt.getExpr().struct == SemanticAnalyzer.boolStruct)) {
			Code.put(Code.const_5);
			Code.put(Code.print);
		}else {
			Code.put(Code.const_5);
			Code.put(Code.bprint);
		}
	}
	
	public void visit(PrintStmtExprConst stmt) {
		
		Code.loadConst(stmt.getN2());
		
		if((stmt.getExpr().struct == Tab.intType) || (stmt.getExpr().struct == SemanticAnalyzer.boolStruct)) {
			Code.put(Code.print);
		}else {
			Code.put(Code.bprint);
		}
	}
	
	public void visit(PrintEmptyStmt stmt) {
		Code.put(Code.print);
	}
	
	//METHODS
	public void visit(MethodName name) {
		if("main".equalsIgnoreCase(name.getMethName())){
			mainPc = Code.pc;
		}
		
		
		
		name.obj.setAdr(Code.pc);
		
		SyntaxNode method = name.getParent();
		
		VarCounter varCounter = new VarCounter();
		method.traverseTopDown(varCounter);
		
		FormalParamCounter fpCounter = new FormalParamCounter();
		method.traverseTopDown(fpCounter);
		
		Code.put(Code.enter);
		Code.put(fpCounter.getCount());
		Code.put(fpCounter.getCount()+varCounter.getCount());	
	}
	
	public void visit(MethodDecl decl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
		
	}
	
	// RETURN
	
	public void visit(ReturnExpr ret) {
		Code.put(Code.exit);
		Code.put(Code.return_);
		this.ret--;
	}
	
	public void visit(ReturnStmt ret) {
		Code.put(Code.exit);
		Code.put(Code.return_);
		this.ret--;
	}
	
	//DESIGNATOR
	public void visit(DesignatorAssign desig) {
		Code.store(desig.getDesignator().obj);
	}
	
	public void visit(DesignatorFunction func) {
		Obj desig = func.getFuncDesign().obj;
		int offset = desig.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		
		if(desig.getType() != Tab.noType) {
			Code.put(Code.pop);
		}
	}
	
	public void visit(DesignatorInc inc ) {
		if(inc.getDesignator().obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		
		Code.load(inc.getDesignator().obj);
		Code.put(Code.const_1);
		Code.put(Code.add);
		Code.store(inc.getDesignator().obj);
	}
	
	public void visit(DesignatorDec dec ) {
		if(dec.getDesignator().obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		
		Code.load(dec.getDesignator().obj);
		Code.put(Code.const_1);
		Code.put(Code.sub);
		Code.store(dec.getDesignator().obj);
	}
	
	public void visit(ArrayDesignator array) {
		Code.load(array.getDesignator().obj);
	}
	
	// EXPR AND TERMS
	
	public void visit(TermsAddOp addOp) {
		SyntaxNode op = addOp.getAddOp();
	
		if(op instanceof Add) {
			Code.put(Code.add);
		}
		if(op instanceof Sub) {
			Code.put(Code.sub);
		}
		
	}
	
	public void visit(TermMulOp mullOp) {
		SyntaxNode op = mullOp.getMulOp();
		
		if(op instanceof Mul) {
			Code.put(Code.mul);
		}else if(op instanceof Div) {
			Code.put(Code.div);
		}else {
			Code.put(Code.rem);
		}
		
	}
	
	public void visit(NegativeExpr expr) {
		Code.put(Code.neg);
	}
	
	// FACTOR
	
	public void visit(FactorNumConst con) {
		Obj cnst = new Obj(Obj.Con, "$", Tab.intType);
		cnst.setLevel(0);
		cnst.setAdr(con.getNumConst());
		Code.load(cnst);
	}
	
	public void visit(FactorCharConst con) {
		Obj cnst = new Obj(Obj.Con, "$", Tab.charType);
		cnst.setLevel(0);
		cnst.setAdr(con.getCharConst());
		Code.load(cnst);
	}
	
	public void visit(FactorBoolConst con) {
		Obj cnst = new Obj(Obj.Con, "$", Tab.charType);
		cnst.setLevel(0);
		
		if(con.getBoolConst().booleanValue()) {
			cnst.setAdr(1);
		}else {
			cnst.setAdr(0);
		}
	
		Code.load(cnst);
	}
	
	public void visit(FactorVar var) {
		
		if(var.getParent().getClass() != DesignatorAssign.class && var.getParent().getClass()!= FactorFunction.class) {
			Code.load(var.getDesignator().obj);
		}
	
	}
	
	public void visit(FactorNewArray array) {
		Code.put(Code.newarray);
		
		if(array.struct.getElemType() == Tab.charType) {
			Code.put(0);
		}else {
			Code.put(1);
		}
		
	}
	
	public void visit(FactorFunction factor) {
		Obj func = factor.getFuncDesign().obj;
		
		int offset = func.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}
	
	// IF ELSE LOOP
	
	public void visit(CondFactExpr condition) {
		Code.loadConst(0);
		Code.putFalseJump(Code.ne, 0);
		jumpFixUp1.push(Code.pc -2);
	}
	
	public void visit(CondFactRelOp relOp) {
		
		if(relOp.getRelOp() instanceof RelOpEq) {
			
			Code.putFalseJump(Code.eq, 0);
			
		}else if(relOp.getRelOp() instanceof RelOpNeq) {
			
			Code.putFalseJump(Code.ne,0);
			
		}else if(relOp.getRelOp() instanceof RelOpGr) {
			
			Code.putFalseJump(Code.gt, 0);
			
		}else if(relOp.getRelOp() instanceof RelOpGre) {
			
			Code.putFalseJump(Code.ge, 0);
			
		}else if(relOp.getRelOp() instanceof RelOpLe) {
			
			Code.putFalseJump(Code.le, 0);
			
		}else if(relOp.getRelOp() instanceof RelOpLow) {
			Code.putFalseJump(Code.lt, 0);
		}	
		
		jumpFixUp1.push(Code.pc -2);
	}

	public void visit(EndOfIf end) {
		Code.fixup(jumpAddress.pop());
	}
	
	public void visit(FinalCondition condition) {
		Code.putJump(0);
		jumpAddress.push(Code.pc - 2);
		while(!jumpFixUp2.isEmpty()) {
			Code.fixup(jumpFixUp2.pop());
		}
	}
	
	public void visit(CondTerm term) {
		Code.putJump(0);
		jumpFixUp2.push(Code.pc - 2);
		while(!jumpFixUp1.empty()) {
			Code.fixup(jumpFixUp1.pop());
		}
		
	}
	
	public void visit(StartElse start) {
		Code.putJump(0);
		jumpElse.push(Code.pc - 2);
		Code.fixup(jumpAddress.pop());
	}
	
	public void visit(IfElseStmt stmt) {
		Code.fixup(jumpElse.pop());
	}
	
	
	// DO WHILE LOOP
	
	public void visit(ContinueStmt stmt) {
		// izvrsena provjera u semantickoj analizi da li sam u do-while
		Code.putJump(whileStart.peek());
	}
	
	public void visit(BreakStmt stmt) { 
		Code.putJump(0);
		breakJumps.peek().add(Code.pc-2);
	}
	
	public void visit(DoStmt stmt) {
		whileStart.push(Code.pc);
		breakJumps.push(new ArrayList<Integer>());
	}
	
	public void visit(NoWhileCondition noWhile) {
		Code.putJump(whileStart.pop());
	}
	
	public void visit(WhileConditionOnly only) {
		Code.putJump(whileStart.pop());
	}
	
	public void visit(WhileConditionStmt stmt) {
		Code.putJump(whileStart.pop());
	}
	
	public void visit(DoWhileStmt stmt) {
		Code.fixup(jumpAddress.pop());
		if(!breakJumps.empty()) {
			ArrayList<Integer> breaks = breakJumps.pop();
			for(Integer i:breaks) {
				Code.fixup(i);
			}
		}
		
	}
	
	public void visit(ExprMap map) {
		Obj func = map.getDesignator().obj;   // Function to call
	    Obj array = map.getDesignator1().obj; // Array to iterate

	    // Step 1️⃣: Initialize sum = 0 (keep it on the stack)
	    Code.loadConst(0);  // Stack: [sum=0]

	    // Step 2️⃣: Load array reference
	    Code.load(array);  // Stack: [sum, arrRef]

	    // Step 3️⃣: Get array length
	    Code.put(Code.arraylength);  // Stack: [sum, arrRef, arrLen]

	    // Step 4️⃣: Initialize index `i = 0`
	    Code.loadConst(0);  // Stack: [sum, arrRef, arrLen, i=0]

	    int loopStart = Code.pc;   // Save loop start address

	    // Step 5️⃣: Compare i < len(arr), exit loop if false
	    Code.put(Code.dup2);  // Stack: [sum, arrRef, arrLen, i, arrLen, i]
	    Code.putFalseJump(Code.gt, 0);  // Jump if i >= len(arr)
	    int loopExit = Code.pc - 2;   // Save exit jump address for fixup

	    Code.put(Code.dup_x2);
	    Code.put(Code.pop);
	    Code.put(Code.dup_x2);
	    Code.put(Code.pop);
	    Code.put(Code.dup2);
	    Code.put(Code.pop);
	    Code.load(array);
	    Code.put(Code.dup_x1);
	    Code.put(Code.pop);
	    Code.put(Code.aload);
	    
	    int offset = func.getAdr() - Code.pc;
	    Code.put(Code.call);
	    Code.put2(offset);
	    
	    Code.put(Code.add);
	    Code.put(Code.dup_x2);
	    Code.put(Code.pop);
	    Code.loadConst(1);
	    Code.put(Code.add);
	    
	    Code.putJump(loopStart);

	    // Step 1️⃣2️⃣: Fixup loop exit jump
	    Code.fixup(loopExit);

	    // Step 1️⃣3️⃣: Remove array reference and length, leaving only sum
	    Code.put(Code.pop);  // Stack: [sum]
	    Code.put(Code.pop);
	}
	
	
	public void visit(ReadDesignatorStatement read) {
		if(read.getDesignator().obj.getType() == Tab.charType) {
			Code.put(Code.bread);
		}else {
			Code.put(Code.read);
		}
		
		
		Code.store(read.getDesignator().obj);
	}
	
}
