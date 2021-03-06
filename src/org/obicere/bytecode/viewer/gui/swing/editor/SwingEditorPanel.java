package org.obicere.bytecode.viewer.gui.swing.editor;

import org.obicere.bytecode.core.io.LeafSource;
import org.obicere.bytecode.core.objects.DefaultJCClass;
import org.obicere.bytecode.viewer.concurrent.Callback;
import org.obicere.bytecode.viewer.concurrent.ClassLoaderService;
import org.obicere.bytecode.viewer.concurrent.ClassModelerService;
import org.obicere.bytecode.viewer.concurrent.RequestCallback;
import org.obicere.bytecode.viewer.context.ClassInformation;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.context.DomainAccess;
import org.obicere.bytecode.viewer.dom.Block;
import org.obicere.bytecode.viewer.dom.Document;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.dom.awt.QuickWidthFont;
import org.obicere.bytecode.viewer.dom.gui.swing.JDocumentArea;
import org.obicere.bytecode.viewer.gui.EditorPanel;
import org.obicere.bytecode.viewer.gui.EditorPanelManager;
import org.obicere.bytecode.viewer.gui.FrameManager;
import org.obicere.bytecode.viewer.gui.GUIManager;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Obicere
 */
public class SwingEditorPanel extends JPanel implements EditorPanel, DomainAccess {

    private final Domain domain;

    private final JDocumentArea documentArea;

    private final JPanel searchPanel;

    private final DocumentBuilder builder;

    private volatile ClassInformation classInformation;

    private volatile DefaultJCClass loadedClass;

    private boolean searchVisible;

    public SwingEditorPanel(final Domain domain) {
        super(new BorderLayout(10, 10));
        this.domain = domain;
        this.builder = new DocumentBuilder(domain);

        this.documentArea = new JDocumentArea(domain);
        this.searchPanel = new SearchPanel(documentArea);

        final JPanel displayArea = new JPanel(new BorderLayout(0, 0));
        final JScrollPane editorScroll = new JScrollPane(documentArea);

        displayArea.add(editorScroll, BorderLayout.CENTER);
        displayArea.add(searchPanel, BorderLayout.SOUTH);
        setSearchVisible(false);

        add(displayArea);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(830, 620);
    }

    @Override
    public DefaultJCClass getClassFile() {
        return loadedClass;
    }

    @Override
    public void setClassInformation(final ClassInformation classInformation) {
        this.classInformation = classInformation;
        setClassFile(classInformation.getClassFile());
    }

    @Override
    public ClassInformation getClassInformation() {
        return classInformation;
    }

    private void setClassFile(final DefaultJCClass classFile) {
        if (classFile == null) {
            return;
        }
        this.loadedClass = classFile;
    }

    @Override
    public void setBlocks(final List<Block> blocks) {
        if (blocks == null) {
            throw new NullPointerException("blocks must be non-null");
        }
        documentArea.setDocument(new Document(blocks));
        repaint();
    }

    @Override
    public DocumentBuilder getBuilder() {
        return builder;
    }

    @Override
    public void setFont(final QuickWidthFont font) {
        documentArea.setFont(font);
    }

    @Override
    public void reload() {
        final ClassModelerService service = domain.getClassModelerService();

        final RequestCallback callback = new RequestCallback();

        service.postRequest(callback, builder, classInformation);
    }

    @Override
    public void hardReload() {
        final GUIManager guiManager = domain.getGUIManager();
        final FrameManager frameManager = guiManager.getFrameManager();
        final EditorPanelManager editorManager = frameManager.getEditorManager();

        final String className = classInformation.getClassFile().getName();

        final ClassLoaderService service = domain.getClassLoaderService();

        final LeafSource fileSource = classInformation.getSource();

        final Callback callback = new Callback() {
            @Override
            public void notifyCompletion() {
                Logger.getGlobal().log(Level.INFO, "Done hard reload " + className);
                editorManager.displayEditorPanel(className);
            }

            @Override
            public void notifyThrowable(final Throwable throwable) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, "Failed to reload class: " + className, "Error loading class", JOptionPane.ERROR_MESSAGE);
                });
                Logger.getGlobal().log(Level.SEVERE, throwable.getMessage(), throwable);
            }
        };
        Logger.getGlobal().log(Level.INFO, "Submitted hard reload request" + className);

        editorManager.removeEditorPanel(className);
        service.postRequest(callback, fileSource);
    }

    @Override
    public void close() {
        final FrameManager manager = domain.getGUIManager().getFrameManager();
        manager.getEditorManager().closeEditorPanel(getName());
    }

    public void setSearchVisible(final boolean searchVisible) {
        this.searchVisible = searchVisible;
        searchPanel.setVisible(searchVisible);

        if (searchVisible) {
            searchPanel.requestFocus();
        }

        revalidate();
        repaint();
    }

    public boolean isSearchVisible() {
        return searchVisible;
    }

    @Override
    public Domain getDomain() {
        return domain;
    }
}
