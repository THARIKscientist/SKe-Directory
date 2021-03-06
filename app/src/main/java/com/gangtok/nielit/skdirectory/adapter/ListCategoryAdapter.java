package com.gangtok.nielit.skdirectory.adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gangtok.nielit.skdirectory.R;
import com.gangtok.nielit.skdirectory.model.Category;

import java.util.ArrayList;

/**
 * Created by NIELIT on 11-08-2016.
 */
public class ListCategoryAdapter extends ArrayAdapter<Category> {

    private int mColorResourceId;
    public ListCategoryAdapter(Activity context, ArrayList<Category> androidWords, int colorResourceId){
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, androidWords);
        mColorResourceId=colorResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.category_listview, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        Category currentWord = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView departmentsTextView = (TextView) listItemView.findViewById(R.id.categories_text_view);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        departmentsTextView.setText(currentWord.getCategories());



        // Find the ImageView in the list_item.xml layout with the ID list_item_icon
        // Find the ImageView in the list_item.xml layout with the ID image.
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
        // Set the ImageView to the image resource specified in the current Word
        if(currentWord.hasImage()) {
            imageView.setImageResource(currentWord.getImageResourceId());
        }
        else {

            imageView.setVisibility(View.GONE);
        }
        //Set Color in each page
        View textContainer=listItemView.findViewById(R.id.text_container);
        int color= ContextCompat.getColor(getContext(),mColorResourceId);
        textContainer.setBackgroundColor(color);
        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
