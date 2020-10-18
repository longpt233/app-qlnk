package controller.SHK;

import dao.ConnectSQLServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.History;
import model.NhanKhau;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerHistory implements Initializable {

    @FXML
    private Button btnDong;
    @FXML
    private TableView<History> tableViewHistory;
    @FXML
    private TableColumn<History, String> clThoiGian;
    @FXML
    private TableColumn<History, String> clNoiDung;
    ObservableList<History> histories;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clThoiGian.setCellValueFactory(new PropertyValueFactory<History, String>("thoiGian"));
        clNoiDung.setCellValueFactory(new PropertyValueFactory<History, String>("noiDung"));

        btnDong.setOnAction(event -> {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        });
    }

    public void khoiTao(String maHoKhau){
        List<History> historyList = ConnectSQLServer.pullHistory(maHoKhau);
        if (historyList != null){
            histories = FXCollections.observableArrayList(historyList);
            tableViewHistory.setItems(histories);
        }
    }
}
