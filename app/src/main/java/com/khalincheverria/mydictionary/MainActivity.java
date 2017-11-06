package com.khalincheverria.mydictionary;



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button binarySearchTreeButton;
    private Button linkedListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binarySearchTreeButton=(Button)findViewById(R.id.binary_tree_button);
        linkedListButton=(Button)findViewById(R.id.linked_list_button);

        binarySearchTreeButton.setOnClickListener(this);
        linkedListButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        if(view.getId()==R.id.binary_tree_button) {
            Intent intent = new Intent(this, Contacts.class);
            intent.putExtra("BinaryTree", true);
            startActivity(intent);
        }else if(view.getId()==R.id.linked_list_button){
            Intent intent= new Intent(this,Contacts.class);
            intent.putExtra("BinaryTree",false);
            startActivity(intent);
        }
    }
}
