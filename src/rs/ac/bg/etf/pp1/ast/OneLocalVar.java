// generated with ast extension for cup
// version 0.8
// 14/1/2025 17:3:36


package rs.ac.bg.etf.pp1.ast;

public class OneLocalVar extends LocalVarDecls {

    private LocalVarIdent LocalVarIdent;

    public OneLocalVar (LocalVarIdent LocalVarIdent) {
        this.LocalVarIdent=LocalVarIdent;
        if(LocalVarIdent!=null) LocalVarIdent.setParent(this);
    }

    public LocalVarIdent getLocalVarIdent() {
        return LocalVarIdent;
    }

    public void setLocalVarIdent(LocalVarIdent LocalVarIdent) {
        this.LocalVarIdent=LocalVarIdent;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(LocalVarIdent!=null) LocalVarIdent.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(LocalVarIdent!=null) LocalVarIdent.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(LocalVarIdent!=null) LocalVarIdent.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("OneLocalVar(\n");

        if(LocalVarIdent!=null)
            buffer.append(LocalVarIdent.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [OneLocalVar]");
        return buffer.toString();
    }
}
