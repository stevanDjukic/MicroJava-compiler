package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

//import javax.activation.FileDataSource;

import org.apache.log4j.Logger;

import java_cup.runtime.Symbol;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;
import rs.etf.pp1.symboltable.structure.SymbolDataStructure;


public class SemanticAnalyzer extends VisitorAdaptor {
	
	Logger log = Logger.getLogger(getClass());
	
	private boolean errorDetected = false;
	
	private Struct currentType = null;
	private Obj currentClass = null;
	private Obj currentMethod = null;
	private Obj currentInterface = null;
	private String currentOperationRelop = "";
	private ArrayList<Obj> vars = new ArrayList<Obj>();
	private boolean classVars = false;
	
	
	private int formParams = 0;
	private int inDoWhile = 0;
	private Boolean varIsArray = false;
	private Boolean returnFound = false;
	
	static Struct boolStruct = new Struct(Struct.Bool);
	static Struct setStruct = new Struct(Struct.Enum);
	
	Stack<ArrayList<Struct>> actualParameter  = new Stack<ArrayList<Struct>>();
	ArrayList<Obj> formalParams = new ArrayList<Obj>();
	
	int nVars = 0;

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	public SemanticAnalyzer() {
		super();
		Tab.insert(Obj.Type, "bool", boolStruct);
		Tab.insert(Obj.Type, "set", setStruct);
		ArrayList<Obj> initialFunc = new ArrayList<Obj>();
		initialFunc.add(Tab.find("ord"));
		initialFunc.add(Tab.find("chr"));
		initialFunc.add(Tab.find("ord"));
		
		for(Obj o: initialFunc) {
			for(Obj s: o.getLocalSymbols()) {
				s.setFpPos(1);
			}
		}
	}
	
	private boolean compatible(Struct first, Struct second) {
		
	  
	if(first.assignableTo(second)) {
		return true;
	}
	
	if(first == Tab.noType && (second.getKind() == Struct.Class || second.getKind() == Struct.Array))
		return true;
	
	
	
	//Da li treba ovo???
	if(first.getKind() == Struct.Array && second.getKind() == Struct.Array) {
		first = first.getElemType();
		second = second.getElemType();
		return compatible(first, second);
	}
	
	return false;
	
	
		/*if(first.getKind()== Struct.Array && second.getKind()==Struct.Array){
			if(first.getElemType().getKind() != Struct.Class && second.getElemType().getKind() != Struct.Class) {
				return first.getElemType().assignableTo(second.getElemType());
			}else if(first.getElemType().getKind()== Struct.Class && second.getElemType().getKind()!=Struct.Class) {
				return false;
			}else if(first.getElemType().getKind()!= Struct.Class && second.getElemType().getKind()==Struct.Class) {
				return false;
			}else {
				// oba su nizovi klasa
				
			}
		}else if(first.getKind()== Struct.Array) {
			if(first.getElemType().getKind() != Struct.Class && second.getKind()!=Struct.Class) {
				return first.getElemType().assignableTo(second);
			}else if(first.getElemType().getKind() == Struct.Class && second.getKind()!= Struct.Class) {
				return false;
			}else if(first.getElemType().getKind() != Struct.Class && second.getKind()== Struct.Class) {
				return false;
			}else {
				//oba su klase
			}
		}else if(second.getKind() == Struct.Array) {
			if(first.getKind() != Struct.Class && second.getElemType().getKind() != Struct.Class) {
				return first.assignableTo(second.getElemType());
			}else if(first.getKind() == Struct.Class && second.getElemType().getKind() != Struct.Class) {
				return false;
			}else if(first.getKind() != Struct.Class && second.getElemType().getKind() == Struct.Class) {
				return false;
			}else {
				// oba su klase
			}
		}
	
		if(first == Tab.noType && (second.getKind() == Struct.Class || second.getKind() == Struct.Array))
			return true;
		
		if(first.getKind() == Struct.Array && second.getKind() == Struct.Array) {
			first = first.getElemType();
			second = second.getElemType();
		}*/
		
	}
	
	public Boolean passed() {
		return !errorDetected;
	}
	
	public void visit(Program program) {
		nVars = Tab.currentScope.getnVars();
		
		Obj main = Tab.find("main");
		if(main == Tab.noObj || main.getKind()!= Obj.Meth || main.getType()!= Tab.noType || main.getLevel()!=0) {
			report_error("Metoda main nije ispravno deklarisana", null);
		}
		
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();
	}
	
	public void visit(ProgName progName) {
		progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
		Tab.openScope();
	}
	
	public void visit(Type type) {
		Obj typeFromTable = Tab.find(type.getTypeName());
		if(typeFromTable == Tab.noObj) {
			report_error(" GRESKA na liniji: "  + type.getLine() + ". Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola! ", null);
    		type.struct = Tab.noType;
		}
		
		else {
			if(Obj.Type == typeFromTable.getKind()){
    			type.struct = typeFromTable.getType();
    		}else{
    			report_error(" GRESKA na liniji: "  + type.getLine() + ". Ime " + type.getTypeName() + " ne predstavlja tip!", null);
    			type.struct = Tab.noType;
    		}
		}
		
		currentType = type.struct;
	}
	
// CONSTANTS
	
	public void visit(ConstType constType) {
		currentType = constType.getType().struct;
		if(currentType !=Tab.intType && currentType != Tab.charType && currentType!= boolStruct) {
			report_error(" GRESKA na liniji: "  + constType.getLine() + ". Tip konstante je pogresan.",null);
		}
	}
	
	public void visit(NumConstValue numConst) {
		String constName = numConst.getConstName();
		Obj constObj = Tab.find(constName);
		
		if(constObj != Tab.noObj) {
			report_error(" GRESKA na liniji: "  + numConst.getLine() + ". Vec postoji konstanta sa " + constName + " imenom.", null);
		}
		
		if(currentType == Tab.intType) {
			constObj = Tab.insert(Obj.Con, constName, currentType);
			report_info("Kreirana konstanta numerickog tipa: "+constName+"="+numConst.getNumConstValue(), null);
			constObj.setAdr(numConst.getNumConstValue());
		}else {
			report_error("GRESKA na liniji "+ numConst.getLine()+ "- Pogresan tip konstante ", null);
		}
		
	}
	
	public void visit(CharConstValue charConst) {
		String constName = charConst.getConstName();
		Obj constObj = Tab.find(constName);
		
		if(constObj != Tab.noObj) {
			report_error(" GRESKA na liniji: "  + charConst.getLine() + ". Vec postoji konstanta sa " + constName + " imenom.", null);
		}
		
		if(currentType == Tab.charType) {
			constObj = Tab.insert(Obj.Con, constName, currentType);
			report_info("Kreirana konstanta znakovnog tipa: "+constName+"="+charConst.getCharConstValue(), null);
			constObj.setAdr(charConst.getCharConstValue());
		}else {
			report_error("GRESKA na liniji "+ charConst.getLine()+ "- Pogresan tip konstante ", null);
		}
		
	}
	
	public void visit(BoolConstValue boolConst) {
		String constName = boolConst.getConstName();
		Obj constObj = Tab.find(constName);
		
		if(constObj != Tab.noObj) {
			report_error(" GRESKA na liniji: "  + boolConst.getLine() + ". Vec postoji konstanta sa " + constName + " imenom.", null);
		}
		
		if(currentType == boolStruct) {
			constObj = Tab.insert(Obj.Con, constName, currentType);
			report_info("Kreirana konstanta logickog tipa: "+constName+"="+boolConst.getBoolConstValue(), null);
			if(boolConst.getBoolConstValue().equals("true")) {
				constObj.setAdr(1);
			}
			else
				constObj.setAdr(0);
		}else {
			report_error("GRESKA na liniji "+ boolConst.getLine()+ "- Pogresan tip konstante ", null);
		}
	}
	
// VAR DECLARATION	
	
	public void visit(OneVarDecl varDecl) {
		String varName = varDecl.getVarName();
		Obj varObj = Tab.find(varName);
		if(varObj != Tab.noObj) {
			if(Tab.currentScope.findSymbol(varName) != null) {
				report_error(" GRESKA na liniji: "  + varDecl.getLine() + ". Promenljiva sa imenom " + varName + " je vec deklarisana.", null);
				return;
			} 
		}
		Struct varType;
		
		if(varIsArray) {
			varType = new Struct(Struct.Array, currentType);
		}else {
			varType = currentType;
		}
		
		Obj var = Tab.insert(Obj.Var, varName, varType);
		report_info("Kreirana promjenjiva: " + varName, varDecl);
		if(varType.getKind() == Struct.Class ||(varType.getKind()==Struct.Array && varType.getElemType().getKind() == Struct.Class)) {
			classVars = true;
			vars.add(var);
		}
		
		varIsArray = false;
	}
	
	public void visit(MoreVarsDecl varDecl) {
		String varName = varDecl.getVarName();
		Obj varObj = Tab.find(varName);
		if(varObj != Tab.noObj) {
			if(Tab.currentScope.findSymbol(varName) != null) {
				report_error(" GRESKA na liniji: "  + varDecl.getLine() + ". Promenljiva sa imenom " + varName + " je vec deklarisana.", null);
				return;
			} 
		}
		Struct varType;
		
		if(varIsArray) {
			varType = new Struct(Struct.Array, currentType);
		}else {
			varType = currentType;
		}
		
		Obj var = Tab.insert(Obj.Var, varName, varType);
		report_info("Kreirana promjenjiva: " + varName, null);
		
		if(varType.getKind() == Struct.Class ||(varType.getKind()==Struct.Array && varType.getElemType().getKind() == Struct.Class)) {
			classVars = true;
			vars.add(var);
		}
		
		varIsArray = false;
	}
	
	public void visit(VarDeclType type) {
		String s = type.getType().getTypeName();
		type.obj = new Obj(Obj.Type, s, currentType);
	}
	
	public void visit(VarDeclaration varDecl) {
		Obj symbol = Tab.find(varDecl.getVarType().obj.getName());
		for(Obj o:vars) {
			SymbolData sym = new SymbolData();
			sym.insertKey(symbol);
			o.setLocals(sym);
			o.getType().setMembers(sym);
		}
		vars.clear();
		classVars = false;
	}
	
	public void visit(VarIsArray isArray) {
		varIsArray = true;
	}
	
// METHOD DECLARATION

	public void visit(MethodDeclaration method) {
		// zatvaranje funkcije 
		String methodName = method.getMethodName().getMethName();
		if(!(returnFound || currentMethod.getType() == Tab.noType) ) { 
			report_error(" GRESKA na liniji: "  + method.getLine() + ". Funkcija " + methodName + " nema return (a nije tipa void).", null);
		}
		
		currentMethod.setLevel(formParams);
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();		
		
		
		formParams = 0;
		currentMethod = null;
		returnFound = false;
	}

	public void visit(ReturnType retType) {
		retType.struct = currentType;
	}
	
	public void visit(ReturnVoid retVoid) {
		retVoid.struct = Tab.noType;
	}
	
	public void visit(MethodName methName) {
		
		currentMethod = Tab.insert(Obj.Meth, methName.getMethName(), methName.getMethodReturnType().struct);
		
		methName.obj = currentMethod;
		
		Tab.openScope();
		
		report_info("Deklarisana funkcija sa imenom "+methName.getMethName()+" na liniji: "+methName.getLine(), null);
	}
	
	public void visit(ReturnExpr returnExpr) {
		
		
		Struct returnType = currentMethod.getType();
		

		if(currentMethod == null) {
			report_error("GRESKA na liniji"+returnExpr.getLine()+": Naredba return se ne moze naci izvan funkcije!",null);
			return;
		}
		
		if(returnType == Tab.noType) {
			report_error("GRESKA na liniji "+returnExpr.getLine()+": Void funkcije nemaju povratnu vrijednost", null);
			
		}else if(!returnType.equals(returnExpr.getExpr().struct)) {
			report_error("GRESKA na liniji "+returnExpr.getLine()+": Povratni tip funkcije se ne poklapa sa tipom u naredbi return ", null);
		}else if(currentMethod == null) {
			report_error("GRESKA na liniji "+ returnExpr.getLine()+": Return naredba se ne smije nalaziti izvan funkcije", null);
		}
		
		returnFound = true;
	}

// TERM AND EXPR HANDLING
	
	public void visit(TermFactor factor) {
		factor.struct = factor.getFactor().struct;
	}
	
	
	public void visit(TermMulOp factor) {
		if(factor.getTerm().struct != Tab.intType || factor.getFactor().struct != Tab.intType) {
			report_error("Greska na liniji "+factor.getLine()+": Oba cinioca moraju biti tipa int", null);
			factor.struct = Tab.noType;
			
		}else {
			factor.struct = factor.getFactor().struct;
		}
		
	}
	
		
	

	public void visit(OnlyTerm onlyTerm) {
		onlyTerm.struct = onlyTerm.getTerm().struct;
	}
	
	public void visit(TermsAddOp addOp) {
		if(!compatible(addOp.getTerm().struct, addOp.getTerms().struct)) {
			report_error("GRESKA na liniji "+addOp.getLine()+": Nekompatibilni tipovi prilikom operacije sabiranja", null);
			addOp.struct = Tab.noType;
		}else if(addOp.getTerm().struct != Tab.intType || addOp.getTerms().struct != Tab.intType) {
			report_error("GRESKA na liniji "+addOp.getLine()+": Tipovi pri sabiranju moraju biti INT",null );
			addOp.struct = Tab.noType;
		}else {
			addOp.struct = Tab.intType;
		}
	}
	
	public void visit(RelOpGr gr) {
		currentOperationRelop = ">";
	}
	
	public void visit(RelOpEq eq) {
		currentOperationRelop = "==";
	}
	
	public void visit(RelOpGre gre) {
		currentOperationRelop = ">=";
	}
	
	
	public void visit(RelOpLow low) {
		currentOperationRelop = "<";
	}
	
	public void visit(RelOpLe le) {
		currentOperationRelop = "<=";
	}
	
	
	public void visit(RelOpNeq neq) {
		currentOperationRelop = "!=";
	}
	
	public void visit(NegativeExpr negExpr) {
		if(negExpr.getTerms().struct != Tab.intType) {
			report_error("GRESKA na liniji "+negExpr.getLine()+": Izraz mora biti tipa INT!", null);
		}
		
		negExpr.struct = negExpr.getTerms().struct;
		
	}
	
	
	public void visit(CondFactRelOp condFact) {
		if(!compatible(condFact.getExpr().struct, condFact.getExpr1().struct)) {
			report_error("GRESKA na liniji "+condFact.getLine()+": Tipovi nisu KOMPATIBILNI!", null);
		}else if((condFact.getExpr().struct.getKind() == Struct.Array || condFact.getExpr().struct.getKind() == Struct.Class) || (condFact.getExpr1().struct.getKind() == Struct.Array || condFact.getExpr1().struct.getKind() == Struct.Class) ) {
			if(!currentOperationRelop.equals("==") || !currentOperationRelop.equals("!=")) {
				report_error("GRESKA na liniji "+condFact.getLine()+": Za klase i nizove se mogu koristiti samo operatori != i ==", null);
			}
		}
	}
	
	
	public void visit(CondFactExpr expr) {
		if(expr.getExpr().struct != boolStruct) {
			report_error("GRESKA na liniji "+expr.getLine()+": Uslov mora biti tipa bool",null);
		}
	}
	

// FACTOR NEW
	
	public void visit(FactorNewArray array) {
		String factorName = array.getType().getTypeName();
		Obj factorObj = Tab.find(factorName);
		if(factorObj == Tab.noObj) {
			report_error("GRESKA na liniji "+array.getLine()+": Klasa/Interfejs nije deklarisana", null);
			array.struct = Tab.noType;
			return;
		}
		if(array.getExpr().struct != Tab.intType) {
			report_error("GRESKA na liniji"+ array.getLine()+": Izraz mora biti tipa INT!", null);
			array.struct = Tab.noType;
		}else {
			array.struct = new Struct(Struct.Array, array.getType().struct);
		}
	}
	
	public void visit(FactorNew factorNew) {
		String factorName = factorNew.getType().getTypeName();
		Obj factorObj = Tab.find(factorName);
		if(factorObj == Tab.noObj) {
			report_error("GRESKA na liniji "+factorNew.getLine()+": Klasa/Interfejs nije deklarisana", null);
			factorNew.struct = Tab.noType;
		}else if(factorObj.getType().getKind() != Struct.Class) {
			report_error("GRESKA na liniji "+factorNew.getLine()+": Iskaz nije klasa/interfejs!", null);
			factorNew.struct = Tab.noType;
		}else {
			factorNew.struct = factorNew.getType().struct;
			SymbolData symb = new SymbolData();
			symb.insertKey(factorObj);
			factorNew.struct.setMembers(symb);
		}
		
	}
	
	public void visit(Expression expr) {
		expr.struct = expr.getTerms().struct;
	}
	
	public void visit(ExprMap map) {
		Obj func = map.getDesignator().obj;
		Obj array = map.getDesignator1().obj;
		
		if(func.getKind() != Obj.Meth) {
			report_error("GRESKA na liniji "+map.getLine()+": Promjenjiva sa lijeve strane kljucne rijeci map nije funkcija!", null);
			map.struct = Tab.noType;
			return;
		}
		
		if(func.getLevel() != 1) {
			report_error("GRESKA na liniji "+map.getLine()+": funkcija sa lijeve strane map mora imati samo 1 argument!", null);
			map.struct = Tab.noType;
			return;
		}
		
		if(func.getType().getKind() != Struct.Int) {
			report_error("GRESKA na liniji "+map.getLine()+": povratna vrijednost funkcije sa lijeve strane map nije tipa int!", null);
		}
		
		if(array.getType().getKind() != Struct.Array || array.getType().getElemType().getKind() != Struct.Int) {
			report_error("GRESKA na liniji "+map.getLine()+": promjenjiva sa desne strane map mora biti niz cijelih brojeva!", map);
		}
		
		map.struct  = Tab.intType;
	}
	
// STATEMENTS
	
	public void visit(ReturnStmt returnStmt) {
		
		
		Struct returnType = currentMethod.getType();
		
		if(currentMethod == null) {
			report_error("GRESKA na liniji"+returnStmt.getLine()+": Naredba return se ne moze naci izvan funkcije!",null);
			return;
		}
		
		if(returnType != Tab.noType) {
			report_error("GRESKA na liniji "+returnStmt.getLine()+": Void funkcije nema povratni tip!", null);
		}else if(currentMethod == null) {
			report_error("GRESKA na liniji "+ returnStmt.getLine()+": Return naredba se ne smije nalaziti izvan funkcije", null);
		}
		
		returnFound = true;
	}
	
	public void visit(ContinueStmt ConStm) {
		if(inDoWhile ==0) {
			report_error("Greska na liniji "+ConStm.getLine()+": Continue se moze naci samo unutar petlje", null);
		}
	}
	
	public void visit(BreakStmt breakStm) {
		// da li moze ovaj uslov za while..
		if(inDoWhile == 0) {
			report_error("Greska na liniji "+breakStm.getLine()+": Break se moze naci samo unutar petlje", null);
		}
	}
	
	public void visit(PrintStmtExpr printExpr) {
		if(printExpr.getExpr().struct != Tab.intType && printExpr.getExpr().struct != Tab.charType && printExpr.getExpr().struct != boolStruct && printExpr.getExpr().struct != setStruct) {
			report_error("GRESKA na liniji "+printExpr.getLine()+": Izraz unutar print naredbe mora biti int, char, bool ili set tipa", null);
		}
	}
	
	public void visit(DoStmt doW) {
		inDoWhile++;
	}
	
	public void visit(DoWhileStmt doWhile) {
		inDoWhile--;
	}
	
	public void visit(PrintStmtExprConst printExpr) {
		if(printExpr.getExpr().struct != Tab.intType && printExpr.getExpr().struct != Tab.charType && printExpr.getExpr().struct != boolStruct) {
			report_error("GRESKA na liniji "+printExpr.getLine()+": Izraz unutar print naredbe mora biti int,char ili bool tipa", null);
		}
	}
	
	// DESIGNATOR (VARIABLES)
	
	public void visit(OneDesignator oneDesig) {
		Obj desig = Tab.find(oneDesig.getDesignatorName());
		if(desig == Tab.noObj) {
			report_error("GRESKA na liniji "+oneDesig.getLine()+": Promjenjiva "+oneDesig.getDesignatorName() +" nije deklarisana prije upotrebe!", null);
			return;
		}
		if(desig.getKind() != Obj.Meth && desig.getFpPos()==0) {
			if(desig.getLevel()==0) {
				report_info("Koriscenje globalne promjenjive "+desig.getName(), oneDesig);
			}else {
				report_info("Koriscenje lokalne promjenjive "+desig.getName(), oneDesig);
			}	
		}else if(desig.getKind() != Obj.Meth && desig.getFpPos()!=0) {
			report_info("Koriscenje formalnog parametra "+desig.getName(), oneDesig);
		}
		//report_info(desig.toString(), null);
		oneDesig.obj = desig;
		
	}
	
	public void visit(ArrayDesignator desig) {
		Obj array = desig.getDesignator().obj;
		
		if(array.getType().getKind() != Struct.Array) {
			report_error("GRESKA na liniji "+desig.getLine()+": Promjenjiva mora biti niz", null);
			return;
		}
		
		desig.obj = desig.getDesignator().obj;
		
	}
	
	public void visit(DesignatorInc inc) {
		Obj desig = inc.getDesignator().obj;
		
		if(desig.getKind()!= Obj.Var && desig.getKind() != Obj.Elem) {
			report_error("GRESKA na liniji "+inc.getLine()+": Designator nije promjenjiva ili element niza", null);
			return;
		}
		
		if(desig.getType().getKind() != Struct.Int) {
			report_error("GRESKA na liniji"+inc.getLine()+": (Promjenjiva/Element niza) moraju biti tipa int!", null);
			return;
		}
	}
	
	public void visit(DesignatorDec dec) {
		Obj desig = dec.getDesignator().obj;
		
		if(desig.getKind()!= Obj.Var && desig.getKind() != Obj.Elem) {
			report_error("GRESKA na liniji "+dec.getLine()+": Designator nije promjenjiva ili element niza", null);
			return;
		}
		
		if(desig.getType().getKind() != Struct.Int) {
			report_error("GRESKA na liniji"+dec.getLine()+": (Promjenjiva/Element niza) moraju biti tipa int!", null);
			return;
		}
	}
	
	public void visit(DesignatorSetOp set) {
		Struct desig1 = set.getDesignator().obj.getType();
		Struct desig2 = set.getDesignator1().obj.getType();
		Struct desig3 = set.getDesignator2().obj.getType();
		
		if(desig1.getKind() != Struct.Enum || desig2.getKind() !=Struct.Enum || desig3.getKind() != Struct.Enum) {
			report_error("GRESKA na liniji "+set.getLine()+": Pogresan tip, svi designatori moraju biti skupovi!", null);
			return;
		}
	}
	
	public void visit(ReadDesignatorStatement read) {
		Obj desig = read.getDesignator().obj;
		
		if(desig.getType().getKind() != Obj.Var && desig.getType().getKind() != Obj.Elem) {
			report_error("GRESKA na liniji "+read.getLine()+": Designator unutar read funkcije mora biti promjenjiva ili element niza!", null);
			return;
		}
		
	}
	
	public void visit(DesignatorArray desigArray) {
		Struct str = desigArray.getArrayDesignator().obj.getType();
		
		if(str.getKind() != Struct.Array) {
			report_error("GRESKA na liniji "+desigArray.getLine()+": Promjenjiva mora biti niz!", null);
			desigArray.obj = Tab.noObj;
		}else if(desigArray.getExpr().struct != Tab.intType) {
			report_error("GRESKA na liniji "+desigArray.getLine()+": Izraz unutar [] mora biti tipa INT", null);
			desigArray.obj = Tab.noObj;
		}else {
			desigArray.obj = new Obj(Obj.Elem, desigArray.getArrayDesignator().obj.getName(), desigArray.getArrayDesignator().obj.getType().getElemType());
			SymbolData sym = new SymbolData();
			for(Obj o: desigArray.getArrayDesignator().obj.getType().getMembers()) {
				sym.insertKey(o);
			}
			
			
			report_info("Pristup elementu niza "+desigArray.getArrayDesignator().obj.getName(), desigArray);
			//desigArray.obj.getType().setMembers(sym);
		}
		
	}
	
	public void visit(FunctionDesignator funcDesig) {
		funcDesig.obj = funcDesig.getDesignator().obj;
		ArrayList<Struct> actPars = new ArrayList<Struct>();
		actualParameter.push(actPars);
	}
	
	public void visit(DesignatorFunction desigFunc) {
		Obj funcDesig = desigFunc.getFuncDesign().obj;
		
		if(funcDesig == null) {
			report_error("GRESKA na liniji "+desigFunc.getLine()+": Greska u radu sa funkcijom", null);
			return;
		}
		
		Obj func = Tab.currentScope().getOuter().getLocals().searchKey(funcDesig.getName());
		
		if(func == Tab.noObj) {
			report_error("GRESKA na liniji "+desigFunc.getLine()+": Funkcija "+funcDesig.getName()+" nije dekalrasana", null);
			return;
		}
		
		if(funcDesig.getKind() != Obj.Meth) {
			report_error("GRESKA na liniji "+desigFunc.getLine()+": Promjenjiva nije funkcija!", null);
			return;
		}
		
		/*if(funcDesig.getType() == Tab.noType) {
			report_error("GRESKA na liniji"+ desigFunc.getLine()+": Funkcija void tipa se ne moze naci u izrazima!", null);
			return;
		}*/
		
		//ArrayList<Obj> formalArgs = new ArrayList<Obj>();
		int counter = 0;
		for(Obj o:funcDesig.getLocalSymbols()) {
			if(counter<funcDesig.getLevel()&& o!=Tab.noObj) {
				counter++;
				formalParams.add(o);
			}
		}
		
		ArrayList<Struct> actArgs = actualParameter.pop();
 		
		if(actArgs.size() != funcDesig.getLevel()) {
			report_error("GRESKA na liniji "+ desigFunc.getLine()+" Broj formalnih i stvarnih argumenata se ne podudara!", null);
			return;
		}
		
		for(int i = 0; i<formalParams.size();i++) {
			if(!compatible(actArgs.get(i), formalParams.get(i).getType())) {
				report_error("GRESKA na liniji "+desigFunc.getLine()+": Tipovi formalnih i stvarnih argumenata nisu kompatibilni!", null);
				formalParams.clear();
				return;
			}
		}
		
		formalParams.clear();
		report_info("USPJESNO pozvana metoda "+funcDesig.getName()+" na liniji "+desigFunc.getLine(), null);
	}
	
	public void visit(DesignatorAssign assignDesig) {
		int desigType = assignDesig.getDesignator().obj.getKind();
		
		if(desigType!= Obj.Var && desigType !=Obj.Fld && desigType!=Obj.Elem) {
			report_error("GRESKA na liniji "+assignDesig.getLine()+": Lijeva vrijednost kod operacije dodjele mora biti promjenjiva, element niza ili polje klase!", null);
			return;
		}
		
	
		
		if(!assignDesig.getExpr().struct.assignableTo(assignDesig.getDesignator().obj.getType())) {
			report_error("GRESKA na liniji "+assignDesig.getLine()+": Tipovi pri dodjeli nisu kompatibilni!", null);
			return;
		}
		
		
		/*if(assignDesig.getExpr().struct.getKind() == Struct.Class ||(assignDesig.getExpr().struct.getKind() == Struct.Array && assignDesig.getExpr().struct.getElemType().getKind() == Struct.Class) )  {
			SymbolData symb = new SymbolData();
			for(Obj o:assignDesig.getExpr().struct.getMembers()) {
				symb.insertKey(o);
			}
			assignDesig.getDesignator().obj.getType().getMembers().clear();
			assignDesig.getDesignator().obj.getType().setMembers(symb);
		}*/
	}
	
	// FACTOR
	public void visit(FactorVar facVar) {
		facVar.struct = facVar.getDesignator().obj.getType();
	}
	
	public void visit(FactorFunction factorFunc) {
		Obj funcDesig = factorFunc.getFuncDesign().obj;
		
		if(funcDesig == null) {
			factorFunc.struct = Tab.noType;
			report_error("GRESKA na liniji "+factorFunc.getLine()+": Greska u radu sa funkcijom", null);
			return;
		}
		
		
		if(funcDesig == Tab.noObj) {
			report_error("GRESKA na liniji "+factorFunc.getLine()+": Funkcija "+funcDesig.getName()+" nije deklarisana", null);
			factorFunc.struct = Tab.noType;
			return;
		}
		
		if(funcDesig.getKind() != Obj.Meth) {
			report_error("GRESKA na liniji "+factorFunc.getLine()+": Promjenjiva nije funkcija!", null);
			factorFunc.struct = Tab.noType;
			return;
		}
		
		if(funcDesig.getType() == Tab.noType) {
			report_error("GRESKA na liniji"+ factorFunc.getLine()+": Funkcija void tipa se ne moze naci u izrazima!", null);
			factorFunc.struct = Tab.noType;
			return;
		}
		
		int counter = 0;
		
		for(Obj o:funcDesig.getLocalSymbols()) {
			if(counter<funcDesig.getLevel() && o!=Tab.noObj) {
				counter++;
				formalParams.add(o);
			}
		}
		
		ArrayList<Struct> actArgs = actualParameter.pop();
 		
		if(actArgs.size() != formalParams.size()) {
			report_error("GRESKA na liniji "+ factorFunc.getLine()+" Broj formalnih "+formalParams.size() +" i stvarnih "+ actArgs.size()+" argumenata funkcije " +funcDesig.getName()+" se ne podudara!", null);
			factorFunc.struct = Tab.noType;
			formalParams.clear();
			return;
		}
		
		for(int i = 0; i<actArgs.size();i++) {
			if(!compatible(actArgs.get(i), formalParams.get(i).getType())) {
				report_error("GRESKA na liniji "+factorFunc.getLine()+": Tipovi formalnih i stvarnih argumenata nisu kompatibilni!", null);
				factorFunc.struct =Tab.noType;
				formalParams.clear();
				return;
			}
		}
		
		factorFunc.struct = funcDesig.getType();
		formalParams.clear();
		report_info("USPJESNO pozvana metoda "+funcDesig.getName()+" na liniji "+factorFunc.getLine(), null);
	}
	
	public void visit(FactorExpr expr) {
		expr.struct = expr.getExpr().struct;
	}
	
	public void visit(FactorNumConst numConst) {
		numConst.struct = Tab.intType;
		report_info("Koriscenje numericke konstante ", numConst);
	}
	
	public void visit(FactorCharConst charConst) {
		charConst.struct = Tab.charType;
		report_info("Koriscenje znakovne konstante ", charConst);
	}
	
	public void visit(FactorBoolConst boolConst) {
		boolConst.struct = boolStruct;
		report_info("Koriscenje logicke konstante ", boolConst);
	}
	
	// FORMAL AND ACTUAL PARAMETERS HANDLING
	
	public void visit(ActPar par) {
		actualParameter.peek().add(par.getExpr().struct);
	}
	
	public void visit(ActParamsList pars) {
		actualParameter.peek().add(pars.getExpr().struct);
	}
	
	public void visit(FormParamDef paramDef) {
		
		formParams++;
		String paramName = paramDef.getFormParamName();
		
		Obj param = Tab.find(paramName);
		
		if(param != Tab.noObj) {
			if(Tab.currentScope().findSymbol(paramName) != null) {
				report_error("GRESKA na liniji "+paramDef.getLine()+": Formalni parametar je vec deklarisan", null);
				return;
			}
		}
		Struct paramType = currentType;
		if(varIsArray) {
			paramType = new Struct(Struct.Array, currentType);		
		}
		
		Obj var = Tab.insert(Obj.Var, paramName, paramType);
		var.setFpPos(formParams);
		/*report_info("=====================UNIO FORM PARAM+++++++++"+var.getName(), paramDef);
		formPars.add(var);
		SymbolData sym = new SymbolData();
		String type = paramDef.getType().getTypeName();
		Obj typeClass = Tab.find(type);
		sym.insertKey(typeClass);
		if(typeClass.getType().getKind() == Struct.Class ||(typeClass.getType().getKind()==Struct.Array && typeClass.getType().getElemType().getKind() == Struct.Class)) {
			var.getType().setMembers(sym);
		}*/
		
		varIsArray = false;
		paramDef.obj = var;//new Obj(Obj.Type, paramName, paramType);
	}
	
	
	// KLASE I INTERFEJSI C nivo
	/*
	public void visit(ClassDeclaration classDecl) {
		//Tab.chainLocalSymbols(currentClass);
		Tab.chainLocalSymbols(currentClass);
		Tab.closeScope();
		currentClass = null;		
	}
	
	public void visit(ThisDesignator thisDesig) {
		if(currentClass == null) {
			report_error("GRESKA na liniji "+thisDesig.getLine()+": Kljucna rijec 'this' se moze koristiti samo unutar klase!", null);
			thisDesig.obj = Tab.noObj;
		}else {
			thisDesig.obj = new Obj(Obj.Var, "this", currentClass.getType());
		}
	}
	
	public void visit(ClassDesignator classDesig) {
		Obj desig = classDesig.getDesignator().obj;
		
		if(desig ==Tab.noObj ||(desig.getType().getKind()==Struct.Array && desig.getType().getElemType().getKind()!= Struct.Class) ) {
			report_error("GRESKA na liniji "+classDesig.getLine()+": Promjenjiva "+desig.getName()+" nije klasa!", null);
			classDesig.obj = Tab.noObj;
			return;
		}else if((desig.getType().getKind() != Struct.Class && !(desig.getType().getKind()==Struct.Array && desig.getType().getElemType().getKind()== Struct.Class))&& (desig.getType().getKind() != Struct.Interface && !(desig.getType().getKind()==Struct.Array && desig.getType().getElemType().getKind()== Struct.Interface))) {
			report_error("GRESKA na liniji "+classDesig.getLine()+": Promjenjiva "+desig.getName()+" nije klasa!", null);
			classDesig.obj = Tab.noObj;
			return;
		}
		
		String fieldName = classDesig.getFieldName();
		Obj field = null;
		
		if(currentClass!=null && currentMethod ==null) {
			field = Tab.currentScope().findSymbol(fieldName);
			if(field != null && field.getKind() == Obj.Meth) {
				report_info("Pristup metodi "+field.getName()+" klase "+currentClass.getName(), null);
			}else if(field != null && field.getKind() == Obj.Fld) {
				report_info("Pristup polju "+field.getName()+" klase "+currentClass.getName(), null);
			}
		}else if(currentClass!= null && currentMethod!=null) {
			field = Tab.currentScope().getOuter().findSymbol(fieldName);
			if(field != null && field.getKind() == Obj.Meth) {
				report_info("Pristup metodi "+field.getName()+" klase "+currentClass.getName(), null);
			}else if(field != null && field.getKind() == Obj.Fld) {
				report_info("Pristup polju "+field.getName()+" klase "+currentClass.getName(), null);
			}
		}else if(currentClass==null&& currentMethod!=null) {
			Obj klasa = desig;
			for(Obj o:desig.getType().getMembers()) {
				//report_info("Tip KLASE "+o.getName(), null);
				for(Obj s:o.getLocalSymbols()) {
					//report_info("---POLJE--- " + s.getName(), null);
					if(s.getName().equalsIgnoreCase(fieldName)) {
						field = s;
						klasa = o;
					}
				}
			
			}
			if(field != null && field.getKind() == Obj.Meth) {
				report_info("Pristup metodi "+field.getName()+" klase "+klasa.getName(), null);
			}else if(field != null && field.getKind() == Obj.Fld) {
				report_info("Pristup polju "+field.getName()+" klase "+klasa.getName(), null);
			}
		}
		
		
		if(field == null) {
			report_error("GRESKA na liniji "+classDesig.getLine()+": Polje/metoda "+fieldName+" nije deklarisano u klasi "+desig.getName(), null);
			return;
		}else {
			classDesig.obj = field;
			return;
		}
		
	}
	
	
	public void visit(LocalVarIdent classVar) {
		String classVarName = classVar.getClassVarName();
		Obj var = Tab.find(classVarName);
		if(var != Tab.noObj) {
			if(Tab.currentScope.findSymbol(classVarName)!= null) {
				report_error(" GRESKA na liniji: " + classVar.getLine() + "Promenljiva  " + var + "je vec deklarisana u klasi. ", null);    
			}
		}
		
		Struct varType;
		
		if(varIsArray) {
			varType = new Struct(Struct.Array, currentType);
		}else {
			varType = currentType;
		}
		
		var = Tab.insert(Obj.Fld, classVarName, varType);
		
		varIsArray = false;
	}
	
	public void visit(ClassName classN) {
		String className = classN.getNameOfClass();
		Obj classNode = Tab.find(className);
		if(classNode!= Tab.noObj) {
			report_error(" GRESKA na liniji: " + classN.getLine() + ". Klasa -" + className + "- je vec deklarisana.", null);
			classN.obj = Tab.noObj;
			Tab.openScope();//radi error recovery - da bi otkrili sve greske do kraja
		}
		
		Struct classType = new Struct(Struct.Class);
		
		currentClass =  Tab.insert(Obj.Type, className, classType);
		classN.obj = currentClass;
		Tab.openScope();
		Obj s = Tab.insert(Obj.Fld, "VFT_address", Tab.intType);
		SymbolData sym = new SymbolData();
		sym.insertKey(s);
		//currentClass.setLocals(sym);
	}
	
	public void visit(IsExtendingClass extend) {
		Obj o = Tab.find(extend.getType().getTypeName());
		if(o == Tab.noObj) {
			report_error("GRESKA na liniji "+extend.getLine()+": Ime nakon naredbe extend nije definisano!", null);
			return;
		}
		
		SymbolData sym = new SymbolData();
		
		if(o.getType().getKind() == Struct.Class) {
			//report_info("POLJA KLASA "+ o.getName(), null);
			for(Obj s:o.getLocalSymbols()) {
				if(s.getName()!="VFT_address" && s.getKind() == Obj.Fld) {
					Tab.currentScope.addToLocals(s);
				}
			}
			
			sym.insertKey(o);
			for(Obj p: o.getType().getMembers()) {
				sym.insertKey(p);
			}
			extend.obj = o;
			currentClass.getType().setMembers(sym);
		}else if(o.getType().getKind() == Struct.Interface) {
			for(Obj s:o.getLocalSymbols()) {
				if(s.getName()!="VFT_address" && s.getKind() == Obj.Fld) {
					Tab.currentScope.addToLocals(s);
				}
			}
			
			sym.insertKey(o);
			
			extend.obj = o;
			currentClass.getType().setMembers(sym);
		}else {
			report_error("GRESKA na liniji "+extend.getLine()+": Klasa moze biti prosirena samo drugom klasom ili interfejsom",null );
		}
		
	}
	
	public void visit(ClassNameDecl classDecl) {
		if(classDecl.getExtendsClass().obj != Tab.noObj) {
			Obj extend = classDecl.getExtendsClass().obj;
			for(Obj s: extend.getLocalSymbols()) {
				if(s.getName()!="VFT_address" && s.getKind() == Obj.Meth) {
					Tab.currentScope.addToLocals(s);
				}
			}
		}
	}
	
	public void visit(NotExtendingClass name) {
		name.obj = Tab.noObj;
	}
	*/
	
}
