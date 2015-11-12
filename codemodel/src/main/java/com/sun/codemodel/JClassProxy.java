/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sun.codemodel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author lkroll
 */
public class JClassProxy extends JClass {

    private JClass proxee = null;
    private final Map<String, JClassProxy> innerProxies = new HashMap<String, JClassProxy>();
    private final String shortName;
    private final ClassResolver resolver;

    public JClassProxy(JCodeModel _owner, String shortName, ClassResolver resolver) {
        super(_owner);
        this.shortName = shortName;
        this.resolver = resolver;
    }

    public boolean isResolved() {
        return proxee != null;
    }

    public void assertResolved() {
        if (proxee == null) {
            proxee = resolver.resolveClass(shortName);
        }
        if (proxee == null) {
            throw new RuntimeException("Class " + shortName + " could not be resolved using resolver " + resolver);
        }
    }

    public void setProxee(JClass clazz) {
        if (clazz instanceof JClassProxy) {
            throw new RuntimeException("Building endless proxy hierarchies is not a good idea!");
        }
        this.proxee = clazz;
    }

    @Override
    public String name() {
        if (isResolved()) {
            return proxee.name();
        } else {
            return shortName;
            //throw new UnresolvedClassException(shortName);
        }
    }

    @Override
    public JPackage _package() {
        assertResolved();
        if (isResolved()) {
            return proxee._package();
        } else {
            throw new UnresolvedClassException(shortName);
        }
    }

    @Override
    public JClass inner(String name) {
        if (isResolved()) {
            return proxee.inner(name);
        } else {
            return new JClassProxy(this.owner(), name, new InnerClassResolver(this));
        }
    }

    @Override
    public JClass outer() {
        assertResolved();
        if (isResolved()) {
            return proxee.outer();
        } else {
            throw new UnresolvedClassException(shortName);
        }
    }

    @Override
    public JClass _extends() {
        assertResolved();
        if (isResolved()) {
            return proxee._extends();
        } else {
            throw new UnresolvedClassException(shortName);
        }
    }

    @Override
    public Iterator<JClass> _implements() {
        assertResolved();
        if (isResolved()) {
            return proxee._implements();
        } else {
            throw new UnresolvedClassException(shortName);
        }
    }

    @Override
    public JTypeVar[] typeParams() {
        assertResolved();
        if (isResolved()) {
            return proxee.typeParams();
        } else {
            throw new UnresolvedClassException(shortName);
        }
    }

    @Override
    public boolean isInterface() {
        assertResolved();
        if (isResolved()) {
            return proxee.isInterface();
        } else {
            throw new UnresolvedClassException(shortName);
        }
    }

    @Override
    public boolean isAbstract() {
        assertResolved();
        if (isResolved()) {
            return proxee.isAbstract();
        } else {
            throw new UnresolvedClassException(shortName);
        }
    }

    @Override
    public JPrimitiveType getPrimitiveType() {
        assertResolved();
        if (isResolved()) {
            return proxee.getPrimitiveType();
        } else {
            throw new UnresolvedClassException(shortName);
        }
    }

    @Override
    public JClass erasure() {
        assertResolved();
        if (isResolved()) {
            return proxee.erasure();
        } else {
            throw new UnresolvedClassException(shortName);
        }
    }

    @Override
    protected JClass substituteParams(JTypeVar[] variables, List<JClass> bindings) {
        assertResolved();
        if (isResolved()) {
            return proxee.substituteParams(variables, bindings);
        } else {
            throw new UnresolvedClassException(shortName);
        }
    }

    @Override
    public String fullName() {
        assertResolved();
        if (isResolved()) {
            return proxee.fullName();
        } else {
            throw new UnresolvedClassException(shortName);
        }
    }

    @Override
    public void generate(JFormatter f) {
        assertResolved();
        if (isResolved()) {
            f.t(proxee);
        } else {
            throw new UnresolvedClassException(shortName);
        }
    }

    /**
     * Prints the class name in javadoc @link format.
     */
    @Override
    void printLink(JFormatter f) {
        assertResolved();
        if (isResolved()) {
            f.p("{@link ").g(proxee).p('}');
        } else {
            throw new UnresolvedClassException(shortName);
        }

    }

    public static class UnresolvedClassException extends RuntimeException {

        private final String shortName;

        private UnresolvedClassException(String shortName) {
            this.shortName = shortName;
        }

        @Override
        public String getMessage() {
            return "Proxied class " + shortName + " has not been resolved, yet";
        }

    }

    public static interface ClassResolver {

        public JClass resolveClass(String name);
    }

    private static class InnerClassResolver implements ClassResolver {

        private final JClassProxy outer;

        InnerClassResolver(JClassProxy outer) {
            this.outer = outer;
        }

        public JClass resolveClass(String name) {
            if (!outer.isResolved()) {
                outer.assertResolved();
            }
            System.out.println("Proxee type is: " + outer.proxee);
            if (outer.proxee instanceof JDefinedClass) {
                JDefinedClass jdc = (JDefinedClass) outer.proxee;
                for (Entry<String, JDefinedClass> e : jdc.getClasses().entrySet()) {
                    JDefinedClass jdcInner = e.getValue();
                    System.out.println("   " + e.getKey() + " --> " + jdcInner.fullName());
                }
            }
            return outer.proxee.inner(name);

        }

        @Override
        public String toString() {
            return "InnerClassResolver(" + outer + ")";
        }
    }
}
