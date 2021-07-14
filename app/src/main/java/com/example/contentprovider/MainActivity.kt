package com.example.contentprovider
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var read_contacts_btn: Button
    lateinit var obj: ContactPOJO
    lateinit var recycler:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        read_contacts_btn = findViewById(R.id.read_contects)
        recycler = findViewById(R.id.recyclerview)
        var contactlist: MutableList<ContactPOJO> = ArrayList()
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = ContactsAdapter(contactlist)



        read_contacts_btn.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                if (checkSelfPermission(android.Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_DENIED)
                {
                    requestPermissions(arrayOf(android.Manifest.permission.READ_CONTACTS),11)
                }
                else
                {
                    readContacts()
                }
            }
            else
            {
                // system is less than Marshmallow , runtime permissions not required
                readContacts()
            }
        }
    }

    private fun readContacts() {
        val contacts = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null)
        var contactlist: MutableList<ContactPOJO> = ArrayList()
        while (contacts?.moveToNext() == true)
        {
            val name = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val number = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            obj = ContactPOJO(name,number)
            contactlist.add(obj)
        }
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = ContactsAdapter(contactlist)
        contacts?.close()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==11 && grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            readContacts()
        }
        else
        {
            Toast.makeText(this,"Permission Denied!", Toast.LENGTH_LONG).show()
        }
    }
}