package com.example.socialmediastatistic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.List;

public class ChartController {

    @FXML
    private BorderPane root;


    @FXML
    private RadioButton showBarChart;

    @FXML
    private RadioButton showPieChart2022;

    @FXML
    private RadioButton showPieChart2020;


    @FXML
    private Button switchViewButton;

    private final ToggleGroup toggleGroup = new ToggleGroup();
    private List<SocialMediaStatistic> data;

    public void initialize() {
        showBarChart.setToggleGroup(toggleGroup);
        showPieChart2022.setToggleGroup(toggleGroup);
        showPieChart2020.setToggleGroup(toggleGroup);
        root.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
    }
    @FXML
    void updateChart() {
        if (showBarChart.isSelected()) {
            switchToBarChart();
        } else if (showPieChart2022.isSelected()) {
            switchToPieChart("2022");
        } else if (showPieChart2020.isSelected()) {
            switchToPieChart("2020");
        }
    }

    @FXML
    private void switchView() {
        if (root.getCenter() instanceof BarChart) {
            switchToTableView();
        } else if (root.getCenter() instanceof PieChart) {
            String selectedYear = showPieChart2022.isSelected() ? "2022" : "2020";
            switchToPieChart(selectedYear);
        } else if (root.getCenter() instanceof TableView) {
            switchToBarChart();
        }
    }



    private void switchToBarChart() {
        BarChart<String, Number> newBarChart = createBarChart();
        root.setCenter(newBarChart);
        populateBarChart(newBarChart, data);

        // Deselect the Bar Chart radio button
        showBarChart.setSelected(true);
    }

    private void switchToPieChart(String year) {
        PieChart newPieChart = createPieChart("Social Media Statistics " + year);
        root.setCenter(newPieChart);

        if (year.equals("2022")) {
            showPieChart2022.setSelected(true);
            showPieChart2020.setSelected(false);
        } else if (year.equals("2020")) {
            showPieChart2022.setSelected(false);
            showPieChart2020.setSelected(true);
        }

        populatePieChart(year);
    }


    private void switchToTableView() {
        // Create the TableView
        TableView<SocialMediaStatistic> tableView = createTableView();

        // Set the TableView as the center
        root.setCenter(createTableViewWithTitle(tableView));

        // Set the VBox to take up the entire width
        VBox vBox = (VBox) root.getCenter();
        vBox.prefWidthProperty().bind(root.widthProperty());

        // Clear the selection in the ToggleGroup
        toggleGroup.selectToggle(null);
    }

    private PieChart createPieChart(String title) {
        PieChart pieChart = new PieChart();
        pieChart.setTitle(title);
        return pieChart;
    }
    private Node createTableViewWithTitle(TableView<SocialMediaStatistic> tableView) {
        VBox vbox = new VBox();  // Use VBox to stack nodes vertically

        // Add a title label
        Label titleLabel = new Label("Social Media Statistics (Table)");
        titleLabel.setId("table-title");
        vbox.getChildren().add(titleLabel);

        HBox tableViewContainer = new HBox(tableView);
        HBox.setHgrow(tableViewContainer, Priority.ALWAYS);

        vbox.getChildren().add(tableViewContainer);
        vbox.getStyleClass().add("vbox-background");

        return vbox;  // Return the VBox containing the title and TableView
    }


    private TableView<SocialMediaStatistic> createTableView() {
        TableView<SocialMediaStatistic> tableView = new TableView<>();
        setTableViewStyle(tableView);

        root.setPadding(new Insets(20));
        VBox vBox = new VBox(tableView);

        TableColumn<SocialMediaStatistic, Integer> rankColumn = new TableColumn<>("Rank");
        rankColumn.setCellValueFactory(data -> data.getValue().rankProperty().asObject());
        rankColumn.setPrefWidth(50);

        TableColumn<SocialMediaStatistic, String> platformColumn = new TableColumn<>("Platform");
        platformColumn.setCellValueFactory(data -> data.getValue().platformProperty());
        platformColumn.setPrefWidth(150);

        TableColumn<SocialMediaStatistic, String> primaryAgeGroupColumn = new TableColumn<>("Primary Age Group");
        primaryAgeGroupColumn.setCellValueFactory(data -> data.getValue().primaryAgeGroupProperty());
        primaryAgeGroupColumn.setPrefWidth(150);

        TableColumn<SocialMediaStatistic, Number> percentage2022Column = new TableColumn<>("2022");
        percentage2022Column.setCellValueFactory(data -> data.getValue().percentage2022Property());
        percentage2022Column.setPrefWidth(100);

        TableColumn<SocialMediaStatistic, Number> percentage2020Column = new TableColumn<>("2020");
        percentage2020Column.setCellValueFactory(data -> data.getValue().percentage2020Property());
        percentage2020Column.setPrefWidth(100);

        tableView.getColumns().addAll(rankColumn, platformColumn, primaryAgeGroupColumn, percentage2022Column, percentage2020Column);
        tableView.setFixedCellSize(35);
        ObservableList<SocialMediaStatistic> observableList = FXCollections.observableArrayList(data);
        tableView.setItems(observableList);

        return tableView;
    }

    private void setTableViewStyle(TableView<SocialMediaStatistic> tableView) {
        tableView.getStyleClass().add("custom-table");
    }

    private BarChart<String, Number> createBarChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Social Media Statistics (2022 vs. 2020)");
        return barChart;
    }

    private void populateBarChart(BarChart<String, Number> barChart, List<SocialMediaStatistic> data) {
        // Clear existing data
        barChart.getData().clear();

        XYChart.Series<String, Number> series2022 = new XYChart.Series<>();
        series2022.setName("2022");

        XYChart.Series<String, Number> series2020 = new XYChart.Series<>();
        series2020.setName("2020");

        for (SocialMediaStatistic statistic : data) {
            String platform = statistic.getPlatform();
            double percentage2022 = statistic.getPercentage2022();
            double percentage2020 = statistic.getPercentage2020();

            series2022.getData().add(new XYChart.Data<>(platform, percentage2022));
            series2020.getData().add(new XYChart.Data<>(platform, percentage2020));
        }

        // Add series to chart
        barChart.getData().addAll(series2022, series2020);

        // Force chart redraw
        barChart.layout();
        barChart.applyCss();
    }

    private void populatePieChart(String year) {
        PieChart pieChart = createPieChart("Social Media Statistics");

        for (SocialMediaStatistic statistic : data) {
            double percentage = 0;

            if (year.equals("2022")) {
                percentage = statistic.getPercentage2022();
            } else if (year.equals("2020")) {
                percentage = statistic.getPercentage2020();
            }

            String label = String.format("%s %.1f%%", statistic.getPlatform(), percentage);
            PieChart.Data pieData = new PieChart.Data(label, percentage);
            pieChart.getData().add(pieData);
        }

        root.setCenter(pieChart);
    }



    public void setData(List<SocialMediaStatistic> socialMediaStatistics) {
        this.data = socialMediaStatistics;
        updateChart();
    }
}