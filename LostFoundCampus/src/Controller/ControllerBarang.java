/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Barang.*;
import java.util.List;
/**
 *
 * @author karina
 */
public class ControllerBarang {
    DAOBarang daoBarang;

    public ControllerBarang(){
        daoBarang = new DAOBarang();
    }

    public void insert(ModelBarang barang){
        daoBarang.insert(barang);
    }

    public void update(ModelBarang barang){
        daoBarang.update(barang);
    }

    public void delete(int id){
        daoBarang.delete(id);
    }

    public List<ModelBarang> getAll(){
        return daoBarang.getAll();
    }

    public List<ModelBarang> getAllByUserId(int userId) {
        return daoBarang.getAllByUserId(userId);
    }

    public List<ModelBarang> search(String keyword){
        return daoBarang.search(keyword);
    }

    public List<ModelBarang> searchByUserId(int userId, String keyword) {
        return daoBarang.searchByUserId(userId, keyword);
    }
    
    public ModelBarang getById(int id){
        return daoBarang.getById(id);
    }
    
    public int getTotalBarang(){
        return daoBarang.getTotalBarang();
    }
    
    public int getTotalByStatus(String status){
        return daoBarang.getTotalByStatus(status);
    }
    
    public List<ModelBarang> getReturnedBarang(){
        return daoBarang.getReturnedBarang();
    }
    
    // Total barang yang dimiliki/dilaporkan oleh user tertentu
    public int getTotalByUserId(int userId) {
        return daoBarang.getTotalByUserId(userId);
    }
 
    // Total barang milik user berdasarkan status (Hilang/Ditemukan)
    public int getTotalByUserIdAndStatus(int userId, String status) {
        return daoBarang.getTotalByUserIdAndStatus(userId, status);
    }
 
    // Total barang yang berhasil diklaim oleh user (sebagai claimer)
    public int getTotalApprovedClaimByUserId(int userId) {
        return daoBarang.getTotalApprovedClaimByUserId(userId);
    }
 
    // Total barang berdasarkan status_claim (global, untuk admin)
    public int getTotalByStatusClaim(String statusClaim) {
        return daoBarang.getTotalByStatusClaim(statusClaim);
    }
}