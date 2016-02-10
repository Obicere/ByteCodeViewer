package org.obicere.bytecode.viewer.startup.application;

import org.obicere.bytecode.core.objects.AnnotationElementValue;
import org.obicere.bytecode.core.objects.ArrayElementValue;
import org.obicere.bytecode.core.objects.BooleanElementValue;
import org.obicere.bytecode.core.objects.ByteElementValue;
import org.obicere.bytecode.core.objects.CharacterElementValue;
import org.obicere.bytecode.core.objects.ClassElementValue;
import org.obicere.bytecode.core.objects.DoubleElementValue;
import org.obicere.bytecode.core.objects.ElementValuePair;
import org.obicere.bytecode.core.objects.EnumElementValue;
import org.obicere.bytecode.core.objects.FloatElementValue;
import org.obicere.bytecode.core.objects.IntegerElementValue;
import org.obicere.bytecode.core.objects.LongElementValue;
import org.obicere.bytecode.core.objects.ShortElementValue;
import org.obicere.bytecode.core.objects.StringElementValue;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.modeler.AnnotationElementValueModeler;
import org.obicere.bytecode.viewer.modeler.ArrayElementValueModeler;
import org.obicere.bytecode.viewer.modeler.BooleanElementValueModeler;
import org.obicere.bytecode.viewer.modeler.ByteElementValueModeler;
import org.obicere.bytecode.viewer.modeler.CharacterElementValueModeler;
import org.obicere.bytecode.viewer.modeler.ClassElementValueModeler;
import org.obicere.bytecode.viewer.modeler.DoubleElementValueModeler;
import org.obicere.bytecode.viewer.modeler.ElementValuePairModeler;
import org.obicere.bytecode.viewer.modeler.EnumElementValueModeler;
import org.obicere.bytecode.viewer.modeler.FloatElementValueModeler;
import org.obicere.bytecode.viewer.modeler.IntegerElementValueModeler;
import org.obicere.bytecode.viewer.modeler.LongElementValueModeler;
import org.obicere.bytecode.viewer.modeler.ModelerSet;
import org.obicere.bytecode.viewer.modeler.ShortElementValueModeler;
import org.obicere.bytecode.viewer.modeler.StringElementValueModeler;
import org.obicere.bytecode.viewer.startup.StartUpTask;

/**
 */
public class ProvideElementValueModelers implements StartUpTask {
    @Override
    public void call(final Domain domain) {
        final ModelerSet modelerSet = domain.getModelers();

        modelerSet.add(ElementValuePair.IDENTIFIER, new ElementValuePairModeler());

        modelerSet.add(AnnotationElementValue.IDENTIFIER, new AnnotationElementValueModeler());
        modelerSet.add(ArrayElementValue.IDENTIFIER, new ArrayElementValueModeler());
        modelerSet.add(BooleanElementValue.IDENTIFIER, new BooleanElementValueModeler());
        modelerSet.add(ByteElementValue.IDENTIFIER, new ByteElementValueModeler());
        modelerSet.add(CharacterElementValue.IDENTIFIER, new CharacterElementValueModeler());
        modelerSet.add(ClassElementValue.IDENTIFIER, new ClassElementValueModeler());
        modelerSet.add(DoubleElementValue.IDENTIFIER, new DoubleElementValueModeler());
        modelerSet.add(EnumElementValue.IDENTIFIER, new EnumElementValueModeler());
        modelerSet.add(FloatElementValue.IDENTIFIER, new FloatElementValueModeler());
        modelerSet.add(IntegerElementValue.IDENTIFIER, new IntegerElementValueModeler());
        modelerSet.add(LongElementValue.IDENTIFIER, new LongElementValueModeler());
        modelerSet.add(ShortElementValue.IDENTIFIER, new ShortElementValueModeler());
        modelerSet.add(StringElementValue.IDENTIFIER, new StringElementValueModeler());
    }
}