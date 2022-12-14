package Controller;


import Infomation.CacHo;
import Infomation.Info;
import Infomation.KhoanThu;
import Infomation.NhanKhau;
import com.example.nhankhaumanager.DBUtil;
import com.example.nhankhaumanager.FadeAnimationMode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
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
    @FXML private TableColumn<Info , String> maHo,maHo_KhoanThu;
    @FXML private TableColumn<Info,String> tenChuHo, tenChuHo_KhoanThu;
    @FXML private TableColumn<CacHo,String> soNha;
    @FXML private TableColumn<CacHo,String> Duong;
    @FXML private TableColumn<CacHo,String> Phuong;
    @FXML private TableColumn<CacHo,String> Quan;
    @FXML private BorderPane pnCacHo;
    @FXML private TextField findCacHo;
    private ObservableList<CacHo> listCacHo;
    public void DanhSachCacHoButton(ActionEvent e){
        pnCacHo.toFront();
    }
    public void addCacHo(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ThemHoScene.fxml"));
        Parent root = loader.load();
        ThemHoController them = loader.getController();
        them.setInit(listCacHo);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Thêm hộ khẩu");
        stage.show();
    }
    public void editCacHo(ActionEvent e) throws IOException {
        CacHo ho = tableCacHo.getSelectionModel().getSelectedItem();
        int i = tableCacHo.getSelectionModel().getSelectedIndex();

        if(ho != null){

            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditCacHo.fxml"));
            Parent root = loader.load();
            EditCacHo_controller edit = loader.getController();
            edit.getTextField(ho,listCacHo,i);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit hộ khẩu");
            stage.show();
        }
    }
    public void truyNhapHo(ActionEvent e){
        listNhanKhau.clear();
        CacHo ho = tableCacHo.getSelectionModel().getSelectedItem();

        if(ho != null){
            new FadeAnimationMode(borderPaneNhanKhau);
            borderPaneNhanKhau.toFront();
            lbMaho.setText(ho.getMaHo());
            lbTenChuHo.setText(ho.getTen());
            tableNhanKhau(ho);
        }
    }
    public void XoaHo(ActionEvent e){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("WARNING!");
        alert.setHeaderText("Are you sure for removing?");
        alert.setContentText("You're about to remove!");
        if(alert.showAndWait().get() == ButtonType.OK){
            Info ho = tableCacHo.getSelectionModel().getSelectedItem();
            try {
                Connection c = DBUtil.getConnection();
                String sql = "DELETE FROM CACHO WHERE MAHO = ?;";
                PreparedStatement pst = c.prepareStatement(sql);
                pst.setString(1,ho.getMaHo());

                int ok = pst.executeUpdate();
                if(ok > 0){
                    System.out.println("delete" + ok + "rows successfully!");
                }
                else System.out.println("Failed");
                DBUtil.Close(c);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            listCacHo.remove(ho);
        }
    }
    private void loadDataCacHo(){
        try {
            Connection conn = DBUtil.getConnection();

            String sqlCacHo = "SELECT * FROM CACHO" ;

            PreparedStatement pst = conn.prepareStatement(sqlCacHo);
            ResultSet rs = pst.executeQuery();

            while(rs.next()){
                listCacHo.add(new CacHo(rs.getString("MAHO"),
                        rs.getString("TENCHUHO"),
                        rs.getInt("SONHA"),
                        rs.getString("DUONG"),
                        rs.getString("PHUONG"),
                        rs.getString("QUAN")));
            }
            DBUtil.Close(conn);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    // ---------------******************************----------------------------
    //                  ************************


    //                      Khoản thu part

    //                   *******************************************


    @FXML private BorderPane pnKhoanThu;
    @FXML private TableView<KhoanThu> tableKhoanThu;
    @FXML private TableColumn<KhoanThu,Double> SoTienDaThu;
    @FXML private TableColumn<KhoanThu,Double> ConPhaiDong;
    @FXML private TextField findKhoanThu;
    private ObservableList<KhoanThu> listKhoanThu;
    public void KhoanThuButton(ActionEvent e){
        pnKhoanThu.toFront();
    }



    // ----------------------***************************-------------------------

    //                        **************************

    //                              ************

    //                               Thống kê part


    public void ThongKeButton(ActionEvent e){}


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
    private void tableNhanKhau(CacHo nhanKhau){

        try {
            Connection conn = DBUtil.getConnection();

            String sql = "SELECT COUNT(*) AS SONHANKHAU FROM NHANKHAU,CACHO "
                    + "WHERE NHANKHAU.MAHO = CACHO.MAHO AND CACHO.MAHO = ?;" ;

            String sql1 = "SELECT n.MAHO, TEN FROM NHANKHAU n,CACHO c WHERE n.MAHO = c.MAHO AND c.MAHO = ?;";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,nhanKhau.getMaHo());
            ResultSet rs = pst.executeQuery();

            PreparedStatement pst1 = conn.prepareStatement(sql1);
            pst1.setString(1,nhanKhau.getMaHo());
            ResultSet rs1 = pst1.executeQuery();

            while(rs1.next()){
                listNhanKhau.add(new NhanKhau(rs1.getString("MAHO"),rs1.getString("TEN")));
            }
            rs.next();
            lbSoNhanKhau.setText(rs.getString("SONHANKHAU"));

            DBUtil.Close(conn);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadThongtinNhanKhau(ActionEvent ee){
        NhanKhau nk = tableNhanKhau.getSelectionModel().getSelectedItem();
        
        if(nk != null){
            try {
                Connection c = DBUtil.getConnection();
                
                String sql = "SELECT * FROM NHANKHAU WHERE NHANKHAU.MAHO = ? AND NHANKHAU.TEN = ?;";
                
                PreparedStatement pst = c.prepareStatement(sql);
                pst.setString(1,nk.getMaHo());
                pst.setString(2,nk.getTen());
                ResultSet rs = pst.executeQuery();

                while(rs.next()){
                    String bd = rs.getString("BIDANH");
                    String nghenghiep = rs.getString("NGHENGHIEP");
                    String noilamV = rs.getString("NOILAMVIEC");
                    String soCCCD = rs.getString("SOCMND");
                    String ngayCap = rs.getString("NGAYCAP");
                    String Noicap = rs.getString("NOICAP");
                    String NDKTT = rs.getString("NGAYDANGKYTHUONGTRU");
                    String noiChuyenden = rs.getString("NOICHUYENDEN");
                    String thuongtrubf = rs.getString("THUONGTRUBEFORE");
                    String ngayChuyendi = rs.getString("NGAYCHUYENDI");

                    QuanHeChuHo.setText(rs.getString("QUANHECHUHO"));
                    ten.setText(rs.getString("TEN"));
                    ngaysinh.setText(rs.getString("NGAYSINH"));
                    noisinh.setText(rs.getString("NOISINH"));
                    quequan.setText(rs.getString("QUEQUAN"));
                    dantoc.setText(rs.getString("DANTOC"));
                    ghichu.setText((rs.getString("GHICHU")));


                    if( bd != null)bidanh.setText(bd);
                    else bidanh.setText("");
                    if( nghenghiep!= null)ngheNghiep.setText(nghenghiep);
                    else ngheNghiep.setText("");
                    if(noilamV != null) noilamviec.setText(noilamV);
                    else noilamviec.setText("");
                    if(soCCCD != null) soCMND.setText(soCCCD);
                    else soCMND.setText("");
                    if( ngayCap != null) ngaycap.setText(ngayCap);
                    else ngaycap.setText("");
                    if( Noicap != null) noicap.setText(Noicap);
                    else noicap.setText("");
                    if( NDKTT != null) ngaydangkythuongtru.setText(NDKTT);
                    else ngaydangkythuongtru.setText("");
                    if( noiChuyenden != null) noichuyenden.setText(noiChuyenden);
                    else noichuyenden.setText("");
                    if( thuongtrubf != null) thuongtrubefore.setText(thuongtrubf);
                    else thuongtrubefore.setText("");
                    if( ngayChuyendi != null) ngaychuyendi.setText(ngayChuyendi);
                    else ngaychuyendi.setText("");
                }
                DBUtil.Close(c);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }
    }
    public void backCacHo(ActionEvent e){
        new FadeAnimationMode(pnB1);
        pnB1.toFront();
    }
    public void addNhanKhau(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ThemNhanKhau.fxml"));
        Parent root = loader.load();
        ThemNhanKhauController them = loader.getController();
        them.init(listNhanKhau,lbMaho.getText());
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Thêm Nhân Khẩu");
        stage.show();
    }
    public void xoaNhanKhau(ActionEvent e) throws ClassNotFoundException, SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure to remove it?");
        alert.setTitle("WARNING!");
        alert.setHeaderText("you're about to remove!");
        if(alert.showAndWait().get() == ButtonType.OK){
            NhanKhau nk = tableNhanKhau.getSelectionModel().getSelectedItem();
            Connection con = DBUtil.getConnection();
            String sql = "DELETE FROM NHANKHAU WHERE TEN = ?;";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1,nk.getTen());
            pst.executeUpdate();
            listNhanKhau.remove(nk);
            DBUtil.Close(con);
        }

    }
    public void editNhanKhau(ActionEvent e) throws IOException {
        NhanKhau nk = tableNhanKhau.getSelectionModel().getSelectedItem();
        int index = tableNhanKhau.getSelectionModel().getSelectedIndex();

        if(nk != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditNhanKhau.fxml"));
            Parent root = loader.load();
            EditNhanKhauController edit = loader.getController();
            edit.init(listNhanKhau,index);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
    public void LichSuThayDoi(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LichsuThaydoiNhanKhau.fxml"));
        Parent root = loader.load();
        LichSuChinhSuaNhanKhau history = loader.getController();
        history.init(lbMaho.getText());
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listCacHo = FXCollections.observableArrayList();
        maHo.setCellValueFactory(new PropertyValueFactory<Info,String>("maHo"));
        tenChuHo.setCellValueFactory(new PropertyValueFactory<Info,String>("ten"));
        soNha.setCellValueFactory(new PropertyValueFactory<CacHo,String>("soNha"));
        Duong.setCellValueFactory(new PropertyValueFactory<CacHo,String>("Duong"));
        Phuong.setCellValueFactory(new PropertyValueFactory<CacHo,String>("Phuong"));
        Quan.setCellValueFactory(new PropertyValueFactory<CacHo,String>("Quan"));

        listKhoanThu = FXCollections.observableArrayList();
        maHo_KhoanThu.setCellValueFactory(new PropertyValueFactory<Info,String>("MaHo"));
        tenChuHo_KhoanThu.setCellValueFactory(new PropertyValueFactory<Info,String>("ten"));
        SoTienDaThu.setCellValueFactory(new PropertyValueFactory<KhoanThu,Double>("SoTienDaThu"));
        ConPhaiDong.setCellValueFactory(new PropertyValueFactory<KhoanThu,Double>("ConPhaiDong"));

        HovaTen.setCellValueFactory(new PropertyValueFactory<NhanKhau,String>("ten"));

        tableNhanKhau.setItems(listNhanKhau);
        tableCacHo.setItems(listCacHo);
        tableKhoanThu.setItems(listKhoanThu);

        FilteredList<CacHo> filteredData = new FilteredList<>(listCacHo, bss->true);
        findCacHo.textProperty().addListener((obser,oValue,nValue)->{
            filteredData.setPredicate(er->{
                if(nValue == null || nValue.isEmpty()){
                    return true;
                }
                String tmp = nValue.toLowerCase();
                if(er.getTen().toLowerCase().indexOf(tmp) != -1){
                    return true;
                }
                return false;
            });
        });

        FilteredList<NhanKhau> filteredNhanKhau = new FilteredList<>(listNhanKhau,b -> true);
        findNhanKhau.textProperty().addListener((observeble,ovalue,nvalue) -> {
            filteredNhanKhau.setPredicate( er ->{

                if(nvalue == null ||  nvalue.isEmpty()){
                    return true;
                }
                String name = nvalue.toLowerCase();
                if(er.getTen().toLowerCase().indexOf(name) != -1){
                    return true;
                }
                return false;
            });
        });

        tableNhanKhau.setItems(filteredNhanKhau);
        tableCacHo.setItems(filteredData);

        loadDataCacHo();
    }
}
