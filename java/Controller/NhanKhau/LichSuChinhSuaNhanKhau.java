package Controller.NhanKhau;

import com.example.nhankhaumanager.DBUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LichSuChinhSuaNhanKhau {
    private String Maho;
    @FXML private TextArea textDisplayHis;
    @FXML private TextArea textChange;
    @FXML private DatePicker dateChange;
    @FXML private Label notify;
    public void init(String MaHo){
        this.Maho = MaHo;
        Connection con = null;
        try {
            con = DBUtil.getConnection();

            String sql = "SELECT LICHSUTHAYDOI FROM CACHO WHERE MAHO = " + MaHo + ";";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                String text = rs.getString("LICHSUTHAYDOI");
                textDisplayHis.setText(text);
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DBUtil.Close(con);
        textDisplayHis.setDisable(true);
    }

    public void Change(ActionEvent ev){
        String s1 = textChange.getText();
        LocalDate s2 = dateChange.getValue();

        if(!s1.isEmpty()){
            if(s2 == null ){
                notify.setTextFill(Color.RED);
                notify.setText("You must fill the date!");
                return;
            }
            else{
                String myDate = s2.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                String change = s1 + " Date: " + myDate;

                Connection con = null;
                try {
                    con = DBUtil.getConnection();

                    String sql = "UPDATE CACHO SET LICHSUTHAYDOI = ?"+
                            " WHERE MAHO = ?;";
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1,change);
                    pst.setString(2,Maho);

                    if(pst.executeUpdate() > 0){
                        notify.setTextFill(Color.GREEN);
                        notify.setText("Update successfully!");
                        textDisplayHis.appendText("\n");
                        textDisplayHis.appendText(change);
                    }
                    else{
                        notify.setTextFill(Color.RED);
                        notify.setText("Failed!!");
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                DBUtil.Close(con);

            }
        }

    }
    public void Cancer(ActionEvent e){
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        if(!textChange.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Haven't saved yet?");
            alert.setContentText("Are you sure to cancer?");
            alert.setTitle("WARNING");
            if(alert.showAndWait().get() == ButtonType.OK){
                stage.close();
            }
        }
        else stage.close();
    }
}
