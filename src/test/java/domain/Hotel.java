package domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Hotel {

    private String hotelName;
    private String city;
    private Boolean isReturnable;
    private String[] services;


    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    @JsonProperty("isReturnable")
    public Boolean getIsReturnable() {
        return isReturnable;
    }

    public void setIsReturnable(Boolean returnable) {
        isReturnable = returnable;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String[] getServices() {
        return services;
    }

    public void setServices(String[] services) {
        this.services = services;
    }

}
