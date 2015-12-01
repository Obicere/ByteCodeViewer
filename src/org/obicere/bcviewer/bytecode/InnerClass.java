package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public class InnerClass extends ByteCodeElement {

    private final int innerClassInfoIndex;
    private final int outerClassInfoIndex;
    private final int innerNameIndex;
    private final int innerClassAccessFlags;

    public InnerClass(final int innerClassInfoIndex, final int outerClassInfoIndex, final int innerNameIndex, final int innerClassAccessFlags) {
        this.innerClassInfoIndex = innerClassInfoIndex;
        this.outerClassInfoIndex = outerClassInfoIndex;
        this.innerNameIndex = innerNameIndex;
        this.innerClassAccessFlags = innerClassAccessFlags;
    }

    public int getInnerClassInfoIndex() {
        return innerClassInfoIndex;
    }

    public int getOuterClassInfoIndex() {
        return outerClassInfoIndex;
    }

    public int getInnerNameIndex() {
        return innerNameIndex;
    }

    public int getInnerClassAccessFlags() {
        return innerClassAccessFlags;
    }

    @Override
    public void model(final DocumentBuilder builder) {
        final String name = builder.getConstantPool().getAsString(innerClassInfoIndex);

        final String modelledProperty = name + ".modelled";
        if(builder.getProperty(modelledProperty) != null){
            return;
        }
        builder.setProperty("accessFlags", innerClassAccessFlags);

        final ClassFile file = builder.getClassInformation().getClass(name);

        if (file == null) {
            builder.addComment("Could not find inner class: " + name);
            return;
        }
        builder.setProperty(modelledProperty, "true");
        builder.setWorkingClass(file);
        file.model(builder);
        builder.clearWorkingClass();
    }
}
