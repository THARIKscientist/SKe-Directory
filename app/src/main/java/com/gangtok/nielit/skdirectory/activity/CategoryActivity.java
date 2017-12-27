package com.gangtok.nielit.skdirectory.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gangtok.nielit.skdirectory.R;
import com.gangtok.nielit.skdirectory.adapter.ListCategoryAdapter;
import com.gangtok.nielit.skdirectory.model.Category;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        final ArrayList<Category> words = new ArrayList<Category>();
        
        words.add(new Category("GOVERNMENT", R.mipmap.ic__0000s_0000_government));
        words.add(new Category("SECRETARIAT", R.mipmap.ic__0000s_0001_secretrait));
        words.add(new Category("LEGISLATURE", R.mipmap.ic__0000s_0002_legislature));
        words.add(new Category("PRESS & MEDIA", R.mipmap.ic__0000s_0003_media));
        words.add(new Category("JUDICIARY", R.mipmap.ic__0000s_0004_judiciary));
        words.add(new Category("H.OD & CORPORATIONS", R.mipmap.ic__0000s_0006_hod));
        words.add(new Category("INSTITUTIONS", R.mipmap.ic__0000s_0005_institutions));
        words.add(new Category("DISTRICT OFFICIALS", R.mipmap.ic__0000s_0007_district));
        words.add(new Category("EMERGENCY/MISCELLANEOUS SERIVES", R.mipmap.ic__0000s_0008_emergency));


        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        ListCategoryAdapter adapter = new ListCategoryAdapter(this,words,R.color.category_departments);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // activity_numbers.xml layout file.
        ListView listView = (ListView) findViewById(R.id.list);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Category word = words.get(position);
                Intent i=new Intent(CategoryActivity.this,DepartmentActivity.class);
                i.putExtra("CategoryName",word.getCategories().toString());
                startActivity(i);


            }
        });

    }
}
