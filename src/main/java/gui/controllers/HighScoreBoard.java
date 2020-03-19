package gui.controllers;

import database.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

@SuppressWarnings({
        "PMD.DataflowAnomalyAnalysis", "PMD.CloseResource"})

public class HighScoreBoard {

    /**
     * this methods creates the highscore board.
     * @return returns highscore of of 5 users.
     * @throws SQLException database query exception.
     */
    public static TableView getBoard() throws SQLException {

        TableView tableView = new TableView<>();
        ObservableList<ObservableList> data = FXCollections.observableArrayList();

        try {
            PreparedStatement st;
            ResultSet rs;
            String query =
                    "select distinct * from highscore Order by score DESC FETCH FIRST 5 ROWS ONLY";
            st = DatabaseConnection.getConnection().prepareStatement(query);
            rs = st.executeQuery();
            // Make the columns. Reads from the database
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {

                final int j = i;
                TableColumn col =
                        new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory((
                        Callback<TableColumn.CellDataFeatures<ObservableList, String>,
                        ObservableValue<String>>) param ->
                        new SimpleStringProperty(param.getValue().get(j).toString()));
                tableView.getColumns().addAll(col);
            }
            // Fill the columns and rows with data. Reads from the database
            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }

            tableView.setItems(data);

            Stage primaryStage = new Stage();
            primaryStage.setScene(new Scene(tableView, 300, 300));
            primaryStage.show();
            st.close();
            rs.close();
        }  catch (SQLException e) {
            e.printStackTrace();
        }
        return tableView;
    }


}
