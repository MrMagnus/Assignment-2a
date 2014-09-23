package yh.contactmanage.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import yh.contactmanage.R;


public class ContactInfo extends Activity {

    //Constants
    public static final int EDIT_CONTACT_REQUEST_CODE = 102;

    //Class variables
    private ImageView portraitIv;
    private TextView nameAndAgeTv;
    private TextView descriptionTv;

    private int selectedItemPosition;
    private String chosenContactUrl;
    private String chosenContactName;
    private String chosenContactAge;
    private String chosenContactDescr;

    private boolean removeItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        removeItem = false;

        portraitIv = (ImageView) findViewById(R.id.portraitIv);
        nameAndAgeTv = (TextView) findViewById(R.id.nameAndAgeTv);
        descriptionTv = (TextView) findViewById(R.id.descriptionTv);

        //Gets the values of the chosen contact and displays it on the view
        selectedItemPosition = getIntent().getExtras().getInt("contactPosition", 0);
        chosenContactUrl = getIntent().getExtras().getString("contactUrl");
        chosenContactName = getIntent().getExtras().getString("contactName");
        chosenContactAge = getIntent().getExtras().getString("contactAge");
        chosenContactDescr = getIntent().getExtras().getString("contactDescription");

        //Picasso get and creats a picture from a url
        Picasso.with(this).load(chosenContactUrl).into(portraitIv);
        nameAndAgeTv.setText(chosenContactName + ", " + chosenContactAge + getResources().getString(R.string.contactInfoAgeString));
        descriptionTv.setText(chosenContactDescr);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contact_info, menu);
        return true;
    }

    /**
     * Sends back information to remove or edit the selected contact.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){

            case R.id.actionRemoveContact:

                Intent intentRemove = getIntent();
                removeItem = true;

                intentRemove.putExtra("selectedItemPosition", selectedItemPosition);
                intentRemove.putExtra("removeItem", removeItem);

                setResult(RESULT_OK, intentRemove);

                finish();
            break;

            case R.id.actionEditContact:

                Intent intentEdit = new Intent(this, CreateContact.class);

                intentEdit.putExtra("selectedItemPosition", selectedItemPosition);

                intentEdit.putExtra("chosenContactUrl",chosenContactUrl);
                intentEdit.putExtra("chosenContactName",chosenContactName);
                intentEdit.putExtra("chosenContactAge",chosenContactAge);
                intentEdit.putExtra("chosenContactDescr",chosenContactDescr);
                intentEdit.putExtra("requestCode", EDIT_CONTACT_REQUEST_CODE);

                startActivityForResult(intentEdit, EDIT_CONTACT_REQUEST_CODE);

                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (EDIT_CONTACT_REQUEST_CODE == requestCode && resultCode == RESULT_OK) {

                Intent intentEdit = getIntent();

                int position = data.getIntExtra("selectedItemPosition", 0);
                String url = data.getStringExtra("editedContactUrl");
                String name = data.getStringExtra("editedContactName");
                String age = data.getStringExtra("editedContactAge");
                String descr = data.getStringExtra("editedContactDescr");

                intentEdit.putExtra("selectedItemPosition", position);
                intentEdit.putExtra("editedContactUrl", url);
                intentEdit.putExtra("editedContactName", name);
                intentEdit.putExtra("editedContactAge", age);
                intentEdit.putExtra("editedContactDescr", descr);

                setResult(RESULT_OK, intentEdit);
                finish();
        }
    }
}
