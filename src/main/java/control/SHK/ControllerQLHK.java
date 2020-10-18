package control.SHK;


import control.Main;
import dao.imp.SoSoHoKhauDAO;
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
import model.SoHoKhauModel;


import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControllerQLHK implements Initializable {
    @FXML
    private TableView tableViewHoKhau;
    @FXML
    private TableColumn<SoHoKhauModel, String> tableColumnMaHoKhau;
    @FXML
    private TableColumn<SoHoKhauModel, String> tableColumnTenChuHo;
    @FXML
    private TableColumn<SoHoKhauModel, String> tableColumnCCCD;
    @FXML
    private TableColumn<SoHoKhauModel, String> tableColumnDiaCHi;
    @FXML
    private TableColumn<SoHoKhauModel, Integer> tableColumnSoNhanKhau;
    @FXML
    private TextField txtTimKiem;
    @FXML
    private Button btnQLNK;
    @FXML
    private Button btnShowAndEdit;
    @FXML
    private Button btnThemMoiSHK;
    @FXML
    private Button btnThongKe;
    @FXML
    private Button btnXoa;

    private ObservableList<SoHoKhauModel> soHoKhauModelObservableList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tableColumnMaHoKhau.setCellValueFactory(new PropertyValueFactory<SoHoKhauModel, String>("maHoKhau"));
        //new PropertyValueFactory<SoHoKhauModel, String>("maHoKhau")
        //=> tuc la lay thuoc tinh maHoKhau tu doi tuong so ho khau model
        //=> set cho cot tablecolummahokhau
        tableColumnTenChuHo.setCellValueFactory(new PropertyValueFactory<SoHoKhauModel, String>("tenChuHo"));
        tableColumnCCCD.setCellValueFactory(new PropertyValueFactory<SoHoKhauModel, String>("CCCD"));
        tableColumnDiaCHi.setCellValueFactory(new PropertyValueFactory<SoHoKhauModel, String>("DiaChi"));
        tableColumnSoNhanKhau.setCellValueFactory(new PropertyValueFactory<SoHoKhauModel, Integer>("soNhanKhau"));


        soHoKhauModelObservableList = FXCollections.observableList(Main.soHoKhauModelArrayList);
        refreshTable();

        btnQLNK.setOnAction(actionEvent->{
            try {
                Parent blad = FXMLLoader.load(getClass().getResource("/view/NK/quanLyNhanKhau.fxml"));
                Scene scene = new Scene(blad);
                Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                appStage.setTitle("QLNK");
                appStage.setScene(scene);
                appStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        btnShowAndEdit.setOnAction(event-> {
            ObservableList<SoHoKhauModel> soHoKhaus = tableViewHoKhau.getSelectionModel().getSelectedItems();
            if (soHoKhaus.get(0)!=null){
                Main.nhanKhauTrongHo.removeAll(Main.nhanKhauTrongHo);
                Main.nhanKhauTrongHo=new SoSoHoKhauDAO().getNhanKhauTrongHoById(soHoKhaus.get(0).getMaHoKhau());
                Parent parent = null;
                FXMLLoader loader = new FXMLLoader();
                try {
                    loader.setLocation(getClass().getResource("/view/SHK/suaSHK.fxml"));
                    parent = loader.load();
                    Scene scene = new Scene(parent);
                    Stage stageChinhSua = new Stage();
                    stageChinhSua.setTitle("Chỉnh sửa sổ hộ khẩu");
                    stageChinhSua.setScene(scene);
                    stageChinhSua.initModality(Modality.WINDOW_MODAL);
                    stageChinhSua.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());
                    // cai nay la j
                    //
                    ControllerSuaSHK controllerSuaSHK = loader.getController();
                    controllerSuaSHK.setSoHoKhau(soHoKhaus.get(0));
                    stageChinhSua.showAndWait();
                    refreshTable();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnXoa.setOnAction(actionEvent->{
            ObservableList<SoHoKhauModel> soHoKhaus = tableViewHoKhau.getSelectionModel().getSelectedItems();
            if (soHoKhaus.get(0) != null){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Xoá sổ hộ khẩu");
                alert.setHeaderText("Bao gồm xoá tất cả các nhân khẩu trong sổ");
                alert.setContentText("Mã sổ: "+soHoKhaus.get(0).getMaHoKhau()+ "\nTên chủ hộ: " +soHoKhaus.get(0).getTenChuHo());
                alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
                alert.showAndWait().ifPresent((btnType)->{
                    if (btnType == ButtonType.OK){
                        new SoSoHoKhauDAO().delete(soHoKhaus.get(0).getMaHoKhau());
                        refreshTable();
                    } else if (btnType == ButtonType.CANCEL){

                    }

                });
            }
        });

        btnThemMoiSHK.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Thêm sổ hộ khẩu");
            dialog.setContentText("Điền địa chỉ:");
            // Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            String idSHK="";
            if(!result.get().equals("")){
//                idSHK = ConnectSQLServer.themSoHoKhau(result.get());
                refreshTable();
                Parent parent = null;
                FXMLLoader loader = new FXMLLoader();
                try {
                    loader.setLocation(getClass().getResource("/view/SHK/themSHK.fxml"));
                    parent = loader.load();
                    Scene scene = new Scene(parent);
                    Stage stageChinhSua = new Stage();
                    Image image = new Image("/drawable/icon.png");
                    stageChinhSua.getIcons().add(image);
                    stageChinhSua.setTitle("Thêm sổ hộ khẩu");
                    stageChinhSua.setScene(scene);
                    stageChinhSua.initModality(Modality.WINDOW_MODAL);
                    stageChinhSua.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());
//                    ControllerThemSHK controllerThemSHK = loader.getController();
//                    controllerThemSHK.setMaVaDiaChiHoKhau(idSHK, result.get());
                    stageChinhSua.showAndWait();
                    refreshTable();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void refreshTable(){
        Main.soHoKhauModelArrayList.removeAll(Main.soHoKhauModelArrayList);
        Main.soHoKhauModelArrayList.addAll(new SoSoHoKhauDAO().getAll());
        // phai la add all chu khong dc = ???
        FilteredList<SoHoKhauModel> filteredList = new FilteredList<>(soHoKhauModelObservableList, p ->true);
        txtTimKiem.textProperty().addListener((observable, oldVable, newValue) ->{
            filteredList.setPredicate(soHoKhau->{
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (soHoKhau.getMaHoKhau().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                } else if(soHoKhau.getTenChuHo()!= null){
                    if(soHoKhau.getTenChuHo().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    } else if(soHoKhau.getCCCD().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }
                }
                return false;
            });
        });
        SortedList<SoHoKhauModel> hoKhauSortedList = new SortedList<>(filteredList);
        hoKhauSortedList.comparatorProperty().bind(tableViewHoKhau.comparatorProperty());
        tableViewHoKhau.setItems(hoKhauSortedList);
    }
}
