package dao.imp;

import conn.Conn;
import dao.BaseDao;
import dao.NhanKhauDao;
import model.NhanKhau;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class NhanKhauDAO implements BaseDao<NhanKhau>, NhanKhauDao {
    @Override
    public NhanKhau get(long id) {
        return null;
    }

    @Override
    public List<NhanKhau> getAll() {
        List<NhanKhau> res=new ArrayList<>();
        try {
            Connection cnt= Conn.getConnection();
            Statement stm = cnt.createStatement();
            ResultSet rs = stm.executeQuery("select * from NhanKhau");
            while(rs.next()){
                NhanKhau nhanKhau = new NhanKhau();
                nhanKhau.setMaNhanKhau(rs.getString("Id_NK"));
                nhanKhau.setCmnd(rs.getString("CCCD"));
                nhanKhau.setMaHoKhau(rs.getString("Id_SHK"));
                nhanKhau.setHoTen(rs.getString("HoTen"));
                nhanKhau.setNgaySinh(rs.getString("NgaySinh"));
                nhanKhau.setGioiTinh("null");
                nhanKhau.setTenGoiKhac(rs.getString("BiDanh"));
                nhanKhau.setQueQuan(rs.getString("NguyenQuan"));
                nhanKhau.setDanToc(rs.getString("DanToc"));
                nhanKhau.setQuocTich("null");
                nhanKhau.setNgheNghiep(rs.getString("NgheNghiep"));
                nhanKhau.setNoiLamViec(rs.getString("NoiLamViec"));
                nhanKhau.setNoiThuongTruTruocKhiChuyenDen(rs.getString("DiaChiTruoc"));
                nhanKhau.setTonGiao("null");
                res.add(nhanKhau);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public void save(NhanKhau nhanKhau) {

    }

    @Override
    public void update(NhanKhau nhanKhau) {

    }

    @Override
    public void delete(String maNhanKhau) {
        try {

            Connection cnt = Conn.getConnection();
            cnt.createStatement().executeUpdate("delete from NhanKhau where MaNhanKhau = " + maNhanKhau);
            System.out.println("delete NhanKhau complete!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean themNK(String maHoKhau, String quanHe, String hoTen, String ngaySinh, String gioiTinh, String tenGoiKhac, String queQuan, String danToc, String quocTich, String tonGiao, String ngheNghiep, String noiLamViec, String noiThuongTruTruoc) {
        Connection cnt = Conn.getConnection();
        try {
            cnt.createStatement().executeUpdate("insert into NhanKhau (MaHoKhau, QuanHe, HoTen, NgaySinh, GioiTinh, TenGoiKhac, QueQuan, DanToc, QuocTich, NgheNghiep, NoiLamViec, NoiThuongTruTruocKhiChuyenDen, TonGiao) "+
                    "values (N'"+maHoKhau+"', N'"+quanHe+"', N'"+hoTen+"', '"+ngaySinh+"', N'"+gioiTinh+"', N'"+tenGoiKhac+"', N'"+queQuan+"', N'"+danToc+"', N'"+quocTich+"', N'"+ngheNghiep+"', N'"+noiLamViec+"', N'"+noiThuongTruTruoc+"', N'"+tonGiao+"')");
            System.out.println("them NhanKhau complete!");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
