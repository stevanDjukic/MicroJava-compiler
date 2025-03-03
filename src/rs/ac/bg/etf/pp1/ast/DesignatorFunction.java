// generated with ast extension for cup
// version 0.8
// 14/1/2025 17:3:36


package rs.ac.bg.etf.pp1.ast;

public class DesignatorFunction extends DesignatorStatement {

    private FuncDesign FuncDesign;
    private ActPars ActPars;

    public DesignatorFunction (FuncDesign FuncDesign, ActPars ActPars) {
        this.FuncDesign=FuncDesign;
        if(FuncDesign!=null) FuncDesign.setParent(this);
        this.ActPars=ActPars;
        if(ActPars!=null) ActPars.setParent(this);
    }

    public FuncDesign getFuncDesign() {
        return FuncDesign;
    }

    public void setFuncDesign(FuncDesign FuncDesign) {
        this.FuncDesign=FuncDesign;
    }

    public ActPars getActPars() {
        return ActPars;
    }

    public void setActPars(ActPars ActPars) {
        this.ActPars=ActPars;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FuncDesign!=null) FuncDesign.accept(visitor);
        if(ActPars!=null) ActPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FuncDesign!=null) FuncDesign.traverseTopDown(visitor);
        if(ActPars!=null) ActPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FuncDesign!=null) FuncDesign.traverseBottomUp(visitor);
        if(ActPars!=null) ActPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorFunction(\n");

        if(FuncDesign!=null)
            buffer.append(FuncDesign.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActPars!=null)
            buffer.append(ActPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorFunction]");
        return buffer.toString();
    }
}
