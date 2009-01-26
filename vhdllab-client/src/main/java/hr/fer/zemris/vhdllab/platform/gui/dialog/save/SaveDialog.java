package hr.fer.zemris.vhdllab.platform.gui.dialog.save;

import hr.fer.zemris.vhdllab.entities.Caseless;
import hr.fer.zemris.vhdllab.platform.gui.dialog.AbstractDialog;
import hr.fer.zemris.vhdllab.platform.i18n.LocalizationSource;
import hr.fer.zemris.vhdllab.platform.manager.workspace.model.FileIdentifier;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SaveDialog extends AbstractDialog<List<FileIdentifier>> {

    private static final long serialVersionUID = 1L;

    /** Size of a border */
    private static final int BORDER_SIZE = 10;
    /** Width of this dialog */
    private static final int DIALOG_WIDTH = 350;
    /** Height of this dialog */
    private static final int DIALOG_HEIGHT = 450;
    /** Height of a label */
    private static final int LABEL_HEIGHT = 50;
    /** Width of all buttons */
    private static final int BUTTON_WIDTH = 100;
    /** Height of all buttons */
    private static final int BUTTON_HEIGHT = 24;

    CheckBoxList list;

    public SaveDialog(LocalizationSource source) {
        super(source);
        // setup label
        JLabel label = new JLabel();
        int width = DIALOG_WIDTH - 2 * BORDER_SIZE;
        int height = LABEL_HEIGHT - 2 * BORDER_SIZE;
        label.setPreferredSize(new Dimension(width, height));
        label.setBorder(BorderFactory.createEmptyBorder(BORDER_SIZE,
                BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));

        // setup check box list
        list = new CheckBoxList();
        width = DIALOG_WIDTH - 2 * BORDER_SIZE;
        height = 0; // because list is a center component and it doesnt need
        // height
        list.setPreferredSize(new Dimension(width, height));
        list.setBorder(BorderFactory.createEmptyBorder(BORDER_SIZE,
                BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));

        // setup select all and deselect all buttons
        JButton selectAll = new JButton("Select All");
        selectAll.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        selectAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                list.setSelectionToAll(true);
            }
        });

        JButton deselectAll = new JButton("Deselect All");
        deselectAll
                .setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        deselectAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                list.setSelectionToAll(false);
            }
        });

        Box selectBox = Box.createHorizontalBox();
        selectBox.add(selectAll);
        selectBox.add(Box.createRigidArea(new Dimension(BORDER_SIZE,
                BUTTON_HEIGHT)));
        selectBox.add(deselectAll);
        selectBox.setBorder(BorderFactory.createEmptyBorder(0, 0, BORDER_SIZE,
                0));

        // setup ok and cancel buttons
        JButton ok = new JButton("OK");
        ok.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeDialog(new ArrayList<FileIdentifier>());
            }
        });

        JButton cancel = new JButton("Cancel");
        cancel.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeDialog(null);
            }
        });

        Box actionBox = Box.createHorizontalBox();
        actionBox.add(ok);
        actionBox.add(Box.createRigidArea(new Dimension(BORDER_SIZE,
                BUTTON_HEIGHT)));
        actionBox.add(cancel);
        actionBox.setBorder(BorderFactory.createEmptyBorder(BORDER_SIZE, 0,
                BORDER_SIZE, 0));

        JPanel selectPanel = new JPanel(new BorderLayout());
        selectPanel.add(selectBox, BorderLayout.EAST);
        JPanel actionPanel = new JPanel(new BorderLayout());
        actionPanel.add(actionBox, BorderLayout.EAST);

        JCheckBox alwaysSave = new JCheckBox("Always save resources");
        alwaysSave.setSelected(false);
        JPanel alwaysSavePanel = new JPanel(new BorderLayout());
        alwaysSavePanel.add(alwaysSave, BorderLayout.WEST);

        JPanel lowerPanel = new JPanel(new BorderLayout());
        lowerPanel.add(selectPanel, BorderLayout.NORTH);
        lowerPanel.add(alwaysSavePanel, BorderLayout.CENTER);
        lowerPanel.add(actionPanel, BorderLayout.SOUTH);

        this.setLayout(new BorderLayout());
        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.add(label, BorderLayout.NORTH);
        messagePanel.add(list, BorderLayout.CENTER);
        messagePanel.add(lowerPanel, BorderLayout.SOUTH);
        this.getContentPane().add(messagePanel, BorderLayout.CENTER);
        this.getRootPane().setDefaultButton(ok);
        this.setPreferredSize(new Dimension(DIALOG_WIDTH, DIALOG_HEIGHT));
        label.setText("Select Resources to save:");
        if (getTitle() == null) {
            setTitle("Save Resources");
        }
    }

    @Override
    public void closeDialog(List<FileIdentifier> resourcesToSave) {
        if (resourcesToSave == null) {
            super.closeDialog(null);
        } else {
            for (SaveItem item : list.getItems()) {
                if (item.isSelected()) {
                    resourcesToSave.add(new FileIdentifier(item
                            .getProjectName(), item.getFileName()));
                }
            }
            super.closeDialog(resourcesToSave);
        }
    }

    public void setSaveFiles(List<FileIdentifier> identifiers) {
        for (FileIdentifier i : identifiers) {
            list
                    .addItem(new SaveItem(true, i.getProjectName(), i
                            .getFileName()));
        }
    }

    /**
     * Represents an item that should be displayed in SaveDialog.
     * 
     * @author Miro Bezjak
     * @see SaveDialog
     */
    private class SaveItem {

        /** Name of a project displayed next to file name in checkbox */
        private Caseless projectName;
        /** Name of a file displayed in checkbox */
        private Caseless fileName;
        /** Indicating if checkbox is selected */
        private boolean selected;

        /**
         * Constructor.
         * 
         * @param selected
         *            whether checkbox should be selected or not
         * @param projectName
         *            a name of a project
         * @param fileName
         *            a name of a file
         */
        public SaveItem(boolean selected, Caseless projectName,
                Caseless fileName) {
            this.selected = selected;
            this.projectName = projectName;
            this.fileName = fileName;
        }

        /**
         * Getter for selected;
         * 
         * @return value of selected
         */
        public boolean isSelected() {
            return selected;
        }

        /**
         * Setter for selected.
         * 
         * @param selected
         *            value to be set
         */
        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        /**
         * Getter for file name.
         * 
         * @return file name
         */
        public Caseless getFileName() {
            return fileName;
        }

        /**
         * Getter for project name.
         * 
         * @return project name
         */
        public Caseless getProjectName() {
            return projectName;
        }

        /**
         * Creates text out of project name and file name.
         * 
         * @return created text
         */
        public String getText() {
            return fileName + " [" + projectName + "]";
        }

    }

    /**
     * Class that displays a list of checkboxes.
     * 
     * @author Miro Bezjak
     * @see SaveDialog
     */
    private class CheckBoxList extends JPanel {

        /**
         * Serial Version UID.
         */
        private static final long serialVersionUID = 4499815884621898659L;
        /** Items that are displayed to user. */
        private Map<JCheckBox, SaveItem> items;
        /** Container of checkboxes. */
        private Box box;

        /**
         * Constructor.
         */
        public CheckBoxList() {
            items = new LinkedHashMap<JCheckBox, SaveItem>();
            box = Box.createVerticalBox();
            JScrollPane scroll = new JScrollPane(box);
            scroll.getViewport().setBackground(Color.WHITE);
            this.setLayout(new BorderLayout());
            this.add(scroll, BorderLayout.CENTER);
        }

        /**
         * Add item to be displayed.
         * 
         * @param item
         *            item to display
         */
        public void addItem(SaveItem item) {
            JCheckBox checkBox = new JCheckBox(item.getText(), item
                    .isSelected());
            checkBox.setBackground(Color.WHITE);
            box.add(checkBox);
            items.put(checkBox, item);
        }

        /**
         * Returns <code>true</code> if there are no displayed items.
         * 
         * @return <code>true</code> if there are no displayed items;
         *         <code>false</code> otherwise.
         */
        public boolean isEmpty() {
            return items.isEmpty();
        }

        /**
         * Returns all displayed items.
         * 
         * @return all displayed items
         */
        public List<SaveItem> getItems() {
            List<SaveItem> allItems = new ArrayList<SaveItem>();
            for (Entry<JCheckBox, SaveItem> entry : items.entrySet()) {
                JCheckBox checkbox = entry.getKey();
                SaveItem item = entry.getValue();
                item.setSelected(checkbox.isSelected());
                allItems.add(item);
            }
            return allItems;
        }

        /**
         * Sets a selection to all checkboxes to a value of
         * <code>selected</code>.
         * 
         * @param selected
         *            <code>true</code> if checkboxes should be selected;
         *            <code>false</code> otherwise
         */
        public void setSelectionToAll(boolean selected) {
            for (JCheckBox c : items.keySet()) {
                c.setSelected(selected);
            }
        }

    }
}