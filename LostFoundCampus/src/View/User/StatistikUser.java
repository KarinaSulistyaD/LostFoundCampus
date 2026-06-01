/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.User;

import Controller.ControllerBarang;
import Model.User.UserSession;
import View.Component.AppButtonFactory;
import View.Component.AppFrame;
import View.Component.AppLabelFactory;
import View.Component.AppTheme;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.List;
import Model.Barang.ModelBarang;
/**
 *
 * @author Ivaa
 */
public class StatistikUser extends AppFrame {
    public StatistikUser() { 
        this(null); 
    }

    public StatistikUser(JFrame parentFrame) {
        super("Statistik Saya", AppTheme.WINDOW_TABLE, parentFrame);
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
                "Statistik Barang Saya"), BorderLayout.WEST
        );

        if (hasParentFrame()) {
            JButton btnBackTop = AppButtonFactory.backButton();
            btnBackTop.addActionListener(e -> backToParent());
            topBar.add(btnBackTop, BorderLayout.EAST);
        }
        add(topBar, BorderLayout.NORTH);

        // ---- Content ----
        JPanel content = new JPanel(null);
        content.setBackground(AppTheme.BACKGROUND);

        int uid = UserSession.getCurrentUserId();
        ControllerBarang cb = new ControllerBarang();
        List<ModelBarang> myBarang = cb.getAllByUserId(uid);

        long totalSaya = myBarang.size();
        long totalHilang = myBarang.stream().filter(b -> 
                "Hilang".equalsIgnoreCase(b.getStatus())).count();
        long totalDitemukan = myBarang.stream().filter(b -> 
                "Ditemukan".equalsIgnoreCase(b.getStatus())).count();
        long sudahDiklaim = myBarang.stream().filter(b -> 
                "Sudah Diklaim".equalsIgnoreCase(b.getStatusClaim())).count();

        String namaUser = UserSession.getCurrentUser() != null
                ? UserSession.getCurrentUser().getNama() : "User";

        // Greeting
        JLabel greeting = AppLabelFactory.body("Halo, " + namaUser 
                + "! Berikut ringkasan barang Anda.");
        greeting.setForeground(AppTheme.TEXT_SECONDARY);
        greeting.setBounds(28, 20, 700, 22);
        content.add(greeting);

        // Stat cards
        content.add(makeStatCard("Total Barang Saya", String.valueOf(totalSaya), AppTheme.PRIMARY, 28,  60, 190, 120));
        content.add(makeStatCard("Barang Hilang", String.valueOf(totalHilang),    AppTheme.DANGER,  238,  60, 190, 120));
        content.add(makeStatCard("Barang Ditemukan", String.valueOf(totalDitemukan), AppTheme.SUCCESS, 448,  60, 190, 120));
        content.add(makeStatCard("Sudah Diklaim", String.valueOf(sudahDiklaim),   AppTheme.ACCENT,  658,  60, 190, 120));

        // Info panel
        JPanel infoPanel = makeInfoPanel(
                totalSaya, totalHilang, totalDitemukan, sudahDiklaim
        );
        infoPanel.setBounds(28, 210, 820, 160);
        content.add(infoPanel);

        // Refresh button
        JButton btnRefresh = AppButtonFactory.primary("Refresh Data");
        btnRefresh.setBounds(28, 390, 160, 38);
        content.add(btnRefresh);
        btnRefresh.addActionListener(e -> {
            dispose();
            new StatistikUser(parentFrame).setVisible(true);
        });

        add(content, BorderLayout.CENTER);
    }

    private JPanel makeStatCard(String label, String value, Color color, int x, int y, int w, int h) {
        JPanel card = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 14));
                g2.fill(new RoundRectangle2D.Float(4, 6, w - 8, h - 6, 14, 14));
                g2.setColor(AppTheme.SURFACE);
                g2.fill(new RoundRectangle2D.Float(0, 0, w - 4, h - 4, 14, 14));
                g2.setColor(color);
                g2.fill(new RoundRectangle2D.Float(0, 0, 4, h - 4, 4, 4));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setBounds(x, y, w, h);
        card.setOpaque(false);

        JLabel lblValue = new JLabel(value, SwingConstants.CENTER);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 38));
        lblValue.setForeground(color);
        lblValue.setBounds(0, 18, w - 4, 50);
        card.add(lblValue);

        JLabel lblTitle = new JLabel(label, SwingConstants.CENTER);
        lblTitle.setFont(AppTheme.CARD_TITLE_FONT);
        lblTitle.setForeground(AppTheme.TEXT_PRIMARY);
        lblTitle.setBounds(0, 70, w - 4, 24);
        card.add(lblTitle);

        return card;
    }

    private JPanel makeInfoPanel(long total, long hilang, long ditemukan, long diklaim) {
        JPanel p = new JPanel(null) {
            @Override 
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(AppTheme.SURFACE);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 14, 14));
                g2.setColor(AppTheme.BORDER);
                g2.setStroke(new java.awt.BasicStroke(1));
                g2.draw(new RoundRectangle2D.Float(0.5f, 0.5f, getWidth()-2, getHeight()-2, 14, 14));
                g2.setColor(AppTheme.PRIMARY_LIGHT);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth()-1, 36, 14, 14));
                g2.fillRect(0, 22, getWidth()-1, 14);
                g2.dispose();
            }
        };
        p.setOpaque(false);

        JLabel titleLbl = new JLabel("Detail Statistik Anda");
        titleLbl.setFont(AppTheme.LABEL_FONT);
        titleLbl.setForeground(AppTheme.PRIMARY);
        titleLbl.setBounds(16, 8, 400, 22);
        p.add(titleLbl);

        String[] info = {
            "• Total barang yang pernah Anda daftarkan  : " + total + " barang",
            "• Barang berstatus Hilang                  : " + hilang + " barang",
            "• Barang berstatus Ditemukan               : " + ditemukan + " barang",
            "• Barang yang sudah berhasil diklaim       : " + diklaim + " barang",
        };
        int ty = 50;
        for (String line : info) {
            JLabel l = AppLabelFactory.body(line);
            l.setBounds(16, ty, 780, 22);
            p.add(l);
            ty += 26;
        }
        return p;
    }
}
