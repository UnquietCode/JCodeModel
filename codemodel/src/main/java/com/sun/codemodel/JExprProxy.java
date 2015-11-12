/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sun.codemodel;

import com.sun.codemodel.JGenProxy.Resolver;

/**
 *
 * @author lkroll
 */
public class JExprProxy extends JExpressionImpl {

    private JExpression proxee = null;
    private final Resolver<JExpression> resolver;
    
    public JExprProxy(Resolver<JExpression> resolver) {
        this.resolver = resolver;
    }
    
    public void generate(JFormatter f) {
        if (proxee == null) {
            proxee = resolver.resolve();
        }
        f.g(proxee);
    }
}
