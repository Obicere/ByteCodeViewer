package org.obicere.bytecode.viewer.modeler.instruction;

import org.obicere.bytecode.core.objects.CodeAttribute;
import org.obicere.bytecode.core.objects.instruction.if_icmpge;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class if_icmpgeModeler extends InstructionModeler<if_icmpge> {
    @Override
    protected void modelValue(final if_icmpge element, final DocumentBuilder builder) {
        final int start = element.getStart();

        // ensure to cast to signed 16-bit §6.5.if_icmp_cond
        final short offset = (short) element.getBranchOffset();

        final CodeAttribute code = (CodeAttribute) builder.getProperty("code");
        final String line = code.getBlockName(start, offset);

        builder.tab();
        if (line == null) {
            builder.add(offset);
        } else {
            builder.add(line);
        }
    }
}
