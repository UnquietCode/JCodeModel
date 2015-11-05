/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sun.codemodel;

/**
 *
 * @author lkroll
 */
public class JFieldProxy extends JExpressionImpl implements JField {

    private JField proxee = null;
    private final String name;
    private final FieldResolver resolver;
    
    public JFieldProxy(String name, FieldResolver resolver) {
        this.name = name;
        this.resolver = resolver;
    }

    public boolean isResolved() {
        if (proxee == null) {
            proxee = resolver.resolveField(name);
        }
        return proxee != null;
    }

    public void setProxee(JField ref) {
        if (ref instanceof JFieldProxy) {
            throw new RuntimeException("Building endless proxy hierarchies is not a good idea!");
        }
        this.proxee = ref;
    }
    
    public void generate(JFormatter f) {
        if (isResolved()) {
            f.g(proxee);
        } else {
            throw new UnresolvedFieldException(name);
        }
    }

    public JExpression assign(JExpression rhs) {
        return JExpr.assign(this, rhs);
    }

    public JExpression assignPlus(JExpression rhs) {
        return JExpr.assignPlus(this, rhs);
    }
    
    public static interface FieldResolver {

        public JField resolveField(String name);
    }
    
    public static class UnresolvedFieldException extends RuntimeException {

        private final String name;

        private UnresolvedFieldException(String name) {
            this.name = name;
        }

        @Override
        public String getMessage() {
            return "Proxied field " + name + " has not been resolved, yet";
        }

    }
}
