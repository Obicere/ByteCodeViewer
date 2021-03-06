package org.obicere.bytecode.viewer.gui.swing.settings;

import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.settings.SettingsController;
import org.obicere.bytecode.viewer.settings.target.NumberSetting;
import org.obicere.bytecode.viewer.settings.target.Setting;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 */
public class IntegerSettingPanel extends SettingPanel<Integer> {

    private final JSpinner spinner;

    public IntegerSettingPanel(final Domain domain, final Setting<Integer> setting) {
        super(setting);

        final SettingsController controller = domain.getSettingsController();

        final int minValue;
        final int maxValue;

        if (setting instanceof NumberSetting) {
            final NumberSetting<Integer> intSetting = (NumberSetting<Integer>) setting;
            minValue = intSetting.getMinValue();
            maxValue = intSetting.getMaxValue();
        } else {
            minValue = Integer.MIN_VALUE;
            maxValue = Integer.MAX_VALUE;
        }

        final JLabel descriptor = new JLabel(setting.getDescriptor());
        final int defaultValue = Math.max(controller.getSettings().getInteger(setting.getName(), 0), minValue);

        this.spinner = new JSpinner(new SpinnerNumberModel(defaultValue, minValue, maxValue, 1));

        spinner.addChangeListener(e -> controller.getSettings().set(setting.getName(), spinner.getValue()));

        final BoxLayout layout = new BoxLayout(this, BoxLayout.LINE_AXIS);
        setLayout(layout);

        add(descriptor);
        add(Box.createHorizontalStrut(5));
        add(Box.createHorizontalGlue());
        add(spinner);
    }

    @Override
    public void setValue(final Object value) {
        spinner.setValue(value);
    }
}
