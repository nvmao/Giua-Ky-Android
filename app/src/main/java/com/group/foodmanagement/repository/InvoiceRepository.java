package com.group.foodmanagement.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.group.foodmanagement.model.Invoice;
import com.group.foodmanagement.model.Product;

import java.util.ArrayList;
import java.util.List;

public class InvoiceRepository extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "food_management";
    private static final String TABLE = "invoices";
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_CREATED_BY = "created_by";
    private static final String KEY_STATUS = "status";

    public InvoiceRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void reset(){
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db,1,1);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_CREATED_AT + " TEXT,"
                + KEY_CREATED_BY + " TEXT,"
                + KEY_STATUS    +  " TEXT"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);

        // Create tables again
        onCreate(db);
    }

    // code to add the new contact
    public void addInvoice(Invoice invoice) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CREATED_AT, invoice.getCreated_at());
        values.put(KEY_CREATED_BY, invoice.getCreated_by());
        values.put(KEY_STATUS,invoice.getStatus());

        db.insert(TABLE, null, values);

        // add detail

        db.close();
    }

    // code to get the single contact
    public Invoice getInvoice(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE, new String[] { KEY_ID,
                        KEY_CREATED_AT, KEY_CREATED_BY }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Invoice invoice = new Invoice(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2)
        );

        // get detail here by invoice
        return invoice;
    }

    public Invoice getLastInvoice() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE, new String[] { KEY_ID,
                        KEY_CREATED_BY, KEY_CREATED_BY }, null,
                null, null, null, KEY_ID +" DESC", "1");
        if (cursor != null)
            cursor.moveToFirst();

        Invoice product = new Invoice(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2)
        );

        // get detail here by invoice
        return product;
    }

    // code to get all contacts in a list view
    public List<Invoice> getInvoicesByUser(String username) {

        List<Invoice> invoiceList = new ArrayList<Invoice>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE;

        SQLiteDatabase db = this.getWritableDatabase();

//        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
//
//        // Create tables again
//        onCreate(db);

//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Invoice invoice = new Invoice();
                invoice.setId(Integer.parseInt(cursor.getString(0)));
                invoice.setCreated_at(cursor.getString(1));
                invoice.setCreated_by(cursor.getString(2));

                // get list detail

                // Adding contact to list
                invoiceList.add(invoice);
            } while (cursor.moveToNext());
        }

        // return contact list
        return invoiceList;
    }

//    // code to update the single contact
//    public int updateContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, contact.getName());
//        values.put(KEY_PH_NO, contact.getPhoneNumber());
//
//        // updating row
//        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
//                new String[] { String.valueOf(contact.getID()) });
//    }
//
//    // Deleting single contact
//    public void deleteContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
//                new String[] { String.valueOf(contact.getID()) });
//        db.close();
//    }



}
