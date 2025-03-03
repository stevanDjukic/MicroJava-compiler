// generated with ast extension for cup
// version 0.8
// 14/1/2025 17:3:36


package rs.ac.bg.etf.pp1.ast;

public class LocalVarIdent implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String classVarName;
    private IsArray IsArray;

    public LocalVarIdent (String classVarName, IsArray IsArray) {
        this.classVarName=classVarName;
        this.IsArray=IsArray;
        if(IsArray!=null) IsArray.setParent(this);
    }

    public String getClassVarName() {
        return classVarName;
    }

    public void setClassVarName(String classVarName) {
        this.classVarName=classVarName;
    }

    public IsArray getIsArray() {
        return IsArray;
    }

    public void setIsArray(IsArray IsArray) {
        this.IsArray=IsArray;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IsArray!=null) IsArray.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IsArray!=null) IsArray.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IsArray!=null) IsArray.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("LocalVarIdent(\n");

        buffer.append(" "+tab+classVarName);
        buffer.append("\n");

        if(IsArray!=null)
            buffer.append(IsArray.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [LocalVarIdent]");
        return buffer.toString();
    }
}
