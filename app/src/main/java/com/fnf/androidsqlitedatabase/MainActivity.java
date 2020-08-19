package com.fnf.androidsqlitedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button buttonInsert, buttonDelete, buttonUpdate, buttonShow;
    private EditText editTextId, editTextAddress, editTextPhone, editTextEmail, editTextName;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqliteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String address = editTextAddress.getText().toString();
                String phone = editTextPhone.getText().toString();
                String email = editTextEmail.getText().toString();
                sqliteDatabase = databaseHelper.getWritableDatabase();
                insertData(name, address, phone, email);
                Toast.makeText(MainActivity.this, "DATA IS INSERTED SUCCESSFULLY", Toast.LENGTH_LONG).show();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqliteDatabase = databaseHelper.getWritableDatabase();
                String id = editTextId.getText().toString();
                deleteDatabyId(id);
                Toast.makeText(getApplicationContext(), "Data is deleted successfully", Toast.LENGTH_LONG).show();
            }
        });
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqliteDatabase = databaseHelper.getWritableDatabase();
                String id = editTextId.getText().toString();
                String name = editTextName.getText().toString();
                String address = editTextAddress.getText().toString();
                String phone = editTextPhone.getText().toString();
                String email = editTextPhone.getText().toString();
                update(id, name, address, phone, email);
                Toast.makeText(getApplicationContext(), "Data is updated successfully", Toast.LENGTH_LONG).show();
            }
        });
        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqliteDatabase = databaseHelper.getReadableDatabase();
               // Cursor cursor = sqliteDatabase.query(DatabaseHelper.TABLE_NAME, null, null, null, null, null, null);
                //Cursor cursor = sqliteDatabase.query(DatabaseHelper.TABLE_NAME, null, "id=?", new String[]{String.valueOf(1)}, null, null, null);
                //Cursor cursor = sqliteDatabase.rawQuery("select *from " + DatabaseHelper.TABLE_NAME, null);
                Cursor cursor = sqliteDatabase.rawQuery("select *from " + DatabaseHelper.TABLE_NAME + " where id=?", new String[]{String.valueOf(2)});
               // Cursor cursor = sqliteDatabase.rawQuery("select *from " + DatabaseHelper.TABLE_NAME + " where id=? and name=?", new String[]{String.valueOf(2), "Mustafa Hamim"});
                if(cursor != null){
                    if(cursor.moveToFirst()){
                        for(int i = 0; i < cursor.getCount(); ++i){
                            Integer id = cursor.getInt(cursor.getColumnIndex("id"));
                            String name = cursor.getString(cursor.getColumnIndex("name"));
                            String phone = cursor.getString(cursor.getColumnIndex("phone"));
                            String address = cursor.getString(cursor.getColumnIndex("address"));
                            String email = cursor.getString(cursor.getColumnIndex("email"));
                            cursor.moveToNext();
                        }
                    }
                }
            }
        });
    }
    private void init(){
        buttonInsert = findViewById(R.id.buttonInsert);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonShow = findViewById(R.id.buttonShow);

        editTextId = findViewById(R.id.editTextId);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextName = findViewById(R.id.editTextName);

        databaseHelper = new DatabaseHelper(this);
    }

    private void insertData(String name, String address, String phone, String email){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.col2, name);
        contentValues.put(DatabaseHelper.col3, address);
        contentValues.put(DatabaseHelper.col4, phone);
        contentValues.put(DatabaseHelper.col5, email);
        long id = sqliteDatabase.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
    }

    private boolean deleteDatabyId(String id){
        return sqliteDatabase.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.col1+="=?", new String[]{id}) > 0;
    }

    private boolean update(String id, String name, String address, String phone, String email){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.col2, name);
        contentValues.put(DatabaseHelper.col3, address);
        contentValues.put(DatabaseHelper.col4, phone);
        contentValues.put(DatabaseHelper.col5, email);
        return sqliteDatabase.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper.col1+="=?", new String[]{id}) > 0;
    }


}