package model;

public class NgayThangNam {
    private String ngay, thang, nam;

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getThang() {
        return thang;
    }

    public void setThang(String thang) {
        this.thang = thang;
    }

    public String getNam() {
        return nam;
    }

    public void setNam(String nam) {
        this.nam = nam;
    }

    public NgayThangNam() {
    }

    public NgayThangNam(String ngay, String thang, String nam) {
        this.ngay = ngay;
        this.thang = thang;
        this.nam = nam;
    }
}
