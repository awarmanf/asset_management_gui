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
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JCheckBox;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author yudi
 */
public class MainForm extends JFrame implements ActionListener {

    private JScrollPane jspPane;
    private JPanel jpnlMain;
    private JPanel jpnlTop;
    private JPanel jpnlBottom;
    private JPanel jpnlBottomWest;
    private JPanel jpnlSearch;
    private JPanel jpnlBottomEast;
    private JTable jtblHTHistory;
    private JTextField jtxtSearch;
    private JButton jbtnSearch;
    private JButton jbtnReset;
    private JButton jbtnShowHistory;
    private JButton jbtnAddHistory;
    private JButton jbtnAddRadio;
    private JButton jbtnExport;
    private JLabel jlblJudul;
    private JRadioButton jrbtnSN;
    private JRadioButton jrbtnName;
    private JCheckBox jchkCurrent;
    private JRadioButton jrbtnDepartment;
    private JRadioButton jrbtnStatus;
    private JRadioButton jrbtnNote;
    private ButtonGroup groupRbtn;
    private Font fontTable;
    private JFileChooser jFC;
    private Object[] rowHeader; // judul
    private int tableRows;

    private GridBagConstraints gbc;
    private DefaultTableModel tableModel;
    private HTHistoryService service;
    private DefaultTableModel model;
    public static Boolean dataChange = false;
    private JSONObject jo;
    
    private final static String json = "{ "
            + "\"var\": \"All\","
            + "\"value\": value_not_important_here"
            + "}";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd");

    public MainForm() {
        initComponents();
        service = new HTHistoryService();
        Header();
        ReloadData(json, false);
        adjustColumns();
    }

    public void Header() {
        rowHeader = service.getMainHeader();
        tableModel = new DefaultTableModel(null, rowHeader) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        fontTable = new Font("Table", 0, RLayout.FS_TABLE);
        jtblHTHistory.getTableHeader().setDefaultRenderer(
                new SimpleHeaderRenderer(Color.DARK_GRAY, RLayout.FS_TABLE));
        jtblHTHistory.setModel(tableModel);
        jtblHTHistory.setFont(fontTable);
    }

    public void ReloadData(String json, boolean allHistory) {

        jo = new JSONObject(json);
        List<HTHistory> list;

        if (allHistory) {
            list = service.SelectAllHistory(jo);
        } else {
            list = service.SelectAll(jo);
        }

        if (list.size() > 0) {
            tableModel.getDataVector().removeAllElements();
            tableModel.fireTableDataChanged();
            tableRows = 0;
            for (HTHistory htHistory : list) {
                // converting Date SQL "yyyy-MM-dd" to Data Table "dd/MM/yyyy" will thrown exception
                try {
                    Object[] data = {
                        dateFormat.format((dateFormatSQL.parse(htHistory.getDate()))),
                        htHistory.getID(),
                        htHistory.getMerek(),
                        htHistory.getTipe(),
                        htHistory.getSN(),
                        htHistory.getFullname(),
                        htHistory.getDepartment(),
                        htHistory.getStatus(),
                        htHistory.getNote()
                    };
                    tableModel.addRow(data);
                    tableRows++;
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "ParseException Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            tableRows--;
            jtblHTHistory.setAutoCreateRowSorter(true);
            jtblHTHistory.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        } else {
            JOptionPane.showMessageDialog(this, "Data tidak ditemukan.");
        }
    }

    public void initComponents() {

        jlblJudul = new JLabel();
        jlblJudul.setFont(jlblJudul.getFont().deriveFont(jlblJudul.getFont().getStyle() |
                java.awt.Font.BOLD, RLayout.FS_TITLE));
        jlblJudul.setText(RStrings.LBL_TITLE_MAIN);

        jpnlMain = new JPanel();
        jpnlTop = new JPanel();
        jpnlBottom = new JPanel();
        jpnlSearch = new JPanel();
        jpnlBottomWest = new JPanel();
        jpnlBottomEast = new JPanel();
        jspPane = new JScrollPane();

        jtxtSearch = new JTextField();
        jtxtSearch.setColumns(10);
        jbtnSearch = new JButton(RStrings.BTN_SEARCH);
        jbtnSearch.addActionListener(this);

        jbtnReset = new JButton(RStrings.BTN_RESET);
        jbtnReset.addActionListener(this);

        jrbtnSN = new JRadioButton(RStrings.RBTN_SN);
        jrbtnName = new JRadioButton(RStrings.RBTN_NAME);

        jchkCurrent = new JCheckBox("All Histories");
        jchkCurrent.setSelected(false);

        jrbtnDepartment = new JRadioButton(RStrings.RBTN_DEPARTMENT);
        jrbtnStatus = new JRadioButton(RStrings.RBTN_STATUS);
        jrbtnNote = new JRadioButton(RStrings.RBTN_NOTE);
        jrbtnSN.setSelected(true);
        groupRbtn = new ButtonGroup();
        groupRbtn.add(jrbtnSN);
        groupRbtn.add(jrbtnName);
        groupRbtn.add(jrbtnDepartment);
        groupRbtn.add(jrbtnStatus);
        groupRbtn.add(jrbtnNote);

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

        jpnlSearch.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 0; // 10
        gbc.gridwidth = 3;
        jpnlSearch.add(new JLabel(RStrings.LBL_SEARCH, SwingConstants.RIGHT), gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipadx = 0; // 10
        gbc.gridwidth = 3;
        jpnlSearch.add(jtxtSearch, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.ipadx = 10;
        gbc.gridwidth = 1;
        jpnlSearch.add(jbtnSearch, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.ipadx = 10;
        gbc.gridwidth = 1;
        jpnlSearch.add(new JLabel(""), gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.ipadx = 10;
        gbc.gridwidth = 1;
        jpnlSearch.add(jbtnReset, gbc);
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.ipadx = 4;
        gbc.gridwidth = 1;
        jpnlSearch.add(jrbtnSN, gbc);
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.ipadx = 4;
        gbc.gridwidth = 1;
        jpnlSearch.add(jrbtnName, gbc);
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.ipadx = 4;
        gbc.gridwidth = 1;
        jpnlSearch.add(jchkCurrent, gbc);
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.ipadx = 4;
        gbc.gridwidth = 1;
        jpnlSearch.add(jrbtnDepartment, gbc);
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.ipadx = 4;
        gbc.gridwidth = 1;
        jpnlSearch.add(jrbtnStatus, gbc);
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.ipadx = 4;
        gbc.gridwidth = 1;
        jpnlSearch.add(jrbtnNote, gbc);

        jpnlBottomWest.setLayout(new FlowLayout());
        Border border = BorderFactory.createRaisedBevelBorder();
        jpnlBottomWest.setBorder(border);
        jpnlBottomWest.add(jpnlSearch);

        jbtnShowHistory = new JButton(RStrings.BTN_SHOW_HISTORY);
        jbtnShowHistory.addActionListener(this);

        jbtnAddHistory = new JButton(RStrings.BTN_ADD_HISTORY);
        jbtnAddHistory.addActionListener(this);

        jbtnAddRadio = new JButton(RStrings.BTN_ADD_RADIO);
        jbtnAddRadio.addActionListener(this);

        jbtnExport = new JButton(RStrings.BTN_EXPORT, createImageIcon("/images/save.gif"));
        jbtnExport.addActionListener(this);

        jpnlBottomEast.setLayout(new GridLayout(2, 2, 10, 10));
        jpnlBottomEast.add(jbtnShowHistory);
        jpnlBottomEast.add(jbtnAddRadio);
        jpnlBottomEast.add(jbtnAddHistory);
        jpnlBottomEast.add(jbtnExport);

        jpnlBottom.setLayout(new BorderLayout());
        jpnlBottom.add(jpnlBottomWest, BorderLayout.WEST);
        jpnlBottom.add(new JLabel(""), BorderLayout.CENTER);
        jpnlBottom.add(jpnlBottomEast, BorderLayout.EAST);
        jpnlBottom.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // (NORTH, WEST, SOUTH, EAST)

        jpnlMain.setLayout(new BorderLayout());
        jpnlMain.add(jpnlTop, BorderLayout.NORTH);
        jpnlMain.add(jspPane, BorderLayout.CENTER);
        jpnlMain.add(jpnlBottom, BorderLayout.SOUTH);
        jpnlMain.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10)); // (NORTH, WEST, SOUTH, EAST)

        this.add(jpnlMain);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(975, 600));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void adjustColumns() {
        TableColumnAdjuster tca = new TableColumnAdjuster(jtblHTHistory, 10);
        tca.adjustColumns();
        // default is  AUTO_RESIZE_OFF then set to AUTO_RESIZE_LAST_COLUMN
        //jtblHTHistory.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    }

    public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = MainForm.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == jbtnShowHistory) {
            if (jtblHTHistory.getSelectedRow() >= 0) {
                String ID = (String) jtblHTHistory.getValueAt(jtblHTHistory.getSelectedRow(), 1);
                String Merek = (String) jtblHTHistory.getValueAt(jtblHTHistory.getSelectedRow(), 2);
                String Tipe = (String) jtblHTHistory.getValueAt(jtblHTHistory.getSelectedRow(), 3);
                String SN = (String) jtblHTHistory.getValueAt(jtblHTHistory.getSelectedRow(), 4);

                //ShowHistoryForm show = new ShowHistoryForm(ID, Merek, Tipe, SN);
                ShowHistoryForm show = new ShowHistoryForm(ID, Merek, Tipe, SN);
                show.Show();
                if (MainForm.dataChange) {
                    ReloadData(json, false);
                    adjustColumns();
                }
            } else {
                JOptionPane.showMessageDialog(this, RStrings.DLG_SELECT_PLEASE);
            }
        } else if (ae.getSource() == jbtnAddHistory) {
            if (jtblHTHistory.getSelectedRow() >= 0) {
                String ID = (String) jtblHTHistory.getValueAt(jtblHTHistory.getSelectedRow(), 1);
                String Merek = (String) jtblHTHistory.getValueAt(jtblHTHistory.getSelectedRow(), 2);
                String Tipe = (String) jtblHTHistory.getValueAt(jtblHTHistory.getSelectedRow(), 3);
                String SN = (String) jtblHTHistory.getValueAt(jtblHTHistory.getSelectedRow(), 4);

                AddHistoryDialog addHistory = new AddHistoryDialog(this, true, ID, Merek, Tipe, SN);
                addHistory.Add();

                if (MainForm.dataChange) {
                    ReloadData(json, false);
                    adjustColumns();
                }
            } else {
                JOptionPane.showMessageDialog(this, RStrings.DLG_SELECT_PLEASE);
            }
        } else if (ae.getSource() == jbtnAddRadio) {
            AddRadio addRadio = new AddRadio(this, true);
            addRadio.Add();

            if (MainForm.dataChange) {
                ReloadData(json, false);
                adjustColumns();
            }
        } else if (ae.getSource() == jbtnExport) {
            boolean showJFC = true;
            jFC = new JFileChooser();
            jFC.addChoosableFileFilter(new FileNameExtensionFilter("MS Excel Documents (xlsx)", "xlsx"));
            jFC.setAcceptAllFileFilterUsed(false);
            File file = null;
            do {
                int returnVal = jFC.showSaveDialog(MainForm.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    file = jFC.getSelectedFile();
                    if (file.exists()) {
                        if (JOptionPane.showConfirmDialog(null,
                                "Are you sure to overwrite the file?", "File Exist!",
                                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE)
                                == JOptionPane.YES_OPTION) {
                            // overwrite the file
                            showJFC = false;
                            WriteXLSX(file.getPath());
                        }
                    } else {
                        showJFC = false;
                        WriteXLSX(file.getPath());
                    }
                } else {
                    showJFC = false;
                }
            } while (showJFC);

        } else if (ae.getSource() == jbtnSearch) {
            String value = jtxtSearch.getText();
            String selected = getSelectedButtonText(groupRbtn);
            String json1 = "{ \"var\": \"" + selected + "\",\"value\": \"" + value + "\"}";
            if (value.isEmpty()) {
                JOptionPane.showMessageDialog(this, RStrings.DLG_SEARCH);
            } else {
                if (jchkCurrent.isSelected()) {
                    ReloadData(json1, true);
                } else {
                    ReloadData(json1, false);
                }
                adjustColumns();
            }
        } else if (ae.getSource() == jbtnReset) {
            jtxtSearch.setText("");
            jrbtnSN.setSelected(true);
            jchkCurrent.setSelected(false);
            ReloadData(json, false);
            adjustColumns();
        }
    }

    private void WriteXLSX(String file) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Radio HT");

        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        org.apache.poi.ss.usermodel.Font font = sheet.getWorkbook().createFont();
        font.setFontName("Arial");
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        cellStyle.setFont(font);

        System.out.println("Saving as " + file);

        // print row header
        int rowCount = 0;
        int columnCount = 0;
        Row row = sheet.createRow(++rowCount);
        Cell cell = row.createCell(columnCount++);
        cell.setCellStyle(cellStyle);
        cell.setCellValue((String) "No");
        for (Object judul : rowHeader) {
            cell = row.createCell(columnCount++);
            cell.setCellStyle(cellStyle);
            cell.setCellValue((String) judul);
        }

        CellStyle cellStyle1 = sheet.getWorkbook().createCellStyle();
        org.apache.poi.ss.usermodel.Font font1 = sheet.getWorkbook().createFont();
        font1.setFontName("Arial");
        //font1.setBold(false);
        font1.setFontHeightInPoints((short) 10);
        cellStyle1.setFont(font1);

        int no = 1;
        for (int tableRow = 0; tableRow <= tableRows; tableRow++) {
            columnCount = 0;
            row = sheet.createRow(++rowCount);
            cell = row.createCell(columnCount++);
            cell.setCellValue((Integer) no++);
            for (int tableCol = 0; tableCol <= 8; tableCol++) {
                cell = row.createCell(columnCount++);
                cell.setCellValue(jtblHTHistory.getValueAt(tableRow, tableCol).toString());
                cell.setCellStyle(cellStyle1);
            }
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                workbook.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }
}
