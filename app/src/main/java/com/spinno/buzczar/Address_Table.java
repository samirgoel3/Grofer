package com.spinno.buzczar;

import com.orm.SugarRecord;

/**
 * Created by samir on 08/03/15.
 */
public class Address_Table extends SugarRecord<Address_Table> {


    String id_of_particular_address;
    String state ;
    String area;
    String houseno ;
    String stree_no ;
    String address_name;
    String pin ;
    String landmark ;


    public Address_Table(String id_of_particular_address, String state, String area, String houseno, String stree_no, String address_name, String pin, String landmark) {
        this.id_of_particular_address = id_of_particular_address;
        this.state = state;
        this.area = area;
        this.houseno = houseno;
        this.stree_no = stree_no;
        this.address_name = address_name;
        this.pin = pin;
        this.landmark = landmark;
    }

    public Address_Table(){

    }


    public String getId_of_particular_address() {
        return id_of_particular_address;
    }

    public void setId_of_particular_address(String id_of_particular_address) {
        this.id_of_particular_address = id_of_particular_address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getHouseno() {
        return houseno;
    }

    public void setHouseno(String houseno) {
        this.houseno = houseno;
    }

    public String getStree_no() {
        return stree_no;
    }

    public void setStree_no(String stree_no) {
        this.stree_no = stree_no;
    }

    public String getAddress_name() {
        return address_name;
    }

    public void setAddress_name(String address_name) {
        this.address_name = address_name;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }








}
