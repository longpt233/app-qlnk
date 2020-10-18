package controller.SHK;

import dao.ConnectSQLServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.GioiTinh;
import model.NgayThangNam;
import model.NhanKhau;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerSuaNK implements Initializable {
    @FXML
    private Button butLuuNhanKhau;
    @FXML
    private Button butGoBack;
    @FXML
    private TextField tfHoTen;
    @FXML
    private TextField tfNgay;
    @FXML
    private TextField tfThang;
    @FXML
    private TextField tfNam;
    @FXML
    private TextField tfQueQuan;
    @FXML
    private TextField tfDanToc;
    @FXML
    private TextField tfNgheNghiep;
    @FXML
    private TextField tfNoiLamViec;
    @FXML
    private ComboBox<GioiTinh> comboGioiTinh;
    @FXML
    private TextField tfTonGiao;
    @FXML
    private TextField tfTenGoiKhac;
    @FXML
    private TextField tfQuocTich;
    @FXML
    private TextField tfNoiOTruoc;
    @FXML
    private Text textMaHoKhau;
    @FXML
    private TextField tfQuanHe;

    private NhanKhau nhanKhau;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<GioiTinh> genderList = FXCollections.observableArrayList(GioiTinh.values());
        comboGioiTinh.setItems(genderList);
        comboGioiTinh.getSelectionModel().select(1);
    }

    public void ActionLuuNhanKhau(ActionEvent event) throws IOException {
        String gioiTinh = comboGioiTinh.getSelectionModel().getSelectedItem().toString();
        boolean check = ConnectSQLServer.updateThongTinNhanKhau(nhanKhau.getMaNhanKhau(), tfQuanHe.getText(), tfHoTen.getText(),tfNam.getText()+"-"+tfThang.getText()+"-"+tfNgay.getText(),gioiTinh, tfTenGoiKhac.getText(), tfQueQuan.getText(), tfDanToc.getText(), tfQuocTich.getText(), tfTonGiao.getText(), tfNgheNghiep.getText(), tfNoiLamViec.getText(),tfNoiOTruoc.getText());
        if (check == false){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Lỗi");
            alert.setContentText("Sai ngày sinh!");
            alert.showAndWait();
        } else {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }
    public void ActionGoBack(ActionEvent event) throws IOException{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setNhanKhau(NhanKhau nhanKhau){
        this.nhanKhau = nhanKhau;
        textMaHoKhau.setText(nhanKhau.getMaHoKhau());
        if(nhanKhau.getDanToc() != null){
            tfDanToc.setText(nhanKhau.getDanToc());
        }
        if(nhanKhau.getHoTen() != null){
            tfHoTen.setText(nhanKhau.getHoTen());
        }
        if(nhanKhau.getNgheNghiep() != null){
            tfNgheNghiep.setText(nhanKhau.getNgheNghiep());
        }
        if(nhanKhau.getNoiLamViec() != null){
            tfNoiLamViec.setText(nhanKhau.getNoiLamViec());
        }
        if(nhanKhau.getNoiThuongTruTruocKhiChuyenDen() != null){
            tfNoiOTruoc.setText(nhanKhau.getNoiThuongTruTruocKhiChuyenDen());
        }
        if(nhanKhau.getQuanHe() != null){
            tfQuanHe.setText(nhanKhau.getQuanHe());
        }
        if(nhanKhau.getQuocTich() != null){
            tfQuocTich.setText(nhanKhau.getQuocTich());
        }
        if(nhanKhau.getQueQuan() != null){
            tfQueQuan.setText(nhanKhau.getQueQuan());
        }
        if(nhanKhau.getTenGoiKhac() != null){
            tfTenGoiKhac.setText(nhanKhau.getTenGoiKhac());
        }
        if(nhanKhau.getTonGiao() != null){
            tfTonGiao.setText(nhanKhau.getTonGiao());
        }
        if (nhanKhau.getGioiTinh()!=null){
            if (nhanKhau.getGioiTinh().contains("Nam")){
                comboGioiTinh.setValue(GioiTinh.MALE);
            } else if (nhanKhau.getGioiTinh().contains("Nu")){
                comboGioiTinh.setValue(GioiTinh.FEMALE);
            } else {
                comboGioiTinh.setValue(GioiTinh.OTHER);
            }
        }
        NgayThangNam ngayThangNam = ConnectSQLServer.getNTN(nhanKhau.getMaNhanKhau());
        if (ngayThangNam != null) {
            tfNam.setText(ngayThangNam.getNam());
            tfThang.setText(ngayThangNam.getThang());
            tfNgay.setText(ngayThangNam.getNgay());
        }
    }
}
