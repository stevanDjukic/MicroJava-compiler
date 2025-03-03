// generated with ast extension for cup
// version 0.8
// 14/1/2025 17:3:36


package rs.ac.bg.etf.pp1.ast;

public class MultipleVarsDeclaration extends VarDecls {

    private MoreVars MoreVars;
    private VarDecls VarDecls;

    public MultipleVarsDeclaration (MoreVars MoreVars, VarDecls VarDecls) {
        this.MoreVars=MoreVars;
        if(MoreVars!=null) MoreVars.setParent(this);
        this.VarDecls=VarDecls;
        if(VarDecls!=null) VarDecls.setParent(this);
    }

    public MoreVars getMoreVars() {
        return MoreVars;
    }

    public void setMoreVars(MoreVars MoreVars) {
        this.MoreVars=MoreVars;
    }

    public VarDecls getVarDecls() {
        return VarDecls;
    }

    public void setVarDecls(VarDecls VarDecls) {
        this.VarDecls=VarDecls;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MoreVars!=null) MoreVars.accept(visitor);
        if(VarDecls!=null) VarDecls.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MoreVars!=null) MoreVars.traverseTopDown(visitor);
        if(VarDecls!=null) VarDecls.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MoreVars!=null) MoreVars.traverseBottomUp(visitor);
        if(VarDecls!=null) VarDecls.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleVarsDeclaration(\n");

        if(MoreVars!=null)
            buffer.append(MoreVars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDecls!=null)
            buffer.append(VarDecls.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleVarsDeclaration]");
        return buffer.toString();
    }
}
