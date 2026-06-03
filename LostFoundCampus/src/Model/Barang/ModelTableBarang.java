/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Barang;

import java.util.List;
import javax.swing.table.AbstractTableModel;
/**
 *
 * @author Ivaa
 */
public class ModelTableBarang extends AbstractTableModel {
    private List<ModelBarang> listBarang;

    public ModelTableBarang(List<ModelBarang> listBarang){
        this.listBarang = listBarang;
    }
    
    String kolom[] = {
            "ID",
            "Nama Barang",
            "Kategori",
            "Lokasi",
            "Status",
            "Claim",
            "Request Claim",
            "Waktu"
    };

    @Override
    public int getRowCount() {
        return listBarang.size();
    }

    @Override
    public int getColumnCount() {
        return kolom.length;
    }
    
    @Override
    public String getColumnName(int column) {
        return kolom[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex){
        switch(columnIndex) {
            case 0: return listBarang.get(rowIndex).getId();
            case 1: return listBarang.get(rowIndex).getNamaBarang();
            case 2: return listBarang.get(rowIndex).getKategori();
            case 3: return listBarang.get(rowIndex).getLokasi();
            case 4: return listBarang.get(rowIndex).getStatus();
            case 5: return listBarang.get(rowIndex).getStatusClaim();
            case 6: return listBarang.get(rowIndex).getPendingClaimCount();
            case 7: return listBarang.get(rowIndex).getCreatedAt();
            default: return null;
        }
    } 
}