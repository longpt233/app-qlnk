package dao;

import controller.Main;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectSQLServer {
    private static String DB_URL = "jdbc:sqlserver://localhost:1433;"
            +"databaseName=QLNK;"
            +"integratedSecurity=true";
    private static String USER_NAME = "sa";
    private static String PASSWORD = "123456";
    private static String QUERYHoKhau = "select R1.MaHoKhau, R2.HoTen, R2.MaNhanKhau as MaNhanKhau, R1.NoiThuongTru, R1.SoNhanKhau from SoHoKhau R1 left join NhanKhau R2 on R1.MaChuHo=R2.MaNhanKhau";
    private static String QUERYNhanKhau = "select * from NhanKhau";


    public static boolean existSHK(String maHoKhau){
        try {
            Connection cnt=getConnect(DB_URL, USER_NAME, PASSWORD);
            ResultSet rs = cnt.createStatement().executeQuery("select * from SoHoKhau where MaHoKhau="+maHoKhau);
            rs.next();
            if (!rs.getString("MaHoKhau").equals("")) return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

   

    public static void pullData(){
        Main.soHoKhauModelArrayList.removeAll(Main.soHoKhauModelArrayList);
        try {
            Connection cnt=getConnect(DB_URL, USER_NAME, PASSWORD);
            Statement stm = cnt.createStatement();
            ResultSet rs = stm.executeQuery(QUERYHoKhau);
            while(rs.next()){
                Main.soHoKhauModelArrayList.add(new SoHoKhauModel(rs.getString("MaHoKhau"),
                        rs.getString("HoTen"), rs.getString("MaNhanKhau"),
                        rs.getString("NoiThuongTru"), rs.getInt("SoNhanKhau")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static long getSLNamNu(String gioiTinh){
        try {
            Connection cnt=getConnect(DB_URL, USER_NAME, PASSWORD);
            Statement stm = cnt.createStatement();
            ResultSet rs = stm.executeQuery("select count(*) as SL from NhanKhau where GioiTinh = N'"+gioiTinh+"'");
            rs.next();
            return rs.getLong("SL");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void pullDataNhanKhauTrongHo(String maHoKhau){
        Main.nhanKhauTrongHo.removeAll(Main.nhanKhauTrongHo);
        try {
            Connection cnt=getConnect(DB_URL, USER_NAME, PASSWORD);
            Statement stm = cnt.createStatement();
            ResultSet rs = stm.executeQuery("select * from NhanKhau where MaHoKhau = "+maHoKhau);
            while(rs.next()){
                Main.nhanKhauTrongHo.add(new NhanKhau(rs.getString("QuanHe"),
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
    }

    public static Connection getConnect(String dbURL, String userName, String password){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection(DB_URL, userName, password);
            System.out.println("Connect database successful");
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void pullDataNhanKhau(){
        Main.nhanKhauArrayList.removeAll(Main.nhanKhauArrayList);
        try {
            Connection cnt=getConnect(DB_URL, USER_NAME, PASSWORD);
            Statement stm = cnt.createStatement();
            ResultSet rs = stm.executeQuery(QUERYNhanKhau);
            while(rs.next()){
                NhanKhau nhanKhau = new NhanKhau();
                nhanKhau.setMaNhanKhau(rs.getString("MaNhanKhau"));
                nhanKhau.setCmnd(rs.getString("MaNhanKhau"));
                nhanKhau.setMaHoKhau(rs.getString("MaHoKhau"));
                nhanKhau.setQuanHe(rs.getString("QuanHe"));
                nhanKhau.setHoTen(rs.getString("HoTen"));
                nhanKhau.setNgaySinh(rs.getString("NgaySinh"));
                nhanKhau.setGioiTinh(rs.getString("GioiTinh"));
                nhanKhau.setTenGoiKhac(rs.getString("TenGoiKhac"));
                nhanKhau.setQueQuan(rs.getString("QueQuan"));
                nhanKhau.setDanToc(rs.getString("DanToc"));
                nhanKhau.setQuocTich(rs.getString("QuocTich"));
                nhanKhau.setNgheNghiep(rs.getString("NgheNghiep"));
                nhanKhau.setNoiLamViec(rs.getString("NoiLamViec"));
                nhanKhau.setNoiThuongTruTruocKhiChuyenDen(rs.getString("NoiThuongTruTruocKhiChuyenDen"));
                nhanKhau.setTonGiao(rs.getString("TonGiao"));
                Main.nhanKhauArrayList.add(nhanKhau);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void xoaSoHoKhau(String maSoHoKhau){
        Connection cnt = getConnect(DB_URL, USER_NAME, PASSWORD);
        try {
            cnt.createStatement().executeUpdate("delete from NhanKhau where MaHoKhau = " + maSoHoKhau + "delete from SoHoKhau where MaHoKhau = " + maSoHoKhau + "delete from LichSu where MaHoKhau = " + maSoHoKhau);
            System.out.println("delete complete!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void xoaNhanKhau(String maNhanKhau){
        Connection cnt = getConnect(DB_URL, USER_NAME, PASSWORD);
        try {
            cnt.createStatement().executeUpdate("delete from NhanKhau where MaNhanKhau = " + maNhanKhau);
            System.out.println("delete NhanKhau complete!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String themSoHoKhau(String diaChi){
        Connection cnt = getConnect(DB_URL, USER_NAME, PASSWORD);
        try {
            ResultSet rs = cnt.createStatement().executeQuery("insert into SoHoKhau (NoiThuongTru) values (N'"+diaChi+"'); select SCOPE_IDENTITY() as ID;");
            rs.next();
            System.out.println("insert SHK complete!");
            return rs.getString("ID");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "-1";
    }

    public static boolean themNK(String maHoKhau, String quanHe, String hoTen, String ngaySinh, String gioiTinh, String tenGoiKhac, String queQuan, String danToc, String quocTich, String tonGiao, String ngheNghiep, String noiLamViec, String noiThuongTruTruoc){
        Connection cnt = getConnect(DB_URL, USER_NAME, PASSWORD);
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

    public static void setChuHo(String maHoKhau, String maNhanKhau){
        Connection cnt = getConnect(DB_URL, USER_NAME, PASSWORD);
        try {
            cnt.createStatement().executeUpdate("update SoHoKhau set MaChuHo="+maNhanKhau+"where MaHoKhau="+maHoKhau);
            System.out.println("set ChuHo complete!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean updateThongTinNhanKhau(String maNhanKhau, String quanHe, String hoTen, String ngaySinh, String gioiTinh, String tenGoiKhac, String queQuan, String danToc, String quocTich, String tonGiao, String ngheNghiep, String noiLamViec, String noiThuongTruTruoc){
        Connection cnt = getConnect(DB_URL, USER_NAME, PASSWORD);
        try {
            cnt.createStatement().executeUpdate("update NhanKhau set QuanHe=N'"+quanHe+"', HoTen=N'"+hoTen+"', NgaySinh='"+ngaySinh+"', GioiTinh=N'"+gioiTinh+"', TenGoiKhac=N'"+tenGoiKhac+"', QueQuan=N'"+queQuan+"', DanToc=N'"+danToc+"', QuocTich=N'"+quocTich+"', TonGiao=N'"+tonGiao+"', NgheNghiep=N'"+ngheNghiep+"', NoiLamViec=N'"+noiLamViec+"', NoiThuongTruTruocKhiChuyenDen=N'"+noiThuongTruTruoc+"' where MaNhanKhau="+maNhanKhau);
            System.out.println("update NhanKhau complete!");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void updateDiaChi(String maHoKhau, String noiThuongTru){
        Connection cnt = getConnect(DB_URL, USER_NAME, PASSWORD);
        try {
            cnt.createStatement().executeUpdate("update SoHoKhau set NoiThuongTru= N'"+noiThuongTru+"' where MaHoKhau="+maHoKhau);
            System.out.println("update diachi complete!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static NgayThangNam getNTN(String maNhanKhau){
        try {
            Connection cnt=getConnect(DB_URL, USER_NAME, PASSWORD);
            Statement stm = cnt.createStatement();
            ResultSet rs = stm.executeQuery("select FORMAT(NgaySinh, 'yyyy') as nam, FORMAT(NgaySinh, 'MM') as thang, FORMAT(NgaySinh, 'dd') as ngay from NhanKhau where MaNhanKhau="+maNhanKhau);
            while(rs.next()){
                return new NgayThangNam(rs.getString("ngay"), rs.getString("thang"), rs.getString("nam"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean chuyenHo(String maNhanKhau, String maHoKhau) {
        Connection cnt = getConnect(DB_URL, USER_NAME, PASSWORD);
        try {
            cnt.createStatement().executeUpdate("update NhanKhau set MaHoKhau= "+maHoKhau+" where MaNhanKhau="+maNhanKhau+"; update SoHoKhau set SoNhanKhau = (select COUNT(*) from NhanKhau where NhanKhau.MaHoKhau= SoHoKhau.MaHoKhau)");
            System.out.println("ChuyenHo complete!");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static GiayKhaiSinh getGKS(String maNhanKhau){
        try {
            Connection cnt=getConnect(DB_URL, USER_NAME, PASSWORD);
            Statement stm = cnt.createStatement();
            ResultSet rs = stm.executeQuery("select * from GiayKhaiSinh where MaNhanKhau="+maNhanKhau);
            while(rs.next()){
                GiayKhaiSinh giayKhaiSinh = new GiayKhaiSinh();
                giayKhaiSinh.setMaNhanKhau(maNhanKhau);
                giayKhaiSinh.setHoTen(rs.getString("HoTen"));
                giayKhaiSinh.setNgaySinh(rs.getString("NgaySinh"));
                giayKhaiSinh.setGioiTinh(rs.getString("GioiTinh"));
                giayKhaiSinh.setNoiSinh(rs.getString("NoiSinh"));
                giayKhaiSinh.setDanToc(rs.getString("DanToc"));
                giayKhaiSinh.setQuocTich(rs.getString("QuocTich"));
                giayKhaiSinh.setHoTenCha(rs.getString("HoTenCha"));
                giayKhaiSinh.setDanTocCha(rs.getString("DanTOcCha"));
                giayKhaiSinh.setQuocTichCha(rs.getString("QuocTichCha"));
                giayKhaiSinh.setNamSinhCha(rs.getString("NamSinhCha"));
                giayKhaiSinh.setThuongTruCha(rs.getString("ThuongTruCha"));
                giayKhaiSinh.setHoTenMe(rs.getString("HoTenMe"));
                giayKhaiSinh.setDanTocMe(rs.getString("DanTocMe"));
                giayKhaiSinh.setQuocTichMe(rs.getString("QuocTichMe"));
                giayKhaiSinh.setNamSinhMe(rs.getString("NamSinhMe"));
                giayKhaiSinh.setThuongTruMe(rs.getString("ThuongTruMe"));
                giayKhaiSinh.setNoiDangKy(rs.getString("NoiDangKy"));
                giayKhaiSinh.setGhiChu(rs.getString("GhiChu"));
                giayKhaiSinh.setHoTenNguoiDiKhaiSinh(rs.getString("HoTenNguoiDiKhaiSinh"));
                giayKhaiSinh.setQuanHe(rs.getString("QuanHe"));
                return giayKhaiSinh;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean themGKS(GiayKhaiSinh giayKhaiSinh){
        Connection cnt = getConnect(DB_URL, USER_NAME, PASSWORD);
        try {
            cnt.createStatement().executeUpdate("insert into GiayKhaiSinh (HoTen, NgaySinh, GioiTinh, NoiSinh, DanToc, QuocTich, HoTenCha, DanTocCha, QuocTichCha, NamSinhCha, ThuongTruCha, HoTenMe, DanTocMe, QuocTichMe, NamSinhMe, ThuongTruMe, NoiDangKy, NgayDangKy, GhiChu, HoTenNguoiDiKhaiSinh, QuanHe, MaNhanKhau) "+
                    "values (N'"+giayKhaiSinh.getHoTen()+"', '"+giayKhaiSinh.getNgaySinh()+"', N'"+giayKhaiSinh.getGioiTinh()+"', N'"+giayKhaiSinh.getNoiSinh()+"', N'"+
                    giayKhaiSinh.getDanToc()+"', N'"+giayKhaiSinh.getQuocTich()+"', N'"+giayKhaiSinh.getHoTenCha()+"', N'"+
                    giayKhaiSinh.getDanTocCha()+"', N'"+giayKhaiSinh.getQuocTichCha()+"', "+giayKhaiSinh.getNamSinhCha()+", N'"+giayKhaiSinh.getThuongTruCha()
                    +"', N'"+giayKhaiSinh.getHoTenMe()+"', N'"+giayKhaiSinh.getDanTocMe()+"', N'"+giayKhaiSinh.getQuocTichMe()+"'), "+giayKhaiSinh.getNamSinhMe()+
                    ", N'"+giayKhaiSinh.getThuongTruMe()+"', N'"+giayKhaiSinh.getNoiDangKy()+"', '"+giayKhaiSinh.getNgayDangKy()+"', N'"+
                    giayKhaiSinh.getGhiChu()+"', N'"+giayKhaiSinh.getHoTenNguoiDiKhaiSinh()+"', N'"+giayKhaiSinh.getQuanHe()+"', N'"+giayKhaiSinh.getMaNhanKhau()+"'");
            System.out.println("them NhanKhau complete!");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void updateHistory(String maHoKhau, String content){
        Connection cnt = getConnect(DB_URL, USER_NAME, PASSWORD);
        try {
            cnt.createStatement().executeUpdate("insert into LichSu values ("+maHoKhau+", N'"+content+"', getdate())");
            System.out.println("update history complete!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<History> pullHistory(String maHoKhau){
        List<History> histories= new ArrayList<>();
        try {
            Connection cnt=getConnect(DB_URL, USER_NAME, PASSWORD);
            Statement stm = cnt.createStatement();
            ResultSet rs = stm.executeQuery("select * from LichSu where MaHoKhau="+maHoKhau);
            while(rs.next()){
                History history = new History();
                history.setMaHoKhau(maHoKhau);
                history.setNoiDung(rs.getString("NoiDung"));
                history.setThoiGian(rs.getString("ThoiDiem"));
                histories.add(history);
            }
            return histories;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
