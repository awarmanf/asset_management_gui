/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yudi.table.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * A simple renderer class for JTable component.
 *
 * @author www.codejava.net
 *
 */
public class SimpleHeaderRenderer extends JLabel implements TableCellRenderer {

    public SimpleHeaderRenderer(Color color,int size) {
        setFont(new Font("Consolas", Font.BOLD, size));
        setForeground(color);
        setBorder(BorderFactory.createEtchedBorder());
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value.toString());
        return this;
    }

}
