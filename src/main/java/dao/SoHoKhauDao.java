package dao;

import java.util.List;

public interface SoHoKhauDao<T> {
    boolean DatLamChuHo(int id);

    boolean existSHK(String maHoKhau);

    List<T> getNhanKhauTrongHoById(String maHoKhau);

    String themSoHoKhau(String diaChi);

    void  updateDiaChi(String maHoKhau, String noiThuongTru);

    void setChuHo(String maHoKhau, String maNhanKhau);
}
