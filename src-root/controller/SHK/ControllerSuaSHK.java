package controller.SHK;
import java.io.IOException;
import java.lang.String;

import dao.ConnectSQLServer;
import controller.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;


import java.net.URL;


import java.util.Optional;
import java.util.ResourceBundle;
public class ControllerSuaSHK implements Initializable {
    @FXML
    private TableView tableNhanKhau;
    @FXML
    private TableColumn<NhanKhau, String> colHoTen;
    @FXML
    private TableColumn<NhanKhau, String> colNgaySinh;
    @FXML
    private TableColumn<NhanKhau, String> colQueQuan;
    @FXML
    private TableColumn<NhanKhau, String> colDanToc;
    @FXML
    private TableColumn<NhanKhau, String> colTonGiao;
    @FXML
    private TableColumn<NhanKhau, String> colNgheNghiep;
    @FXML
    private TableColumn<NhanKhau, String> colNoiLamViec;
    @FXML
    private TableColumn<NhanKhau, String> colQuanHe;
    @FXML
    private TableColumn<NhanKhau, String> colGioiTinh;
    @FXML
    private TableColumn<NhanKhau, String> colMaNhanKhau;
    @FXML
    private TableColumn<NhanKhau, String> colQuocTich;
    @FXML
    private TableColumn<NhanKhau, String> colTenGoiKhac;

    @FXML
    private Button btnThemNhanKhau;
    @FXML
    private Button btnChinhSua;
    @FXML
    private Button btnXoa;
    @FXML
    private Button btnLuu;
    @FXML
    private Button btnHuy;
    @FXML
    private Text textMaHoKhau;
    @FXML
    private TextField txtDiaChi;
    @FXML
    private Button btnDatLamChuHo;
    @FXML
    private Text textChuHo;
    @FXML
    private Button btnChuyenHo;
    @FXML
    private Button btnTachHo;
    @FXML
    private Button btnLichSu;


    private SoHoKhauModel soHoKhauModel;
    private ObservableList<NhanKhau> nhankhauObservableList;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colHoTen.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("hoTen"));
        colNgaySinh.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("ngaySinh"));
        colQueQuan.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("queQuan"));
        colDanToc.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("danToc"));
        colTonGiao.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("tonGiao"));
        colNgheNghiep.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("ngheNghiep"));
        colNoiLamViec.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("noiLamViec"));
        colQuanHe.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("quanHe"));
        colQuocTich.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("quocTich"));
        colMaNhanKhau.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("maNhanKhau"));
        colTenGoiKhac.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("tenGoiKhac"));
        colGioiTinh.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("gioiTinh"));


        nhankhauObservableList = FXCollections.observableArrayList(Main.nhanKhauTrongHo);
        tableNhanKhau.setItems(nhankhauObservableList);;
        tableNhanKhau.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        btnThemNhanKhau.setOnAction(event -> {
            Parent parent = null;
            FXMLLoader loader = new FXMLLoader();
            try {
                loader.setLocation(getClass().getResource("/view/SHK/themNK.fxml"));
                parent = loader.load();
                Scene scene = new Scene(parent);
                Stage stageChinhSua = new Stage();
                Image image = new Image("/drawable/icon.png");
                stageChinhSua.getIcons().add(image);
                stageChinhSua.setTitle("Thêm nhân khẩu");
                stageChinhSua.setScene(scene);
                stageChinhSua.initModality(Modality.WINDOW_MODAL);
                stageChinhSua.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());
                ControllerThemNK controllerThemNK = loader.getController();
                controllerThemNK.setMaHoKhau(soHoKhauModel.getMaHoKhau());
                stageChinhSua.showAndWait();
                refreshTable();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnLuu.setOnAction(event -> {
            if (!txtDiaChi.getText().equals(soHoKhauModel.getDiaChi())){
                ConnectSQLServer.updateDiaChi(soHoKhauModel.getMaHoKhau(), txtDiaChi.getText());
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        });

        btnHuy.setOnAction(event -> {
            if(soHoKhauModel.getCCCD()!=null){
                ConnectSQLServer.setChuHo(soHoKhauModel.getMaHoKhau(), soHoKhauModel.getCCCD());
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        });

        btnXoa.setOnAction(event -> {
            ObservableList<NhanKhau> nhanKhaus = tableNhanKhau.getSelectionModel().getSelectedItems();
            if (nhanKhaus.get(0) != null){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Xoá nhân khẩu");
                alert.setHeaderText("Thông tin nhân khẩu:");
                alert.setContentText("Mã nhân khẩu: "+nhanKhaus.get(0).getMaNhanKhau()+ "\nHọ và tên: " +nhanKhaus.get(0).getHoTen());
                alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
                alert.showAndWait().ifPresent((btnType)->{
                    if (btnType == ButtonType.OK){
                        ConnectSQLServer.xoaNhanKhau(nhanKhaus.get(0).getMaNhanKhau());
                        ConnectSQLServer.updateHistory(nhanKhaus.get(0).getMaHoKhau(), "Xoá nhân khẩu:"+nhanKhaus.get(0).getHoTen());
                        refreshTable();
                    } else if (btnType == ButtonType.CANCEL){

                    }
                });
            }
        });

        btnChinhSua.setOnAction(event -> {
            ObservableList<NhanKhau> nhanKhaus = tableNhanKhau.getSelectionModel().getSelectedItems();
            Parent parent = null;
            FXMLLoader loader = new FXMLLoader();
            try {
                loader.setLocation(getClass().getResource("/view/SHK/suaNK.fxml"));
                parent = loader.load();
                Scene scene = new Scene(parent);
                Stage stageChinhSua = new Stage();
                Image image = new Image("/drawable/icon.png");
                stageChinhSua.getIcons().add(image);
                stageChinhSua.setTitle("Chỉnh sửa nhân khẩu");
                stageChinhSua.setScene(scene);
                stageChinhSua.initModality(Modality.WINDOW_MODAL);
                stageChinhSua.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());
                ControllerSuaNK controllerSuaNK = loader.getController();
                controllerSuaNK.setNhanKhau(nhanKhaus.get(0));
                stageChinhSua.showAndWait();
                refreshTable();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnDatLamChuHo.setOnAction(event -> {
            ObservableList<NhanKhau> nhanKhaus = tableNhanKhau.getSelectionModel().getSelectedItems();
            if (nhanKhaus.get(0) != null){
                ConnectSQLServer.setChuHo(soHoKhauModel.getMaHoKhau(), nhanKhaus.get(0).getMaNhanKhau());
                textChuHo.setText(nhanKhaus.get(0).getHoTen());
            }
        });

        btnChuyenHo.setOnAction(event -> {
            ObservableList<NhanKhau> nhanKhaus = tableNhanKhau.getSelectionModel().getSelectedItems();
            boolean check = false;
            for (NhanKhau nhanKhau:nhanKhaus
                 ) {
                if(nhanKhau.getHoTen().equals(textChuHo.getText())) {
                    check = true;
                    break;
                }
            }
            if(check){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Lỗi!");
                alert.setContentText("Không chuyển được chủ hộ!");
                alert.showAndWait();
            } else {
                if(nhanKhaus.get(0)!=null){
                    Parent parent = null;
                    FXMLLoader loader = new FXMLLoader();
                    try {
                        loader.setLocation(getClass().getResource("/view/SHK/chonSHKChuyenHo.fxml"));
                        parent = loader.load();
                        Scene scene = new Scene(parent);
                        Stage stageChinhSua = new Stage();
                        Image image = new Image("/drawable/icon.png");
                        stageChinhSua.getIcons().add(image);
                        stageChinhSua.setTitle("Chọn SHK");
                        stageChinhSua.setScene(scene);
                        stageChinhSua.initModality(Modality.WINDOW_MODAL);
                        stageChinhSua.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());
                        ControllerChonSHKChuyenHo controllerChonSHKChuyenHo = loader.getController();
                        controllerChonSHKChuyenHo.setNhanKhauObservableList(nhanKhaus);
                        stageChinhSua.showAndWait();
                        refreshTable();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btnTachHo.setOnAction(event -> {
            ObservableList<NhanKhau> nhanKhaus = tableNhanKhau.getSelectionModel().getSelectedItems();
            if(nhanKhaus.get(0)!=null){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Tách hộ");
                alert.setHeaderText("Bạn có đồng ý tách hộ?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    // ... user chose OK
                    String idSHK="";
                    idSHK = ConnectSQLServer.themSoHoKhau("");
                    for (NhanKhau nhanKhau: nhanKhaus) {
                        ConnectSQLServer.chuyenHo(nhanKhau.getMaNhanKhau(), idSHK);
                        ConnectSQLServer.updateHistory(soHoKhauModel.getMaHoKhau(), "Nhân khẩu tách hộ: "+nhanKhau.getHoTen());
                        ConnectSQLServer.updateHistory(idSHK, "Nhân khẩu chuyển đến: "+nhanKhau.getHoTen());
                    }
                    Parent parent = null;
                    FXMLLoader loader = new FXMLLoader();
                    try {
                        loader.setLocation(getClass().getResource("/view/SHK/themSHKtachHo.fxml"));
                        parent = loader.load();
                        Scene scene = new Scene(parent);
                        Stage stageChinhSua = new Stage();
                        Image image = new Image("/drawable/icon.png");
                        stageChinhSua.getIcons().add(image);
                        stageChinhSua.setTitle("Sổ hộ khẩu mới");
                        stageChinhSua.setScene(scene);
                        stageChinhSua.initModality(Modality.WINDOW_MODAL);
                        stageChinhSua.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());
                        ControllerThemSHKtachHo controllerThemSHKtachHo = loader.getController();
                        controllerThemSHKtachHo.setInfoTachHo(idSHK);
                        stageChinhSua.showAndWait();
                        refreshTable();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // ... user chose CANCEL or closed the dialog
                }
            }
        });

        btnLichSu.setOnAction(event -> {
            Parent parent = null;
            FXMLLoader loader = new FXMLLoader();
            try {
                loader.setLocation(getClass().getResource("/view/SHK/history.fxml"));
                parent = loader.load();
                Scene scene = new Scene(parent);
                Stage stageHistory = new Stage();
                Image image = new Image("/drawable/icon.png");
                stageHistory.getIcons().add(image);
                stageHistory.setTitle("Lịch sử thay đổi nhân khẩu");
                stageHistory.setScene(scene);
                stageHistory.initModality(Modality.WINDOW_MODAL);
                stageHistory.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());
                ControllerHistory controllerHistory = loader.getController();
                controllerHistory.khoiTao(soHoKhauModel.getMaHoKhau());
                stageHistory.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    public void setSoHoKhau(SoHoKhauModel soHoKhauModel) {
        this.soHoKhauModel = soHoKhauModel;
        textMaHoKhau.setText(soHoKhauModel.getMaHoKhau());
        txtDiaChi.setText(soHoKhauModel.getDiaChi());
        textChuHo.setText(soHoKhauModel.getTenChuHo());
    }

    private void refreshTable(){
        ConnectSQLServer.pullDataNhanKhauTrongHo(soHoKhauModel.getMaHoKhau());
        nhankhauObservableList.removeAll(nhankhauObservableList);
        nhankhauObservableList = FXCollections.observableArrayList(Main.nhanKhauTrongHo);
        tableNhanKhau.setItems(nhankhauObservableList);
    }
}
