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
public class JGenProxy implements JGenerable {

    private JGenerable proxee;
    private final Resolver resolver;

    public JGenProxy(Resolver resolver) {
        this.resolver = resolver;
    }

    public void generate(JFormatter f) {
        if (proxee == null) {
            proxee = resolver.resolve();
        }
        f.g(proxee);
    }

    public static interface Resolver<E extends JGenerable> {

        public E resolve();
    }

}
