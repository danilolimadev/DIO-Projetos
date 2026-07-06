package org.example.ui.custom.panel;

import org.example.ui.custom.inputs.NumberText;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class SudokuSector extends JPanel  {
    public SudokuSector(final List<NumberText> textFields) {
        var dimension = new Dimension(170, 170);
        this.setPreferredSize(dimension);
        this.setSize(dimension);
        this.setBorder(new LineBorder(Color.BLACK, 2, true));
        //this.setLayout(new GridLayout(3, 3 ));
        this.setVisible(true);
        textFields.forEach(this::add);
        }
}
