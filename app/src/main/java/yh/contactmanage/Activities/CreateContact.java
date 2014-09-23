package yh.contactmanage.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import yh.contactmanage.Adapters.PicassoAdapter;
import yh.contactmanage.Models.ContactModel;
import yh.contactmanage.R;


public class CreateContact extends Activity {

    //Class variables
    ArrayList<ContactModel> contactArrayList;
    PicassoAdapter adapter;

    private int selectedItemPosition;
    private TextView createAndEditTv;
    private EditText urlEt;
    private EditText nameEt;
    private EditText ageEt;
    private EditText descrEt;
    private int requestCode;

    /**
     * If the user comes from the ContactInfo activity and wants to edit a user, the Edit text
     * fields will already be filled in with the values of the contact.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        adapter = new PicassoAdapter(this, contactArrayList);

        requestCode = getIntent().getIntExtra("requestCode", 0);

        createAndEditTv = (TextView) findViewById(R.id.createAndEditTV);
        urlEt = (EditText) findViewById(R.id.nameAndAgeTv);
        nameEt = (EditText) findViewById(R.id.nameEt);
        ageEt = (EditText) findViewById(R.id.ageEt);
        descrEt = (EditText) findViewById(R.id.descrEt);



        if (requestCode == ContactInfo.EDIT_CONTACT_REQUEST_CODE){

            selectedItemPosition = getIntent().getIntExtra("chosenContactUrl", 0);

            String url = getIntent().getStringExtra("chosenContactUrl");
            String name = getIntent().getStringExtra("chosenContactName");
            String age = getIntent().getStringExtra("chosenContactAge");
            String descr = getIntent().getStringExtra("chosenContactDescr");

            createAndEditTv.setText(R.string.editContact);

            fillTextFields(url, name, age, descr);

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_contact, menu);
        return true;
    }

    /**
     * Sends off the edited or the new data to the MainActivity through the ContactInfo activity.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.actionCreateAccept){

            if (requestCode == MainActivity.ADD_CONTACT_REQUEST_CODE) {

                String newContactUrl = urlEt.getText().toString().trim();
                String newContactName = nameEt.getText().toString().trim();
                String newContactAge = ageEt.getText().toString().trim();
                String newContactDescr = descrEt.getText().toString().trim();

                if(!newContactUrl.equals("") && !newContactName.equals("") &&
                   !newContactAge.equals("") && !newContactDescr.equals("")){

                    Intent intentAdd = getIntent();

                    intentAdd.putExtra("newContactUrl", newContactUrl);
                    intentAdd.putExtra("newContactName", newContactName);
                    intentAdd.putExtra("newContactAge", newContactAge);
                    intentAdd.putExtra("newContactDescr", newContactDescr);

                    setResult(RESULT_OK, intentAdd);
                    finish();

                } else {

                    Toast.makeText(this, R.string.toastInputErrorText, Toast.LENGTH_SHORT).show();
                }


            } else {


                String editedContactUrl = urlEt.getText().toString().trim();
                String editedContactName = nameEt.getText().toString().trim();
                String editedContactAge = ageEt.getText().toString().trim();
                String editedContactDescr = descrEt.getText().toString().trim();

                if(!editedContactUrl.equals("") && !editedContactName.equals("") &&
                   !editedContactAge.equals("") && !editedContactDescr.equals("")){

                    Intent intentEdit = getIntent();

                    intentEdit.putExtra("selectedItemPosition", selectedItemPosition);
                    intentEdit.putExtra("editedContactUrl", editedContactUrl);
                    intentEdit.putExtra("editedContactName", editedContactName);
                    intentEdit.putExtra("editedContactAge", editedContactAge);
                    intentEdit.putExtra("editedContactDescr", editedContactDescr);

                    setResult(RESULT_OK, intentEdit);
                    finish();
                } else {
                    Toast.makeText(this, R.string.toastInputErrorText, Toast.LENGTH_SHORT).show();
                }
            }
        }

        if(id == R.id.actionCreateCancel){

            Intent goBackIntent = new Intent(this, MainActivity.class);

            startActivity(goBackIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Sets the text in the Edit texts with the values of a contact
     * @param url
     * @param name
     * @param age
     * @param descr
     */
    public void fillTextFields(String url, String name, String age, String descr){

        urlEt.setText(url, TextView.BufferType.EDITABLE);
        nameEt.setText(name, TextView.BufferType.EDITABLE);
        ageEt.setText(age, TextView.BufferType.EDITABLE);
        descrEt.setText(descr, TextView.BufferType.EDITABLE);
    }
}
