// generated with ast extension for cup
// version 0.8
// 14/1/2025 17:3:36


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclaration extends ConstDecl {

    private ConstType ConstType;
    private ConstNameDecl ConstNameDecl;
    private MoreConsts MoreConsts;

    public ConstDeclaration (ConstType ConstType, ConstNameDecl ConstNameDecl, MoreConsts MoreConsts) {
        this.ConstType=ConstType;
        if(ConstType!=null) ConstType.setParent(this);
        this.ConstNameDecl=ConstNameDecl;
        if(ConstNameDecl!=null) ConstNameDecl.setParent(this);
        this.MoreConsts=MoreConsts;
        if(MoreConsts!=null) MoreConsts.setParent(this);
    }

    public ConstType getConstType() {
        return ConstType;
    }

    public void setConstType(ConstType ConstType) {
        this.ConstType=ConstType;
    }

    public ConstNameDecl getConstNameDecl() {
        return ConstNameDecl;
    }

    public void setConstNameDecl(ConstNameDecl ConstNameDecl) {
        this.ConstNameDecl=ConstNameDecl;
    }

    public MoreConsts getMoreConsts() {
        return MoreConsts;
    }

    public void setMoreConsts(MoreConsts MoreConsts) {
        this.MoreConsts=MoreConsts;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstType!=null) ConstType.accept(visitor);
        if(ConstNameDecl!=null) ConstNameDecl.accept(visitor);
        if(MoreConsts!=null) MoreConsts.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstType!=null) ConstType.traverseTopDown(visitor);
        if(ConstNameDecl!=null) ConstNameDecl.traverseTopDown(visitor);
        if(MoreConsts!=null) MoreConsts.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstType!=null) ConstType.traverseBottomUp(visitor);
        if(ConstNameDecl!=null) ConstNameDecl.traverseBottomUp(visitor);
        if(MoreConsts!=null) MoreConsts.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclaration(\n");

        if(ConstType!=null)
            buffer.append(ConstType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstNameDecl!=null)
            buffer.append(ConstNameDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MoreConsts!=null)
            buffer.append(MoreConsts.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclaration]");
        return buffer.toString();
    }
}
