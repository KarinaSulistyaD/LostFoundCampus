/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.User;

import Controller.ControllerBarang;
import Model.Barang.ModelBarang;
import Model.Barang.ModelTableBarang;
import Model.User.UserSession;
import View.Component.AppButtonFactory;
import View.Component.AppFrame;
import View.Component.AppLabelFactory;
import View.Component.AppTableFactory;
import View.Component.AppTheme;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author Ivaa
 */
public class RiwayatBarang extends AppFrame {
    private final JTable tableBarang;
    private final JTextField txtSearch;

    public RiwayatBarang() { this(null); }

    public RiwayatBarang(JFrame parentFrame) {
        super("Riwayat Barang Saya", AppTheme.WINDOW_TABLE, parentFrame);
        setLayout(new BorderLayout());
        setBackground(AppTheme.BACKGROUND);

        // ---- Top bar ----
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(AppTheme.SURFACE);
        topBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, AppTheme.BORDER),
            BorderFactory.createEmptyBorder(0, 22, 0, 22)));
        topBar.setPreferredSize(new Dimension(0, 62));
        topBar.add(AppLabelFactory.sectionTitle(
                "Riwayat Barang Saya"
        ), BorderLayout.WEST);

        if (hasParentFrame()) {
            JButton btnBackTop = AppButtonFactory.backButton();
            btnBackTop.addActionListener(e -> backToParent());
            topBar.add(btnBackTop, BorderLayout.EAST);
        }
        add(topBar, BorderLayout.NORTH);

        // ---- Toolbar ----
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        toolbar.setBackground(AppTheme.BACKGROUND);
        toolbar.setBorder(BorderFactory.createEmptyBorder(4, 16, 0, 16));

        txtSearch = new JTextField(22);
        AppTableFactory.styleSearchField(txtSearch);
        txtSearch.setPreferredSize(new Dimension(220, 36));

        JButton btnSearch  = AppButtonFactory.primary("Cari");
        JButton btnRefresh = AppButtonFactory.success("Refresh");

        toolbar.add(txtSearch);
        toolbar.add(btnSearch);
        toolbar.add(btnRefresh);

        // ---- Table ----
        tableBarang = new JTable();
        AppTableFactory.style(tableBarang);
        JScrollPane scroll = new JScrollPane(tableBarang);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(AppTheme.SURFACE);

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(AppTheme.BACKGROUND);
        center.setBorder(BorderFactory.createEmptyBorder(8, 16, 16, 16));
        center.add(toolbar, BorderLayout.NORTH);
        center.add(scroll, BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        loadTable();

        btnSearch.addActionListener(e -> searchData());
        btnRefresh.addActionListener(e -> { 
            txtSearch.setText(""); loadTable(); 
        });
        
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            
            @Override 
            public void keyReleased(java.awt.event.KeyEvent e) { 
                searchData(); 
            }
        });
    }

    private void loadTable() {
        int uid = UserSession.getCurrentUserId();
        tableBarang.setModel(new ModelTableBarang(
                new ControllerBarang().getAllByUserId(uid)));
    }

    private void searchData() {
        int uid = UserSession.getCurrentUserId();
        tableBarang.setModel(new ModelTableBarang(
                new ControllerBarang().searchByUserId(uid, txtSearch.getText()))
        );
    }
}
