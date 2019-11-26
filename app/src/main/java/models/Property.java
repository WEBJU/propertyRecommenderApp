package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Property {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("property_name")
    @Expose
    private String propertyName;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("propertyy_image")
    @Expose
    private String propertyyImage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPropertyyImage() {
        return propertyyImage;
    }

    public void setPropertyyImage(String propertyyImage) {
        this.propertyyImage = propertyyImage;
    }

}
