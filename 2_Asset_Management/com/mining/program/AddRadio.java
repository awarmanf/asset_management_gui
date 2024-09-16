/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mining.program;

import com.mining.entity.HTHistory;
import com.mining.entity.HTList;
import com.mining.resources.RLayout;
import com.mining.resources.RStrings;
import com.mining.service.HTHistoryService;
import com.mining.service.HTMerekService;
import com.mining.service.HTListService;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author yudi
 */
public class AddRadio extends JDialog {

    private JPanel pnlMain;
    private JPanel pnlTop;
    private JPanel pnlCenter;
    private JPanel pnlBottom;
    private JLabel jlblJudul;

    private JLabel jlblID;
    private JLabel jlblSN;
    private JLabel jlblSAP;
    private JLabel jlblMerek;
    private JLabel jlblTipe;
    private JLabel jlblFrekuensi;
    private JTextField jtxtSN;
    private JTextField jtxtSAP;
    private JTextField jtxtID;
    private JComboBox jcmbMerek;
    private JComboBox jcmbTipe;
    private JTextField jtxtFrekuensi;
    private JButton jbtnCancel;
    private JButton jbtnSave;
    private JButton jbtnReset;
    private Font font1;
    private HTListService serviceList;

    private GridBagLayout gblLayout; // layout of this frame
    private GridBagConstraints constraints; // constraints of this layout
    private HTList htList;
    private HTHistory htHistory;
    private String ID, SN, Merek, Tipe;
    private String SAP;
    private int radioCount;
    private final static char prefix = 'D';

    public AddRadio(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
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
        jlblSN = new JLabel();
        jlblSAP = new JLabel();
        jlblMerek = new JLabel();
        jlblTipe = new JLabel();
        jlblFrekuensi = new JLabel();

        jtxtID = new JTextField();
        jtxtSN = new JTextField();
        jtxtSAP = new JTextField();
        jcmbMerek = new JComboBox();
        jcmbTipe = new JComboBox();
        jtxtFrekuensi = new JTextField();

        jbtnCancel = new JButton();
        jbtnSave = new JButton();
        jbtnReset = new JButton();

        jlblJudul.setFont(jlblJudul.getFont().deriveFont(jlblJudul.getFont().getStyle() | java.awt.Font.BOLD, 18));
        jlblJudul.setText(RStrings.LBL_TITLE_ADD_RADIO);

        jlblID.setFont(font1);
        jlblID.setText(RStrings.LBL_ID_RADIO);
        serviceList = new HTListService();
        //radioCount = serviceList.countAll() + 1;
        //ID = prefix + Integer.toString(radioCount);
        ID = NewID();
        jtxtID.setText(ID);
        jtxtID.setEditable(false);
        jtxtID.setPreferredSize(RLayout.TXT_PREFERRED_SIZE);

        jlblSN.setFont(font1);
        jlblSN.setText(RStrings.LBL_SN);

        jlblSAP.setFont(font1);
        jlblSAP.setText(RStrings.LBL_SAP);

        jlblFrekuensi.setFont(font1);
        jlblFrekuensi.setText(RStrings.LBL_FREKUENSI);
        jtxtFrekuensi.setFont(font1);
        jtxtFrekuensi.setText("VHF");
        jtxtFrekuensi.setEditable(false);

        jlblMerek.setFont(font1);
        jlblMerek.setText(RStrings.LBL_MEREK);
        HTMerekService serviceMerek = new HTMerekService();
        List<String> listMerek = serviceMerek.getMereks();
        for (String s : listMerek) {
            jcmbMerek.addItem(s);
        }
        jcmbMerek.setFont(font1);
        jcmbMerek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcmbMerekActionPerformed(evt);
            }
        });

        addItemsTipe();
        jlblTipe.setFont(font1);
        jlblTipe.setText(RStrings.LBL_TIPE);
        jcmbTipe.setFont(font1);
        //jcmbTipe.setPreferredSize(RLayout.jcmbPreferredSize);

        jbtnCancel.setText(RStrings.BTN_CANCEL);
        jbtnCancel.setPreferredSize(RLayout.BTN_PREFERRED_SIZE);
        jbtnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCancelActionPerformed(evt);
            }
        });

        jbtnSave.setText(RStrings.BTN_SAVE);
        jbtnSave.setPreferredSize(RLayout.BTN_PREFERRED_SIZE);
        jbtnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSaveActionPerformed(evt);
            }
        });

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

        addComponent(jlblID, 0, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST, 10, 0);
        addComponent(jtxtID, 0, 1, 1, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 0, 0);

        addComponent(jlblSN, 1, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST, 10, 0);
        addComponent(jtxtSN, 1, 1, 1, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 0, 0);

        addComponent(jlblSAP, 2, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST, 10, 0);
        addComponent(jtxtSAP, 2, 1, 1, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 0, 0);

        addComponent(jlblMerek, 3, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST, 10, 0);
        addComponent(jcmbMerek, 3, 1, 1, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 0, 0);

        addComponent(jlblTipe, 4, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST, 10, 0);
        addComponent(jcmbTipe, 4, 1, 1, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 0, 0);

        addComponent(jlblFrekuensi, 5, 0, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST, 10, 0);
        addComponent(jtxtFrekuensi, 5, 1, 1, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 0, 0);

        pnlBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        pnlBottom.add(jbtnReset);
        //pnlBottom.add(jbtnCancel);
        pnlBottom.add(jbtnSave);

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
        jtxtSN.requestFocusInWindow();
    }

    private void jbtnCancelActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        MainForm.dataChange = false;
        dispose();
    }

    private void jbtnSaveActionPerformed(java.awt.event.ActionEvent evt) {

        if (jtxtSN.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, RStrings.DLG_INPUT_WARNING, null, JOptionPane.WARNING_MESSAGE);

        } else {
            SN = jtxtSN.getText().trim();
            SAP = jtxtSAP.getText().trim();

            Tipe = (String) jcmbTipe.getSelectedItem();
            HTMerekService serviceHTMerek = new HTMerekService();
            short idRadio = serviceHTMerek.getID(Merek, Tipe);

            HTListService serviceHTList = new HTListService();
            htList = new HTList();
            htList.setID(ID);
            htList.setIDRadio(idRadio);
            htList.setSN(SN);
            htList.setSAP(SAP);

            if (serviceHTList.insert(htList)) {
                HTHistoryService serviceHTHistory = new HTHistoryService();
                htHistory = new HTHistory();
                htHistory.setID(ID);
                htHistory.setCurrent(true);
                htHistory.setNIK(RStrings.NIK_IT);
                htHistory.setStatus("STORE");
                htHistory.setNote("");
                serviceHTHistory.insert(htHistory);

                JOptionPane.showMessageDialog(null, RStrings.DLG_ADD_RADIO_OK);
                
                ID = NewID();
                jtxtID.setText(ID);
            }
            jbtnResetActionPerformed(null);
        }
    }

    private void jbtnResetActionPerformed(java.awt.event.ActionEvent evt) {
        jtxtSN.setText("");
        jtxtSAP.setText("");
        jtxtSN.requestFocusInWindow();
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
        gblLayout.setConstraints(component, constraints);
        //add(component);
        pnlCenter.add(component);
    }

    private void addItemsTipe() {
        Merek = (String) jcmbMerek.getSelectedItem();
        jcmbTipe.removeAllItems();

        HTMerekService serviceTipe = new HTMerekService();
        List<String> listTipe = serviceTipe.getTipe(Merek);
        for (String s : listTipe) {
            jcmbTipe.addItem(s);
        }
    }

    private void jcmbMerekActionPerformed(java.awt.event.ActionEvent evt) {
        addItemsTipe();
    }

    public String NewID() {
        radioCount = serviceList.countAll() + 1;
        ID = prefix + Integer.toString(radioCount);
        return ID;
    }

    public static void main(String[] args) {

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AddRadio dialog = new AddRadio(new javax.swing.JFrame(), true);
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
