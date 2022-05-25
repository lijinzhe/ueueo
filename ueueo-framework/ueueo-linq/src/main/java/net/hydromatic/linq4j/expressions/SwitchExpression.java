package net.hydromatic.linq4j.expressions;

import lombok.Getter;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-24 21:29
 */
@Getter
public final class SwitchExpression extends Expression {

    public Collection<SwitchCase> cases;
    public Method comparison;
    public Expression defaultBody;
    public ExpressionType nodeType;
    public Expression switchValue;
    public Type type;

    /**
     * Creates an Expression.
     *
     * <p>The type of the expression may, at the caller's discretion, be a
     * regular class (because {@link Class} implements {@link Type}) or it may
     * be a different implementation that retains information about type
     * parameters.</p>
     *
     * @param nodeType Node type
     */
    public SwitchExpression(ExpressionType nodeType) {
        super(nodeType, Void.TYPE);
    }


    @Override
    public ExpressionType getNodeType() {
        return nodeType;
    }

    @Override
    public Type getType() {
        return type;
    }

    public SwitchExpression update(Expression switchValue, Collection<SwitchCase> cases, Expression defaultBody) {
        this.switchValue = switchValue;
        this.cases = cases;
        this.defaultBody = defaultBody;
        return this;
    }

    @Override
    public Expression accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
