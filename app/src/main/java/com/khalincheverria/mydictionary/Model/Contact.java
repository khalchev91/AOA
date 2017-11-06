package com.khalincheverria.mydictionary.Model;

import java.io.Serializable;


    public class Contact implements Serializable {

        private Name name;
        private Address address;

        public Contact(Name name,Address address){
            setName(name);
            setAddress(address);
        }

        public Contact(){
            this(new Name(),new Address());
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

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }






        public String toString(){
            String out = "";
            out += "Contacts Name:" + getName() + "\n";
            out += "Address:" + getAddress() + "\n";
            return out;
        }

    }





