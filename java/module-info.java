module com.example.nhankhaumanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;

    exports Controller;
    exports Infomation;
    opens Controller to javafx.fxml;
    exports Main;
    opens Main to javafx.fxml;
    exports Controller.CacHo;
    opens Controller.CacHo to javafx.fxml;
    exports Controller.NhanKhau;
    opens Controller.NhanKhau to javafx.fxml;
    exports Controller.KhoanThu;
    opens Controller.KhoanThu to javafx.fxml;
    exports Controller.ThongKe;
    opens Controller.ThongKe to javafx.fxml;
}