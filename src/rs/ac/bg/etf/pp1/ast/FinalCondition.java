// generated with ast extension for cup
// version 0.8
// 14/1/2025 17:3:36


package rs.ac.bg.etf.pp1.ast;

public class FinalCondition implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Conditions Conditions;

    public FinalCondition (Conditions Conditions) {
        this.Conditions=Conditions;
        if(Conditions!=null) Conditions.setParent(this);
    }

    public Conditions getConditions() {
        return Conditions;
    }

    public void setConditions(Conditions Conditions) {
        this.Conditions=Conditions;
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
        if(Conditions!=null) Conditions.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Conditions!=null) Conditions.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Conditions!=null) Conditions.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FinalCondition(\n");

        if(Conditions!=null)
            buffer.append(Conditions.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FinalCondition]");
        return buffer.toString();
    }
}
