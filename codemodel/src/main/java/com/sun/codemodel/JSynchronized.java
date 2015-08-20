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
public class JSynchronized implements JStatement {

    private final JExpression lock;
    private final JBlock body = new JBlock();

    JSynchronized(JExpression lock) {
        this.lock = lock;
    }

    public JBlock body() {
        return this.body;
    }

    public void state(JFormatter f) {
        if (JOp.hasTopOp(lock)) {
            f.p("synchronized").g(lock);
        } else {
            f.p("synchronized(").g(lock).p(')');
        }
        f.g(body).nl();
    }

}
