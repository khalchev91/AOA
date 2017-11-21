package com.khalincheverria.analysisofalgorithms.Model;

import java.io.Serializable;

public class Name implements Serializable{

private String firstName;
private String lastName;

public Name (){
this("","");
}

public Name(String firstName, String lastName){
    setFirstName(firstName);
    setLastName(lastName);
}

public Name(Name name){
    this(name.firstName,name.lastName);
}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        String name = "";
        name+= getFirstName()+" ";
        name+=getLastName();
        return name;
    }
}
