package control.NhanKhau;


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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerThemNK implements Initializable {
    @FXML
    private Button butAddNhanKhau;
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

    private String maHoKhau;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<GioiTinh> genderList = FXCollections.observableArrayList(GioiTinh.values());
        comboGioiTinh.setItems(genderList);
        comboGioiTinh.getSelectionModel().select(1);
    }

//    public void ActionAddNhanKhau(ActionEvent event) throws IOException {
//        String gioiTinh = comboGioiTinh.getSelectionModel().getSelectedItem().toString();
//        if (tfHoTen.getText().isEmpty()){
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Lỗi");
//            alert.setContentText("Thiếu họ tên!");
//            alert.showAndWait();
//        } else {
//            boolean check = ConnectSQLServer.themNK(maHoKhau, tfQuanHe.getText(), tfHoTen.getText(), tfNam.getText()+"-"+tfThang.getText()+"-"+tfNgay.getText(),gioiTinh, tfTenGoiKhac.getText(), tfQueQuan.getText(), tfDanToc.getText(), tfQuocTich.getText(), tfTonGiao.getText(), tfNgheNghiep.getText(), tfNoiLamViec.getText(), tfNoiOTruoc.getText());
//            if (check == false){
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Lỗi");
//                alert.setContentText("Sai hoặc thiếu ngày sinh!");
//                alert.showAndWait();
//            } else {
//                ConnectSQLServer.updateHistory(maHoKhau, "Thêm mới nhân khẩu:"+tfHoTen.getText());
//                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//                stage.close();
//            }
//        }
//    }
    public void ActionGoBack(ActionEvent event) throws IOException{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setMaHoKhau(String maHoKhau) {
        this.maHoKhau = maHoKhau;
        textMaHoKhau.setText(maHoKhau);
    }
}
