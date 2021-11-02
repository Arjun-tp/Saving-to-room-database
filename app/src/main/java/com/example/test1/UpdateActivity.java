package com.example.test1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.test1.EntityClass.PersonModel;

public class UpdateActivity extends AppCompatActivity {

    EditText name, age, fee;
    CalendarView calender;
    Button save, back, update;
    TextView cal;
    String personName, personAge, personFee, personDate, name_txt = "", age_txt = "", fee_txt = "", termDate_text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialization
        name =findViewById(R.id.extName);
        age = findViewById(R.id.extAge);
        fee = findViewById(R.id.extFee);
        calender = findViewById(R.id.calView);
        save = findViewById(R.id.btnSave);
        update = findViewById(R.id.btnUpdate);
        back = findViewById(R.id.btnBack);
        cal = findViewById(R.id.txvCalender);

        update.setVisibility(View.INVISIBLE);
        save.setVisibility(View.VISIBLE);

        personName = getIntent().getStringExtra("nameSent");
        personAge = getIntent().getStringExtra("ageSent");
        personFee = getIntent().getStringExtra("feeSent");
        personDate = getIntent().getStringExtra("dateSent");

        name.setText(personName);
        age.setText(personAge);
        fee.setText(personFee);
        cal.setText(personDate);

        name_txt = name.getText().toString().trim();
        age_txt = age.getText().toString().trim();
        fee_txt = fee.getText().toString().trim();

        // Hide and show save and Update Buttons
        if (!name_txt.equals("")){
            save.setVisibility(View.INVISIBLE);
            update.setVisibility(View.VISIBLE);
        }

        // Save button Listener
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        // Update the data in Room
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = getIntent().getExtras().getString("idSent");
                name_txt = name.getText().toString().trim();
                age_txt = age.getText().toString().trim();
                fee_txt = fee.getText().toString().trim();
                termDate_text = cal.getText().toString().trim();
                DatabaseClass.getDatabase(getApplicationContext()).getDao().updateData(name_txt, age_txt, fee_txt, termDate_text, Integer.parseInt(id));
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                DatabaseClass.getDatabase(getApplicationContext()).getDao().getAllData();
//                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                DatabaseClass.getDatabase(getApplicationContext()).getDao().getAllData();
            }
        });

        // Calender Listener
        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                i1 = i1+1;
                String date = i + "/" + i1 + "/" + i2;
                Log.d("CalenderActivity", "On selectedDay Change date: " + date);
                cal.setText(date);
            }
        });
    }

    // Save Data to Room
    private void saveData() {
        name_txt = name.getText().toString().trim();
        age_txt = age.getText().toString().trim();
        fee_txt = fee.getText().toString().trim();
        termDate_text = cal.getText().toString().trim();

        PersonModel model = new PersonModel();
        model.setName(name_txt);
        model.setAge(age_txt);
        model.setFee(fee_txt);
        model.setTermDate(termDate_text);

        DatabaseClass.getDatabase(getApplicationContext()).getDao().insertAllData(model);
        name.setText("");
        age.setText("");
        fee.setText("");
        cal.setText("");
        Toast.makeText(this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
    }
}