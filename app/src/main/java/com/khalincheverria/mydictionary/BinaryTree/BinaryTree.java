package com.khalincheverria.mydictionary.BinaryTree;


import com.khalincheverria.mydictionary.Model.Contact;

import java.io.Serializable;


@SuppressWarnings("ConstantConditions")
public class BinaryTree implements Serializable {
    private TreeNode root;
    private Contact contact;
    boolean check=false;
   public int count;


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

    public void display(){
        inOrder(root);
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
}
