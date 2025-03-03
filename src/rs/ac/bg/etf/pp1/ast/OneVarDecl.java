// generated with ast extension for cup
// version 0.8
// 14/1/2025 17:3:36


package rs.ac.bg.etf.pp1.ast;

public class OneVarDecl extends OneVar {

    private String varName;
    private IsArray IsArray;

    public OneVarDecl (String varName, IsArray IsArray) {
        this.varName=varName;
        this.IsArray=IsArray;
        if(IsArray!=null) IsArray.setParent(this);
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName=varName;
    }

    public IsArray getIsArray() {
        return IsArray;
    }

    public void setIsArray(IsArray IsArray) {
        this.IsArray=IsArray;
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
        buffer.append("OneVarDecl(\n");

        buffer.append(" "+tab+varName);
        buffer.append("\n");

        if(IsArray!=null)
            buffer.append(IsArray.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [OneVarDecl]");
        return buffer.toString();
    }
}
