package org.obicere.bytecode.viewer.startup.application;

import org.obicere.bytecode.core.objects.AnnotationDefaultAttribute;
import org.obicere.bytecode.core.objects.BootstrapMethodsAttribute;
import org.obicere.bytecode.core.objects.CodeAttribute;
import org.obicere.bytecode.core.objects.ConstantValueAttribute;
import org.obicere.bytecode.core.objects.DeprecatedAttribute;
import org.obicere.bytecode.core.objects.EnclosingMethodAttribute;
import org.obicere.bytecode.core.objects.InnerClassesAttribute;
import org.obicere.bytecode.core.objects.LineNumberTableAttribute;
import org.obicere.bytecode.core.objects.LocalVariableTableAttribute;
import org.obicere.bytecode.core.objects.LocalVariableTypeTableAttribute;
import org.obicere.bytecode.core.objects.RuntimeInvisibleAnnotationsAttribute;
import org.obicere.bytecode.core.objects.RuntimeInvisibleParameterAnnotationsAttribute;
import org.obicere.bytecode.core.objects.RuntimeInvisibleTypeAnnotationsAttribute;
import org.obicere.bytecode.core.objects.RuntimeVisibleAnnotationsAttribute;
import org.obicere.bytecode.core.objects.RuntimeVisibleParameterAnnotationsAttribute;
import org.obicere.bytecode.core.objects.RuntimeVisibleTypeAnnotationsAttribute;
import org.obicere.bytecode.core.objects.SignatureAttribute;
import org.obicere.bytecode.core.objects.SourceDebugExtensionAttribute;
import org.obicere.bytecode.core.objects.SourceFileAttribute;
import org.obicere.bytecode.core.objects.StackMapTableAttribute;
import org.obicere.bytecode.core.objects.SyntheticAttribute;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.modeler.AnnotationDefaultAttributeModeler;
import org.obicere.bytecode.viewer.modeler.BootstrapMethodsAttributeModeler;
import org.obicere.bytecode.viewer.modeler.CodeAttributeModeler;
import org.obicere.bytecode.viewer.modeler.ConstantValueAttributeModeler;
import org.obicere.bytecode.viewer.modeler.DeprecatedAttributeModeler;
import org.obicere.bytecode.viewer.modeler.EnclosingMethodAttributeModeler;
import org.obicere.bytecode.viewer.modeler.InnerClassesAttributeModeler;
import org.obicere.bytecode.viewer.modeler.LineNumberTableAttributeModeler;
import org.obicere.bytecode.viewer.modeler.LocalVariableTableAttributeModeler;
import org.obicere.bytecode.viewer.modeler.LocalVariableTypeTableAttributeModeler;
import org.obicere.bytecode.viewer.modeler.ModelerSet;
import org.obicere.bytecode.viewer.modeler.RuntimeInvisibleAnnotationsAttributeModeler;
import org.obicere.bytecode.viewer.modeler.RuntimeInvisibleParameterAnnotationsAttributeModeler;
import org.obicere.bytecode.viewer.modeler.RuntimeInvisibleTypeAnnotationsAttributeModeler;
import org.obicere.bytecode.viewer.modeler.RuntimeVisibleAnnotationsAttributeModeler;
import org.obicere.bytecode.viewer.modeler.RuntimeVisibleParameterAnnotationsAttributeModeler;
import org.obicere.bytecode.viewer.modeler.RuntimeVisibleTypeAnnotationsAttributeModeler;
import org.obicere.bytecode.viewer.modeler.SignatureAttributeModeler;
import org.obicere.bytecode.viewer.modeler.SourceDebugExtensionAttributeModeler;
import org.obicere.bytecode.viewer.modeler.SourceFileAttributeModeler;
import org.obicere.bytecode.viewer.modeler.StackMapTableAttributeModeler;
import org.obicere.bytecode.viewer.modeler.SyntheticAttributeModeler;
import org.obicere.bytecode.viewer.startup.StartUpTask;

/**
 */
public class ProvideAttributeModelers implements StartUpTask {
    @Override
    public void call(final Domain domain) {
        final ModelerSet modelerSet = domain.getModelers();

        modelerSet.add(AnnotationDefaultAttribute.IDENTIFIER, new AnnotationDefaultAttributeModeler());
        modelerSet.add(BootstrapMethodsAttribute.IDENTIFIER, new BootstrapMethodsAttributeModeler());
        modelerSet.add(CodeAttribute.IDENTIFIER, new CodeAttributeModeler());
        modelerSet.add(ConstantValueAttribute.IDENTIFIER, new ConstantValueAttributeModeler());
        modelerSet.add(DeprecatedAttribute.IDENTIFIER, new DeprecatedAttributeModeler());
        modelerSet.add(EnclosingMethodAttribute.IDENTIFIER, new EnclosingMethodAttributeModeler());
        modelerSet.add(InnerClassesAttribute.IDENTIFIER, new InnerClassesAttributeModeler());
        modelerSet.add(LineNumberTableAttribute.IDENTIFIER, new LineNumberTableAttributeModeler());
        modelerSet.add(LocalVariableTableAttribute.IDENTIFIER, new LocalVariableTableAttributeModeler());
        modelerSet.add(LocalVariableTypeTableAttribute.IDENTIFIER, new LocalVariableTypeTableAttributeModeler());
        modelerSet.add(RuntimeInvisibleAnnotationsAttribute.IDENTIFIER, new RuntimeInvisibleAnnotationsAttributeModeler());
        modelerSet.add(RuntimeInvisibleParameterAnnotationsAttribute.IDENTIFIER, new RuntimeInvisibleParameterAnnotationsAttributeModeler());
        modelerSet.add(RuntimeInvisibleTypeAnnotationsAttribute.IDENTIFIER, new RuntimeInvisibleTypeAnnotationsAttributeModeler());
        modelerSet.add(RuntimeVisibleAnnotationsAttribute.IDENTIFIER, new RuntimeVisibleAnnotationsAttributeModeler());
        modelerSet.add(RuntimeVisibleParameterAnnotationsAttribute.IDENTIFIER, new RuntimeVisibleParameterAnnotationsAttributeModeler());
        modelerSet.add(RuntimeVisibleTypeAnnotationsAttribute.IDENTIFIER, new RuntimeVisibleTypeAnnotationsAttributeModeler());
        modelerSet.add(SignatureAttribute.IDENTIFIER, new SignatureAttributeModeler());
        modelerSet.add(SourceDebugExtensionAttribute.IDENTIFIER, new SourceDebugExtensionAttributeModeler());
        modelerSet.add(SourceFileAttribute.IDENTIFIER, new SourceFileAttributeModeler());
        modelerSet.add(StackMapTableAttribute.IDENTIFIER, new StackMapTableAttributeModeler());
        modelerSet.add(SyntheticAttribute.IDENTIFIER, new SyntheticAttributeModeler());
    }
}