package Controller;

import Controller.CacHo.MainCacHo;
import Controller.KhoanThu.MainKhoanThu;
import Controller.NhanKhau.MainNhanKhau;
import Controller.ThongKe.ThongKeController;
import Infomation.CacHo;
import Infomation.Info;
import Infomation.KhoanThu;
import Infomation.NhanKhau;
import com.example.nhankhaumanager.FadeAnimationMode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    // ----------------------***************************-------------------------

    //                        **************************

    //                              ************

    //                               login part

    // ---------------------------------------------------------------------------
    @FXML Button loginButton;
    @FXML SplitPane pnB1;
    @FXML TextField username;
    @FXML PasswordField password;
    @FXML Label notify;
    public void LoginButton(ActionEvent e){

        String user = username.getText().replaceAll("\\s" , "");
        String pass = password.getText();

        if(user.equals("admin") && pass.equals("admin")){
            new FadeAnimationMode(pnB1);
            pnB1.toFront();
            MainCacHoController.init(tableCacHo);
        }
        else{
            notify.setTextFill(Color.RED);
            notify.setText("Check your account again!");
        }
    }


    // **********************************----------------****************************//

                            //*****************************//

    //                         Các hộ part

    //-------------------------*****************------------------------------------//

    @FXML private TableView<CacHo> tableCacHo;
    @FXML private TableColumn<String , String> maHo;
    @FXML private TableColumn<Info,String> tenChuHo;
    @FXML private TableColumn<String,String> soNha;
    @FXML private TableColumn<CacHo,String> Duong;
    @FXML private TableColumn<CacHo,String> Phuong;
    @FXML private TableColumn<CacHo,String> Quan;
    @FXML private BorderPane pnCacHo;
    @FXML private TextField findCacHo;
    private ObservableList<CacHo> listCacHo = FXCollections.observableArrayList();
    private MainCacHo MainCacHoController = new MainCacHo(listCacHo);
    public void DanhSachCacHoButton(ActionEvent e){
        pnCacHo.toFront();
        MainCacHoController.init(tableCacHo);
    }
    private void initCacHoTable(){
        maHo.setCellValueFactory(new PropertyValueFactory<String,String>("maHo"));
        tenChuHo.setCellValueFactory(new PropertyValueFactory<Info,String>("ten"));
        soNha.setCellValueFactory(new PropertyValueFactory<String,String>("soNha"));
        Duong.setCellValueFactory(new PropertyValueFactory<CacHo,String>("Duong"));
        Phuong.setCellValueFactory(new PropertyValueFactory<CacHo,String>("Phuong"));
        Quan.setCellValueFactory(new PropertyValueFactory<CacHo,String>("Quan"));
        FilteredList<CacHo> filteredList = new FilteredList<>(listCacHo,b->true);
        findCacHo.textProperty().addListener((obser,oValue,nValue)->{
            filteredList.setPredicate(er->{
                if(nValue == null || nValue.isEmpty())return true;
                String tmp = nValue.toLowerCase();
                if(er.getTen().toLowerCase().contains(tmp))return true;
                return false;
            });
        });
        tableCacHo.setItems(filteredList);
    }
    public void addCacHo(ActionEvent e) throws IOException {
        MainCacHoController.addCacHo();
    }
    public void editCacHo(ActionEvent e) throws IOException {
        MainCacHoController.editCacHo();
    }
    public void truyNhapHo(ActionEvent e){
        new FadeAnimationMode(borderPaneNhanKhau);
        borderPaneNhanKhau.toFront();
        initNhanKhau();
        MainCacHoController.TruyNhapHo(MainNhanKhauController,tableNhanKhau,listNhanKhau,lbMaho,lbTenChuHo,lbSoNhanKhau);
    }
    public void XoaHo(ActionEvent e){
        MainCacHoController.XoaHo();
    }

    // ---------------******************************----------------------------
    //                  ************************


    //                      Khoản thu part

    //                   *******************************************

    @FXML private BorderPane pnKhoanThu;
    @FXML private TableView<KhoanThu> tableKhoanThu;
    @FXML private TableColumn<String,String> MHKhoanThu;
    @FXML private TableColumn<String,String> CHKhoanThu;
    @FXML private TableColumn<Double,Double> Tong;
    @FXML private TableColumn<Double,Double> DaThu;
    @FXML private TableColumn<Double,Double> ConThieu;
    @FXML private TableColumn<Double,Double> UngHo;
    @FXML private TextField findKhoanThu;
    @FXML private ChoiceBox<String> choiceBoxKhoanThu;
    @FXML private Label TongTienCuaDot;
    @FXML private Label SoHoDaNop;
    private MainKhoanThu MainKhoanThuController = new MainKhoanThu();
    private ObservableList<KhoanThu> listKhoanThu = FXCollections.observableArrayList();
    public void KhoanThuButton(ActionEvent e){
        pnKhoanThu.toFront();
        MainKhoanThuController.init(tableKhoanThu,listKhoanThu,TongTienCuaDot,SoHoDaNop);
    }
    public void CapNhatKhoanThuButton(ActionEvent e){
        MainKhoanThuController.CapNhatKhoanThu();
    }
    public void xemButton(ActionEvent e){
        String dot = choiceBoxKhoanThu.getValue();
        if(dot != null ) MainKhoanThuController.xem(dot);
    }
    public void refresh(ActionEvent e){
        MainKhoanThuController.refresh();
    }
    public void ChiTietKhoanThuButton(ActionEvent e){
        MainKhoanThuController.ChiTietKhoanThu();
    }
    public void LichSuCapNhatKhoanThuButton(ActionEvent e){
        MainKhoanThuController.LichSuKhoanThu();
    }
    private void initKhoanThuTable(){
        MHKhoanThu.setCellValueFactory(new PropertyValueFactory<String,String>("MaHo"));
        CHKhoanThu.setCellValueFactory(new PropertyValueFactory<String,String>("ten"));
        Tong.setCellValueFactory(new PropertyValueFactory<Double,Double>("tong"));
        DaThu.setCellValueFactory(new PropertyValueFactory<Double,Double>("DaThu"));
        ConThieu.setCellValueFactory(new PropertyValueFactory<Double,Double>("ConThieu"));
        UngHo.setCellValueFactory(new PropertyValueFactory<Double,Double>("UngHo"));
        FilteredList<KhoanThu> filteredList = new FilteredList<>(listKhoanThu,b->true);
        findKhoanThu.textProperty().addListener((obs,oValue,nValue)->{
            filteredList.setPredicate(er->{
                if(nValue == null || nValue.isEmpty())return true;
                String tmp = nValue.toLowerCase();
                if(er.getTen().toLowerCase().contains(tmp))return true;
                return false;
            });
        });
        tableKhoanThu.setItems(filteredList);
        choiceBoxKhoanThu.getItems().addAll("Đợt 1","Đợt 2");
    }



    // ----------------------***************************-------------------------

    //                        **************************

    //                              ************

    //                               Thống kê part

    @FXML private BorderPane PaneThongKe;
    @FXML private Label counter;
    @FXML private ChoiceBox<String> TvTtChoiceBox;
    @FXML private ChoiceBox<String> ageChoiceBox;
    @FXML private ChoiceBox<String> sexChoiceBox;
    @FXML private TableView<NhanKhau> tableThongKe;
    @FXML private TableColumn<String,String> maHoThongKe;
    @FXML private TableColumn<String,String> tenThongKe;
    @FXML private TableColumn<String,String> gioiTinh;
    @FXML private TableColumn<Boolean,String> tvtt;
    @FXML private TableColumn<Integer,String> tuoi;
    private ObservableList<NhanKhau> listThongKe = FXCollections.observableArrayList();
    private ThongKeController thongKeController = new ThongKeController();
    public void ThongKeButton(ActionEvent e){PaneThongKe.toFront();}
    private void initThongKeTable(){
        maHoThongKe.setCellValueFactory(new PropertyValueFactory<String,String>("maHo"));
        tenThongKe.setCellValueFactory(new PropertyValueFactory<String,String>("ten"));
        gioiTinh.setCellValueFactory(new PropertyValueFactory<String,String>("gioiTinh"));
        tvtt.setCellValueFactory(new PropertyValueFactory<Boolean,String>("tvtt"));
        tuoi.setCellValueFactory(new PropertyValueFactory<Integer,String>("tuoi"));
        tableThongKe.setItems(listThongKe);
        TvTtChoiceBox.getItems().addAll( "Tất cả","No", "Yes");
        ageChoiceBox.getItems().addAll("Tất cả","3-5", "6-10","11-14" ,"15-17", "18-29", "30-50","51-100");
        sexChoiceBox.getItems().addAll( "Tất cả","Nam", "Nữ");
    }
    public void FilterButton(ActionEvent e){
        listThongKe.clear();
        thongKeController.init(listThongKe,TvTtChoiceBox,ageChoiceBox,sexChoiceBox,counter);
        thongKeController.Loc();
    }



    // ---------------------------------------------------------------------------

    // ----------------------***************************-------------------------

    //                        **************************

    //                              ************

    //                               Nhân khẩu part

    // ---------------------------------------------------------------------------

    @FXML private TableView<NhanKhau> tableNhanKhau;
    @FXML private TableColumn<NhanKhau,String> HovaTen;
    @FXML  private BorderPane borderPaneNhanKhau;
    @FXML private TextField findNhanKhau;
    @FXML private Label lbMaho;
    @FXML private Label lbTenChuHo;
    @FXML private Label lbSoNhanKhau;
    @FXML private Label QuanHeChuHo;
    @FXML private Label ten;
    @FXML private Label bidanh;
    @FXML private Label ngaysinh;
    @FXML private Label noisinh;
    @FXML private Label quequan;
    @FXML private Label dantoc;
    @FXML private Label ngheNghiep;
    @FXML private Label noilamviec;
    @FXML private Label soCMND;
    @FXML private Label ngaycap;
    @FXML private Label noicap;
    @FXML private Label ngaydangkythuongtru;
    @FXML private Label thuongtrubefore;
    @FXML private Label noichuyenden;
    @FXML private Label ngaychuyendi;
    @FXML private Label ghichu;
    private ObservableList<NhanKhau> listNhanKhau = FXCollections.observableArrayList();
    MainNhanKhau MainNhanKhauController = new MainNhanKhau();
    private void initNhanKhauTable(){
        HovaTen.setCellValueFactory(new PropertyValueFactory<NhanKhau,String>("ten"));
        FilteredList<NhanKhau> filteredNhanKhau = new FilteredList<>(listNhanKhau,b -> true);
        findNhanKhau.textProperty().addListener((observeble,ovalue,nvalue) -> {
            filteredNhanKhau.setPredicate( er ->{
                if(nvalue == null ||  nvalue.isEmpty())
                    return true;

                String name = nvalue.toLowerCase();
                if(er.getTen().toLowerCase().contains(name))
                    return true;
                return false;
            });
        });
        tableNhanKhau.setItems(filteredNhanKhau);
    }
    private void initNhanKhau(){
        MainNhanKhauController.init(QuanHeChuHo,ten,bidanh,ngaysinh,noisinh,quequan, dantoc,
                ngheNghiep,noilamviec,soCMND,ngaycap,noicap,ngaydangkythuongtru,
                thuongtrubefore,noichuyenden,ngaychuyendi,ghichu);
    }
    public void XemChiTietNhanKhau(ActionEvent e){
        MainNhanKhauController.xemThongtinNhanKhau(false);
    }
    public void backCacHo(ActionEvent e){
        new FadeAnimationMode(pnB1);
        pnB1.toFront();
    }
    public void addNhanKhau(ActionEvent e) throws IOException {
        MainNhanKhauController.addNhanKhau();
    }
    public void xoaNhanKhau(ActionEvent e) throws ClassNotFoundException, SQLException {
        MainNhanKhauController.XoaNhanKhau();
    }
    public void editNhanKhau(ActionEvent e) throws IOException {
        MainNhanKhauController.editNhanKhau();
    }
    public void LichSuThayDoi(ActionEvent e) throws IOException {
        MainNhanKhauController.LichSuThayDoi();
    }

    // phiếu tạm trú tạm vắng

    // ***********************---------------- ************ ------------------//


    @FXML private BorderPane pnTTTV;
    @FXML private Label tttvLabel;
    public void KhaiBaoTTTV(ActionEvent e){
        pnTTTV.toFront();
    }
    public void Gui(ActionEvent e){
        tttvLabel.setTextFill(Color.GREEN);
        tttvLabel.setText("Add successfully!");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initThongKeTable();
        initNhanKhauTable();
        initCacHoTable();
        initKhoanThuTable();
    }

}


