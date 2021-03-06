package ua.com.idltd.hydracargo.ukrpost.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UPShipmentResponse {

    private String uuid;
    private String type;
    private UPUuid sender;
    private UPUuid recipient;
    private String deliveryType;
    private boolean paidByRecipient;
    private String description;
    private List<UPParcel> parcels;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UPUuid getSender() {
        return sender;
    }

    public void setSender(UPUuid sender) {
        this.sender = sender;
    }

    public UPUuid getRecipient() {
        return recipient;
    }

    public void setRecipient(UPUuid recipient) {
        this.recipient = recipient;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public boolean isPaidByRecipient() {
        return paidByRecipient;
    }

    public void setPaidByRecipient(boolean paidByRecipient) {
        this.paidByRecipient = paidByRecipient;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UPParcel> getParcels() {
        return parcels;
    }

    public void setParcels(List<UPParcel> parcels) {
        this.parcels = parcels;
    }
}
