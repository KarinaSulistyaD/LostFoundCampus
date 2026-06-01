package View.User;

import Controller.ControllerClaimRequest;
import Model.Claim.ModelClaimRequest;
import View.Component.AppButtonFactory;
import View.Component.AppTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class ModalClaimBarang extends JDialog {

    private ControllerClaimRequest controller;
    private int idBarang;
    private int idUser;

    public ModalClaimBarang(JFrame parent, int idBarang, int idUser) {
        super(parent, "Claim Barang", true);
        this.idBarang = idBarang;
        this.idUser = idUser;
        controller = new ControllerClaimRequest();
        initComponents();
    }

    private void initComponents() {
        setSize(AppTheme.DIALOG_CLAIM);
        setLocationRelativeTo(getParent());
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));

        // Root with rounded corners
        JPanel root = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Drop shadow
                g2.setColor(new Color(0,0,0,40));
                g2.fill(new RoundRectangle2D.Float(6, 8, getWidth()-8, getHeight()-8, 18, 18));
                // Main surface
                g2.setColor(AppTheme.SURFACE);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth()-6, getHeight()-6, 18, 18));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        root.setOpaque(false);

        // Header bar
        JPanel header = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(AppTheme.PRIMARY);
                g2.fill(new RoundRectangle2D.Float(0, 0,
                        getWidth()-6, getHeight(), 18, 18));
                g2.fillRect(0, getHeight()/2, getWidth()-6, getHeight()/2);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        header.setOpaque(false);
        header.setPreferredSize(new Dimension(0, 56));
        header.setBorder(BorderFactory.createEmptyBorder(0, 22, 0, 14));

        JLabel lblTitle = new JLabel("Klaim Barang Ini");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setForeground(Color.WHITE);

        // Close X button
        JButton btnClose = new JButton("✕") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) {
                    g2.setColor(new Color(255,255,255,40));
                    g2.fill(new RoundRectangle2D.Float(0,0,getWidth(),getHeight(),8,8));
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnClose.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnClose.setForeground(Color.WHITE);
        btnClose.setOpaque(false);
        btnClose.setContentAreaFilled(false);
        btnClose.setBorderPainted(false);
        btnClose.setFocusPainted(false);
        btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnClose.setPreferredSize(new Dimension(36, 36));
        btnClose.addActionListener(e -> dispose());

        header.add(lblTitle, BorderLayout.WEST);
        header.add(btnClose, BorderLayout.EAST);

        // Content
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(20, 24, 22, 24));

        JLabel lblInfo = new JLabel("Pengajuan klaim akan dikirim ke admin untuk disetujui.");
        lblInfo.setFont(AppTheme.BODY_FONT);
        lblInfo.setForeground(AppTheme.TEXT_SECONDARY);
        lblInfo.setAlignmentX(LEFT_ALIGNMENT);
        content.add(lblInfo);
        content.add(Box.createVerticalStrut(20));

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnRow.setOpaque(false);
        btnRow.setAlignmentX(LEFT_ALIGNMENT);
        JButton btnCancel = AppButtonFactory.warning("Batal");
        JButton btnSubmit = AppButtonFactory.primary("Ajukan Klaim");
        btnRow.add(btnCancel);
        btnRow.add(btnSubmit);
        content.add(btnRow);

        root.add(header, BorderLayout.NORTH);
        root.add(content, BorderLayout.CENTER);
        setContentPane(root);

        btnCancel.addActionListener(e -> dispose());
        btnSubmit.addActionListener(e -> submitClaim());
        setVisible(true);
    }

    private void submitClaim() {
        try {
            // Cek duplikasi pending request
            if (controller.existsPendingRequest(idBarang, idUser)) {
                JOptionPane.showMessageDialog(this,
                        "Anda sudah mengajukan claim untuk barang ini dan masih menunggu persetujuan.");
                return;
            }

            ModelClaimRequest req = new ModelClaimRequest();
            req.setBarangId(idBarang);
            req.setRequesterUserId(idUser);
            req.setStatus("Pending");

            controller.insert(req);
            JOptionPane.showMessageDialog(this,
                    "Klaim berhasil diajukan! Silakan tunggu persetujuan admin.");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Gagal mengajukan klaim: " + e.getMessage());
        }
    }
}