package com.techkshetra.tpc1.sqltut;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ContactDetailsActivity extends AppCompatActivity {

    private EditText editText_name;
    private EditText editText_email;
    private EditText editText_phone;
    private EditText editText_age;

    private Button button_save;
    private Button button_update;

    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        editText_name=findViewById(R.id.editText_name);
        editText_email=findViewById(R.id.editText_email);
        editText_phone=findViewById(R.id.editText_phone);
        editText_age=findViewById(R.id.editText_age);

        button_save=findViewById(R.id.button_save);
        button_update=findViewById(R.id.button_update);

        Bundle b2=getIntent().getExtras();
        if(b2!=null) {
            int position=b2.getInt("position");
            DBHelper db=new DBHelper(this);
            contact=db.getAllContacts().get(position);
            editText_name.setText(contact.getName());
            editText_email.setText(contact.getEmail());
            editText_phone.setText(contact.getPhone());
            editText_age.setText(String.valueOf(contact.getAge()));
            button_save.setVisibility(View.INVISIBLE);
        }

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveContact();
            }
        });
        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateContact();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.details_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_delete_contact:
               deleteContact();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void SaveContact() {
        DBHelper db=new DBHelper(this);
        Contact newcontact=new Contact();
        newcontact.setName(editText_name.getText().toString());
        newcontact.setEmail(editText_email.getText().toString());
        newcontact.setPhone(editText_phone.getText().toString());
        newcontact.setAge(Integer.valueOf(editText_age.getText().toString()));
        db.insertcontact(newcontact);
        finish();
    }
    private void updateContact() {
        DBHelper db=new DBHelper(this);
        Contact newcontact=new Contact();
        newcontact.setId(contact.getId());
        newcontact.setName(editText_name.getText().toString());
        newcontact.setEmail(editText_email.getText().toString());
        newcontact.setPhone(editText_phone.getText().toString());
        newcontact.setAge(Integer.valueOf(editText_age.getText().toString()));
        db.updatecontact(newcontact);
        finish();
    }

    private void deleteContact() {
        DBHelper db=new DBHelper(this);
        db.deletecontact(contact);
        finish();
    }
}
