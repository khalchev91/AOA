package com.khalincheverria.mydictionary.Model;


class Address {
private String street;
private String townName;
private String country;



    public Address (){
        this("","","");
    }
    public Address(String street, String townName,String country){
        setStreet(street);
        setTownName(townName);
        setCountry(country);
    }

    public Address(Address address){
        this(address.getStreet(),address.getTownName(),address.getCountry());
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString(){
        String out = "";
        out += "Street:" + getStreet() + ", ";
        out += "City:" + getTownName() + ", ";
        out += "Country:" + getCountry() + "\n";
        return out;
    }

}
