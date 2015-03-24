package com.sun.codemodel.tests;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;

import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.tests.util.CodeModelTestsUtils;
import com.sun.codemodel.writer.SingleStreamCodeWriter;

public class GenericTest {
    /**
     * https://github.com/UnquietCode/JCodeModel/issues/5 
     */
    
    @Test
    public void generateImport() throws Exception {

        final JCodeModel codeModel = new JCodeModel();
        final JDefinedClass stateClass = codeModel._class("Test");
        String abstractStateBaseClassName = "com.stateforge.statemachine.state.AbstractState<PingPingState, PingContext>";
        stateClass._extends(codeModel.directClass(abstractStateBaseClassName));
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        codeModel.build(new SingleStreamCodeWriter(os));
        String source = os.toString();
        System.out.print(source);
        assertTrue(source.contains("import com.stateforge.statemachine.state.AbstractState;"));
    }

}
