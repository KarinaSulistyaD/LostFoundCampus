/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Claim;

/**
 *
 * @author karina
 */

public class ModelClaim {

    private int idClaim;
    private int idBarang;
    private int idUser;
    private String alasanClaim;
    private String statusClaim;
    private String tanggalClaim;
    
    public int getIdClaim() {
        return idClaim;
    }

    public void setIdClaim(int idClaim) {
        this.idClaim = idClaim;
    }

    public int getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(int idBarang) {
        this.idBarang = idBarang;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getAlasanClaim() {
        return alasanClaim;
    }

    public void setAlasanClaim(String alasanClaim) {
        this.alasanClaim = alasanClaim;
    }

    public String getStatusClaim() {
        return statusClaim;
    }

    public void setStatusClaim(String statusClaim) {
        this.statusClaim = statusClaim;
    }

    public String getTanggalClaim() {
        return tanggalClaim;
    }

    public void setTanggalClaim(String tanggalClaim) {
        this.tanggalClaim = tanggalClaim;
    }
}
