package control.NhanKhau;

import control.Main;
// control.SoHoKhau.ControllerSuaNK;
import dao.imp.NhanKhauDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.NhanKhau;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControllerQLNK implements Initializable {
    @FXML
    private TableView tableViewNhanKhau;
    @FXML
    private TableColumn<NhanKhau, String> tableColumnMaHoKhau;
    @FXML
    private TableColumn<NhanKhau, String> tableColumnHoVaTen;
    @FXML
    private TableColumn<NhanKhau, String> tableColumnGioiTinh;
    @FXML
    private TableColumn<NhanKhau, String> tableColumnNgaySinh;
    @FXML
    private TableColumn<NhanKhau, Integer> tableColumnMaNhanKhau;
    @FXML
    private TextField txtTimKiem;
    @FXML
    private Button btnQLNK;
    @FXML
    private Button btnChinhSua;
    @FXML
    private Button btnXoa;
    @FXML
    private Button btnThemMoiNhanKhau;
    @FXML
    private Button btnGiayKhaiSinh;
    @FXML
    private Button btnThongKe;

    private ObservableList<NhanKhau> NhanKhauObservableList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableColumnMaNhanKhau.setCellValueFactory(new PropertyValueFactory<NhanKhau, Integer>("maNhanKhau"));
        tableColumnHoVaTen.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("hoTen"));
        tableColumnNgaySinh.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("ngaySinh"));
        tableColumnGioiTinh.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("gioiTinh"));
        tableColumnMaHoKhau.setCellValueFactory(new PropertyValueFactory<NhanKhau, String>("maHoKhau"));

        // la mot list co the lang nghe ?
        NhanKhauObservableList = FXCollections.observableList(Main.nhanKhauArrayList);
        // cai nay dc truyen cho mot sorted list va dc truyen vao cho table view o trong
        // day
        refreshTable();

        btnQLNK.setOnAction(actionEvent->{
            try {
                Parent blad = FXMLLoader.load(getClass().getResource("/view/SHK/quanLyHoKhau.fxml"));
                Scene scene = new Scene(blad);
                Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                appStage.setTitle("QLHK");
                appStage.setScene(scene);
                appStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnChinhSua.setOnAction(event-> {
            ObservableList<NhanKhau> nhanKhaus = tableViewNhanKhau.getSelectionModel().getSelectedItems();
            if(nhanKhaus.get(0)!=null){
                Parent parent = null;
                FXMLLoader loader = new FXMLLoader();
                try {
                    loader.setLocation(getClass().getResource("/view/NK/suaNK.fxml"));
                    parent = loader.load();
                    Scene scene = new Scene(parent);
                    Stage stageChinhSua = new Stage();
                    stageChinhSua.setTitle("Chỉnh sửa nhân khẩu");
                    stageChinhSua.setScene(scene);
                    stageChinhSua.initModality(Modality.WINDOW_MODAL);
                    stageChinhSua.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());
//                    ControllerSuaNK controllerSuaNK = loader.getController();
//                    controllerSuaNK.setNhanKhau(nhanKhaus.get(0));
                    stageChinhSua.showAndWait();
                    refreshTable();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnXoa.setOnAction(event -> {
            ObservableList<NhanKhau> nhanKhaus = tableViewNhanKhau.getSelectionModel().getSelectedItems();
            if (nhanKhaus.get(0) != null){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Xoá nhân khẩu");
                alert.setHeaderText("Thông tin nhân khẩu:");
                alert.setContentText("Mã nhân khẩu: "+nhanKhaus.get(0).getMaNhanKhau()+ "\nHọ và tên: " +nhanKhaus.get(0).getHoTen());
                alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
                alert.showAndWait().ifPresent((btnType)->{
                    if (btnType == ButtonType.OK){
//                        ConnectSQLServer.xoaNhanKhau(nhanKhaus.get(0).getMaNhanKhau());
//                        ConnectSQLServer.updateHistory(nhanKhaus.get(0).getMaHoKhau(), "Xoá nhân khẩu:"+nhanKhaus.get(0).getHoTen());
                        refreshTable();
                    } else if (btnType == ButtonType.CANCEL){

                    }
                });
            }
        });

        btnThemMoiNhanKhau.setOnAction(event -> {
            Parent parent = null;
            FXMLLoader loader = new FXMLLoader();
            try {
                loader.setLocation(getClass().getResource("/view/SHK/chonSHK.fxml"));
                parent = loader.load();
                Scene scene = new Scene(parent);
                Stage stageChinhSua = new Stage();
                Image image = new Image("/drawable/icon.png");
                stageChinhSua.getIcons().add(image);
                stageChinhSua.setTitle("Chọn SHK");
                stageChinhSua.setScene(scene);
                stageChinhSua.initModality(Modality.WINDOW_MODAL);
                stageChinhSua.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());
                stageChinhSua.showAndWait();
                refreshTable();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnGiayKhaiSinh.setOnAction(event -> {
            ObservableList<NhanKhau> nhanKhaus = tableViewNhanKhau.getSelectionModel().getSelectedItems();
            if(nhanKhaus.get(0)!=null){
                Parent parent = null;
                FXMLLoader loader = new FXMLLoader();
                try {
                    loader.setLocation(getClass().getResource("/view/SHK/giayKhaiSinh.fxml"));
                    parent = loader.load();
                    Scene scene = new Scene(parent);
                    Stage stageChinhSua = new Stage();
                    Image image = new Image("/drawable/icon.png");
                    stageChinhSua.getIcons().add(image);
                    stageChinhSua.setTitle("Giấy khai sinh");
                    stageChinhSua.setScene(scene);
                    stageChinhSua.initModality(Modality.WINDOW_MODAL);
                    stageChinhSua.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());
//                    ControllerGiayKhaiSinh controllerGiayKhaiSinh = loader.getController();
//                    controllerGiayKhaiSinh.setMaNhanKhau(nhanKhaus.get(0).getMaNhanKhau());
                    stageChinhSua.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnThongKe.setOnAction(event -> {
            List<String> choices = new ArrayList<>();
            choices.add("Giới tính");
            choices.add("Độ tuổi");

            ChoiceDialog<String> dialog = new ChoiceDialog<>("Giới tính", choices);
            dialog.setTitle("Thống kê");
            dialog.setHeaderText("Lựa chọn thống kê");
            dialog.setContentText("Thống kê theo:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                if(result.get().equals("Giới tính")){
                    Parent parent = null;
                    FXMLLoader loader = new FXMLLoader();
                    try {
                        loader.setLocation(getClass().getResource("/view/NK/thongKe.fxml"));
                        parent = loader.load();
                        Scene scene = new Scene(parent);
                        Stage stageThongKe = new Stage();
                        Image image = new Image("/drawable/icon.png");
                        stageThongKe.getIcons().add(image);
                        stageThongKe.setTitle("Thống kê theo giới tính");
                        stageThongKe.setScene(scene);
                        stageThongKe.initModality(Modality.WINDOW_MODAL);
                        stageThongKe.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());
                        stageThongKe.show();
//                        ControllerThongKe controllerThongKe = loader.getController();
//                        controllerThongKe.theoGioiTinh();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if(result.get().equals("Độ tuổi")){
                    Parent parent = null;
                    FXMLLoader loader = new FXMLLoader();
                    try {
                        loader.setLocation(getClass().getResource("/view/NK/thongKe.fxml"));
                        parent = loader.load();
                        Scene scene = new Scene(parent);
                        Stage stageThongKe = new Stage();
                        Image image = new Image("/drawable/icon.png");
                        stageThongKe.getIcons().add(image);
                        stageThongKe.setTitle("Thống kê theo giới tính");
                        stageThongKe.setScene(scene);
                        stageThongKe.initModality(Modality.WINDOW_MODAL);
                        stageThongKe.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());
                        stageThongKe.show();
//                        ControllerThongKe controllerThongKe = loader.getController();
//                        controllerThongKe.theoTuoi();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void refreshTable(){
        Main.nhanKhauArrayList.addAll(new NhanKhauDAO().getAll());
        FilteredList<NhanKhau> filteredList = new FilteredList<>(NhanKhauObservableList, p ->true);
        txtTimKiem.textProperty().addListener((observable, oldVable, newValue) ->{
            filteredList.setPredicate(nhanKhau->{
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (nhanKhau.getMaHoKhau().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                } else if(nhanKhau.getHoTen().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                return false;
            });
        });

        SortedList<NhanKhau> nhanKhaus = new SortedList<>(filteredList);

        nhanKhaus.comparatorProperty().bind(tableViewNhanKhau.comparatorProperty());
        tableViewNhanKhau.setItems(nhanKhaus);
    }
}
