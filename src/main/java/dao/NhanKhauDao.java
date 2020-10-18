package dao;

public interface NhanKhauDao<T> {
    boolean themNK(String maHoKhau, String quanHe, String hoTen, String ngaySinh, String gioiTinh, String tenGoiKhac, String queQuan, String danToc, String quocTich, String tonGiao, String ngheNghiep, String noiLamViec, String noiThuongTruTruoc);

}
