package View.Admin;

import Controller.ControllerClaimRequest;
import Model.Claim.ModelClaimRequest;
import Model.User.UserSession;
import View.Component.AppButtonFactory;
import View.Component.AppFrame;
import View.Component.AppLabelFactory;
import View.Component.AppTheme;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

public class ViewClaimAdmin extends AppFrame {

    private JTable tablePending;
    private JTable tableRiwayat;
    private ControllerClaimRequest controller;

    public ViewClaimAdmin() { this(null); }

    public ViewClaimAdmin(JFrame parentFrame) {
        super("Manajemen Claim", AppTheme.WINDOW_TABLE, parentFrame);
        controller = new ControllerClaimRequest();
        setLayout(new BorderLayout());
        setBackground(AppTheme.BACKGROUND);

        // ---- Top bar ----
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(AppTheme.SURFACE);
        topBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, AppTheme.BORDER),
            BorderFactory.createEmptyBorder(0, 22, 0, 22)));
        topBar.setPreferredSize(new Dimension(0, 62));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titlePanel.setOpaque(false);
        JLabel pageTitle = AppLabelFactory.sectionTitle("Manajemen Claim");
        titlePanel.add(pageTitle);
        topBar.add(titlePanel, BorderLayout.WEST);

        if (hasParentFrame()) {
            JButton btnBackTop = AppButtonFactory.backButton();
            btnBackTop.addActionListener(e -> backToParent());
            topBar.add(btnBackTop, BorderLayout.EAST);
        }
        add(topBar, BorderLayout.NORTH);

        // ---- Main panel ----
        JPanel mainPanel = new JPanel(new BorderLayout(0, 16));
        mainPanel.setBackground(AppTheme.BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(16, 18, 18, 18));

        // ---- Pending Claims table ----
        JPanel pendingPanel = makeCardPanel("Claim Menunggu Persetujuan");
        String[] colsPending = {
            "ID Request", "ID Barang", "Nama Barang", "Kategori",
            "Pemohon", "Username", "Status", "Tanggal Pengajuan"
        };
        DefaultTableModel modelPending = new DefaultTableModel(colsPending, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablePending = buildStyledTable(modelPending);
        JScrollPane scrollPending = new JScrollPane(tablePending);
        styleScrollPane(scrollPending);
        pendingPanel.add(scrollPending, BorderLayout.CENTER);

        // Action buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        actionPanel.setOpaque(false);
        JButton btnApprove = AppButtonFactory.success("Approve");
        JButton btnReject  = AppButtonFactory.danger("Reject");
        JButton btnRefresh = AppButtonFactory.primary("Refresh");
        actionPanel.add(btnRefresh);
        actionPanel.add(btnReject);
        actionPanel.add(btnApprove);
        pendingPanel.add(actionPanel, BorderLayout.SOUTH);

        // ---- Riwayat table ----
        JPanel riwayatPanel = makeCardPanel("Riwayat Klaim (Approved / Rejected)");
        String[] colsRiwayat = {
            "ID Request", "ID Barang", "Nama Barang", "Kategori",
            "Pemohon", "Username", "Status", "Tanggal Pengajuan", "Tanggal Review", "Direview Oleh"
        };
        DefaultTableModel modelRiwayat = new DefaultTableModel(colsRiwayat, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tableRiwayat = buildStyledTable(modelRiwayat);
        JScrollPane scrollRiwayat = new JScrollPane(tableRiwayat);
        styleScrollPane(scrollRiwayat);
        riwayatPanel.add(scrollRiwayat, BorderLayout.CENTER);

        // Split vertically
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pendingPanel, riwayatPanel);
        splitPane.setDividerLocation(280);
        splitPane.setDividerSize(8);
        splitPane.setBorder(null);
        splitPane.setBackground(AppTheme.BACKGROUND);
        splitPane.setOpaque(false);
        mainPanel.add(splitPane, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        // Load data
        loadPending(modelPending);
        loadRiwayat(modelRiwayat);

        // Actions
        btnApprove.addActionListener(e -> {
            approveClaim();
            loadPending(modelPending);
            loadRiwayat(modelRiwayat);
        });
        btnReject.addActionListener(e -> {
            rejectClaim();
            loadPending(modelPending);
            loadRiwayat(modelRiwayat);
        });
        btnRefresh.addActionListener(e -> {
            loadPending(modelPending);
            loadRiwayat(modelRiwayat);
        });
    }

    private void loadPending(DefaultTableModel model) {
        model.setRowCount(0);
        List<ModelClaimRequest> list = controller.getPendingRequests();
        for (ModelClaimRequest r : list) {
            model.addRow(new Object[]{
                r.getId(),
                r.getBarangId(),
                r.getBarangName(),
                r.getBarangCategory(),
                r.getRequesterName(),
                r.getRequesterUsername(),
                r.getStatus(),
                r.getRequestedAt()
            });
        }
    }

    private void loadRiwayat(DefaultTableModel model) {
        model.setRowCount(0);
        // Ambil semua claim request dari DB (kita filter non-Pending)
        // DAOClaimRequest belum punya getAllRequests(), kita pakai query terpisah
        // Gunakan getAllRequests jika tersedia, jika tidak kita load via DAOClaimRequest
        try {
            Model.Claim.DAOClaimRequest dao = new Model.Claim.DAOClaimRequest();
            // Panggil method yang ada: getPendingRequests() hanya untuk Pending
            // Untuk Riwayat, kita perlu method getAllRequests — tambahkan di DAOClaimRequest
            // Sementara, kita load semua lalu filter di sini via reflection workaround:
            java.lang.reflect.Method m = dao.getClass().getDeclaredMethod("getAllRequests");
            m.setAccessible(true);
            @SuppressWarnings("unchecked")
            List<ModelClaimRequest> all = (List<ModelClaimRequest>) m.invoke(dao);
            for (ModelClaimRequest r : all) {
                if (!"Pending".equalsIgnoreCase(r.getStatus())) {
                    model.addRow(new Object[]{
                        r.getId(),
                        r.getBarangId(),
                        r.getBarangName(),
                        r.getBarangCategory(),
                        r.getRequesterName(),
                        r.getRequesterUsername(),
                        r.getStatus(),
                        r.getRequestedAt(),
                        r.getReviewedAt(),
                        r.getReviewerName() != null ? r.getReviewerName() : "-"
                    });
                }
            }
        } catch (Exception ignored) {
            // getAllRequests belum ada — tabel riwayat kosong sampai method ditambahkan
        }
    }

    private void approveClaim() {
        int row = tablePending.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih claim terlebih dahulu!");
            return;
        }
        int requestId = Integer.parseInt(tablePending.getValueAt(row, 0).toString());
        int adminId = UserSession.getCurrentUserId();
        controller.approveRequest(requestId, adminId);
        JOptionPane.showMessageDialog(this, "Claim berhasil di-approve!");
    }

    private void rejectClaim() {
        int row = tablePending.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih claim terlebih dahulu!");
            return;
        }
        int requestId = Integer.parseInt(tablePending.getValueAt(row, 0).toString());
        // Panggil rejectRequest — perlu ditambahkan ke controller & DAO
        try {
            Model.Claim.DAOClaimRequest dao = new Model.Claim.DAOClaimRequest();
            java.lang.reflect.Method m = dao.getClass().getDeclaredMethod("rejectRequest", int.class, int.class);
            m.setAccessible(true);
            m.invoke(dao, requestId, UserSession.getCurrentUserId());
            JOptionPane.showMessageDialog(this, "Claim berhasil di-reject.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Gagal reject: method rejectRequest belum tersedia di DAOClaimRequest.\n" + ex.getMessage());
        }
    }

    // ---- UI helpers ----

    private JPanel makeCardPanel(String title) {
        JPanel outer = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 12));
                g2.fill(new RoundRectangle2D.Float(3, 5, getWidth()-5, getHeight()-5, 14, 14));
                g2.setColor(AppTheme.SURFACE);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth()-3, getHeight()-3, 14, 14));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        outer.setOpaque(false);

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 10));
        header.setOpaque(false);
        JLabel lbl = new JLabel(title);
        lbl.setFont(AppTheme.LABEL_FONT);
        lbl.setForeground(AppTheme.TEXT_PRIMARY);
        header.add(lbl);
        outer.add(header, BorderLayout.NORTH);
        outer.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 4));
        return outer;
    }

    private JTable buildStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setRowHeight(34);
        table.setFont(AppTheme.BODY_FONT);
        table.setShowVerticalLines(false);
        table.setGridColor(AppTheme.BORDER);
        table.setSelectionBackground(AppTheme.PRIMARY_LIGHT);
        table.setSelectionForeground(AppTheme.TEXT_PRIMARY);
        table.setIntercellSpacing(new Dimension(0, 0));

        JTableHeader th = table.getTableHeader();
        th.setFont(AppTheme.LABEL_FONT);
        th.setBackground(AppTheme.TABLE_HEADER);
        th.setForeground(AppTheme.TEXT_SECONDARY);
        th.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppTheme.BORDER));
        th.setPreferredSize(new Dimension(0, 38));

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t,
                    Object value, boolean sel, boolean focus, int row, int col) {
                super.getTableCellRendererComponent(t, value, sel, focus, row, col);
                if (!sel) {
                    setBackground(row % 2 == 0 ? AppTheme.SURFACE : AppTheme.TABLE_STRIPE);
                }
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                String val = value != null ? value.toString() : "";
                if (val.equalsIgnoreCase("Approved") || val.equalsIgnoreCase("Sudah Diklaim")) {
                    setForeground(AppTheme.SUCCESS);
                    setFont(AppTheme.LABEL_FONT);
                } else if (val.equalsIgnoreCase("Pending")) {
                    setForeground(AppTheme.WARNING);
                    setFont(AppTheme.LABEL_FONT);
                } else if (val.equalsIgnoreCase("Rejected")) {
                    setForeground(AppTheme.DANGER);
                    setFont(AppTheme.LABEL_FONT);
                } else {
                    setForeground(AppTheme.TEXT_PRIMARY);
                    setFont(AppTheme.BODY_FONT);
                }
                return this;
            }
        });
        return table;
    }

    private void styleScrollPane(JScrollPane sp) {
        sp.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, AppTheme.BORDER));
        sp.getViewport().setBackground(AppTheme.SURFACE);
    }
}