package com.techkshetra.tpc1.sqltut;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listview_Allcontacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview_Allcontacts=findViewById(R.id.listviewallcontact);
        listview_Allcontacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this,ContactDetailsActivity.class);
                Bundle b=new Bundle();
                b.putInt("position",position);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_new_contact:
            startActivity(new Intent(MainActivity.this,ContactDetailsActivity.class));
            break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        DBHelper db=new DBHelper(this);
        ArrayList<String> names=new ArrayList<String>();
        for (int i=0; i<db.getAllContacts().size();i++)
            names.add(db.getAllContacts().get(i).getName());

        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,names);
        listview_Allcontacts.setAdapter(adapter);
        super.onResume();
    }
}
