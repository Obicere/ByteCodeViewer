package org.obicere.bcviewer.gui.swing.settings;

import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.settings.SettingsController;
import org.obicere.bcviewer.settings.target.NumberSetting;
import org.obicere.bcviewer.settings.target.Setting;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 */
public class FloatSettingPanel extends SettingPanel<Float> {

    private final JLabel descriptor;

    private final JSpinner spinner;

    public FloatSettingPanel(final Domain domain, final Setting<Float> setting) {
        super(setting);

        final SettingsController controller = domain.getSettingsController();

        final float minValue;
        final float maxValue;

        if (setting instanceof NumberSetting) {
            final NumberSetting<Float> floatSetting = (NumberSetting<Float>) setting;
            minValue = floatSetting.getMinValue();
            maxValue = floatSetting.getMaxValue();
        } else {
            minValue = Float.MIN_VALUE;
            maxValue = Float.MAX_VALUE;
        }

        this.descriptor = new JLabel(setting.getDescriptor());
        this.spinner = new JSpinner(new SpinnerNumberModel((float) controller.getSettings().getFloat(setting.getName()), minValue, maxValue, 1));

        spinner.addChangeListener(e -> controller.getSettings().set(setting.getName(), spinner.getValue()));

        add(descriptor);
        add(Box.createHorizontalStrut(5));
        add(Box.createVerticalGlue());
        add(spinner);
    }

    @Override
    public void setValue(final Object value) {
        spinner.setValue(value);
    }
}
