package com.example.seolhalee.sqliteapp;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    EditText editName, editSurname, editMarks, editId;
    Button butAddData, butviewData,butUpdate, butDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DatabaseHelper(this);

        editName = (EditText) findViewById(R.id.editText_name);
        editSurname = (EditText) findViewById(R.id.editText_surname);
        editMarks = (EditText) findViewById(R.id.editText_marks);
        editId = (EditText) findViewById(R.id.editText_id);
        butAddData = (Button) findViewById(R.id.buttonAddData);
        butviewData = (Button) findViewById(R.id.butviewData);
        butUpdate = (Button) findViewById(R.id.butUpdate);
        butDelete = (Button) findViewById(R.id.butDelete);

        AddData();
        viewData();
        UpdateData();
        DeleteData();



    }

    public void UpdateData(){
        butUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isUpdated = myDB.updateData(editId.getText().toString(),
                                editName.getText().toString(), editSurname.getText().toString(), editMarks.getText().toString());
                        if(isUpdated == true)
                            Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();
                    }
                }

        );
    }

    public void DeleteData(){
        butDelete.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        int deletedRows = myDB.deleteData(editId.getText().toString());
                        if(deletedRows>0)
                            Toast.makeText(MainActivity.this, "Data deleted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not deleted", Toast.LENGTH_LONG).show();
                    }


                }
        );

    }

    public void AddData(){
        butAddData.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick (View v){
                        boolean isInserted = myDB.insertData(editName.getText().toString(),
                                editSurname.getText().toString(), editMarks.getText().toString());
                        if(isInserted == true)
                            Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();

                    }
                }

        );

    }

    public void viewData(){
        butviewData.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Cursor res = myDB.getAllData();
                        if(res.getCount()==0){
                            showMessage("Error", "No data found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()){
                            buffer.append("ID: "+res.getString(0)+"\n");
                            buffer.append("NAME: "+res.getString(1)+"\n");
                            buffer.append("SURNAME: "+res.getString(2)+"\n");
                            buffer.append("MARKS: "+res.getString(3)+"\n\n");
                        }

                        //show all data
                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }

    public void showMessage (String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setTitle(message);
        builder.show();

    }
}
