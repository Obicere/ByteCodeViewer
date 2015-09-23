package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;

/**
 * @author Obicere
 */
public class RuntimeVisibleAnnotationsAttribute extends Attribute {

    private final Annotation[] annotations;

    public RuntimeVisibleAnnotationsAttribute(final Annotation[] annotations) {
        this.annotations = annotations;
    }

    public Annotation[] getAnnotations() {
        return annotations;
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        for (final Annotation annotation : annotations) {
            annotation.model(builder, parent);
        }
    }

}
