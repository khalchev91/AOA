package com.khalincheverria.mydictionary.BinaryTree;

import com.khalincheverria.mydictionary.Model.Contact;

import java.io.Serializable;

public class TreeNode implements Serializable {
    private TreeNode left,right;
    private Contact contact;

    public TreeNode(){
        left=null;
        right=null;
        contact =new Contact();
    }
    public TreeNode(Contact contact){
        left=null;
        right=null;
        this.contact = contact;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    public Contact getContact() {
        return contact;

    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String toString(){
        return contact.toString();
    }
}
