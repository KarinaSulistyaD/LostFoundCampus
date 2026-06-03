package View.Admin;

import Controller.ControllerBarang;
import Model.Barang.ModelBarang;
import View.Component.AppButtonFactory;
import View.Component.AppFrame;
import View.Component.AppLabelFactory;
import View.Component.AppTheme;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class EditBarang extends AppFrame {

    public EditBarang() {
        this(null);
    }

    public EditBarang(JFrame parentFrame) {
        this(parentFrame, null);
    }

    public EditBarang(JFrame parentFrame, ModelBarang barang) {
        super("Edit Barang", new Dimension(540, 620), parentFrame);
        setLayout(new BorderLayout());
        setBackground(AppTheme.BACKGROUND);

        // ---- Top bar ----
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(AppTheme.SURFACE);
        topBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, AppTheme.BORDER),
            BorderFactory.createEmptyBorder(0, 22, 0, 22)));
        topBar.setPreferredSize(new Dimension(0, 62));
        topBar.add(AppLabelFactory.sectionTitle("Edit Barang"), BorderLayout.WEST);
        if (hasParentFrame()) {
            JButton btnBackTop = AppButtonFactory.backButton();
            btnBackTop.addActionListener(e -> backToParent());
            JPanel backWrapper = new JPanel();
            backWrapper.setLayout(new BoxLayout(backWrapper, BoxLayout.Y_AXIS));
            backWrapper.setOpaque(false);
            backWrapper.add(Box.createVerticalGlue());      
            btnBackTop.setAlignmentX(Component.CENTER_ALIGNMENT);
            backWrapper.add(btnBackTop);                    
            backWrapper.add(Box.createVerticalGlue());
            topBar.add(backWrapper, BorderLayout.EAST);
        }
        add(topBar, BorderLayout.NORTH);

        // ---- Form Panel (null layout dengan preferred size eksplisit) ----
        // Total tinggi: tombol bawah y=460 + h=42 + padding 20 = 522
        JPanel formPanel = new JPanel(null) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(500, 530);
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(AppTheme.SURFACE);
                g2.fill(new RoundRectangle2D.Float(16, 12, getWidth() - 32, getHeight() - 24, 14, 14));
                g2.setColor(AppTheme.BORDER);
                g2.setStroke(new java.awt.BasicStroke(1));
                g2.draw(new RoundRectangle2D.Float(16.5f, 12.5f, getWidth() - 33, getHeight() - 25, 14, 14));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        formPanel.setOpaque(false);

        // Field: Nama Barang
        JLabel lblNama = makeLabel("Nama Barang");
        lblNama.setBounds(40, 30, 200, 22);
        JTextField txtNama = makeTextField();
        txtNama.setBounds(40, 54, 420, 36);
        formPanel.add(lblNama);
        formPanel.add(txtNama);

        // Field: Kategori
        JLabel lblKategori = makeLabel("Kategori");
        lblKategori.setBounds(40, 102, 200, 22);
        String[] kategoriOptions = {"Elektronik", "Pakaian", "Aksesoris", "Dokumen", "Tas", "Lainnya"};
        JComboBox<String> cmbKategori = makeComboBox(kategoriOptions);
        cmbKategori.setBounds(40, 126, 420, 36);
        formPanel.add(lblKategori);
        formPanel.add(cmbKategori);

        // Field: Lokasi
        JLabel lblLokasi = makeLabel("Lokasi");
        lblLokasi.setBounds(40, 174, 200, 22);
        JTextField txtLokasi = makeTextField();
        txtLokasi.setBounds(40, 198, 420, 36);
        formPanel.add(lblLokasi);
        formPanel.add(txtLokasi);

        // Field: Status Barang
        JLabel lblStatus = makeLabel("Status Barang");
        lblStatus.setBounds(40, 246, 200, 22);
        String[] statusOptions = {"Hilang", "Ditemukan"};
        JComboBox<String> cmbStatus = makeComboBox(statusOptions);
        cmbStatus.setBounds(40, 270, 195, 36);
        formPanel.add(lblStatus);
        formPanel.add(cmbStatus);

        // Field: Status Claim
        JLabel lblStatusClaim = makeLabel("Status Claim");
        lblStatusClaim.setBounds(265, 246, 200, 22);
        String[] statusClaimOptions = {"Belum Diklaim", "Pending", "Sudah Diklaim", "Sudah Ditemukan"};
        JComboBox<String> cmbStatusClaim = makeComboBox(statusClaimOptions);
        cmbStatusClaim.setBounds(265, 270, 195, 36);
        formPanel.add(lblStatusClaim);
        formPanel.add(cmbStatusClaim);

        // Field: Deskripsi
        JLabel lblDeskripsi = makeLabel("Deskripsi");
        lblDeskripsi.setBounds(40, 318, 200, 22);
        JTextArea txtDeskripsi = new JTextArea();
        txtDeskripsi.setFont(AppTheme.BODY_FONT);
        txtDeskripsi.setForeground(AppTheme.TEXT_PRIMARY);
        txtDeskripsi.setLineWrap(true);
        txtDeskripsi.setWrapStyleWord(true);
        JScrollPane scrollDeskripsi = new JScrollPane(txtDeskripsi);
        scrollDeskripsi.setBounds(40, 342, 420, 90);
        scrollDeskripsi.setBorder(BorderFactory.createLineBorder(AppTheme.BORDER));
        formPanel.add(lblDeskripsi);
        formPanel.add(scrollDeskripsi);

        // ---- Tombol Simpan & Batal ----
        JButton btnSimpan = AppButtonFactory.primary("💾  Simpan Perubahan");
        btnSimpan.setBounds(40, 452, 200, 42);
        formPanel.add(btnSimpan);

        JButton btnBatal = AppButtonFactory.danger("Batal");
        btnBatal.setBounds(265, 452, 195, 42);
        formPanel.add(btnBatal);

        // ---- Isi form jika data barang tersedia ----
        if (barang != null) {
            txtNama.setText(barang.getNamaBarang());
            txtLokasi.setText(barang.getLokasi());
            txtDeskripsi.setText(barang.getDeskripsi());
            cmbKategori.setSelectedItem(barang.getKategori());
            cmbStatus.setSelectedItem(barang.getStatus());
            cmbStatusClaim.setSelectedItem(barang.getStatusClaim());
        }

        // Bungkus formPanel dalam JScrollPane agar aman di layar kecil
        JScrollPane scrollForm = new JScrollPane(formPanel);
        scrollForm.setBorder(BorderFactory.createEmptyBorder());
        scrollForm.getViewport().setBackground(AppTheme.BACKGROUND);
        scrollForm.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(AppTheme.BACKGROUND);
        wrapper.setBorder(BorderFactory.createEmptyBorder(8, 12, 12, 12));
        wrapper.add(scrollForm, BorderLayout.CENTER);
        add(wrapper, BorderLayout.CENTER);

        // ---- Action Listeners ----
        btnBatal.addActionListener(e -> backToParent());

        btnSimpan.addActionListener(e -> {
            String nama      = txtNama.getText().trim();
            String lokasi    = txtLokasi.getText().trim();
            String deskripsi = txtDeskripsi.getText().trim();
            String kategori  = (String) cmbKategori.getSelectedItem();
            String status    = (String) cmbStatus.getSelectedItem();
            String statusClaim = (String) cmbStatusClaim.getSelectedItem();

            if (nama.isEmpty() || lokasi.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Nama Barang dan Lokasi wajib diisi!",
                    "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
                return;
            }

            ModelBarang updated = barang != null ? barang : new ModelBarang();
            updated.setNamaBarang(nama);
            updated.setKategori(kategori);
            updated.setLokasi(lokasi);
            updated.setDeskripsi(deskripsi);
            updated.setStatus(status);
            updated.setStatusClaim(statusClaim);

            new ControllerBarang().update(updated);
            JOptionPane.showMessageDialog(this,
                "Data barang berhasil diperbarui!",
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
            backToParent();
        });
    }

    // ---- Helper UI ----
    private JLabel makeLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(AppTheme.LABEL_FONT);
        lbl.setForeground(AppTheme.TEXT_PRIMARY);
        return lbl;
    }

    private JTextField makeTextField() {
        JTextField tf = new JTextField();
        tf.setFont(AppTheme.BODY_FONT);
        tf.setForeground(AppTheme.TEXT_PRIMARY);
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(AppTheme.BORDER),
            BorderFactory.createEmptyBorder(4, 10, 4, 10)));
        return tf;
    }

    private JComboBox<String> makeComboBox(String[] items) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setFont(AppTheme.BODY_FONT);
        cb.setForeground(AppTheme.TEXT_PRIMARY);
        cb.setBackground(AppTheme.SURFACE);
        return cb;
    }
}
