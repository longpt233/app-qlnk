package dao.imp;

import conn.Conn;
import dao.BaseDao;
import dao.SoHoKhauDao;
import model.NhanKhau;
import model.SoHoKhauModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SoSoHoKhauDAO implements BaseDao<SoHoKhauModel>, SoHoKhauDao<NhanKhau> {
    @Override
    public SoHoKhauModel get(long id) {
        return null;
    }

    @Override
    public List<SoHoKhauModel> getAll() {

        List<SoHoKhauModel> res=new ArrayList<>();
        try {
            Connection cnt=Conn.getConnection();
            Statement stm = cnt.createStatement();
            ResultSet rs = stm.executeQuery(" select\n" +
                    "\tR1.Id_SHK,\n" +
                    "\tR3.HoTen,\n" +
                    "\tR3.CCCD,\n" +
                    "\tR1.DiaChiThuongTru,\n" +
                    "\tR1.SoNhanKhau\n" +
                    "from SoHoKhau R1\n" +
                    "join\tChuHo R2\n" +
                    "on  R1.Id_SHK=R2.Id_SHK\n" +
                    "join NhanKhau R3\n" +
                    "on R2.Id_NK=R3.Id_NK;");
            while(rs.next()){
                res.add(new SoHoKhauModel(
                        rs.getString("Id_SHK"),
                        rs.getString("HoTen"),
                        rs.getString("CCCD"),
                        rs.getString("DiaChiThuongTru"),
                        rs.getInt("SoNhanKhau")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public void save(SoHoKhauModel soHoKhauModel) {

    }

    @Override
    public void update(SoHoKhauModel soHoKhauModel) {

    }

    @Override
    public void delete(String maSoHoKhau) {
        try {

            Connection cnt = Conn.getConnection() ;
            cnt.createStatement().executeUpdate("delete from NhanKhau where MaHoKhau = " + maSoHoKhau + "delete from SoHoKhau where MaHoKhau = " + maSoHoKhau + "delete from LichSu where MaHoKhau = " + maSoHoKhau);
            System.out.println("delete complete!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean DatLamChuHo(int id) {
        return false;
    }

    @Override
    public boolean existSHK(String maHoKhau) {
        try {
            Connection cnt= Conn.getConnection();
            ResultSet rs = cnt.createStatement().executeQuery("select * from SoHoKhau where MaHoKhau="+maHoKhau);
            rs.next();
            if (!rs.getString("MaHoKhau").equals("")) return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public List<NhanKhau> getNhanKhauTrongHoById(String maHoKhau) {
        List<NhanKhau> res=new ArrayList<>();
        try{
        Connection cnt=Conn.getConnection();
        Statement stm = cnt.createStatement();
        ResultSet rs = stm.executeQuery("select * from NhanKhau where MaHoKhau = "+maHoKhau);
        while(rs.next()){
            res.add(new NhanKhau(rs.getString("QuanHe"),
                    rs.getString("MaNhanKhau"), rs.getString("HoTen"), rs.getString("NgaySinh"),
                    rs.getString("GioiTinh"), rs.getString("MaHoKhau"), rs.getString("TenGoiKhac"),
                    rs.getString("QueQuan"), rs.getString("DanToc"), rs.getString("QuocTich"),
                    rs.getString("NgheNghiep"),rs.getString("NoiLamViec"),
                    rs.getString("NoiThuongTruTruocKhiChuyenDen"), rs.getString("MaNhanKhau"),
                    rs.getString("TonGiao")));
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public String themSoHoKhau(String diaChi) {

        try {
            Connection cnt = Conn.getConnection();
            ResultSet rs = cnt.createStatement().executeQuery("insert into SoHoKhau (NoiThuongTru) values (N'"+diaChi+"'); select SCOPE_IDENTITY() as ID;");
            rs.next();
            System.out.println("insert SHK complete!");
            return rs.getString("ID");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "-1";
    }

    @Override
    public void updateDiaChi(String maHoKhau, String noiThuongTru) {
        Connection cnt =Conn.getConnection();
        try {
            cnt.createStatement().executeUpdate("update SoHoKhau set NoiThuongTru= N'"+noiThuongTru+"' where MaHoKhau="+maHoKhau);
            System.out.println("update diachi complete!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setChuHo(String maHoKhau, String maNhanKhau) {
        Connection cnt = Conn.getConnection();
        try {
            cnt.createStatement().executeUpdate("update SoHoKhau set MaChuHo="+maNhanKhau+"where MaHoKhau="+maHoKhau);
            System.out.println("set ChuHo complete!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
