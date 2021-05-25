package com.group.foodmanagement.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.group.foodmanagement.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepository extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "food_management";
    private static final String TABLE_PRODUCTS = "products";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_IMAGE_ID = "image_id";
    private static final String KEY_PRICE = "price";
    private static final String KEY_STOCK = "in_stock";

    public ProductRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void reset(){
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db,1,1);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_IMAGE_ID + " INTEGER,"
                + KEY_NAME + " TEXT,"
                + KEY_PRICE + " FLOAT,"
                + KEY_STOCK + " INTEGER"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);

        // Create tables again
        onCreate(db);
    }

    // code to add the new contact
    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IMAGE_ID, product.getImageId());
        values.put(KEY_NAME, product.getName());
        values.put(KEY_PRICE, product.getPrice());
        values.put(KEY_STOCK,product.getInStock());

        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    // code to get the single contact
    public Product getProduct(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRODUCTS, new String[] { KEY_ID,
                        KEY_IMAGE_ID,KEY_NAME,KEY_PRICE,KEY_STOCK }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);


        if (cursor != null){
            cursor.moveToFirst();
        }

        if(cursor.getCount() == 0){
            return  null;
        }

        Product product = new Product(
                Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)),
                cursor.getString(2),
                Float.parseFloat(cursor.getString(3)),
                Integer.parseInt(cursor.getString(4))
            );
        return product;
    }

    // code to get all contacts in a list view
    public List<String> getAllProduct()
    {
        List<String> productList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                String id =cursor.getString(0);
                String name = cursor.getString(2);
                String stat = id+"_"+name;
                productList.add(stat);


            } while (cursor.moveToNext());
        }
        return productList;
    }
    public List<Product> getAllProducts() {


        List<Product> productList = new ArrayList<Product>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCTS;

        SQLiteDatabase db = this.getWritableDatabase();
//        onCreate(db);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(Integer.parseInt(cursor.getString(0)));
                product.setImageId(Integer.parseInt(cursor.getString(1)));
                product.setName(cursor.getString(2));
                product.setPrice(Float.parseFloat(cursor.getString(3)));
                product.setInStock(Integer.parseInt(cursor.getString(4)));
                // Adding contact to list
                productList.add(product);
            } while (cursor.moveToNext());
        }

        // return contact list
        return productList;
    }

    // code to update the single contact
    public int updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IMAGE_ID, product.getImageId());
        values.put(KEY_NAME, product.getName());
        values.put(KEY_PRICE, product.getPrice());
        values.put(KEY_STOCK,product.getInStock());

        // updating row
        return db.update(TABLE_PRODUCTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(product.getId()) });
    }
//
//    // Deleting single contact
//    public void deleteContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
//                new String[] { String.valueOf(contact.getID()) });
//        db.close();
//    }


}