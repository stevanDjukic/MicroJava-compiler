package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collection;

import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.structure.SymbolDataStructure;

public class SymbolData extends SymbolDataStructure {
	
	ArrayList<Obj> symbols = new ArrayList<Obj>();
	
	@Override
	public Obj searchKey(String key) {
		// TODO Auto-generated method stub
		for(int i =0;i<symbols.size();i++) {
			if(key.contentEquals(symbols.get(i).getName())) {
				return symbols.get(i);
			}
		}
		
		return null;
	}

	@Override
	public boolean deleteKey(String key) {
		// TODO Auto-generated method stub
		for(Obj o: symbols) {
			if(key.contentEquals(o.getName())) {
				return symbols.remove(o);
			}
		}
		
		
		return false;
	}

	@Override
	public boolean insertKey(Obj node) {
		// TODO Auto-generated method stub
		return symbols.add(node);
	}

	@Override
	public Collection<Obj> symbols() {
		// TODO Auto-generated method stub
		return symbols;
	}

	@Override
	public int numSymbols() {
		// TODO Auto-generated method stub
		return symbols.size();
	}

}
