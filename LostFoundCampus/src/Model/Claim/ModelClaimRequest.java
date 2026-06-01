package Model.Claim;

public class ModelClaimRequest {

    private int id;
    private int barangId;
    private int requesterUserId;
    private String status;
    private String requestedAt;
    private String reviewedAt;
    private int reviewedByUserId;
    private String barangName;
    private String barangCategory;
    private String requesterName;
    private String requesterUsername;
    private String reviewerName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBarangId() {
        return barangId;
    }

    public void setBarangId(int barangId) {
        this.barangId = barangId;
    }

    public int getRequesterUserId() {
        return requesterUserId;
    }

    public void setRequesterUserId(int requesterUserId) {
        this.requesterUserId = requesterUserId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(String requestedAt) {
        this.requestedAt = requestedAt;
    }

    public String getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(String reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public int getReviewedByUserId() {
        return reviewedByUserId;
    }

    public void setReviewedByUserId(int reviewedByUserId) {
        this.reviewedByUserId = reviewedByUserId;
    }

    public String getBarangName() {
        return barangName;
    }

    public void setBarangName(String barangName) {
        this.barangName = barangName;
    }

    public String getBarangCategory() {
        return barangCategory;
    }

    public void setBarangCategory(String barangCategory) {
        this.barangCategory = barangCategory;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public String getRequesterUsername() {
        return requesterUsername;
    }

    public void setRequesterUsername(String requesterUsername) {
        this.requesterUsername = requesterUsername;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }
}
