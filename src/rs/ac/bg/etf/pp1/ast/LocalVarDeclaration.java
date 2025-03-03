// generated with ast extension for cup
// version 0.8
// 14/1/2025 17:3:36


package rs.ac.bg.etf.pp1.ast;

public class LocalVarDeclaration extends LocalVarDecl {

    private LocalVarType LocalVarType;
    private LocalVarDecls LocalVarDecls;

    public LocalVarDeclaration (LocalVarType LocalVarType, LocalVarDecls LocalVarDecls) {
        this.LocalVarType=LocalVarType;
        if(LocalVarType!=null) LocalVarType.setParent(this);
        this.LocalVarDecls=LocalVarDecls;
        if(LocalVarDecls!=null) LocalVarDecls.setParent(this);
    }

    public LocalVarType getLocalVarType() {
        return LocalVarType;
    }

    public void setLocalVarType(LocalVarType LocalVarType) {
        this.LocalVarType=LocalVarType;
    }

    public LocalVarDecls getLocalVarDecls() {
        return LocalVarDecls;
    }

    public void setLocalVarDecls(LocalVarDecls LocalVarDecls) {
        this.LocalVarDecls=LocalVarDecls;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(LocalVarType!=null) LocalVarType.accept(visitor);
        if(LocalVarDecls!=null) LocalVarDecls.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(LocalVarType!=null) LocalVarType.traverseTopDown(visitor);
        if(LocalVarDecls!=null) LocalVarDecls.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(LocalVarType!=null) LocalVarType.traverseBottomUp(visitor);
        if(LocalVarDecls!=null) LocalVarDecls.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("LocalVarDeclaration(\n");

        if(LocalVarType!=null)
            buffer.append(LocalVarType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(LocalVarDecls!=null)
            buffer.append(LocalVarDecls.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [LocalVarDeclaration]");
        return buffer.toString();
    }
}
