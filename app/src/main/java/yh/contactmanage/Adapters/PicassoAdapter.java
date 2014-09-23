package yh.contactmanage.Adapters;

import yh.contactmanage.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


import yh.contactmanage.Models.ContactModel;

/**
 * Created by Magnus on 2014-09-16.
 */
public class PicassoAdapter extends ArrayAdapter<ContactModel> {

    private Context context;
    private ArrayList<ContactModel> contactArrayList;

    //Constructor
    public PicassoAdapter(Context context, ArrayList<ContactModel> contactArrayList) {
        super(context, R.layout.row_contacts, contactArrayList);

        this.context = context;
        this.contactArrayList = contactArrayList;
    }

    /**
     * Instead of creating a new list item for every contact this method will create as many items
     * that will fit on the action and then re-use the list view by giving them new values
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        {
            ViewHolder viewHolder;

            if(convertView == null){

                //Inflates the list
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.row_contacts, parent, false);

                viewHolder = new ViewHolder();

                //Saves the view items as a viewholder object.
                viewHolder.nameHolder = (TextView) convertView.findViewById(R.id.nameAndAgeTv);
                viewHolder.imageHolder = (ImageView) convertView.findViewById(R.id.portraitIv);

                convertView.setTag(viewHolder);
            }
            else{

                viewHolder = (ViewHolder)convertView.getTag();
            }

            ContactModel galleryFrame = contactArrayList.get(position);

            viewHolder.nameHolder.setText(galleryFrame.getName());
            //Picasso creates a image with and url taken from the internet. It also have
            //placeholders as the image is loading or if an error will occur.
            Picasso.with(context)
                    .load(galleryFrame.getUrl())
                    .placeholder(R.drawable.ic_launcher)
                    .error(R.drawable.ic_launcher)
                    .into(viewHolder.imageHolder);

            return convertView;
        }
    }

    public static class ViewHolder{
        public TextView nameHolder;
        public ImageView imageHolder;
    }
}