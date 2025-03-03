package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java_cup.runtime.Symbol;
import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;


public class Compiler {
	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	public static void tsdump() {
		Logger log = Logger.getLogger(Compiler.class);
		System.out.println("=====================SADRZAJ TABELE SIMBOLA=========================");
		DumpSymbolTableVisitor dump_visitor = new DumpSymbolTableVisitor();
		for (Scope s = Tab.currentScope(); s != null; s = s.getOuter()) {
			s.accept(dump_visitor);
		}
		log.info(dump_visitor.getOutput());
	}
	
	
	public static void main(String[] args) throws Exception {
		Logger log = Logger.getLogger(Compiler.class);
		
		/*if(args.length<2) {
			log.error("Nedevoljno argumenata komadne linije!!!");
			return;
		}
		
		File sourceCode = new File(args[0]);
		if (!sourceCode.exists()) {
			log.error("Source file [" + sourceCode.getAbsolutePath() + "] not found!");
			return;
		}
			
		log.info("Compiling source file: " + sourceCode.getAbsolutePath());
		*/
		
		
		Reader br = null;
		
		try {
			File sourceCode = new File("test/program.mj");
			log.info("Compiling source code file:"+sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer  = new Yylex(br);
			
			MJParser p = new MJParser(lexer);
			Symbol s = p.parse();
			
			Program prog  = (Program)(s.value);
			log.info(prog.toString(""));
			log.info("===============================================");
			
			
			
			Tab.init();
	
			
			
			RuleVisitor v = new RuleVisitor();
			SemanticAnalyzer sm = new SemanticAnalyzer();
			
			prog.traverseBottomUp(sm);
			tsdump();
				
				
				
			if(!p.errorDetected && sm.passed()) {
				
				//File objFile = new File(args[1]);
	        	File objFile = new File("test/program.obj");
				log.info("Generating bytecode file: " + objFile.getAbsolutePath());
	        	if (objFile.exists()) {
	        		objFile.delete();
	        	}
	        	
	        	// Code generation...
	        	CodeGenerator codeGenerator = new CodeGenerator();
	        	prog.traverseBottomUp(codeGenerator);
	        	Code.dataSize = sm.nVars;
	        	Code.mainPc = codeGenerator.getMainPc();
	        	Code.write(new FileOutputStream(objFile));
	        	log.info("Parsiranje uspesno zavrseno!");

			}else {
				log.info("Dogodila se greska prilikom parsiranja");
			}
		
			
			
			
			
		}finally {
			if(br!= null)try {br.close();}catch(IOException e1){log.error(e1.getMessage(),e1);}
		}
	}
}
