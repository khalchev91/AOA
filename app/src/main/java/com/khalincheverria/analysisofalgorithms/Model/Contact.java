package com.khalincheverria.analysisofalgorithms.Model;

import java.io.Serializable;


    public class Contact implements Serializable {

        private Name name;
        private String address;

        public Contact(Name name,String address){
            setName(name);
            setAddress(address);
        }

        public Contact(){
            this(new Name(),"");
        }

        public Contact(Contact contact){
            this(contact.getName(), contact.getAddress());
        }

        public Name getName() {
            return name;
        }

        public void setName(Name name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }


        public String toString(){
            String out = "";
            out += "Contacts Name:" + getName() + "\n";
            out += "Address:" + getAddress() + "\n";
            return out;
        }

    }





