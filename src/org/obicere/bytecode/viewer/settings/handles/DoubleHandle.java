package org.obicere.bytecode.viewer.settings.handles;

/**
 */
public class DoubleHandle implements Handle<Double> {

    @Override
    public Double decode(final String property) {
        try {
            return Double.parseDouble(property);
        } catch (final NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String encode(final Object value) {
        return Double.toString((Double) value);
    }
}
