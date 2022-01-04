/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

/**
 *
 * @author Tu
 */
public class HocSinh {
    
    private String hoTenSV;
    private String maSV;
    private float diemToan;
    private float diemVan;
    private float diemAv;

    public HocSinh(String hoTenSV, String maSV, float diemToan, float diemVan, float diemAv) {
        this.hoTenSV = hoTenSV;
        this.maSV = maSV;
        this.diemToan = diemToan;
        this.diemVan = diemVan;
        this.diemAv = diemAv;
    }

    public String getHoTenSV() {
        return hoTenSV;
    }

    public void setHoTenSV(String hoTenSV) {
        this.hoTenSV = hoTenSV;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public float getDiemToan() {
        return diemToan;
    }

    public void setDiemToan(float diemToan) {
        this.diemToan = diemToan;
    }

    public float getDiemVan() {
        return diemVan;
    }

    public void setDiemVan(float diemVan) {
        this.diemVan = diemVan;
    }

    public float getDiemAv() {
        return diemAv;
    }

    public void setDiemAv(float diemAv) {
        this.diemAv = diemAv;
    }

   
    


    
    
}
