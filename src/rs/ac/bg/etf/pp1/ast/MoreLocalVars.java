// generated with ast extension for cup
// version 0.8
// 14/1/2025 17:3:36


package rs.ac.bg.etf.pp1.ast;

public class MoreLocalVars extends LocalVarDecls {

    private LocalVarDecls LocalVarDecls;
    private LocalVarIdent LocalVarIdent;

    public MoreLocalVars (LocalVarDecls LocalVarDecls, LocalVarIdent LocalVarIdent) {
        this.LocalVarDecls=LocalVarDecls;
        if(LocalVarDecls!=null) LocalVarDecls.setParent(this);
        this.LocalVarIdent=LocalVarIdent;
        if(LocalVarIdent!=null) LocalVarIdent.setParent(this);
    }

    public LocalVarDecls getLocalVarDecls() {
        return LocalVarDecls;
    }

    public void setLocalVarDecls(LocalVarDecls LocalVarDecls) {
        this.LocalVarDecls=LocalVarDecls;
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
        if(LocalVarDecls!=null) LocalVarDecls.accept(visitor);
        if(LocalVarIdent!=null) LocalVarIdent.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(LocalVarDecls!=null) LocalVarDecls.traverseTopDown(visitor);
        if(LocalVarIdent!=null) LocalVarIdent.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(LocalVarDecls!=null) LocalVarDecls.traverseBottomUp(visitor);
        if(LocalVarIdent!=null) LocalVarIdent.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MoreLocalVars(\n");

        if(LocalVarDecls!=null)
            buffer.append(LocalVarDecls.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(LocalVarIdent!=null)
            buffer.append(LocalVarIdent.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MoreLocalVars]");
        return buffer.toString();
    }
}
