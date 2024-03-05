package com.example.socialmediastatistic;

import javafx.beans.property.*;

public class SocialMediaStatistic {
    private final IntegerProperty rank = new SimpleIntegerProperty();
    private final StringProperty platform = new SimpleStringProperty();
    private final StringProperty primaryAgeGroup = new SimpleStringProperty();
    private final DoubleProperty percentage2022 = new SimpleDoubleProperty();
    private final DoubleProperty percentage2020 = new SimpleDoubleProperty();

    // Constructor
    public SocialMediaStatistic(int rank, String platform, String primaryAgeGroup, double percentage2022, double percentage2020) {
        setRank(rank);
        setPlatform(platform);
        setPrimaryAgeGroup(primaryAgeGroup);
        setPercentage2022(percentage2022);
        setPercentage2020(percentage2020);
    }


    // Getter methods
    public int getRank() {
        return rank.get();
    }

    public String getPlatform() {
        return platform.get();
    }

    public String getPrimaryAgeGroup() {
        return primaryAgeGroup.get();
    }

    public double getPercentage2022() {
        return percentage2022.get();
    }

    public double getPercentage2020() {
        return percentage2020.get();
    }

    // Setter methods
    public void setRank(int rank) {
        this.rank.set(rank);
    }

    public void setPlatform(String platform) {
        this.platform.set(platform);
    }

    public void setPrimaryAgeGroup(String primaryAgeGroup) {
        this.primaryAgeGroup.set(primaryAgeGroup);
    }

    public void setPercentage2022(double percentage2022) {
        this.percentage2022.set(percentage2022);
    }

    public void setPercentage2020(double percentage2020) {
        this.percentage2020.set(percentage2020);
    }

    // Property methods
    public IntegerProperty rankProperty() {
        return rank;
    }

    public StringProperty platformProperty() {
        return platform;
    }

    public StringProperty primaryAgeGroupProperty() {
        return primaryAgeGroup;
    }

    public DoubleProperty percentage2022Property() {
        return percentage2022;
    }

    public DoubleProperty percentage2020Property() {
        return percentage2020;
    }
}
