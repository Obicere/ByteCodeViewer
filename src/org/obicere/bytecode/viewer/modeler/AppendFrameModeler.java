package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.AppendFrame;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class AppendFrameModeler extends StackMapFrameModeler<AppendFrame> {

    @Override
    protected void modelValue(final AppendFrame frame, final DocumentBuilder builder) {
        builder.newLine();
        builder.add("Locals:");
        modelInfo(builder, frame.getLocals());
    }
}