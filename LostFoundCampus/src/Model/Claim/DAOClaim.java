/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Claim;

import Model.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author karina
 */
public class DAOClaim {

    Connection connection;

    public DAOClaim() {
        connection = DatabaseConnection.getConnection();
    }

    public void insertClaim(ModelClaim claim) {

        try {
            String query =
                    "INSERT INTO claim_barang("
                    + "id_barang,"
                    + "id_user,"
                    + "alasan_claim,"
                    + "status_claim"
                    + ") VALUES (?,?,?,?)";

            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setInt(1, claim.getIdBarang());
            stmt.setInt(2, claim.getIdUser());
            stmt.setString(3, claim.getAlasanClaim());
            stmt.setString(4, "Pending");
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<ModelClaim> getAllClaim(){

    List<ModelClaim> list = new ArrayList<>();
        try {
            String query = "SELECT * FROM claim_barang";

            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                ModelClaim claim = new ModelClaim();

                claim.setIdClaim(
                        rs.getInt("id_claim")
                );

                claim.setIdBarang(
                        rs.getInt("id_barang")
                );

                claim.setIdUser(
                        rs.getInt("id_user")
                );

                claim.setAlasanClaim(
                        rs.getString("alasan_claim")
                );

                claim.setStatusClaim(
                        rs.getString("status_claim")
                );

                claim.setTanggalClaim(
                        rs.getString("tanggal_claim")
                );
                list.add(claim);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return list;
    }
    
    public void approveClaim(int idClaim){

        try {
            // UPDATE CLAIM STATUS
            String query1 =
                    "UPDATE claim_barang "
                  + "SET status_claim='Approved' "
                  + "WHERE id_claim=?";

            PreparedStatement stmt1 = connection.prepareStatement(query1);

            stmt1.setInt(1, idClaim);
            stmt1.executeUpdate();

            // UPDATE BARANG STATUS
            String query2 =
                    "UPDATE barang "
                  + "SET status='Returned' "
                  + "WHERE id IN ("
                  + "SELECT id_barang "
                  + "FROM claim_barang "
                  + "WHERE id_claim=?"
                  + ")";

            PreparedStatement stmt2 = connection.prepareStatement(query2);

            stmt2.setInt(1, idClaim);
            stmt2.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void rejectClaim(int idClaim){
        try {

            String query =
                    "UPDATE claim_barang "
                  + "SET status_claim='Rejected' "
                  + "WHERE id_claim=?";

            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setInt(1, idClaim);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
