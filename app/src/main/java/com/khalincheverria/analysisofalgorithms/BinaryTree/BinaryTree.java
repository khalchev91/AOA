package com.khalincheverria.analysisofalgorithms.BinaryTree;


import com.khalincheverria.analysisofalgorithms.Model.Contact;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


@SuppressWarnings("ConstantConditions")
public class BinaryTree implements Serializable {
    private TreeNode root;
    private Contact contact;
    boolean check=false;
   public int count;

   private static ArrayList<Contact> contacts = new ArrayList<>();

    public static ArrayList<Contact> getContacts() {
        return contacts;
    }

    private boolean atEnd;


    public void setAtEnd(boolean atEnd) {
        this.atEnd = atEnd;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isCheck() {
        return check;
    }

    public BinaryTree(){
        root=null;
    }

    public boolean isEmpty(){
        return root==null;
    }

    public void insert(Contact contact) {
        TreeNode treeNode = new TreeNode(contact);
        TreeNode node;

        if (treeNode != null) {
            if (root == null) {
                root = treeNode;
            } else {
                node = root;
                while (true) {
                    if (treeNode.getContact().getName().getLastName().compareToIgnoreCase(node.getContact().getName().getLastName()) < 0) {
                        if (node.getLeft() == null) {
                            node.setLeft(treeNode);
                            break;
                        } else {
                            node = node.getLeft();
                        }
                    } else {
                        if (node.getRight() == null) {
                            node.setRight(treeNode);
                            break;
                        } else {
                            node = node.getRight();
                        }
                    }
                }
            }
        }
    }

    public boolean atEnd(){
        atEnd(root);
        return atEnd;
    }

    private void atEnd(TreeNode node){
        if(node!=null){
            atEnd(node.getLeft());
            atEnd = node.getContact()==null;
            atEnd(node.getRight());
        }
    }

    public void display(){
        inOrder(root);
    }

    public void depthFirstSearch(String key){
        contacts.clear();
        search(root,key);
    }

    public void breadthFirstSearch(TreeNode node, String fname, String lname){

        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.add(node);

        while(!q.isEmpty()){
            node = q.remove();

            if(node.getContact().getName().getFirstName().equalsIgnoreCase(fname)){
                if(node.getContact().getName().getLastName().equalsIgnoreCase(lname)){
                    //The contact is found.
                    break;
                }
            }

            if(node.getLeft() != null){
                q.add(node.getLeft());
            }
            if(node.getRight() != null){
                q.add(node.getRight());
            }
        }
    }

    private void search(TreeNode node,String key){
        if(node==null){
            return;
        }
        search(node.getLeft(),key);
        if(node.getContact().getName().toString().compareToIgnoreCase(key)==0){
            contacts.add(new Contact(node.getContact()));
        }
        search(node.getRight(),key);

    }

    private void inOrder(TreeNode node){
        if(node!=null){
            inOrder(node.getLeft());
            System.out.println(node.getContact());
            inOrder(node.getRight());
        }
    }

    public int count(){
        return count(root);

    }

    private int count(TreeNode node){
        if(node==null){
            return 0;
        }else {
            int l=1;
            l+=count(node.getLeft());
            l+=count(node.getRight());
            return l;
        }
    }

    public class Counter {
        int count = 0;
    }

    private void inOrderTraverseTree(TreeNode root, int index, Counter counter){
        if(root == null || counter.count > index) {
            return;
        }
        inOrderTraverseTree(root.getLeft(),index,counter);
        if (counter.count == index) {
            setContact(root.getContact());
        }
        counter.count = counter.count + 1;
        inOrderTraverseTree(root.getRight(),index,counter);

    }

    public Contact get(int index){
        inOrderTraverseTree(root,index,new Counter());
        return contact;
    }

    public void clear(){
        root=null;
    }

    public Contact search(String contactName){
        return new Contact();
    }

}
