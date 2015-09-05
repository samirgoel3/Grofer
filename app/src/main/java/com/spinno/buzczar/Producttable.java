package com.spinno.buzczar;

import com.orm.SugarRecord;

/**
 * Created by samir on 23/02/15.
 */
public class Producttable extends SugarRecord<Producttable> {



    String ProductName ;
    String ProductPrice ;
    String ProductDescription;
    String ProductId ;
    String Productimage ;
    String SalePrice ;
    String DiscountPrice ;
    String PerUnit ;
    String UnitType ;
    public int    NumberOfUnits;








    public Producttable(String productName, String productPrice, String productDescription, String productId, String productimage, int numberOfUnits, String SalePrice, String DiscountPrice, String PerUnit, String UnitType) {
        this.ProductName = productName;
        this.ProductPrice = productPrice;
        this.ProductDescription = productDescription;
        this.ProductId = productId;
        this.Productimage = productimage;
        this.NumberOfUnits = numberOfUnits;
        this.SalePrice = SalePrice ;
        this.DiscountPrice = DiscountPrice ;
        this.PerUnit = PerUnit ;
        this.UnitType = UnitType ;
    }

    public Producttable(){

    }


    public int getNumberOfUnits() {
        return NumberOfUnits;
    }

    public void setNumberOfUnits(int numberOfUnits) {
        NumberOfUnits = numberOfUnits;
    }



    public String getProductimage() {
        return Productimage;
    }

    public void setProductimage(String productimage) {
        Productimage = productimage;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getSalePrice() {
        return SalePrice;
    }

    public void setSalePrice(String salePrice) {
        SalePrice = salePrice;
    }

    public String getDiscountPrice() {
        return DiscountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        DiscountPrice = discountPrice;
    }

    public String getPerUnit() {
        return PerUnit;
    }

    public void setPerUnit(String perUnit) {
        PerUnit = perUnit;
    }

    public String getUnitType() {
        return UnitType;
    }

    public void setUnitType(String unitType) {
        UnitType = unitType;
    }



}
