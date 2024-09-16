/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mining.program;

import com.mining.entity.TK;
import com.mining.resources.RStrings;
import com.mining.service.TKService;
import com.yudi.table.util.SimpleHeaderRenderer;
import com.yudi.table.util.TableColumnAdjuster;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;
import org.json.JSONObject;

/**
 *
 * @author yudi
 */
public class MainForm extends JFrame {

    private JScrollPane jspPane;
    private JPanel jpnlPane;
    private JPanel jpnlSearch;
    private JPanel jpnlLeft;
    private JPanel jpnlRight;
    private JTable jtblTK;
    private JLabel jlblSpace;
    private JLabel jlblPadx1;
    private JLabel jlblPadx2;
    private JLabel jlblPadx3;
    private JLabel jlblPadx4;
    private JLabel jlblPadx5;
    private JLabel jlblPady1;
    private JTextField jtxtSearch;
    private JButton jbtnSearch;
    private JButton jbtnReset;
    private JButton jbtnAdd;
    private JButton jbtnChange;
    private JLabel jlblJudul;

    private GridBagLayout layout;
    private GridBagConstraints constraints;
    private GridBagConstraints gbc;
    private DefaultTableModel tableModel;
    private TKService service;
    private DefaultTableModel model;
    private JSONObject jo;
    private final static String json = "{ "
            + "\"var\": \"All\","
            + "\"value\": value_not_important_here"
            + "}";

    public static Boolean dataChange = false;

    public MainForm() {
        initComponents();

        Header();
        ReloadData(json);
        adjustColumns();
    }

    public void Header() {
        Object[] judul = service.getHeader();
        tableModel = new DefaultTableModel(null, judul) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jtblTK.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer(Color.DARK_GRAY, 12));
        jtblTK.setModel(tableModel);
    }

    public void ReloadData(String json) {

        jo = new JSONObject(json);
        List<TK> list = service.selectAll(jo);

        if (list.size() > 0) {
            tableModel.getDataVector().removeAllElements();
            tableModel.fireTableDataChanged();

            for (TK tk : list) {
                //tableModel.addRow(tk);
                Object[] data = {tk.getNIK(), tk.getFullname(), tk.getPosition(), tk.getDepartment()};
                tableModel.addRow(data);
            }
            jtblTK.setAutoCreateRowSorter(true);
        } else {
            JOptionPane.showMessageDialog(this, "Data tidak ditemukan.");
        }
    }

    public void initComponents() {

        jlblJudul = new JLabel();
        jlblJudul.setFont(jlblJudul.getFont().deriveFont(jlblJudul.getFont().getStyle() | java.awt.Font.BOLD, 18));
        jlblJudul.setText(RStrings.lblJudulMain);

        jpnlPane = new JPanel();
        jpnlPane.setLayout(new BorderLayout());

        jpnlLeft = new JPanel();
        jpnlLeft.setLayout(new FlowLayout());

        jtxtSearch = new JTextField();
        jtxtSearch.setColumns(10);
        jlblPadx3 = new JLabel("");
        jlblPadx4 = new JLabel("");
        jbtnSearch = new JButton(RStrings.btnSearch);
        jbtnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSearchActionPerformed(evt);
            }
        });
        jbtnReset = new JButton(RStrings.btnReset);
        jbtnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnResetActionPerformed(evt);
            }
        });
        jpnlSearch = new JPanel();
        jpnlSearch.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 10;
        gbc.gridwidth = 3;
        jpnlSearch.add(jtxtSearch, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipady = 5;
        gbc.gridwidth = 3;
        jpnlSearch.add(jlblPadx3, gbc); //jlblPadx3
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.ipadx = 10;
        gbc.gridwidth = 1;
        jpnlSearch.add(jbtnSearch, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.ipadx = 10;
        gbc.gridwidth = 1;
        jpnlSearch.add(jlblPadx4, gbc); //jlblPadx4
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.ipadx = 10;
        gbc.gridwidth = 1;
        jpnlSearch.add(jbtnReset, gbc);

        jpnlLeft.add(jpnlSearch);
        jpnlLeft.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        Border border = BorderFactory.createTitledBorder(RStrings.titleMainForm);
        jpnlLeft.setBorder(border);

        jlblPady1 = new JLabel(" ");
        jlblPadx5 = new JLabel(" ");
        jbtnAdd = new JButton(RStrings.btnAdd);
        jbtnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAddActionPerformed(evt);
            }
        });
        jbtnChange = new JButton(RStrings.btnChange);
        jbtnChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnChangeActionPerformed(evt);
            }
        });

        jpnlRight = new JPanel();
        jpnlRight.setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 1;
        gbc.gridwidth = 1;
        jpnlRight.add(jbtnAdd, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.ipadx = 5;
        //gbc.gridheight = 1;
        jpnlRight.add(jlblPady1, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipadx = 1;
        gbc.ipady = 1;
        gbc.gridwidth = 1;
        jpnlRight.add(jlblPadx5, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.ipadx = 1;
        gbc.gridwidth = 1;
        jpnlRight.add(jbtnChange, gbc);

        jlblSpace = new JLabel("");
        jpnlPane.add(jpnlLeft, BorderLayout.WEST);
        jpnlPane.add(jlblSpace, BorderLayout.CENTER);
        jpnlPane.add(jpnlRight, BorderLayout.EAST);

        jspPane = new JScrollPane();
        jtblTK = new JTable();

        jlblPadx1 = new JLabel("");
        jlblPadx2 = new JLabel("");

        String[] columns = new String[]{"Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"};
        Object[][] data = new Object[][]{
            {null, null, null, null, null, null},
            {null, null, null, null, null, null},
            {null, null, null, null, null, null},
            {null, null, null, null, null, null},};

        model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        jtblTK = new JTable(model);
        jspPane.setViewportView(jtblTK);

        layout = new GridBagLayout();
        setLayout(layout); // set frame layout
        constraints = new GridBagConstraints(); // instantiate constraints

        addComponent(jlblJudul, 0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.CENTER, 0, 10);

        addComponent(jspPane, 1, 0, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.WEST, 0, 10);

        addComponent(jlblPadx1, 2, 0, 1, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.WEST, 0, 10);

        addComponent(jpnlPane, 3, 0, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST, 0, 0);

        addComponent(jlblPadx2, 4, 0, 1, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.WEST, 0, 10);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 500));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        

        service = new TKService();
    }

    // method to set constraints on 
    private void addComponent(Component component,
            int row, int column, int width, int height,
            int weightx, int weighty, int constraint, int anchor,
            int ipadx, int ipady) {
        constraints.gridx = column; // set gridx
        constraints.gridy = row;    // set gridy
        constraints.gridwidth = width;   // set gridwidth
        constraints.gridheight = height; // set gridheight
        constraints.weightx = weightx;   // can grow wider
        constraints.weighty = weighty;   // can grow taller
        constraints.fill = constraint;
        constraints.anchor = anchor;
        constraints.ipadx = ipadx;
        constraints.ipady = ipady;
        layout.setConstraints(component, constraints); // set constraints
        add(component); // add component
    } // end method addComponent

    private void jbtnAddActionPerformed(java.awt.event.ActionEvent evt) {

        AddTK addTK = new AddTK(this, false);
        addTK.Add();

        ReloadData(json);
        adjustColumns();

    }

    private void jbtnChangeActionPerformed(java.awt.event.ActionEvent evt) {

        if (jtblTK.getSelectedRow() >= 0) {
            int nik = (Integer) jtblTK.getValueAt(jtblTK.getSelectedRow(), 0);

            ChangeTK changeTK = new ChangeTK(this, false);
            changeTK.Change(nik);

            // if canceling change then don't reload
            if (MainForm.dataChange) {
                ReloadData(json);
            }
        } else {
            JOptionPane.showMessageDialog(this, RStrings.dlgSelectPlease);
        }
        adjustColumns();
    }

    private void jbtnSearchActionPerformed(java.awt.event.ActionEvent evt) {

        int nik = 0;
        String name = "";
        String json1;

        // check if entry is integer
        try {
            nik = Integer.parseInt(jtxtSearch.getText());
        } catch (Exception e) {
            name = jtxtSearch.getText();
            if (name.equals("")) {
                name = "%";
            }
        } finally {
            if (nik > 0) {
                json1 = "{ "
                        + "\"var\": \"NIK\","
                        + "\"value\": " + nik
                        + "}";
            } else {
                json1 = "{ "
                        + "\"var\": \"Name\","
                        + "\"value\": " + name
                        + "}";
            }
        }

        ReloadData(json1);
        adjustColumns();
    }

    private void jbtnResetActionPerformed(java.awt.event.ActionEvent evt) {
        jtxtSearch.setText("");
        ReloadData(json);
        adjustColumns();
    }

    public void adjustColumns() {
        //jtblTK.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnAdjuster tca = new TableColumnAdjuster(jtblTK, 15);
        tca.adjustColumns();
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }
}
