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


import java.util.ResourceBundle;
public class ControllerThemSHK implements Initializable {
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
	private Button btnGiayKhaiSinh;


	private String maHoKhau;
	private String diaChi;
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

		khoiTaoTable();

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
				controllerThemNK.setMaHoKhau(this.maHoKhau);
				stageChinhSua.showAndWait();
				refreshTable();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		btnLuu.setOnAction(event -> {
			if (!txtDiaChi.getText().equals(diaChi)){
				ConnectSQLServer.updateDiaChi(maHoKhau, txtDiaChi.getText());
			}
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
		});

		btnHuy.setOnAction(event -> {
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			ConnectSQLServer.xoaSoHoKhau(maHoKhau);
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
			if(nhanKhaus.get(0)!=null){
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
			}
		});

		btnDatLamChuHo.setOnAction(event -> {
			ObservableList<NhanKhau> nhanKhaus = tableNhanKhau.getSelectionModel().getSelectedItems();
			if (nhanKhaus.get(0) != null){
				ConnectSQLServer.setChuHo(maHoKhau, nhanKhaus.get(0).getMaNhanKhau());
				textChuHo.setText(nhanKhaus.get(0).getHoTen());
			}
		});

		btnGiayKhaiSinh.setOnAction(event -> {
			ObservableList<NhanKhau> nhanKhaus = tableNhanKhau.getSelectionModel().getSelectedItems();
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
					ControllerGiayKhaiSinh controllerGiayKhaiSinh = loader.getController();
					controllerGiayKhaiSinh.setMaNhanKhau(nhanKhaus.get(0).getMaNhanKhau());
					stageChinhSua.showAndWait();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void khoiTaoTable(){

		Main.nhanKhauTrongHo.removeAll(Main.nhanKhauTrongHo);
		nhankhauObservableList = FXCollections.observableArrayList(Main.nhanKhauTrongHo);
		tableNhanKhau.setItems(nhankhauObservableList);
	}

	public void setMaVaDiaChiHoKhau(String maHoKhau, String diaChi) {
		this.maHoKhau = maHoKhau;
		this.diaChi = diaChi;
		textMaHoKhau.setText(maHoKhau);
		txtDiaChi.setText(diaChi);
	}

	private void refreshTable(){
		ConnectSQLServer.pullDataNhanKhauTrongHo(maHoKhau);
		nhankhauObservableList.removeAll(nhankhauObservableList);
		nhankhauObservableList = FXCollections.observableArrayList(Main.nhanKhauTrongHo);
		tableNhanKhau.setItems(nhankhauObservableList);
	}
}
