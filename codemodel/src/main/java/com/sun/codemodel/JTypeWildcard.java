/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.codemodel;

import java.util.Iterator;
import java.util.List;

/**
 * Represents a wildcard type like "? extends Foo" or "? super Foo".
 *
 * <p>
 * Instances of this class can be obtained from {@link JClass#wildcard()}
 *
 * <p>
 * Our modeling of types are starting to look really ugly.
 * ideally it should have been done somewhat like APT,
 * but it's too late now.
 *
 * @author Kohsuke Kawaguchi
 */
final class JTypeWildcard extends JClass {

    private final JClass bound;
    private final boolean _super;

    JTypeWildcard(JClass bound, boolean _super) {
        super(bound.owner());
        this.bound = bound;
        this._super = _super;
    }

    JTypeWildcard(JClass bound) {
        this(bound, false);
    }

    public String name() {
        return _super ? "? super " + bound.name() : "? extends " + bound.name();
    }

    public String fullName() {
        return _super ? "? super " + bound.fullName() : "? extends " + bound.fullName();
    }

    public JPackage _package() {
        return null;
    }

    /**
     * Returns the class bound of this variable.
     *
     * <p>
     * If no bound is given, this method returns {@link Object}.
     */
    public JClass _extends() {
        if(bound!=null)
            return bound;
        else
            return owner().ref(Object.class);
    }

    /**
     * Returns the interface bounds of this variable.
     */
    public Iterator<JClass> _implements() {
        return bound._implements();
    }

    public boolean isInterface() {
        return false;
    }

    public boolean isAbstract() {
        return false;
    }

    protected JClass substituteParams(JTypeVar[] variables, List<JClass> bindings) {
        JClass nb = bound.substituteParams(variables,bindings);
        if(nb==bound)
            return this;
        else
            return new JTypeWildcard(nb);
    }

    public void generate(JFormatter f) {
        if (_super) {
            f.p("? super").g(bound);
        } else if(bound._extends()==null) {
             f.p("?");   // instead of "? extends Object"
        } else {
            f.p("? extends").g(bound);
        }
    }

    @Override
    public JClass inner(String name) {
        return null;
    }
}
