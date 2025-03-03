// generated with ast extension for cup
// version 0.8
// 14/1/2025 17:3:36


package rs.ac.bg.etf.pp1.ast;

public class MoreConstants extends MoreConsts {

    private MoreConsts MoreConsts;
    private ConstNameDecl ConstNameDecl;

    public MoreConstants (MoreConsts MoreConsts, ConstNameDecl ConstNameDecl) {
        this.MoreConsts=MoreConsts;
        if(MoreConsts!=null) MoreConsts.setParent(this);
        this.ConstNameDecl=ConstNameDecl;
        if(ConstNameDecl!=null) ConstNameDecl.setParent(this);
    }

    public MoreConsts getMoreConsts() {
        return MoreConsts;
    }

    public void setMoreConsts(MoreConsts MoreConsts) {
        this.MoreConsts=MoreConsts;
    }

    public ConstNameDecl getConstNameDecl() {
        return ConstNameDecl;
    }

    public void setConstNameDecl(ConstNameDecl ConstNameDecl) {
        this.ConstNameDecl=ConstNameDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MoreConsts!=null) MoreConsts.accept(visitor);
        if(ConstNameDecl!=null) ConstNameDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MoreConsts!=null) MoreConsts.traverseTopDown(visitor);
        if(ConstNameDecl!=null) ConstNameDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MoreConsts!=null) MoreConsts.traverseBottomUp(visitor);
        if(ConstNameDecl!=null) ConstNameDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MoreConstants(\n");

        if(MoreConsts!=null)
            buffer.append(MoreConsts.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstNameDecl!=null)
            buffer.append(ConstNameDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MoreConstants]");
        return buffer.toString();
    }
}
