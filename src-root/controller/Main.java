package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.NhanKhau;
import model.SoHoKhauModel;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    public static List<SoHoKhauModel> soHoKhauModelArrayList = new ArrayList<>();
    public static List<NhanKhau> nhanKhauArrayList = new ArrayList<>();
    public static List<NhanKhau> nhanKhauTrongHo = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/SHK/quanLyHoKhau.fxml"));
        Image image = new Image("/drawable/icon.png");
        primaryStage.getIcons().add(image);
        primaryStage.setTitle("QLHK");
        primaryStage.setScene(new Scene(root, 1510, 810));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
