package View.User;

import Controller.ControllerClaimRequest;
import Model.Claim.ModelClaimRequest;
import View.Component.AppButtonFactory;
import View.Component.AppTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class ModalClaimBarang extends JDialog {

    private final ControllerClaimRequest controller;
    private final int idBarang;
    private final int idUser;

    public ModalClaimBarang(JFrame parent, int idBarang, int idUser) {
        super(parent, "Claim Barang", true);
        this.idBarang   = idBarang;
        this.idUser     = idUser;
        this.controller = new ControllerClaimRequest();
        initComponents();
    }

    private void initComponents() {
        setSize(440, 210);
        setLocationRelativeTo(getParent());
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));

        // Root panel (rounded card + drop shadow)
        JPanel root = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // shadow
                g2.setColor(new Color(0, 0, 0, 35));
                g2.fill(new RoundRectangle2D.Float(5, 7, getWidth() - 7, getHeight() - 7, 16, 16));
                // surface
                g2.setColor(AppTheme.SURFACE);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 5, getHeight() - 5, 16, 16));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        root.setOpaque(false);

        // Header
        JPanel header = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(AppTheme.PRIMARY);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 5, getHeight(), 16, 16));
                g2.fillRect(0, getHeight() / 2, getWidth() - 5, getHeight() / 2);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        header.setOpaque(false);
        header.setPreferredSize(new Dimension(0, 48));
        header.setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 10));

        // Judul
        JLabel lblTitle = new JLabel("  Konfirmasi Klaim Barang");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitle.setForeground(Color.WHITE);

        // Tombol tutup (✕)
        JButton btnClose = new JButton("✕") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) {
                    g2.setColor(new Color(255, 255, 255, 45));
                    g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnClose.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnClose.setForeground(Color.WHITE);
        btnClose.setOpaque(false);
        btnClose.setContentAreaFilled(false);
        btnClose.setBorderPainted(false);
        btnClose.setFocusPainted(false);
        btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnClose.setPreferredSize(new Dimension(32, 32));
        btnClose.addActionListener(e -> dispose());

        header.add(lblTitle,  BorderLayout.WEST);
        header.add(btnClose,  BorderLayout.EAST);

        // Body
        JPanel body = new JPanel(new BorderLayout(0, 14));
        body.setOpaque(false);
        body.setBorder(BorderFactory.createEmptyBorder(16, 18, 16, 18));

        // Info box (rounded, warna soft)
        JPanel infoBox = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(AppTheme.PRIMARY_LIGHT);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        infoBox.setOpaque(false);
        infoBox.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));

        JLabel lblInfo = new JLabel(
                "<html>Pengajuan klaim akan dikirim ke <b>admin</b> untuk ditinjau.<br>"
                + "Pastikan barang ini benar-benar milik Anda sebelum mengajukan.</html>"
        );
        lblInfo.setFont(AppTheme.BODY_FONT);
        lblInfo.setForeground(AppTheme.PRIMARY);

        infoBox.add(lblInfo, BorderLayout.CENTER);

        // Tombol aksi
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btnRow.setOpaque(false);

        JButton btnCancel = AppButtonFactory.warning("Batal");
        JButton btnSubmit = AppButtonFactory.primary("Ajukan Klaim");
        btnCancel.setPreferredSize(new Dimension(100, 36));
        btnSubmit.setPreferredSize(new Dimension(130, 36));

        btnRow.add(btnCancel);
        btnRow.add(btnSubmit);

        body.add(infoBox,  BorderLayout.CENTER);
        body.add(btnRow,   BorderLayout.SOUTH);

        root.add(header, BorderLayout.NORTH);
        root.add(body,   BorderLayout.CENTER);
        setContentPane(root);

        btnCancel.addActionListener(e -> dispose());
        btnSubmit.addActionListener(e -> submitClaim());
        setVisible(true);
    }

    private void submitClaim() {
        try {
            if (controller.existsPendingRequest(idBarang, idUser)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Anda sudah mengajukan klaim untuk barang ini "
                                + "dan masih menunggu persetujuan.");
                return;
            }

            ModelClaimRequest req = new ModelClaimRequest();
            req.setBarangId(idBarang);
            req.setRequesterUserId(idUser);
            req.setStatus("Pending");

            controller.insert(req);
            JOptionPane.showMessageDialog(this,
                    "Klaim berhasil diajukan! Silakan tunggu persetujuan admin."
            );
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Gagal mengajukan klaim: " + e.getMessage());
        }
    }
}
