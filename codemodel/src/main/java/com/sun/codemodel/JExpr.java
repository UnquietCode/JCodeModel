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

/**
 * Factory methods that generate various {@link JExpression}s.
 */
public abstract class JExpr {

    /**
     * This class is not instanciable.
     */
    private JExpr() {
    }

    public static JExpressionStatement assign(JAssignmentTarget lhs, JExpression rhs) {
        return new JAssignment(lhs, rhs);
    }

    public static JExpressionStatement assignPlus(JAssignmentTarget lhs, JExpression rhs) {
        return new JAssignment(lhs, rhs, "+");
    }

    public static JExpressionStatement assignMinus(JAssignmentTarget lhs, JExpression rhs) {
        return new JAssignment(lhs, rhs, "-");
    }

    public static JExpressionStatement assignTimes(JAssignmentTarget lhs, JExpression rhs) {
        return new JAssignment(lhs, rhs, "*");
    }

    public static JExpressionStatement assignDivide(JAssignmentTarget lhs, JExpression rhs) {
        return new JAssignment(lhs, rhs, "/");
    }

    public static JExpressionStatement assignMod(JAssignmentTarget lhs, JExpression rhs) {
        return new JAssignment(lhs, rhs, "%");
    }

    public static JExpressionStatement assignShl(JAssignmentTarget lhs, JExpression rhs) {
        return new JAssignment(lhs, rhs, "<<");
    }

    public static JExpressionStatement assignShr(JAssignmentTarget lhs, JExpression rhs) {
        return new JAssignment(lhs, rhs, ">>");
    }

    public static JExpressionStatement assignUshr(JAssignmentTarget lhs, JExpression rhs) {
        return new JAssignment(lhs, rhs, ">>>");
    }

    public static JExpressionStatement assignAnd(JAssignmentTarget lhs, JExpression rhs) {
        return new JAssignment(lhs, rhs, "&");
    }

    public static JExpressionStatement assignOr(JAssignmentTarget lhs, JExpression rhs) {
        return new JAssignment(lhs, rhs, "|");
    }

    public static JExpressionStatement assignXor(JAssignmentTarget lhs, JExpression rhs) {
        return new JAssignment(lhs, rhs, "^");
    }

    public static JExpressionStatement incr(final JExpression expression) {
        return new JExpressionStatementWrapper(JOp.incr(expression));
    }

    public static JExpressionStatement preincr(final JExpression expression) {
        return new JExpressionStatementWrapper(JOp.preincr(expression));
    }

    public static JExpressionStatement decr(final JExpression expression) {
        return new JExpressionStatementWrapper(JOp.decr(expression));
    }

    public static JExpressionStatement predecr(final JExpression expression) {
        return new JExpressionStatementWrapper(JOp.predecr(expression));
    }

    public static JInvocation _new(JClass c) {
        return new JInvocation(c);
    }

    public static JInvocation _new(JType t) {
        return new JInvocation(t);
    }

    public static JInvocation invoke(String method) {
        return new JInvocation((JExpression) null, method);
    }

    public static JInvocation invoke(JMethod method) {
        return new JInvocation((JExpression) null, method);
    }

    public static JInvocation invoke(JExpression lhs, JMethod method) {
        return new JInvocation(lhs, method);
    }

    public static JInvocation invoke(JExpression lhs, String method) {
        return new JInvocation(lhs, method);
    }

    public static JFieldRef ref(String field) {
        return new JFieldRef((JExpression) null, field);
    }

    public static JFieldRef ref(JExpression lhs, JVar field) {
        return new JFieldRef(lhs, field);
    }

    public static JFieldRef ref(JExpression lhs, String field) {
        return new JFieldRef(lhs, field);
    }

    public static JFieldRef refthis(String field) {
        return new JFieldRef(null, field, true);
    }

    public static JExpression dotclass(final JClass cl) {
        return new JExpressionImpl() {
            public void generate(JFormatter f) {
                JClass c;
                if (cl instanceof JNarrowedClass) {
                    c = ((JNarrowedClass) cl).basis;
                } else {
                    c = cl;
                }
                f.g(c).p(".class");
            }
        };
    }
    
    public static JExpression dotthis(final JClass cl) {
        return new JExpressionImpl() {
            public void generate(JFormatter f) {
                JClass c;
                if (cl instanceof JNarrowedClass) {
                    c = ((JNarrowedClass) cl).basis;
                } else {
                    c = cl;
                }
                f.g(c).p(".this");
            }
        };
    }
    
    public static JExpression dotsuper(final JClass cl) {
        return new JExpressionImpl() {
            public void generate(JFormatter f) {
                JClass c;
                if (cl instanceof JNarrowedClass) {
                    c = ((JNarrowedClass) cl).basis;
                } else {
                    c = cl;
                }
                f.g(c).p(".super");
            }
        };
    }

    public static JArrayCompRef component(JExpression lhs, JExpression index) {
        return new JArrayCompRef(lhs, index);
    }

    public static JCast cast(JType type, JExpression expr) {
        return new JCast(type, expr);
    }

    public static JArray newArray(JType type) {
        return newArray(type, null);
    }

    /**
     * Generates {@code new T[size]}.
     *
     * @param type
     * The type of the array component. 'T' or {@code new T[size]}.
     */
    public static JArray newArray(JType type, JExpression size) {
        // you cannot create an array whose component type is a generic
        return new JArray(type.erasure(), size);
    }

    /**
     * Generates {@code new T[size]}.
     *
     * @param type
     * The type of the array component. 'T' or {@code new T[size]}.
     */
    public static JArray newArray(JType type, int size) {
        return newArray(type, lit(size));
    }

    private static final JExpression __this = new JAtom("this");

    /**
     * Returns a reference to "this", an implicit reference
     * to the current object.
     */
    public static JExpression _this() {
        return __this;
    }

    private static final JExpression __super = new JAtom("super");

    /**
     * Returns a reference to "super", an implicit reference
     * to the super class.
     */
    public static JExpression _super() {
        return __super;
    }

    /* -- Literals -- */
    private static final JExpression __null = new JAtom("null");

    public static JExpression _null() {
        return __null;
    }

    /**
     * Boolean constant that represents <code>true</code>
     */
    public static final JExpression TRUE = new JAtom("true");

    /**
     * Boolean constant that represents <code>false</code>
     */
    public static final JExpression FALSE = new JAtom("false");

    public static JExpression lit(boolean b) {
        return b ? TRUE : FALSE;
    }

    public static JExpression lit(int n) {
        return new JAtom(Integer.toString(n));
    }

    public static JExpression lit(long n) {
        return new JAtom(Long.toString(n) + "L");
    }

    public static JExpression lit(float f) {
        if (f == Float.NEGATIVE_INFINITY) {
            return new JAtom("java.lang.Float.NEGATIVE_INFINITY");
        } else if (f == Float.POSITIVE_INFINITY) {
            return new JAtom("java.lang.Float.POSITIVE_INFINITY");
        } else if (Float.isNaN(f)) {
            return new JAtom("java.lang.Float.NaN");
        } else {
            return new JAtom(Float.toString(f) + "F");
        }
    }

    public static JExpression lit(double d) {
        if (d == Double.NEGATIVE_INFINITY) {
            return new JAtom("java.lang.Double.NEGATIVE_INFINITY");
        } else if (d == Double.POSITIVE_INFINITY) {
            return new JAtom("java.lang.Double.POSITIVE_INFINITY");
        } else if (Double.isNaN(d)) {
            return new JAtom("java.lang.Double.NaN");
        } else {
            return new JAtom(Double.toString(d) + "D");
        }
    }

    static final String charEscape = "\b\t\n\f\r\"\'\\";
    static final String charMacro = "btnfr\"'\\";

    /**
     * Escapes the given string, then surrounds it by the specified
     * quotation mark.
     */
    public static String quotify(char quote, String s) {
        int n = s.length();
        StringBuilder sb = new StringBuilder(n + 2);
        sb.append(quote);
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            int j = charEscape.indexOf(c);
            if (j >= 0) {
                if ((quote == '"' && c == '\'') || (quote == '\'' && c == '"')) {
                    sb.append(c);
                } else {
                    sb.append('\\');
                    sb.append(charMacro.charAt(j));
                }
            } else {
                // technically Unicode escape shouldn't be done here,
                // for it's a lexical level handling.
                // 
                // However, various tools are so broken around this area,
                // so just to be on the safe side, it's better to do
                // the escaping here (regardless of the actual file encoding)
                //
                // see bug 
                if (c < 0x20 || 0x7E < c) {
                    // not printable. use Unicode escape
                    sb.append("\\u");
                    String hex = Integer.toHexString(((int) c) & 0xFFFF);
                    for (int k = hex.length(); k < 4; k++) {
                        sb.append('0');
                    }
                    sb.append(hex);
                } else {
                    sb.append(c);
                }
            }
        }
        sb.append(quote);
        return sb.toString();
    }

    public static JExpression lit(char c) {
        return new JAtom(quotify('\'', "" + c));
    }

    public static JExpression lit(String s) {
        return new JStringLiteral(s);
    }

    /**
     * Creates an expression directly from a source code fragment.
     * <p>
     * <p>
     * This method can be used as a short-cut to create a JExpression.
     * For example, instead of <code>_a.gt(_b)</code>, you can write
     * it as: <code>{@code JExpr.direct("a>b")}</code>.
     * <p>
     * <p>
     * Be warned that there is a danger in using this method,
     * as it obfuscates the object model.
     */
    public static JExpression direct(final String source) {
        if ((source == null) || source.isEmpty()) {
            throw new RuntimeException("DUDE!");
        }
        return new JExpressionImpl() {
            public void generate(JFormatter f) {
                f.p('(').p(source).p(')');
            }
        };
    }

    static class JExpressionStatementWrapper extends JExpressionImpl implements JExpressionStatement {

        final JExpression expression;

        JExpressionStatementWrapper(JExpression expression) {
            this.expression = expression;
        }

        public void generate(JFormatter f) {
            expression.generate(f);
        }

        public void state(JFormatter f) {
            expression.generate(f);
            f.p(';').nl();
        }
    }
}
