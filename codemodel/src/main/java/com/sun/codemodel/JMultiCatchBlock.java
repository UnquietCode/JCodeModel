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
public class JMultiCatchBlock implements JTryBlock.CatchBlock {

    private final JMods mods;
    private final String var;
    private final JClass[] exceptions;
    private JBlock body = new JBlock();
    private JVar variable = null;
    
    JMultiCatchBlock(JMods mods, String name, JClass... exceptions) {
        this.mods = mods;
        var = name;
        this.exceptions = exceptions;
    }
    
    public JBlock body() {
        return body;
    }
    
    public JVar getVar() {
        if (variable == null) {
            variable = new JVar(mods, exceptions[0], var, null);
        }
        return variable;
    }
    
    public void generate(JFormatter f) {
        f.p("catch(");
        f.g(mods);
        for (int i = 0; i < exceptions.length; i++) {
            f.g(exceptions[i]);
            if (i != (exceptions.length -1)) {
                f.p(" | ");
            }
        }
        f.id(var).p(')').g(body);
    }
    
}
