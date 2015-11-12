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
public class JDirectRef extends JExpressionImpl implements JAssignmentTarget {

    private final String ref;

    public JDirectRef(String ref) {
        this.ref = ref;
    }

    public void generate(JFormatter f) {
        f.p(ref);
    }

    public JExpression assign(JExpression rhs) {
        return JExpr.assign(this, rhs);
    }

    public JExpression assignPlus(JExpression rhs) {
        return JExpr.assignPlus(this, rhs);
    }

}
