/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mining.program;

import com.mining.entity.TK;
import com.mining.resources.RLayout;
import com.mining.resources.RStrings;
import com.mining.service.DepartmentService;
import com.mining.service.PositionService;
import com.mining.service.TKService;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author yudi
 */
public class ChangeTK extends JDialog {

    private JLabel jlblJudul;
    private JLabel jlblNIK;
    private JLabel jlblFullname;
    private JLabel jlblPosition;
    private JLabel jlblDepartment;
    private JLabel jlblStatus;
    private JLabel jlblStaff;
    private JTextField jtxtNIK;
    private JTextField jtxtFullname;
    private JComboBox jcmbStatus;
    private JTextField jtxtStatus;
    private JTextField jtxtStaff;
    private JTextField jtxtDepartment;
    private JComboBox jcmbPosition;
    private JLabel jlblPadx1, jlblPadx2;
    private JLabel jlblPady1, jlblPady2;
    private JButton jbtnBatal;
    private JButton jbtnSimpan;
    private Font font1;

    private GridBagLayout layout; // layout of this frame
    private GridBagConstraints constraints; // constraints of this layout
    private TK tk;

    public ChangeTK(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public void Change(int nik) {

        TKService service = new TKService();
        TK tk = new TK();

        tk = service.selectByNIK(nik);

        if (tk != null) {

            jtxtNIK.setText(String.valueOf(tk.getNIK()));
            jtxtNIK.setEditable(false);

            jtxtFullname.setText(tk.getFullname());

            PositionService serviceposition = new PositionService();
            List<String> listPosition = serviceposition.getPositionDept(tk.getDepartment(), tk.isStaff());
            for (String s : listPosition) {
                jcmbPosition.addItem(s);
            }
            jcmbPosition.setFont(font1);
            jcmbPosition.setSelectedItem(tk.getPosition());
            jcmbPosition.setEnabled(true);

            jtxtDepartment.setText(tk.getDepartment());
            jtxtDepartment.setFont(font1);
            jtxtDepartment.setEditable(false);

            for (String s : RStrings.Status) {
                jcmbStatus.addItem(s);
            }
            jcmbStatus.setFont(font1);
            jcmbStatus.setSelectedItem(tk.getStatus());

            if (tk.isStaff()) {
                jtxtStaff.setText(RStrings.Staff[0]);
            } else {
                jtxtStaff.setText(RStrings.Staff[1]);
            }
            jtxtStaff.setFont(font1);
            jtxtStaff.setEditable(false);

            setLocationRelativeTo(getParent());
            setVisible(true);

        } else {
            JOptionPane.showMessageDialog(this, RStrings.dlgSearchTK, null, JOptionPane.WARNING_MESSAGE);
        }

    }

    public void initComponents() {

        font1 = new Font("Dialog", 0, RLayout.fsDialog);
        jlblJudul = new JLabel();

        jlblNIK = new JLabel();
        jlblFullname = new JLabel();
        jlblPosition = new JLabel();
        jlblDepartment = new JLabel();
        jlblStatus = new JLabel();
        jlblStaff = new JLabel();

        jtxtNIK = new JTextField();
        jtxtNIK.setColumns(40);
        jtxtFullname = new JTextField();
        jcmbPosition = new JComboBox();
        jtxtDepartment = new JTextField();
        jcmbStatus = new JComboBox();
        jtxtStatus = new JTextField();
        jtxtStaff = new JTextField();

        jlblPadx1 = new JLabel();
        jlblPadx2 = new JLabel();
        jlblPady1 = new JLabel();
        jlblPady2 = new JLabel();

        jbtnBatal = new JButton();
        jbtnSimpan = new JButton();

        jlblJudul.setFont(jlblJudul.getFont().deriveFont(jlblJudul.getFont().getStyle() | java.awt.Font.BOLD, 18));
        jlblJudul.setText(RStrings.lblJudulChange);
        jlblNIK.setFont(font1);
        jlblNIK.setText(RStrings.lblNIK);
        jlblFullname.setFont(font1);
        jlblFullname.setText(RStrings.lblFullname);
        jlblPosition.setFont(font1);
        jlblPosition.setText(RStrings.lblPosition);
        jlblDepartment.setFont(font1);
        jlblDepartment.setText(RStrings.lblDepartment);
        jlblStatus.setFont(font1);
        jlblStatus.setText(RStrings.lblStatus);
        jlblStaff.setFont(font1);
        jlblStaff.setText(RStrings.lblStaff);

        jlblPadx1.setText("");
        jlblPadx2.setText("");

        jlblPady1.setText("");
        jlblPady2.setText("");

        jbtnBatal.setText(RStrings.btnCancel);
        jbtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnBatalActionPerformed(evt);
            }
        });

        jbtnSimpan.setText(RStrings.btnSave);
        jbtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSimpanActionPerformed(evt);
            }
        });

        layout = new GridBagLayout();
        setLayout(layout); // set frame layout
        constraints = new GridBagConstraints(); // instantiate constraints

        addComponent(jlblPadx1, 0, 0, 1, 10, 0, 0, GridBagConstraints.NONE, GridBagConstraints.CENTER, 10, 0);

        addComponent(jlblJudul, 0, 1, 3, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 20);

        addComponent(jlblNIK, 1, 1, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST, 10, 10);
        addComponent(jtxtNIK, 1, 2, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 0, 0);

        addComponent(jlblFullname, 2, 1, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST, 10, 10);
        addComponent(jtxtFullname, 2, 2, 2, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 0, 0);

        addComponent(jlblPosition, 3, 1, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST, 10, 10);
        addComponent(jcmbPosition, 3, 2, 2, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 0, 0);

        addComponent(jlblDepartment, 4, 1, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST, 10, 10);
        addComponent(jtxtDepartment, 4, 2, 2, 1, 1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 0, 0);

        addComponent(jlblStatus, 5, 1, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST, 10, 10);
        addComponent(jcmbStatus, 5, 2, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 0, 0);

        addComponent(jlblStaff, 6, 1, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST, 10, 10);
        addComponent(jtxtStaff, 6, 2, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 0, 0);

        addComponent(jlblPady1, 7, 0, 5, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 10);

        addComponent(jbtnBatal, 8, 2, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.WEST, 10, 0);
        addComponent(jbtnSimpan, 8, 3, 1, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.EAST, 10, 0);

        addComponent(jlblPady2, 9, 0, 5, 1, 0, 0, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 15);

        addComponent(jlblPadx2, 0, 4, 1, 10, 0, 0, GridBagConstraints.NONE, GridBagConstraints.CENTER, 10, 0);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setPreferredSize(new Dimension(420, 250));
        pack();

    }

    private void jbtnBatalActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        MainForm.dataChange = false;
        dispose();
    }

    private void jbtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {

        if (jtxtFullname.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, RStrings.dlgFullnameWarning, null, JOptionPane.WARNING_MESSAGE);
        } else {

            try {
                int nik = Integer.parseInt(jtxtNIK.getText());

                try {
                    String position = (String) jcmbPosition.getSelectedItem();
                    PositionService service1 = new PositionService();
                    short idPosition = service1.getPositionID(position);

                    String department = (String) jtxtDepartment.getText();
                    DepartmentService service2 = new DepartmentService();
                    short idDepartment = service2.getDepartmentID(department);

                    TKService service = new TKService();
                    TK tk = new TK();

                    tk.setNIK(Integer.parseInt(jtxtNIK.getText()));
                    tk.setFullname(jtxtFullname.getText());
                    tk.setIDPosition(idPosition);
                    tk.setIDDepartment(idDepartment);
                    tk.setStatus(jcmbStatus.getSelectedItem().toString());
                    service.update(tk);

                    MainForm.dataChange = true;
                    dispose();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), null, JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, RStrings.dlgInputError, null, JOptionPane.ERROR_MESSAGE);
            }
        }

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

    public static void main(String[] args) {

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ChangeTK dialog = new ChangeTK(new javax.swing.JFrame(), true);
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
