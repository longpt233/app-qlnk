package controller.NK;

import dao.ConnectSQLServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerThongKe implements Initializable {

    @FXML
    private PieChart pcGioiTinh;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void theoGioiTinh(){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Nam", ConnectSQLServer.getSLNamNu("Nam")),
                new PieChart.Data("Nữ", ConnectSQLServer.getSLNamNu("Nu"))
        );
        pcGioiTinh.setData(pieChartData);
        pcGioiTinh.setVisible(true);
    }

    public void theoTuoi(){
    }
}
