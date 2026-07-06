package org.example.ui.custom.inputs;

import org.example.model.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class NumberText extends JTextField {

    private final Space space;

    public NumberText(final Space space) {
        this.space = space;
        var dimension = new Dimension(50, 50);
        this.setPreferredSize(dimension);
        this.setSize(dimension);
        this.setVisible(true);
        this.setFont(new Font("Monospaced", Font.PLAIN, 20));
        this.setHorizontalAlignment(JTextField.CENTER);
        this.setDocument(new NumberTextLimit());
        this.setEnabled(space.isFixed());
        if (space.isFixed()){
            this.setText(space.getActual(Integer.parseInt(getText())).toString());
        }
        this.getDocument().addDocumentListener(new DocumentListener() {

            private void changeSpace(){
                if (getText().isEmpty()) {
                    space.clearSpace();
                    return;
                }
                space.getActual(Integer.parseInt(getText()));
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                changeSpace();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changeSpace();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changeSpace();
            }
        });
    }
}
