// generated with ast extension for cup
// version 0.8
// 14/1/2025 17:3:36


package rs.ac.bg.etf.pp1.ast;

public class IfCond extends IfCondition {

    private FinalCondition FinalCondition;

    public IfCond (FinalCondition FinalCondition) {
        this.FinalCondition=FinalCondition;
        if(FinalCondition!=null) FinalCondition.setParent(this);
    }

    public FinalCondition getFinalCondition() {
        return FinalCondition;
    }

    public void setFinalCondition(FinalCondition FinalCondition) {
        this.FinalCondition=FinalCondition;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FinalCondition!=null) FinalCondition.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FinalCondition!=null) FinalCondition.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FinalCondition!=null) FinalCondition.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IfCond(\n");

        if(FinalCondition!=null)
            buffer.append(FinalCondition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IfCond]");
        return buffer.toString();
    }
}
