package com.example.NoSound;

import android.Manifest;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.NoSound.Business.BusinessData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements OnDataPass {

   
    private BusinessData order;
    private String orderID;
    private Employee employee;

    private static final int EXTERNAL_STORAGE_PERMISSION_CODE = 23;
    private HashMap<String,BusinessData> customerInfo = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method creates a file and requests permission to store files in the external storage, which in this case is Documents.
     * It then calls upon the method that will store the file in the storage.
     * @param v
     */
    public void savePublicly(View v) {
        // Requesting Permission to access External Storage
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                EXTERNAL_STORAGE_PERMISSION_CODE);


        // getExternalStoragePublicDirectory() represents root of external storage, we are using DOWNLOADS
        // We can use following directories: MUSIC, PODCASTS, ALARMS, RINGTONES, NOTIFICATIONS, PICTURES, MOVIES
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

        // Storing the data in file with name as geeksData.txt
        File file = new File(folder, "customerinfo.txt");
        writeTextData(file,orderID, order);
    }

    /**
     * The method puts ID coupled with Name into a map then makes sure that it is saved on the file
     * that it takes as an argument.
     * @param file  a File that the map will be stored in.
     * @param orderID a String representing the ID of the order.
     * @param order a BusinessData variable conating information about the order.
     */
    private void writeTextData(File file, String orderID ,BusinessData order) {
        if (!(orderID == null || order == null)){
            customerInfo.put(orderID,order);
        }
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(customerInfo);
            objectOutputStream.close();
            Toast.makeText(this, "Done" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * This method creates a file and requests permission to store files in the external storage, which in this case is Documents.
     * It then calls upon the method that will store the file in the storage. This method stores Employee info however.
     * @param v
     */
    public void saveEmployeePublicly(View v) {
        // Requesting Permission to access External Storage
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                EXTERNAL_STORAGE_PERMISSION_CODE);


        // getExternalStoragePublicDirectory() represents root of external storage, we are using DOWNLOADS
        // We can use following directories: MUSIC, PODCASTS, ALARMS, RINGTONES, NOTIFICATIONS, PICTURES, MOVIES
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

        // Storing the data in file with name as geeksData.txt
        File file = new File(folder, "employeeinfo.txt");
        writeEmployeeData(file, employee);
    } /**
     * This method takes an employee an stores the employee in a file. Similarly to the previous method WriteTextData.
     * @param file  a File that the map will be stored in.
     * @param employee a String representing the businesses name .
     */
    private void writeEmployeeData(File file, Employee employee){
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(employee);
            objectOutputStream.close();
            Toast.makeText(this, "Done" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void onDataPass(BusinessData order,String orderID) {
        this.order = order;
        this.orderID = orderID;
    }

    @Override
    public void onEmployeePass(Employee employee) {
        this.employee = employee;
    }

}