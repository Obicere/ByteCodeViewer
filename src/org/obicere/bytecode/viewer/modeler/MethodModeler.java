package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.DefaultJCMethod;
import org.obicere.bytecode.core.objects.attribute.AnnotationDefaultAttribute;
import org.obicere.bytecode.core.objects.attribute.DefaultAttributeSet;
import org.obicere.bytecode.core.objects.attribute.CodeAttribute;
import org.obicere.bytecode.core.objects.constant.ConstantPool;
import org.obicere.bytecode.core.objects.attribute.ExceptionsAttribute;
import org.obicere.bytecode.core.objects.attribute.MethodParametersAttribute;
import org.obicere.bytecode.core.objects.common.Parameter;
import org.obicere.bytecode.core.objects.attribute.RuntimeInvisibleAnnotationsAttribute;
import org.obicere.bytecode.core.objects.attribute.RuntimeInvisibleParameterAnnotationsAttribute;
import org.obicere.bytecode.core.objects.attribute.RuntimeInvisibleTypeAnnotationsAttribute;
import org.obicere.bytecode.core.objects.attribute.RuntimeVisibleAnnotationsAttribute;
import org.obicere.bytecode.core.objects.attribute.RuntimeVisibleParameterAnnotationsAttribute;
import org.obicere.bytecode.core.objects.attribute.RuntimeVisibleTypeAnnotationsAttribute;
import org.obicere.bytecode.core.objects.attribute.SignatureAttribute;
import org.obicere.bytecode.core.objects.attribute.SyntheticAttribute;
import org.obicere.bytecode.core.objects.signature.MethodSignature;
import org.obicere.bytecode.core.objects.signature.Parameters;
import org.obicere.bytecode.core.objects.signature.Result;
import org.obicere.bytecode.core.objects.signature.ThrowsSignatures;
import org.obicere.bytecode.core.objects.signature.TypeParameters;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.util.ByteCodeUtils;

import java.util.Set;

/**
 */
public class MethodModeler implements Modeler<DefaultJCMethod> {

    @Override
    public void model(final DefaultJCMethod element, final DocumentBuilder builder) {
        modelSynthetic(element, builder);
        modelAnnotations(element, builder);
        modelAccessFlags(element, builder);
        modelSignature(element, builder);
        modelAbstractClose(element, builder);
        modelCodeBody(element, builder);
    }

    private void modelSynthetic(final DefaultJCMethod element, final DocumentBuilder builder) {
        final DefaultAttributeSet attributes = element.getAttributeSet();
        final SyntheticAttribute synthetic = attributes.getAttribute(SyntheticAttribute.class);
        if (synthetic != null) {
            builder.model(synthetic);
        } else if (ByteCodeUtils.isSynthetic(element.getAccessFlags())) {
            builder.model(new SyntheticAttribute(0));
        }
        builder.newLine();
    }

    private void modelAccessFlags(final DefaultJCMethod element, final DocumentBuilder builder) {
        final DefaultAttributeSet attributes = element.getAttributeSet();

        final int accessFlags = element.getAccessFlags();
        final String[] accessNames = ByteCodeUtils.getMethodAccessNames(accessFlags);

        // make sure to add the default flag if the method has a body
        // and its containing class is an interface
        final CodeAttribute codeAttribute = attributes.getAttribute(CodeAttribute.class);

        // if this method has a body, yet it the containing class is an interface
        if (codeAttribute != null && ByteCodeUtils.isInterface(builder.getClassFile().getAccessFlags())) {
            builder.addKeyword("default ");
        }

        for (final String accessName : accessNames) {
            builder.addKeyword(accessName);
            builder.pad(1);
        }
    }

    private void modelSignature(final DefaultJCMethod element, final DocumentBuilder builder) {
        final ConstantPool constantPool = builder.getConstantPool();
        final DefaultAttributeSet attributes = element.getAttributeSet();

        final int nameIndex = element.getNameIndex();
        final String methodName = constantPool.getAsString(nameIndex);
        if (methodName.equals("<clinit>")) {
            // the name 'static' was added through access flags
            // there is no signature to be added
            return;
        }

        final MethodSignature signature;
        final SignatureAttribute attribute = attributes.getAttribute(SignatureAttribute.class);
        if (attribute != null) {
            signature = attribute.getAsMethodSignature(constantPool);
        } else {
            final int descriptorIndex = element.getDescriptorIndex();
            final String name = constantPool.getAsString(descriptorIndex);
            signature = MethodSignature.parse(name);
        }

        modelTypeParameters(signature, builder);
        modelMethodName(methodName, signature, builder);
        modelParameters(element, signature, builder);
        modelExceptions(element, signature, builder);
    }

    private void modelTypeParameters(final MethodSignature signature, final DocumentBuilder builder) {
        final TypeParameters typeParameters = signature.getTypeParameters();
        builder.model(typeParameters);
    }

    private void modelMethodName(final String methodName, final MethodSignature signature, final DocumentBuilder builder) {

        if (methodName.equals("<init>")) {
            // instead replace method name "<init>" with the class name
            final boolean importMode = builder.getDomain().getSettingsController().getSettings().getBoolean("code.importMode");
            if (importMode) {
                builder.add(ByteCodeUtils.getClassName(builder.getClassFile().getName()));
            } else {
                builder.add(ByteCodeUtils.getQualifiedName(builder.getClassFile().getName()));
            }
        } else {
            // set the name to the method name otherwise - no name for clinit
            // static initializer has 'static' access flag which is the name
            final Result result = signature.getResult();
            builder.model(result);
            builder.add(" ");
            builder.add(methodName);
        }
    }

    private void modelParameters(final DefaultJCMethod element, final MethodSignature signature, final DocumentBuilder builder) {
        final DefaultAttributeSet attributes = element.getAttributeSet();

        addAnnotationsSignature(element, signature);
        final Parameters parameters = signature.getParameters();

        final MethodParametersAttribute parameterAttribute = attributes.getAttribute(MethodParametersAttribute.class);
        if (parameterAttribute != null) {
            final Parameter[] names = parameterAttribute.getParameters();
            parameters.setParameters(names);
        }
        builder.model(parameters);
    }

    private void modelExceptions(final DefaultJCMethod element, final MethodSignature signature, final DocumentBuilder builder) {
        final DefaultAttributeSet attributes = element.getAttributeSet();
        final ConstantPool constantPool = builder.getConstantPool();

        final ThrowsSignatures signatures = signature.getThrowsSignatures();
        final ExceptionsAttribute exceptionsAttribute = attributes.getAttribute(ExceptionsAttribute.class);

        boolean first = true;
        if (exceptionsAttribute != null) {
            for (final int index : exceptionsAttribute.getIndexTable()) {
                if (first) {
                    builder.addKeyword(" throws ");
                    first = false;
                } else {
                    builder.comma();
                }
                final String name = constantPool.getAsString(index);
                builder.add(ByteCodeUtils.getQualifiedName(name));
            }
        }

        if (signatures.size() != 0) {
            if (first) {
                builder.addKeyword(" throws ");
            } else {
                builder.comma();
            }
            builder.model(signatures);
        }
    }

    private void addAnnotationsSignature(final DefaultJCMethod element, final MethodSignature signature) {
        final DefaultAttributeSet attributes = element.getAttributeSet();

        final RuntimeVisibleParameterAnnotationsAttribute rvpa = attributes.getAttribute(RuntimeVisibleParameterAnnotationsAttribute.class);
        final RuntimeInvisibleParameterAnnotationsAttribute ripa = attributes.getAttribute(RuntimeInvisibleParameterAnnotationsAttribute.class);
        final RuntimeVisibleTypeAnnotationsAttribute rvta = attributes.getAttribute(RuntimeVisibleTypeAnnotationsAttribute.class);
        final RuntimeInvisibleTypeAnnotationsAttribute rita = attributes.getAttribute(RuntimeInvisibleTypeAnnotationsAttribute.class);

        if (rvpa != null) {
            signature.addAnnotations(rvpa.getParameterAnnotations());
        }
        if (ripa != null) {
            signature.addAnnotations(ripa.getParameterAnnotations());
        }
        if (rvta != null) {
            signature.addAnnotations(rvta.getAnnotations());
        }
        if (rita != null) {
            signature.addAnnotations(rita.getAnnotations());
        }
    }

    private void modelAnnotations(final DefaultJCMethod element, final DocumentBuilder builder) {
        final DefaultAttributeSet attributes = element.getAttributeSet();

        final Set<RuntimeVisibleAnnotationsAttribute> rvaAttributes = attributes.getAttributes(RuntimeVisibleAnnotationsAttribute.class);
        final Set<RuntimeInvisibleAnnotationsAttribute> riaAttributes = attributes.getAttributes(RuntimeInvisibleAnnotationsAttribute.class);

        if (rvaAttributes != null) {
            rvaAttributes.forEach(e -> {
                builder.newLine();
            });
        }
        if (riaAttributes != null) {
            riaAttributes.forEach(e -> {
                builder.model(e);
                builder.newLine();
            });
        }
    }

    private void modelAbstractClose(final DefaultJCMethod element, final DocumentBuilder builder) {
        final DefaultAttributeSet attributes = element.getAttributeSet();

        final AnnotationDefaultAttribute hasDefault = attributes.getAttribute(AnnotationDefaultAttribute.class);
        if (hasDefault != null) {
            builder.model(hasDefault);
            builder.add(";");
        }
    }

    private void modelCodeBody(final DefaultJCMethod element, final DocumentBuilder builder) {
        final DefaultAttributeSet attributes = element.getAttributeSet();

        final CodeAttribute code = attributes.getAttribute(CodeAttribute.class);

        if (code != null) {

            builder.add("{");
            builder.indent();
            builder.openCollapsibleBlock();

            builder.model(code);

            builder.unindent();
            builder.closeCollapsibleBlock();
            builder.add("}");
        } else {
            builder.add(";");
        }
    }
}
