package View.User;

import Model.User.UserSession;
import View.Admin.InputBarang;
import View.Component.AppFrame;
import View.Component.AppLabelFactory;
import View.Component.AppTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class DashboardUser extends AppFrame {

    public DashboardUser() { this(null); }

    public DashboardUser(JFrame parentFrame) {
        super("Dashboard User", AppTheme.WINDOW_DASHBOARD, parentFrame);
        setLayout(new BorderLayout());
        setBackground(AppTheme.BACKGROUND);

        // ======== SIDEBAR ========
        JPanel sidebar = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Gradient sidebar
                GradientPaint gp = new GradientPaint(0, 0, AppTheme.SIDEBAR, 0, getHeight(), new Color(14, 22, 50));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                // Right accent line
                g2.setColor(new Color(67, 97, 238, 60));
                g2.fillRect(getWidth() - 2, 0, 2, getHeight());
                g2.dispose();
            }
        };
        sidebar.setPreferredSize(new Dimension(230, 0));
        sidebar.setBackground(AppTheme.SIDEBAR);

        // Logo area
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setBounds(0, 0, 230, 85);
        logoPanel.setOpaque(false);

        // Colored logo badge
        JLabel logoIcon = new JLabel("L&F") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(AppTheme.PRIMARY);
                g2.fill(new RoundRectangle2D.Float(0, 0, 40, 40, 10, 10));
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
                FontMetrics fm = g2.getFontMetrics();
                String t = "L&F";
                g2.drawString(t, (40 - fm.stringWidth(t)) / 2, (40 + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        logoIcon.setPreferredSize(new Dimension(40, 40));

        JLabel logoText = new JLabel("L&F Kampus");
        logoText.setFont(new Font("Segoe UI", Font.BOLD, 15));
        logoText.setForeground(Color.WHITE);
        JLabel logoSub = new JLabel("User Panel");
        logoSub.setFont(AppTheme.SMALL_FONT);
        logoSub.setForeground(new Color(148, 163, 184));

        JPanel textBox = new JPanel();
        textBox.setLayout(new BoxLayout(textBox, BoxLayout.Y_AXIS));
        textBox.setOpaque(false);
        textBox.add(logoText);
        textBox.add(Box.createVerticalStrut(2));
        textBox.add(logoSub);

        JPanel logoRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 22));
        logoRow.setOpaque(false);
        logoRow.add(logoIcon);
        logoRow.add(textBox);
        logoPanel.add(logoRow, BorderLayout.CENTER);

        // Separator
        JPanel sep = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(255, 255, 255, 18));
                g.fillRect(14, 0, getWidth() - 28, 1);
            }
        };
        sep.setOpaque(false);
        sep.setBounds(0, 83, 230, 2);

        // Menu label
        JLabel lblMenu = new JLabel("  NAVIGASI");
        lblMenu.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblMenu.setForeground(new Color(99, 179, 237, 160));
        lblMenu.setBounds(20, 100, 190, 18);

        JButton btnLihat    = makeSidebarBtn("Lihat Barang",  120);
        JButton btnInput    = makeSidebarBtn("Input Barang",  165);
        JButton btnRiwayat  = makeSidebarBtn("Riwayat Saya",  210);
        JButton btnStatistik= makeSidebarBtn("Statistik Saya",255);

        // Divider
        JPanel sep2 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(255, 255, 255, 12));
                g.fillRect(14, 0, getWidth() - 28, 1);
            }
        };
        sep2.setOpaque(false);
        sep2.setBounds(0, 310, 230, 2);

        JButton btnLogout = makeSidebarBtn("Logout", 330);
        btnLogout.setForeground(new Color(252, 165, 165));

        JButton btnBack = null;
        if (hasParentFrame()) {
            btnBack = makeSidebarBtn("Kembali", 375);
            btnBack.setForeground(new Color(165, 243, 252));
        }

        sidebar.add(logoPanel);
        sidebar.add(sep);
        sidebar.add(lblMenu);
        sidebar.add(btnLihat);
        sidebar.add(btnInput);
        sidebar.add(btnRiwayat);
        sidebar.add(btnStatistik);
        sidebar.add(sep2);
        sidebar.add(btnLogout);
        if (btnBack != null) sidebar.add(btnBack);

        // ======== CONTENT AREA ========
        JPanel content = new JPanel(null);
        content.setBackground(AppTheme.BACKGROUND);

        // Top bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBounds(0, 0, 770, 66);
        topBar.setBackground(AppTheme.SURFACE);
        topBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, AppTheme.BORDER),
            BorderFactory.createEmptyBorder(0, 24, 0, 24)));

        JLabel pageTitle = new JLabel("Dashboard User");
        pageTitle.setFont(AppTheme.SECTION_TITLE_FONT);
        pageTitle.setForeground(AppTheme.TEXT_PRIMARY);

        // Badge nama user
        String namaUser = (UserSession.getCurrentUser() != null && UserSession.getCurrentUser().getNama() != null)
                ? UserSession.getCurrentUser().getNama() : "User";
        JLabel userInfo = new JLabel(namaUser);
        userInfo.setFont(AppTheme.LABEL_FONT);
        userInfo.setForeground(AppTheme.PRIMARY);

        JPanel userBadge = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int pad = 10;
                int h = getHeight() - pad * 2;
                g2.setColor(AppTheme.PRIMARY_LIGHT);
                g2.fill(new RoundRectangle2D.Float(0, pad, getWidth(), h, h, h));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        userBadge.setOpaque(false);
        userBadge.setPreferredSize(new Dimension(160, 66));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 4, 0, 4);
        userBadge.add(userInfo, gbc);

        topBar.add(pageTitle, BorderLayout.WEST);
        topBar.add(userBadge, BorderLayout.EAST);
        content.add(topBar);

        // Welcome
        JLabel welcome = AppLabelFactory.body("Selamat datang di Sistem Lost & Found Kampus");
        welcome.setForeground(AppTheme.TEXT_SECONDARY);
        welcome.setBounds(28, 86, 600, 22);
        content.add(welcome);

        // Stat cards (baris 1)
        content.add(makeStatCard("Lihat Barang",   AppTheme.PRIMARY,  28,  126, 200, 110));
        content.add(makeStatCard("Input Barang",   AppTheme.SUCCESS,  248, 126, 200, 110));
        content.add(makeStatCard("Riwayat Saya",   AppTheme.ACCENT,   468, 126, 200, 110));

        // Stat cards (baris 2)
        content.add(makeStatCard("Statistik Saya", AppTheme.WARNING,  28,  256, 200, 110));
        content.add(makeStatCard("Claim Barang",   new Color(139, 92, 246), 248, 256, 200, 110));

        // Info panel
        JPanel infoPanel = makeInfoPanel();
        infoPanel.setBounds(28, 394, 640, 150);
        content.add(infoPanel);

        add(sidebar, BorderLayout.WEST);
        add(content, BorderLayout.CENTER);

        // ======== ACTIONS ========
        btnLihat.addActionListener(e -> showChildFrame(new LihatBarang(this)));
        btnInput.addActionListener(e -> showChildFrame(new InputBarang(this)));
        btnRiwayat.addActionListener(e -> showChildFrame(new RiwayatBarang(this)));
        btnStatistik.addActionListener(e -> showChildFrame(new StatistikUser(this)));
        btnLogout.addActionListener(e -> {
            UserSession.clear();
            dispose();
            new Login().setVisible(true);
        });
        if (btnBack != null) {
            final JButton fb = btnBack;
            fb.addActionListener(e -> backToParent());
        }
    }

    // ======== SIDEBAR BUTTON ========
    private JButton makeSidebarBtn(String text, int y) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover() || getModel().isPressed()) {
                    g2.setColor(AppTheme.SIDEBAR_HOVER);
                    g2.fill(new RoundRectangle2D.Float(4, 2, getWidth() - 8, getHeight() - 4, 8, 8));
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(AppTheme.SIDEBAR_FONT);
        btn.setForeground(new Color(203, 213, 225));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBounds(10, y, 210, 38);
        return btn;
    }

    // ======== STAT CARD ========
    private JPanel makeStatCard(String label, Color color, int x, int y, int w, int h) {
        JPanel card = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Shadow
                g2.setColor(new Color(0, 0, 0, 14));
                g2.fill(new RoundRectangle2D.Float(4, 6, w - 8, h - 6, 14, 14));
                // Card background
                g2.setColor(AppTheme.SURFACE);
                g2.fill(new RoundRectangle2D.Float(0, 0, w - 4, h - 4, 14, 14));
                // Left accent bar
                g2.setColor(color);
                g2.fill(new RoundRectangle2D.Float(0, 0, 4, h - 4, 4, 4));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setBounds(x, y, w, h);
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(16, 18, 12, 14));

        JLabel lbl = new JLabel(label);
        lbl.setFont(AppTheme.CARD_TITLE_FONT);
        lbl.setForeground(AppTheme.TEXT_PRIMARY);
        card.add(lbl, BorderLayout.CENTER);

        return card;
    }

    // ======== INFO PANEL ========
    private JPanel makeInfoPanel() {
        JPanel p = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(AppTheme.SURFACE);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 14, 14));
                g2.setColor(AppTheme.BORDER);
                g2.setStroke(new java.awt.BasicStroke(1));
                g2.draw(new RoundRectangle2D.Float(0.5f, 0.5f, getWidth() - 2, getHeight() - 2, 14, 14));
                // Title bar
                g2.setColor(AppTheme.PRIMARY_LIGHT);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, 36, 14, 14));
                g2.fillRect(0, 22, getWidth() - 1, 14);
                g2.dispose();
            }
        };
        p.setOpaque(false);

        JLabel titleLbl = new JLabel("Panduan Penggunaan");
        titleLbl.setFont(AppTheme.LABEL_FONT);
        titleLbl.setForeground(AppTheme.PRIMARY);
        titleLbl.setBounds(16, 8, 400, 22);
        p.add(titleLbl);

        String[] tips = {
            "\u2022 Gunakan menu Lihat Barang untuk melihat daftar barang",
            "\u2022 Gunakan Input Barang untuk menambahkan barang baru",
            "\u2022 Ajukan claim jika barang milikmu ditemukan",
        };
        int ty = 50;
        for (String tip : tips) {
            JLabel l = AppLabelFactory.body(tip);
            l.setBounds(16, ty, 600, 22);
            p.add(l);
            ty += 28;
        }
        return p;
    }
}