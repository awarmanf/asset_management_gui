/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mining.program;

import com.mining.entity.HTHistory;
import com.mining.entity.TK;
import com.mining.resources.RLayout;
import com.mining.resources.RStrings;
import com.mining.service.HTHistoryService;
import com.mining.service.TKService;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author yudi
 */
public class AddHistoryDialog extends JDialog implements FocusListener {

    private JPanel pnlMain;
    private JPanel pnlTop;
    private JPanel pnlCenter;
    private JPanel pnlBottom;

    private JLabel jlblJudul;
    private JLabel jlblID;
    private JLabel jlblDate;
    private JLabel jlblNIK;
    private JLabel jlblName;
    private JLabel jlblPosition;
    private JLabel jlblDepartment;
    private JLabel jlblStatus;
    private JLabel jlblNote;

    private JTextField jtxtID;
    private JTextField jtxtDate;
    private JCheckBox jchkCurrent;
    private JFormattedTextField jftxtNIK;
    private JTextField jtxtName;
    private JTextField jtxtPosition;
    private JTextField jtxtDepartment;
    private JComboBox jcmbStatus;
    private JTextField jtxtNote;

    private JButton jbtnCancel;
    private JButton jbtnSubmit;
    private JButton jbtnReset;
    private Font font1;

    private GridBagLayout gblLayout; // layout of this frame
    private GridBagConstraints constraints; // constraints of this layout
    private String ID, Merek, Tipe, SN, jtxtDateSQL;

    private TKService serviceTK;
    private HTHistoryService serviceHT;
    private HTHistory htHistory;
    private List<TK> list;

    private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd");

    public AddHistoryDialog(java.awt.Frame parent, boolean modal, String ID, String Merek, String Tipe, String SN) {
        super(parent, modal);
        this.ID = ID;
        this.Merek = Merek;
        this.Tipe = Tipe;
        this.SN = SN;
        initComponents();
    }

    public void Add() {
        setLocationRelativeTo(getParent());
        setVisible(true);
    }

    public void initComponents() {

        font1 = new Font("Dialog", 0, RLayout.FS_DIALOG);
        pnlMain = new JPanel();
        pnlTop = new JPanel();
        pnlCenter = new JPanel();
        pnlBottom = new JPanel();

        jlblJudul = new JLabel();
        jlblID = new JLabel();
        jlblDate = new JLabel();
        jlblNIK = new JLabel();
        jlblName = new JLabel();
        jlblPosition = new JLabel();
        jlblDepartment = new JLabel();
        jlblStatus = new JLabel();
        jlblNote = new JLabel();

        jtxtID = new JTextField();
        jtxtID.setText(ID);
        jtxtID.setEditable(false);

        try {
            jftxtNIK = new JFormattedTextField(new MaskFormatter("########"));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        jtxtDate = new JTextField();
        jtxtDate.addFocusListener(this);
        jchkCurrent = new JCheckBox("Latest update");
        jchkCurrent.setSelected(true);
        jchkCurrent.setFont(font1);
        //jchkCurrent.setToolTipText(RStrings.txtCurrent);
        //jftxtNIK.setToolTipText(RStrings.txtTipNIK);
        jftxtNIK.addFocusListener(this);

        jtxtName = new JTextField();
        jtxtName.setEditable(false);

        jtxtPosition = new JTextField();
        jtxtPosition.setEditable(false);
        jtxtPosition.setPreferredSize(RLayout.TXT_PREFERRED_SIZE_2);

        jtxtDepartment = new JTextField();
        jtxtDepartment.setEditable(false);

        jcmbStatus = new JComboBox();
        jtxtNote = new JTextField();

        jbtnCancel = new JButton();
        jbtnSubmit = new JButton();
        jbtnReset = new JButton();

        jlblJudul.setFont(jlblJudul.getFont().deriveFont(jlblJudul.getFont().getStyle() | java.awt.Font.BOLD, 14));
        jlblJudul.setText("Add History of Radio " + Merek + " " + Tipe + " S/N " + SN);

        jlblID.setFont(font1);
        jlblID.setText(RStrings.LBL_ID_RADIO);

        jlblDate.setFont(font1);
        jlblDate.setText("Date (dd/mm/yyyy)");

        jlblNIK.setFont(font1);
        jlblNIK.setText(RStrings.LBL_NIK);

        jlblName.setFont(font1);
        jlblName.setText(RStrings.LBL_NAME);

        jlblPosition.setFont(font1);
        jlblPosition.setText(RStrings.LBL_POSITION);

        jlblDepartment.setFont(font1);
        jlblDepartment.setText(RStrings.LBL_DEPARTMENT);

        jlblStatus.setFont(font1);
        jlblStatus.setText(RStrings.LBL_STATUS);
        for (String s : RStrings.STATUS) {
            jcmbStatus.addItem(s);
        }
        jcmbStatus.setFont(font1);
        jcmbStatus.setPreferredSize(RLayout.CMB_PREFERRED_SIZE);

        jlblNote.setFont(font1);
        jlblNote.setText(RStrings.LBL_NOTE);

        jbtnCancel.setText(RStrings.BTN_CANCEL);
        jbtnCancel.setPreferredSize(RLayout.BTN_PREFERRED_SIZE);
        jbtnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCancelActionPerformed(evt);
            }
        });

        jbtnSubmit.setText(RStrings.BTN_SAVE);
        jbtnSubmit.setPreferredSize(RLayout.BTN_PREFERRED_SIZE);
        jbtnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSubmitActionPerformed(evt);
            }
        });
        jbtnSubmit.setEnabled(false);

        jbtnReset.setText(RStrings.BTN_RESET);
        jbtnReset.setPreferredSize(RLayout.BTN_PREFERRED_SIZE);
        jbtnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnResetActionPerformed(evt);
            }
        });

        pnlTop.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        pnlTop.add(jlblJudul);

        gblLayout = new GridBagLayout();
        pnlCenter.setLayout(gblLayout); // set frame gblLayout
        constraints = new GridBagConstraints(); // instantiate constraints
        // jchkCurrent

        addComponent(jlblID, 0, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST, 10, 0);
        addComponent(jtxtID, 0, 1, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 0, 0);

        addComponent(jlblDate, 1, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST, 10, 0);
        addComponent(jtxtDate, 1, 1, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 0, 0);
        addComponent(jchkCurrent, 1, 2, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST, 0, 0);

        addComponent(jlblNIK, 2, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST, 10, 0);
        addComponent(jftxtNIK, 2, 1, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 0, 0);

        addComponent(jlblName, 3, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST, 10, 0);
        addComponent(jtxtName, 3, 1, 2, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 0, 0);

        addComponent(jlblPosition, 4, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST, 10, 0);
        addComponent(jtxtPosition, 4, 1, 2, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 0, 0);

        addComponent(jlblDepartment, 5, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST, 10, 0);
        addComponent(jtxtDepartment, 5, 1, 2, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 0, 0);

        addComponent(jlblStatus, 6, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST, 10, 0);
        addComponent(jcmbStatus, 6, 1, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 0, 0);

        addComponent(jlblNote, 7, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST, 10, 0);
        addComponent(jtxtNote, 7, 1, 2, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 0, 0);

        pnlBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        pnlBottom.add(jbtnReset);
        pnlBottom.add(jbtnCancel);
        pnlBottom.add(jbtnSubmit);

        pnlMain.setLayout(new BorderLayout());
        pnlMain.add(pnlTop, BorderLayout.NORTH);
        pnlMain.add(pnlCenter, BorderLayout.CENTER);
        pnlMain.add(pnlBottom, BorderLayout.SOUTH);
        pnlMain.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20)); // (NORTH, WEST, SOUTH, EAST)

        //pnlMain.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20)); // (NORTH, WEST, SOUTH, EAST)
        this.add(pnlMain);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        //setPreferredSize(new Dimension(420, 250));
        this.pack();
        jtxtDate.requestFocusInWindow();
    }

    private void jbtnCancelActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    private void jbtnSubmitActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            htHistory = new HTHistory();
            serviceHT = new HTHistoryService();

            htHistory.setID(jtxtID.getText());
            htHistory.setDate(jtxtDateSQL);
            htHistory.setCurrent(jchkCurrent.isSelected());
            htHistory.setNIK(Integer.parseInt(jftxtNIK.getText()));
            htHistory.setStatus(jcmbStatus.getSelectedItem().toString());
            htHistory.setNote(jtxtNote.getText());
            serviceHT.ResetCurrentID(jtxtID.getText());
            serviceHT.insert(htHistory);

            JOptionPane.showMessageDialog(null, RStrings.DLG_ADD_HISTORY_OK);
            MainForm.dataChange = true;
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
        }

    }

    private void jbtnResetActionPerformed(java.awt.event.ActionEvent evt) {
        jtxtDate.setText("");
        jchkCurrent.setSelected(false);
        jftxtNIK.setText("");
        jtxtName.setText("");
        jtxtPosition.setText("");
        jtxtDepartment.setText("");
        jbtnSubmit.setEnabled(false);
        jtxtDate.requestFocusInWindow();
    }

    /**
     * Called when a field's "value" property changes. The method not used any
     * more. Case:
     * - Fill the NIK, Name, Position & Dept are filled automatically
     * - Do Reset then fill with the same NIK but Name, Position & Dept still empty !
     *
     * public void propertyChange(PropertyChangeEvent evt) { Object source =
     * evt.getSource(); if (source == jftxtNIK) { String NIK =
     * jftxtNIK.getText().trim(); System.out.println("NIK:" + NIK + ":"); if
     * (NIK != null && !NIK.isEmpty()) { service = new TKService(); try { list =
     * service.selectAll(jftxtNIK.getText().toString());
     *
     * if (list.size() > 0) { for (TK tk : list) {
     * jtxtName.setText(tk.getFullname());
     * jtxtPosition.setText(tk.getPosition());
     * jtxtDepartment.setText(tk.getDepartment()); }
     * jbtnSubmit.setEnabled(true); } else { jtxtName.setText("");
     * jtxtPosition.setText(""); jtxtDepartment.setText("");
     * jbtnSubmit.setEnabled(false); } } catch (Exception ex) {
     * System.out.println(ex.getMessage()); } } } }
     *
     */
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
        gblLayout.setConstraints(component, constraints);
        pnlCenter.add(component);
    }

    @Override
    public void focusGained(FocusEvent fe) {
    }

    @Override
    public void focusLost(FocusEvent fe) {
        if (fe.getSource() == jftxtNIK) {
            String NIK = jftxtNIK.getText().trim();
            if (NIK != null && !NIK.isEmpty()) {
                serviceTK = new TKService();
                try {
                    list = serviceTK.selectAll(jftxtNIK.getText().toString());

                    if (list.size() > 0) {
                        for (TK tk : list) {
                            jtxtName.setText(tk.getFullname());
                            jtxtPosition.setText(tk.getPosition());
                            jtxtDepartment.setText(tk.getDepartment());
                        }
                        jbtnSubmit.setEnabled(true);
                    } else {
                        jtxtName.setText("");
                        jtxtPosition.setText("");
                        jtxtDepartment.setText("");
                        jbtnSubmit.setEnabled(false);
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        if (fe.getSource() == jtxtDate) {
            String txtDate = jtxtDate.getText().trim();
            if (txtDate.isEmpty()) {
                jtxtDateSQL = dateFormatSQL.format(new Date());
                jtxtDate.setText(dateFormat.format(new Date()));
            } else {
                try {
                    jtxtDateSQL = dateFormatSQL.format(dateFormat.parse(jtxtDate.getText()));
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "ParseException Error", JOptionPane.ERROR_MESSAGE);
                    jtxtDate.setText("");
                    jtxtDate.requestFocusInWindow();
                }
            }
        }
    }
    
        public static void main(String[] args) {

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String ID = "D140";
                String Merek = "Icom";
                String Tipe = "V-80";
                String SN = "123567";

                AddHistoryDialog dialog = new AddHistoryDialog(new javax.swing.JFrame(), true, ID, Tipe, Merek, SN);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
}
