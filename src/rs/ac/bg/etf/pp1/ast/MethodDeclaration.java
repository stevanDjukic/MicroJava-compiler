// generated with ast extension for cup
// version 0.8
// 14/1/2025 17:3:36


package rs.ac.bg.etf.pp1.ast;

public class MethodDeclaration extends MethodDecl {

    private MethodName MethodName;
    private MethodFormParams MethodFormParams;
    private MethodVars MethodVars;
    private StatementList StatementList;

    public MethodDeclaration (MethodName MethodName, MethodFormParams MethodFormParams, MethodVars MethodVars, StatementList StatementList) {
        this.MethodName=MethodName;
        if(MethodName!=null) MethodName.setParent(this);
        this.MethodFormParams=MethodFormParams;
        if(MethodFormParams!=null) MethodFormParams.setParent(this);
        this.MethodVars=MethodVars;
        if(MethodVars!=null) MethodVars.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public MethodName getMethodName() {
        return MethodName;
    }

    public void setMethodName(MethodName MethodName) {
        this.MethodName=MethodName;
    }

    public MethodFormParams getMethodFormParams() {
        return MethodFormParams;
    }

    public void setMethodFormParams(MethodFormParams MethodFormParams) {
        this.MethodFormParams=MethodFormParams;
    }

    public MethodVars getMethodVars() {
        return MethodVars;
    }

    public void setMethodVars(MethodVars MethodVars) {
        this.MethodVars=MethodVars;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodName!=null) MethodName.accept(visitor);
        if(MethodFormParams!=null) MethodFormParams.accept(visitor);
        if(MethodVars!=null) MethodVars.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodName!=null) MethodName.traverseTopDown(visitor);
        if(MethodFormParams!=null) MethodFormParams.traverseTopDown(visitor);
        if(MethodVars!=null) MethodVars.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodName!=null) MethodName.traverseBottomUp(visitor);
        if(MethodFormParams!=null) MethodFormParams.traverseBottomUp(visitor);
        if(MethodVars!=null) MethodVars.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDeclaration(\n");

        if(MethodName!=null)
            buffer.append(MethodName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodFormParams!=null)
            buffer.append(MethodFormParams.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodVars!=null)
            buffer.append(MethodVars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDeclaration]");
        return buffer.toString();
    }
}
