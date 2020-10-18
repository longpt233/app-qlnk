package controller.SHK;

import dao.ConnectSQLServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.GiayKhaiSinh;
import model.GioiTinh;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerGiayKhaiSinh implements Initializable {

    @FXML
    private Button btnLuu;

    @FXML
    private Button btnHuy;

    @FXML
    private TextField txtQuanHe;

    @FXML
    private TextField txtQuocTichCha;

    @FXML
    private TextField txtNam;

    @FXML
    private TextField txtNoiSinh;

    @FXML
    private TextField txtGhiChu;

    @FXML
    private TextField txtNamSinhCha;

    @FXML
    private TextField txtNamDK;

    @FXML
    private TextField txtQuocTich;

    @FXML
    private TextField txtHoTen;

    @FXML
    private TextField txtDanTocMe;

    @FXML
    private TextField txtDanTocCha;

    @FXML
    private TextField txtNgayDK;

    @FXML
    private TextField txtThang;

    @FXML
    private Text textMaNhanKhau;

    @FXML
    private TextField txtNgay;

    @FXML
    private TextField txtThuongTruMe;

    @FXML
    private TextField txtHoTenMe;

    @FXML
    private TextField txtHoTenCha;

    @FXML
    private TextField txtNoiDangKy;

    @FXML
    private TextField txtThangDK;

    @FXML
    private ComboBox<GioiTinh> comboGioiTinh;

    @FXML
    private TextField txtDanToc;

    @FXML
    private TextField txtThuongTruCha;

    @FXML
    private TextField txtNamSinhMe;

    @FXML
    private TextField txtNguoiKS;
    @FXML
    private TextField txtQuocTichMe;

    private String maNhanKhau;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<GioiTinh> genderList = FXCollections.observableArrayList(GioiTinh.values());
        comboGioiTinh.setItems(genderList);
        comboGioiTinh.getSelectionModel().select(1);

        btnLuu.setOnAction(event -> {
            GiayKhaiSinh giayKhaiSinh = new GiayKhaiSinh();
            giayKhaiSinh.setMaNhanKhau(maNhanKhau);
            giayKhaiSinh.setHoTen(txtHoTen.getText());
            giayKhaiSinh.setNgaySinh(txtNam.getText()+"-"+txtThang.getText()+"-"+txtNgay.getText());
            giayKhaiSinh.setGioiTinh(comboGioiTinh.getSelectionModel().getSelectedItem().toString());
            giayKhaiSinh.setNoiSinh(txtNoiSinh.getText());
            giayKhaiSinh.setDanToc(txtDanToc.getText());
            giayKhaiSinh.setQuocTich(txtQuocTich.getText());
            giayKhaiSinh.setHoTenCha(txtHoTenCha.getText());
            giayKhaiSinh.setDanTocCha(txtDanTocCha.getText());
            giayKhaiSinh.setQuocTichCha(txtQuocTichCha.getText());
            giayKhaiSinh.setNamSinhCha(txtNamSinhCha.getText());
            giayKhaiSinh.setThuongTruCha(txtThuongTruCha.getText());
            giayKhaiSinh.setHoTenMe(txtHoTenMe.getText());
            giayKhaiSinh.setDanTocMe(txtDanTocMe.getText());
            giayKhaiSinh.setQuocTichMe(txtQuocTichMe.getText());
            giayKhaiSinh.setNamSinhMe(txtNamSinhMe.getText());
            giayKhaiSinh.setThuongTruMe(txtThuongTruMe.getText());
            giayKhaiSinh.setNoiDangKy(txtNoiDangKy.getText());
            giayKhaiSinh.setGhiChu(txtGhiChu.getText());
            giayKhaiSinh.setHoTenNguoiDiKhaiSinh(txtNguoiKS.getText());
            giayKhaiSinh.setQuanHe(txtQuanHe.getText());
            if(ConnectSQLServer.getGKS(giayKhaiSinh.getMaNhanKhau())!= null){
                ConnectSQLServer.executeQuery("delete from GiayKhaiSinh where MaNhanKhau="+giayKhaiSinh.getMaNhanKhau());
            }
            ConnectSQLServer.themGKS(giayKhaiSinh);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        });

        btnHuy.setOnAction(event -> {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        });
    }

    public void setMaNhanKhau(String maNhanKhau){
        this.maNhanKhau = maNhanKhau;
        textMaNhanKhau.setText(maNhanKhau);
        GiayKhaiSinh giayKhaiSinh = ConnectSQLServer.getGKS(maNhanKhau);
        if (giayKhaiSinh != null){
            txtHoTen.setText(giayKhaiSinh.getHoTen());
            if (giayKhaiSinh.getGioiTinh()!=null){
                if (giayKhaiSinh.getGioiTinh().contains("Nam")){
                    comboGioiTinh.setValue(GioiTinh.MALE);
                } else if (giayKhaiSinh.getGioiTinh().contains("Nu")){
                    comboGioiTinh.setValue(GioiTinh.FEMALE);
                } else {
                    comboGioiTinh.setValue(GioiTinh.OTHER);
                }
            }
            txtNoiSinh.setText(giayKhaiSinh.getNoiSinh());
            txtDanToc.setText(giayKhaiSinh.getDanToc());
            txtQuocTich.setText(giayKhaiSinh.getQuocTich());
            txtHoTenCha.setText(giayKhaiSinh.getHoTenCha());
            txtDanTocCha.setText(giayKhaiSinh.getDanTocCha());
            txtQuocTichCha.setText(giayKhaiSinh.getQuocTichCha());
            txtNamSinhCha.setText(giayKhaiSinh.getNamSinhCha());
            txtThuongTruCha.setText(giayKhaiSinh.getThuongTruCha());
            txtHoTenMe.setText(giayKhaiSinh.getHoTenMe());
            txtDanTocMe.setText(giayKhaiSinh.getDanTocMe());
            txtQuocTichMe.setText(giayKhaiSinh.getQuocTichMe());
            txtNamSinhMe.setText(giayKhaiSinh.getNamSinhMe());
            txtThuongTruMe.setText(giayKhaiSinh.getThuongTruMe());
            txtNoiDangKy.setText(giayKhaiSinh.getNoiDangKy());
            txtGhiChu.setText(giayKhaiSinh.getGhiChu());
            txtNguoiKS.setText(giayKhaiSinh.getHoTenNguoiDiKhaiSinh());
            txtQuanHe.setText(giayKhaiSinh.getQuanHe());
        }
    }
}
