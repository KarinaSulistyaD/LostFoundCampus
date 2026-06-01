package Model.Claim;

import Model.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOClaimRequest implements InterfaceDAOClaimRequest {

    private final Connection connection;

    public DAOClaimRequest() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public void insert(ModelClaimRequest claimRequest) {
        String query = "INSERT INTO claim_requests (barang_id, "
                + "requester_user_id, status) VALUES (?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, claimRequest.getBarangId());
            ps.setInt(2, claimRequest.getRequesterUserId());
            ps.setString(3, claimRequest.getStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean existsPendingRequest(int barangId, int requesterUserId) {
        String query = "SELECT 1 FROM claim_requests WHERE barang_id = ? "
                + "AND requester_user_id = ? AND status = 'Pending'";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, barangId);
            ps.setInt(2, requesterUserId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public List<ModelClaimRequest> getPendingRequests() {
        return getRequestsByQuery(
            "SELECT cr.id, cr.barang_id, cr.requester_user_id, cr.status, cr.requested_at, cr.reviewed_at, "
                + "cr.reviewed_by_user_id, b.nama_barang, b.kategori, u.nama AS requester_nama, "
                + "u.username AS requester_username, reviewer.nama AS reviewer_nama "
                + "FROM claim_requests cr "
                + "JOIN barang b ON b.id = cr.barang_id "
                + "JOIN users u ON u.id = cr.requester_user_id "
                + "LEFT JOIN users reviewer ON reviewer.id = cr.reviewed_by_user_id "
                + "WHERE cr.status = 'Pending' ORDER BY cr.requested_at ASC"
        );
    }

    /**
     * Mengambil SEMUA claim request (Pending, Approved, Rejected).
     * Digunakan oleh ViewClaimAdmin untuk menampilkan riwayat.
     */
    public List<ModelClaimRequest> getAllRequests() {
        return getRequestsByQuery(
            "SELECT cr.id, cr.barang_id, cr.requester_user_id, cr.status, cr.requested_at, cr.reviewed_at, "
                + "cr.reviewed_by_user_id, b.nama_barang, b.kategori, u.nama AS requester_nama, "
                + "u.username AS requester_username, reviewer.nama AS reviewer_nama "
                + "FROM claim_requests cr "
                + "JOIN barang b ON b.id = cr.barang_id "
                + "JOIN users u ON u.id = cr.requester_user_id "
                + "LEFT JOIN users reviewer ON reviewer.id = cr.reviewed_by_user_id "
                + "ORDER BY cr.requested_at DESC"
        );
    }

    @Override
    public List<ModelClaimRequest> getPendingRequestsByBarang(int barangId) {
        String query =
            "SELECT cr.id, cr.barang_id, cr.requester_user_id, cr.status, cr.requested_at, cr.reviewed_at, "
                + "cr.reviewed_by_user_id, b.nama_barang, b.kategori, u.nama AS requester_nama, "
                + "u.username AS requester_username, reviewer.nama AS reviewer_nama "
                + "FROM claim_requests cr "
                + "JOIN barang b ON b.id = cr.barang_id "
                + "JOIN users u ON u.id = cr.requester_user_id "
                + "LEFT JOIN users reviewer ON reviewer.id = cr.reviewed_by_user_id "
                + "WHERE cr.status = 'Pending' AND cr.barang_id = ? ORDER BY cr.requested_at ASC";

        List<ModelClaimRequest> requests = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, barangId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                requests.add(mapRequest(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return requests;
    }

    @Override
    public void approveRequest(int requestId, int reviewedByUserId) {
        try {
            connection.setAutoCommit(false);

            int barangId = 0;
            int requesterUserId = 0;
            String selectRequest = "SELECT barang_id, requester_user_id FROM claim_requests WHERE id = ? AND status = 'Pending'";
            try (PreparedStatement ps = connection.prepareStatement(selectRequest)) {
                ps.setInt(1, requestId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    barangId = rs.getInt("barang_id");
                    requesterUserId = rs.getInt("requester_user_id");
                }
            }

            if (barangId == 0 || requesterUserId == 0) {
                connection.rollback();
                return;
            }

            // Update status barang
            approveBarangClaim(barangId, requesterUserId);

            // Approve request yang dipilih
            try (PreparedStatement ps = connection.prepareStatement(
                "UPDATE claim_requests SET status = 'Approved', reviewed_by_user_id = ?, reviewed_at = CURRENT_TIMESTAMP WHERE id = ?"
            )) {
                ps.setInt(1, reviewedByUserId);
                ps.setInt(2, requestId);
                ps.executeUpdate();
            }

            // Tolak semua request lain untuk barang yang sama
            try (PreparedStatement ps = connection.prepareStatement(
                "UPDATE claim_requests SET status = 'Rejected', reviewed_by_user_id = ?, reviewed_at = CURRENT_TIMESTAMP "
                    + "WHERE barang_id = ? AND status = 'Pending' AND id <> ?"
            )) {
                ps.setInt(1, reviewedByUserId);
                ps.setInt(2, barangId);
                ps.setInt(3, requestId);
                ps.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                System.out.println(rollbackException.getMessage());
            }
            System.out.println(e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Menolak sebuah claim request tanpa memengaruhi request lain.
     * Digunakan oleh admin dari ViewClaimAdmin.
     */
    public void rejectRequest(int requestId, int reviewedByUserId) {
        String query = "UPDATE claim_requests SET status = 'Rejected', "
                + "reviewed_by_user_id = ?, reviewed_at = CURRENT_TIMESTAMP "
                + "WHERE id = ? AND status = 'Pending'";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, reviewedByUserId);
            ps.setInt(2, requestId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void manualClaim(int barangId, int requesterUserId, int reviewedByUserId) {
        try {
            connection.setAutoCommit(false);

            try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO claim_requests (barang_id, requester_user_id, status, reviewed_by_user_id, reviewed_at) VALUES (?, ?, 'Approved', ?, CURRENT_TIMESTAMP)"
            )) {
                ps.setInt(1, barangId);
                ps.setInt(2, requesterUserId);
                ps.setInt(3, reviewedByUserId);
                ps.executeUpdate();
            }

            approveBarangClaim(barangId, requesterUserId);

            try (PreparedStatement ps = connection.prepareStatement(
                "UPDATE claim_requests SET status = 'Rejected', reviewed_by_user_id = ?, reviewed_at = CURRENT_TIMESTAMP "
                    + "WHERE barang_id = ? AND status = 'Pending'"
            )) {
                ps.setInt(1, reviewedByUserId);
                ps.setInt(2, barangId);
                ps.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                System.out.println(rollbackException.getMessage());
            }
            System.out.println(e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void approveBarangClaim(int barangId, int requesterUserId) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
            "UPDATE barang SET status_claim = 'Sudah Diklaim', claimed_by_user_id = ? WHERE id = ?"
        )) {
            ps.setInt(1, requesterUserId);
            ps.setInt(2, barangId);
            ps.executeUpdate();
        }
    }

    private List<ModelClaimRequest> getRequestsByQuery(String query) {
        List<ModelClaimRequest> requests = new ArrayList<>();

        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                requests.add(mapRequest(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return requests;
    }

    private ModelClaimRequest mapRequest(ResultSet rs) throws SQLException {
        ModelClaimRequest request = new ModelClaimRequest();
        request.setId(rs.getInt("id"));
        request.setBarangId(rs.getInt("barang_id"));
        request.setRequesterUserId(rs.getInt("requester_user_id"));
        request.setStatus(rs.getString("status"));
        request.setRequestedAt(rs.getString("requested_at"));
        request.setReviewedAt(rs.getString("reviewed_at"));
        request.setReviewedByUserId(rs.getInt("reviewed_by_user_id"));
        request.setBarangName(rs.getString("nama_barang"));
        request.setBarangCategory(rs.getString("kategori"));
        request.setRequesterName(rs.getString("requester_nama"));
        request.setRequesterUsername(rs.getString("requester_username"));
        request.setReviewerName(rs.getString("reviewer_nama"));
        return request;
    }
}