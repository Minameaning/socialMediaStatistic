//package com.example.socialmediastatistic;
//
//import javafx.application.Application;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Node;
//import javafx.scene.Scene;
//import javafx.scene.chart.*;
//import javafx.scene.control.*;
//import javafx.scene.image.Image;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.StackPane;
//import javafx.stage.Stage;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class TableViewData extends Application {
//
//    private final DataConnector dataConnector = new DataConnector();
//    private List<SocialMediaStatistic> data;
//
//    @Override
//    public void start(Stage primaryStage) {
//        Image icon = new Image(getClass().getResourceAsStream("social-media.png"));
//        primaryStage.getIcons().add(icon);
//        BorderPane root = new BorderPane();  // Initialize root
//
//        ToggleGroup toggleGroup = new ToggleGroup();
//
//        RadioButton showBarChart = new RadioButton("Bar Chart");
//        showBarChart.setToggleGroup(toggleGroup);
//
//        RadioButton showPieChart2022 = new RadioButton("2022");
//        showPieChart2022.setToggleGroup(toggleGroup);
//
//        RadioButton showPieChart2020 = new RadioButton("2020");
//        showPieChart2020.setToggleGroup(toggleGroup);
//
//        HBox radioButtonsBox = new HBox(showBarChart, showPieChart2022, showPieChart2020);
//        radioButtonsBox.setSpacing(10);
//
//        Button switchViewButton = new Button("Switch View");
//        switchViewButton.getStyleClass().add("custom-button");
//        switchViewButton.setOnAction(event -> switchView(primaryStage, root));  // Pass root to switchView
//
//        root.setBottom(createButtonBox(radioButtonsBox, switchViewButton));
//
//        data = fetchDataFromDatabase();
//        showBarChart.setSelected(true);
//        updateChart(root, data);
//
//        Scene scene = new Scene(root, 600, 400);
//        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Social Media Statistics");
//        primaryStage.show();
//
//
//        showBarChart.setOnAction(event -> updateChart(root, data));
//        showPieChart2022.setOnAction(event -> updateChart(root, data, "2022"));
//        showPieChart2020.setOnAction(event -> updateChart(root, data, "2020"));
//    }
//
//    private HBox createButtonBox(Node... nodes) {
//        HBox buttonBox = new HBox(nodes);
//        buttonBox.setSpacing(10);
//        buttonBox.setPadding(new Insets(10));
//        buttonBox.setAlignment(Pos.CENTER);
//
//        return buttonBox;
//    }
//
//    //Bar chart
//    private void updateChart(BorderPane root, List<SocialMediaStatistic> data) {
//        BarChart<String, Number> barChart = createBarChart();
//        root.setCenter(barChart);
//        populateBarChart(barChart, data);
//    }
////pie charts
//    private void updateChart(BorderPane root, List<SocialMediaStatistic> data, String year) {
//        PieChart pieChart = createPieChart();
//        root.setCenter(pieChart);
//        populatePieChart(pieChart, data, year);
//    }
//
//    private BarChart<String, Number> createBarChart() {
//        CategoryAxis xAxis = new CategoryAxis();
//        NumberAxis yAxis = new NumberAxis();
//        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
//        barChart.setTitle("Social Media Statistics (2022 vs. 2020)");
//        return barChart;
//    }
//
//    private void populateBarChart(BarChart<String, Number> barChart, List<SocialMediaStatistic> data) {
//        XYChart.Series<String, Number> series2022 = new XYChart.Series<>();
//        series2022.setName("2022");
//
//        XYChart.Series<String, Number> series2020 = new XYChart.Series<>();
//        series2020.setName("2020");
//
//        for (SocialMediaStatistic statistic : data) {
//            String platform = statistic.getPlatform();
//            double percentage2022 = statistic.getPercentage2022();
//            double percentage2020 = statistic.getPercentage2020();
//
//            series2022.getData().add(new XYChart.Data<>(platform, percentage2022));
//            series2020.getData().add(new XYChart.Data<>(platform, percentage2020));
//        }
//
//        barChart.getData().addAll(series2022, series2020);
//    }
//
//    private PieChart createPieChart() {
//        PieChart pieChart = new PieChart();
//        pieChart.setTitle("Social Media Statistics");
//        return pieChart;
//    }
//
//    private void populatePieChart(PieChart pieChart, List<SocialMediaStatistic> data, String year) {
//        pieChart.getData().clear();
//
//        for (SocialMediaStatistic statistic : data) {
//            double percentage = 0;
//            if (year.equals("2022")) {
//                percentage = statistic.getPercentage2022();
//            } else if (year.equals("2020")) {
//                percentage = statistic.getPercentage2020();
//            }
//
//            String label = String.format("%s %.1f%%", statistic.getPlatform(), percentage);
//            PieChart.Data pieData = new PieChart.Data(label, percentage);
//            pieChart.getData().add(pieData);
//        }
//    }
//
//    private List<SocialMediaStatistic> fetchDataFromDatabase() {
//        List<SocialMediaStatistic> data = new ArrayList<>();
//
//        try (Connection connection = dataConnector.connect();
//             Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery("SELECT * FROM SocialMediaStatistics")) {
//
//            while (resultSet.next()) {
//                SocialMediaStatistic statistic = new SocialMediaStatistic(
//                        resultSet.getInt("SocialRank"),
//                        resultSet.getString("Platform"),
//                        resultSet.getString("PrimaryAgeGroup"),
//                        resultSet.getDouble("Percentage2022"),
//                        resultSet.getDouble("Percentage2020")
//                );
//                data.add(statistic);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return data;
//    }
//
//    private void switchView(Stage primaryStage, BorderPane root) {
//        if (root.getCenter() instanceof BarChart) {
//            TableView<SocialMediaStatistic> tableView = createTableView();
//            root.setCenter(tableView);
//            setTableViewStyle(tableView);
//
//        } else if (root.getCenter() instanceof PieChart) {
//            TableView<SocialMediaStatistic> tableView = createTableView();
//            root.setCenter(tableView);
//            setTableViewStyle(tableView);
//
//        } else if (root.getCenter() instanceof TableView) {
//            BarChart<String, Number> barChart = createBarChart();
//            root.setCenter(barChart);
//            populateBarChart(barChart, data);
//        }
//    }
//
//    private void setTableViewStyle(TableView<SocialMediaStatistic> tableView) {
//        tableView.getStyleClass().add("custom-table");
//    }
//
//
//    private TableView<SocialMediaStatistic> createTableView() {
//        TableView<SocialMediaStatistic> tableView = new TableView<>();
//        setTableViewStyle(tableView);
//
//
//        // Add padding
//        tableView.setPadding(new Insets(15));
//
//        BorderPane root = new BorderPane();
//        root.setCenter(tableView);
//
//        TableColumn<SocialMediaStatistic, Integer> rankColumn = new TableColumn<>("Rank");
//        rankColumn.setCellValueFactory(data -> data.getValue().rankProperty().asObject());
//
//
//        TableColumn<SocialMediaStatistic, String> platformColumn = new TableColumn<>("Platform");
//        platformColumn.setCellValueFactory(data -> data.getValue().platformProperty());
//
//        TableColumn<SocialMediaStatistic, String> primaryAgeGroupColumn = new TableColumn<>("Primary Age Group");
//        primaryAgeGroupColumn.setCellValueFactory(data -> data.getValue().primaryAgeGroupProperty());
//
//
//        TableColumn<SocialMediaStatistic, Number> percentage2022Column = new TableColumn<>("2022");
//        percentage2022Column.setCellValueFactory(data -> data.getValue().percentage2022Property());
//
//
//        TableColumn<SocialMediaStatistic, Number> percentage2020Column = new TableColumn<>("2020");
//        percentage2020Column.setCellValueFactory(data -> data.getValue().percentage2020Property());
//
//
//
//        tableView.getColumns().addAll(rankColumn, platformColumn, primaryAgeGroupColumn, percentage2022Column, percentage2020Column);
//
//        ObservableList<SocialMediaStatistic> observableList = FXCollections.observableArrayList(data);
//        tableView.setItems(observableList);
//
//
//
//        return tableView;
//    }
//
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
package com.example.socialmediastatistic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TableViewData extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChartView.fxml"));
        Parent root = loader.load();
        Image icon = new Image(getClass().getResourceAsStream("social-media.png"));
        primaryStage.getIcons().add(icon);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Social Media Statistics");

        ChartController chartController = loader.getController();
        chartController.setData(fetchDataFromDatabase());
        chartController.updateChart();

        primaryStage.show();
    }


    private List<SocialMediaStatistic> fetchDataFromDatabase() throws SQLException {
        List<SocialMediaStatistic> data = new ArrayList<>();

        final DataConnector dataConnector = new DataConnector();
        try (Connection connection = dataConnector.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM SocialMediaStatistics")) {

            while (resultSet.next()) {

                int socialRank = resultSet.getInt("SocialRank");
                String platform = resultSet.getString("Platform");
                String primaryAgeGroup = resultSet.getString("PrimaryAgeGroup");
                double percentage2022 = resultSet.getDouble("Percentage2022");
                double percentage2020 = resultSet.getDouble("Percentage2020");

                SocialMediaStatistic statistic = new SocialMediaStatistic(
                        socialRank,
                        platform,
                        primaryAgeGroup,
                        percentage2022,
                        percentage2020
                );
                data.add(statistic);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
