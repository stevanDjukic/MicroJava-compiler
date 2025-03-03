// generated with ast extension for cup
// version 0.8
// 14/1/2025 17:3:36


package rs.ac.bg.etf.pp1.ast;

public class OneVarDeclaration extends VarDecls {

    private OneVar OneVar;

    public OneVarDeclaration (OneVar OneVar) {
        this.OneVar=OneVar;
        if(OneVar!=null) OneVar.setParent(this);
    }

    public OneVar getOneVar() {
        return OneVar;
    }

    public void setOneVar(OneVar OneVar) {
        this.OneVar=OneVar;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OneVar!=null) OneVar.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OneVar!=null) OneVar.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OneVar!=null) OneVar.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("OneVarDeclaration(\n");

        if(OneVar!=null)
            buffer.append(OneVar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [OneVarDeclaration]");
        return buffer.toString();
    }
}
