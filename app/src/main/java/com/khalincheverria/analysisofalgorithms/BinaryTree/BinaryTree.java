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

    public void breadthFirstSearch(String name){
        contacts.clear();
        breadthFirstSearch(root,name);
    }

    private void knuthMorrisPratt(TreeNode root,String pattern){
        if(root == null){
            return;
        }
        knuthMorrisPratt(root.getLeft(),pattern);
        if(prattSearch(pattern,root.getContact().getName().getLastName())){
            contacts.add(root.getContact());
        }
        knuthMorrisPratt(root.getRight(),pattern);
    }

    private boolean prattSearch(String pattern, String name){
        int patternLength = pattern.length();
        int nameLength = name.length();
        name = name.toLowerCase();
        pattern = pattern.toLowerCase();
        int longestPrefix[] = new int[patternLength];
        int someNumber = 0;
        computePrefixArray(pattern,longestPrefix);
        int i = 0;
        while (i<nameLength){
            if(pattern.charAt(someNumber) == name.charAt(i)){
                someNumber++;
                i++;
            }
            if(someNumber == patternLength){
                someNumber = longestPrefix[someNumber-1];
                return true;
            }else if(i < nameLength && (pattern.charAt(someNumber)!=name.charAt(i))){
                if(someNumber!=0){
                    someNumber = longestPrefix[someNumber-1];
                }else {
                    i=i+1;
                }
            }
        }
        return false;
    }

    private void computePrefixArray(String pattern,int longestPrefix[]) {
        int length = 0;
        int i = 1;

        longestPrefix[0] = 0;

        while (i<pattern.length()){
            if(pattern.charAt(i) == pattern.charAt(length)){
                length++;
                longestPrefix[i] = length;
                i++;
            }else{
                if(length!=0){
                    length = longestPrefix[length-1];
                }else {
                    longestPrefix[i] = length;
                    i++;
                }
            }
        }
    }

    public ArrayList<Contact> knuthMorrisPratt(String pattern){
        contacts.clear();
        knuthMorrisPratt(root,pattern);
        return contacts;
    }



    private void breadthFirstSearch(TreeNode node, String name){

        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.add(node);

        while(!q.isEmpty()){
            node = q.remove();

            if(node.getContact().getName().toString().equalsIgnoreCase(name)){
                contacts.add(node.getContact());
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
