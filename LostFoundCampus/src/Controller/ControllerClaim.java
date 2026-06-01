/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Claim.DAOClaim;
import Model.Claim.ModelClaim;
import java.util.List;
/**
 *
 * @author karina
 */
public class ControllerClaim {

    private DAOClaim daoClaim;

    public ControllerClaim() {
        daoClaim = new DAOClaim();
    }

    public void submitClaim(int idBarang, int idUser, String alasan) {

        if(alasan.isEmpty()){
            throw new IllegalArgumentException(
                    "Alasan claim wajib diisi!"
            );
        }

        ModelClaim claim = new ModelClaim();
        claim.setIdBarang(idBarang);
        claim.setIdUser(idUser);
        claim.setAlasanClaim(alasan);
        daoClaim.insertClaim(claim);
    }
    
    public List<ModelClaim> getAllClaim(){
        return daoClaim.getAllClaim();
    }
    
    public void approveClaim(int idClaim){
        daoClaim.approveClaim(idClaim);
    }
    
    public void rejectClaim(int idClaim){
        daoClaim.rejectClaim(idClaim);
    }
}
