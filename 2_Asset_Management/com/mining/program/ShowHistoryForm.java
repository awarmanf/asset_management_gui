/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mining.program;

import com.mining.entity.HTHistory;
import com.mining.resources.RLayout;
import com.mining.resources.RStrings;
import com.mining.service.HTHistoryService;
import com.yudi.table.util.SimpleHeaderRenderer;
import com.yudi.table.util.TableColumnAdjuster;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

/**
 *
 * @author yudi This will show table history with column Date using date format
 * dd/MM/yyyy whereas the Date itself in mysql save as Date DATE in the format
 * of yyyy-MM-dd not Date DATESTAMP The HTHistory simple save and get Date
 * column as String not as Timestamp. If using Timestamp the trailing timestamp
 * will prevail, eg 00:00:00
 */
public class ShowHistoryForm extends JFrame implements KeyListener {

    private JScrollPane jspPane;
    private JPanel jpnlMain;
    private JPanel jpnlTop;
    private JPanel jpnlBottom;
    private JTable jtblHTHistory;
    private JButton jbtnUpdate;
    private JButton jbtnDelete;
    private JButton jbtnClose;
    private JComboBox jcmbStatus;
    private JLabel jlblJudul;
    private Font fontTable;
    private DefaultTableModel tableModel;
    private HTHistory htHistory;
    private HTHistoryService service;
    private DefaultTableModel model;
    private TableColumn columnStatus;
    private String ID, Merek, Tipe, SN;
    private String dDate, Status, Note;
    private Integer HID;
    private Boolean Current;
    private GridBagConstraints gbc;

    private List<HTHistory> listhtHistory = new ArrayList<>();
    private Map<Integer, HTHistory> hashMap = new HashMap<Integer, HTHistory>();
    private static final int Margin = 100;
    private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd");
    private static final Font font1 = new Font("Dialog", 0, RLayout.FS_DIALOG);
    private final Class[] columnClass = new Class[]{
        Integer.class, String.class, Boolean.class, String.class, String.class, String.class, String.class};
    private boolean tableListener = true;
    public static Boolean dataChange = false;

    public ShowHistoryForm(String ID, String Merek, String Tipe, String SN) {
        this.ID = ID;
        this.Merek = Merek;
        this.Tipe = Tipe;
        this.SN = SN;
        initComponents();

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    public void Show() {
        service = new HTHistoryService();
        Header();
        ReloadData();
        adjustColumns();

        if (RLayout.SET_RANDOM_LOCATION_FORM == 'Y') {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension frameSize = getSize();
            setLocation(NewPoint(Margin,
                    (int) screenSize.getWidth() - frameSize.width,
                    (int) screenSize.getHeight() - frameSize.height)
            );
        } else {
            setLocationRelativeTo(getParent());
        }
        setVisible(true);

        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (tableListener) {
                    jbtnUpdate.setEnabled(true);
                    try {
                        HID = Integer.parseInt(jtblHTHistory.getValueAt(jtblHTHistory.getSelectedRow(), 0).toString());
                        dDate = (String) jtblHTHistory.getValueAt(jtblHTHistory.getSelectedRow(), 1);
                        Current = (Boolean) jtblHTHistory.getValueAt(jtblHTHistory.getSelectedRow(), 2);
                        Status = (String) jtblHTHistory.getValueAt(jtblHTHistory.getSelectedRow(), 5);
                        Note = (String) jtblHTHistory.getValueAt(jtblHTHistory.getSelectedRow(), 6);

                        Date columnDate = dateFormat.parse(dDate);
                        String mysqlDateString = dateFormatSQL.format(columnDate);
                        htHistory = new HTHistory();
                        htHistory.setHID(HID);
                        htHistory.setCurrent(Current);
                        htHistory.setDate(mysqlDateString);
                        htHistory.setStatus(Status);
                        htHistory.setNote(Note);
                        hashMap.put(HID, htHistory);
                    } catch (ParseException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "ParseException Error", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage() + " " + HID);
                    }
                } else {
                    jbtnUpdate.setEnabled(false);
                }
            }
        });
    }

    public void Header() {
        Object[] judul = service.getShowHistoryHeader();

        tableModel = new DefaultTableModel(null, judul) {
            @Override
            public boolean isCellEditable(int row, int column) {
                switch (column) {
                    case 1:
                        return true;
                    case 2:
                        return true;
                    case 5:
                        return true;
                    case 6:
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnClass[columnIndex];
            }
        };
        fontTable = new Font("Table", 0, RLayout.FS_TABLE);
        jtblHTHistory.getTableHeader().setDefaultRenderer(
                new SimpleHeaderRenderer(Color.DARK_GRAY, RLayout.FS_TABLE));
        jtblHTHistory.setModel(tableModel);
        jtblHTHistory.setFont(fontTable);
    }

    public void ReloadData() {

        tableListener = false;
        List<HTHistory> list = service.SelectById(ID);

        if (list.size() > 0) {
            tableModel.getDataVector().removeAllElements();
            tableModel.fireTableDataChanged();

            for (HTHistory htHistory : list) {
                // converting Date SQL "yyyy-MM-dd" to Data Table "dd/MM/yyyy" will thrown exception
                try {
                    Object[] data = {
                        htHistory.getHID(),
                        dateFormat.format((dateFormatSQL.parse(htHistory.getDate()))),
                        htHistory.isCurrent(),
                        htHistory.getFullname(),
                        htHistory.getDepartment(),
                        htHistory.getStatus() + RStrings.COLUMN_STATUS_SUFFIX,
                        htHistory.getNote()
                    };
                    tableModel.addRow(data);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "ParseException Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            jtblHTHistory.setAutoCreateRowSorter(true);
            jtblHTHistory.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

            jcmbStatus = new JComboBox();
            for (String s : RStrings.STATUS) {
                jcmbStatus.addItem(s + RStrings.COLUMN_STATUS_SUFFIX);
            }
            jcmbStatus.setFont(font1);
            // Set combo box as the editor for the column Status
            columnStatus = jtblHTHistory.getColumn("Status");
            columnStatus.setCellEditor(new DefaultCellEditor(jcmbStatus));
            tableListener = true;

        } else {
            JOptionPane.showMessageDialog(this, "Data radio tidak ditemukan.");
        }
    }

    public void initComponents() {

        jlblJudul = new JLabel();
        jlblJudul.setFont(jlblJudul.getFont().deriveFont(jlblJudul.getFont().getStyle() | java.awt.Font.BOLD, RLayout.FS_TITLE));
        jlblJudul.setText("History of Radio " + Merek + " " + Tipe + " S/N " + SN + " (" + ID + ")");

        jpnlMain = new JPanel();
        jpnlTop = new JPanel();
        jpnlBottom = new JPanel();
        jspPane = new JScrollPane();

        jpnlTop.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        jpnlTop.add(jlblJudul);

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
        jtblHTHistory = new JTable(model);
        jspPane.setViewportView(jtblHTHistory);

        jbtnUpdate = new JButton(RStrings.BTN_UPDATE);
        jbtnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnUpdateActionPerformed(evt);
            }
        });
        jbtnUpdate.setEnabled(false);

        jbtnDelete = new JButton(RStrings.BTN_DELETE);
        jbtnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnDeleteActionPerformed(evt);
            }
        });

        jbtnClose = new JButton(RStrings.BTN_CLOSE);
        jbtnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCloseActionPerformed(evt);
            }
        });

        jpnlBottom.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        // set globally
        gbc.fill = GridBagConstraints.HORIZONTAL;
        //gbc.anchor = GridBagConstraints.PAGE_END; //bottom of space -> no needed
        gbc.insets = new Insets(10, 0, 0, 0);  //top padding
        // set locally
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        jpnlBottom.add(jbtnUpdate, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        jpnlBottom.add(new JLabel(""), gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0;
        jpnlBottom.add(jbtnDelete, gbc);
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 1;
        jpnlBottom.add(new JLabel(""), gbc);
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weightx = 0;
        jpnlBottom.add(jbtnClose, gbc);

        jpnlMain.setLayout(new BorderLayout());
        jpnlMain.add(jpnlTop, BorderLayout.NORTH);
        jpnlMain.add(jspPane, BorderLayout.CENTER);
        jpnlMain.add(jpnlBottom, BorderLayout.SOUTH);
        jpnlMain.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10)); // (NORTH, WEST, SOUTH, EAST)

        this.add(jpnlMain);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 300));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();

    }

    private void jbtnUpdateActionPerformed(java.awt.event.ActionEvent evt) {

        if (hashMap.size() > 0) {
            //boolean saving = false;
            // Get a set of the entries
            Set set = hashMap.entrySet();
            // Get an iterator
            Iterator iterator = set.iterator();

            while (iterator.hasNext()) {
                Map.Entry me = (Map.Entry<Integer, HTHistory>) iterator.next();
                listhtHistory.add((HTHistory) me.getValue());
            }
            for (HTHistory htHistory : listhtHistory) {
                service.update(htHistory);
            }
            JOptionPane.showMessageDialog(null, RStrings.DLG_UPDATE_OK);
            hashMap.clear();
            listhtHistory.clear();
            jbtnUpdate.setEnabled(false);
        }
    }

    private void jbtnDeleteActionPerformed(java.awt.event.ActionEvent evt) {

        if (jtblHTHistory.getSelectedRow() >= 0) {
            int hid = (Integer) jtblHTHistory.getValueAt(jtblHTHistory.getSelectedRow(), 0);
            // hapus history
            service.delete(hid);
            service.SetCurrentID(ID);
            ReloadData();
            adjustColumns();
        } else {
            JOptionPane.showMessageDialog(this, RStrings.DLG_SELECT_PLEASE);
        }
    }

    private void jbtnCloseActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    public void adjustColumns() {
        //jtblHTHistory.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnAdjuster tca = new TableColumnAdjuster(jtblHTHistory, 10);
        tca.adjustColumns();
        // default is  AUTO_RESIZE_OFF then set to AUTO_RESIZE_LAST_COLUMN
        jtblHTHistory.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    }

    /**
     * Set a new frame location randomly
     *
     */
    private Point NewPoint(int margin, int width, int height) {
        Random test = new Random();
        int x = margin + test.nextInt(width);
        int y = margin + test.nextInt(height);
        return new Point(x, y);
    }

    public static void main(String[] args) {
        /*
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ShowHistoryForm show = new ShowHistoryForm("D140", "Icom", "V-80", "60175588-0");
                show.Show();
            }
        });
         */
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ShowHistoryForm show = new ShowHistoryForm("D140", "Icom", "V-80", "60175588-0");
                show.Show();
            }
        });
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
            dispose();
        }
    }
}
