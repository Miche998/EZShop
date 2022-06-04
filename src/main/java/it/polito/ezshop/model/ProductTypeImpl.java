package it.polito.ezshop.model;

import it.polito.ezshop.data.*;

public class ProductTypeImpl implements ProductType {

    private Integer id;
    private Integer quantity;
    private String location;
    private String note;
    private String description;
    private String barCode;
    private Double pricePerUnit;


    public ProductTypeImpl(Integer id, Integer quantity, String location, String note, String description, String barCode, Double pricePerUnit) {
        this.id = id;
        this.quantity = quantity;
        this.location = location;
        this.note = note;
        this.description = description;
        this.barCode = barCode;
        this.pricePerUnit = pricePerUnit;
    }

    @Override
    public Integer getQuantity()
    {
        return quantity;
    }

    @Override
    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
    }

    @Override
    public String getLocation()
    {
        return location;
    }

    @Override
    public void setLocation(String location)
    {
        this.location = location;
    }

    @Override
    public String getNote()
    {
        return note;
    }

    @Override
    public void setNote(String note)
    {
        this.note = note;
    }

    @Override
    public String getProductDescription()
    {
        return description;
    }

    @Override
    public void setProductDescription(String productDescription)
    {
        this.description = productDescription;
    }

    @Override
    public String getBarCode()
    {
        return barCode;
    }

    @Override
    public void setBarCode(String barCode)
    {
        this.barCode = barCode;
    }

    @Override
    public Double getPricePerUnit()
    {
        return pricePerUnit;
    }

    @Override
    public void setPricePerUnit(Double pricePerUnit)
    {
        this.pricePerUnit = pricePerUnit;
    }

    @Override
    public Integer getId()
    {
        return id;
    }

    @Override
    public void setId(Integer id)
    {
        this.id = id;
    }

    public void modifyQuantity(int toBeAdded){
        this.quantity += toBeAdded;
    }

}
