package com.example.spring_activities_platform.entities;

import com.example.spring_activities_platform.constants.ActivityCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "activities")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private Long id;

    @Size(min = 4, max = 255)
    private String name;

    @Size(min = 10, max = 511)
    private String description;

    @Min(1)
    private double price;

    @NotNull
    @Min(1)
    private int duration; // in minutes;

    @NotNull
    private String suitableAge;

    @NotNull
    private ActivityCategory category;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getSuitableAge() {
        return suitableAge;
    }

    public void setSuitableAge(String suitableAge) {
        this.suitableAge = suitableAge;
    }

    public ActivityCategory getCategory() {
        return category;
    }

    public void setCategory(ActivityCategory category) {
        this.category = category;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
