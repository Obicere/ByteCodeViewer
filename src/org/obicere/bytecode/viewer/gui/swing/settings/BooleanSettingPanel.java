package org.obicere.bytecode.viewer.gui.swing.settings;

import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.settings.SettingsController;
import org.obicere.bytecode.viewer.settings.target.Setting;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

/**
 */
public class BooleanSettingPanel extends SettingPanel<Boolean> {

    private final JCheckBox checkBox;

    public BooleanSettingPanel(final Domain domain, final Setting<Boolean> setting) {
        super(setting);
        final SettingsController settings = domain.getSettingsController();

        final JLabel descriptor = new JLabel(setting.getDescriptor());
        this.checkBox = new JCheckBox("", settings.getSettings().getBoolean(setting.getName()));

        checkBox.addChangeListener(e -> settings.getSettings().set(setting.getName(), checkBox.isSelected()));

        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        add(descriptor);
        add(Box.createHorizontalStrut(5));
        add(Box.createHorizontalGlue());
        add(checkBox);
    }

    @Override
    public void setValue(final Object value) {
        checkBox.setSelected((boolean) value);
    }
}
