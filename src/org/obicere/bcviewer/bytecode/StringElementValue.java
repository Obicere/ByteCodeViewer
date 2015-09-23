package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.literals.StringElement;

/**
 * @author Obicere
 */
public class StringElementValue extends ElementValue {

    private static final int TAG = 's';

    private final int constantValueIndex;

    public StringElementValue(final int constantValueIndex) {
        super(TAG);
        this.constantValueIndex = constantValueIndex;
    }

    public int getConstantValueIndex() {
        return constantValueIndex;
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        final String constant = builder.getConstantPool().getAsString(constantValueIndex);
        parent.add(new StringElement("value", constant, builder));
    }
}
