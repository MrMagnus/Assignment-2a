package yh.contactmanage.Activities;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import yh.contactmanage.Adapters.PicassoAdapter;
import yh.contactmanage.Models.ContactModel;
import yh.contactmanage.R;


public class MainActivity extends ListActivity implements AdapterView.OnItemLongClickListener, DialogInterface.OnClickListener{

    //Constants
    public static final int DETAIL_CONTACT_REQUEST_CODE = 99;
    public static final int ADD_CONTACT_REQUEST_CODE = 100;
    private static final int REMOVE_CONTACT_REQUEST_CODE = 101;
    private static final int EDIT_CONTACT_REQUEST_CODE = 102;

    //Class variables
    ArrayList<ContactModel> contactArrayList;
    PicassoAdapter picassoAdapter;
    private int itemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactArrayList = new ArrayList<ContactModel>();
        contactArrayList.add(new ContactModel("http://standout.se/img/david.elbe.jpg", "David Elbe", "10", "balblallalsldlasdlalsdkalsd"));
        contactArrayList.add(new ContactModel("http://upload.wikimedia.org/wikipedia/commons/thumb/6/62/Cthulhu_and_R%27lyeh.jpg/250px-Cthulhu_and_R%27lyeh.jpg", "Cthulhu", "139512341", "Cthulhu fhtagn ph'nglui mglw’nafh Cthulhu r’lyeh wgah’nagl ftaghn! I'a, I'a, I'a!"));
        contactArrayList.add(new ContactModel("http://standout.se/img/david.elbe.jpg", "David Elbe", "11", "balblallalsldlasdlalsdkalsd"));
        contactArrayList.add(new ContactModel("http://standout.se/img/david.elbe.jpg", "David Elbe", "42", "balblallalsldlasdlalsdkalsd"));

        picassoAdapter = new PicassoAdapter(this, contactArrayList);

        setListAdapter(picassoAdapter);

        getListView().setOnItemLongClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Sends a startActivityForResult() to the create/edit action and waits for a result
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.actionCreateContact) {

            //Create a new intent, sends it to the next activity and wait for result.
            Intent intentAdd = new Intent(this, CreateContact.class);
            intentAdd.putExtra("requestCode", ADD_CONTACT_REQUEST_CODE);

            startActivityForResult(intentAdd, ADD_CONTACT_REQUEST_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * When the request intent is finished this method will check what request code it has and then
     * Adds, removes or updates a contact object with the data that was sent back.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Creates a new contact with data the user inputted.
        if(ADD_CONTACT_REQUEST_CODE == requestCode && resultCode == RESULT_OK){

            String url = data.getStringExtra("newContactUrl");
            String name = data.getStringExtra("newContactName");
            String age = data.getStringExtra("newContactAge");
            String descr = data.getStringExtra("newContactDescr");

            ContactModel contact = new ContactModel(url, name, age, descr);

            contactArrayList.add(contact);
            picassoAdapter.notifyDataSetChanged();
        }

        //Remove the chosen contact if the removeItem variable is true
        if(DETAIL_CONTACT_REQUEST_CODE == requestCode && resultCode == RESULT_OK && data.getBooleanExtra("removeItem", false)){

            int selectedItemPosition = data.getIntExtra("selectedItemPosition", 0);
            contactArrayList.remove(selectedItemPosition);

            //Tells the adapter that the data has been changed
            picassoAdapter.notifyDataSetChanged();
        }

        //Edit the chosen contacts information if the removeItem variable is true
        if(DETAIL_CONTACT_REQUEST_CODE == requestCode && resultCode == RESULT_OK && !data.getBooleanExtra("removeItem", false)){

                int selectedItemPosition = data.getIntExtra("selectedItemPosition", 0);
                String editedUrl = data.getStringExtra("editedContactUrl");
                String editedName = data.getStringExtra("editedContactName");
                String editedAge = data.getStringExtra("editedContactAge");
                String editedDescr = data.getStringExtra("editedContactDescr");

                ContactModel editContact = contactArrayList.get(selectedItemPosition);

                editContact.setUrl(editedUrl);
                editContact.setName(editedName);
                editContact.setAge(editedAge);
                editContact.setDescription(editedDescr);

                //Tells the adapter that the data has been changed
                picassoAdapter.notifyDataSetChanged();

        }
    }

    /**
     * Takes the user to a view with more details about the contact he/she clicked on. Instead of
     * just sending off the data to the next activity, the startActivityForResult() will be used to
     * check if the user wants to remove or edit a user.
     * @param listView
     * @param view
     * @param position
     * @param id
     */
    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {


        ContactModel chosenContact = contactArrayList.get(position);
        Intent intent = new Intent(this, ContactInfo.class);

        intent.putExtra("contactPosition", position);
        intent.putExtra("contactUrl", chosenContact.getUrl());
        intent.putExtra("contactName", chosenContact.getName());
        intent.putExtra("contactAge", chosenContact.getAge());
        intent.putExtra("contactDescription", chosenContact.getDescription());

        startActivityForResult(intent, DETAIL_CONTACT_REQUEST_CODE);

    }

    /**
     * If the user long clicks an item a Dialog will be displayed and the user will be given a
     * choice to remove the selected item or to cancel.
     * @param adapterView
     * @param view
     * @param position
     * @param id
     * @return
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

        itemPosition = position;

        ContactModel chosenContact = contactArrayList.get(position);

        AlertDialog.Builder deleteAlert = new AlertDialog.Builder(MainActivity.this);
        deleteAlert.setTitle("Delete");
        deleteAlert.setMessage("Delete " + chosenContact.getName() + " from your contact list?");
        deleteAlert.setNegativeButton("Cancel", this);
        deleteAlert.setPositiveButton("Yes", this);

        deleteAlert.show();

        return false;
    }


    /**
     * If the user select the positive alternative in the dialog the selectet item will be removed.
     * Otherwise do nothing.
     * @param dialog
     * @param which
     */
    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which == AlertDialog.BUTTON_POSITIVE){
            contactArrayList.remove(itemPosition);
            picassoAdapter.notifyDataSetChanged();
        }
        dialog.dismiss();
    }
}
