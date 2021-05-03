package sg.edu.rp.c346.id19045083.billplease;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    // Step 1: Declare the field variables
    EditText etAmount;
    EditText etPax;
    EditText etDiscount;
    Button btnSplit;
    Button btnReset;
    ToggleButton SVStbtn;
    ToggleButton GSTtbtn;
    RadioGroup rgPayment;
    TextView tvTotalAmount;
    TextView tvPerPax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Step 2: Link the field variable to UI components in layout
        etAmount = findViewById(R.id.editTextAmount);
        etPax = findViewById(R.id.editTextPaxNo);
        etDiscount = findViewById(R.id.editTextDiscount);
        btnSplit = findViewById(R.id.buttonSplit);
        btnReset = findViewById(R.id.buttonReset);
        SVStbtn = findViewById(R.id.toggleButtonService);
        GSTtbtn = findViewById(R.id.toggleButtonGST);
        rgPayment = findViewById(R.id.PaymentMethod);
        tvTotalAmount = findViewById(R.id.textViewTotalAmount);
        tvPerPax = findViewById(R.id.textViewPayPerPax);

        // Code for action
        btnSplit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etAmount.getText().toString().trim().length()!=0
                        && etPax.getText().toString().trim().length()!=0
                        && etDiscount.getText().toString().trim().length()!= 0) {

                    double gst = 1.07;
                    double svs = 1.10;
                    double gst_svs = 1.177;
                    double totalBill = 0;
                    double PerPaxBill = 0;
                    String txt = "";

                    double amountToPay = Double.parseDouble(etAmount.getText().toString());
                    int PaxNum = Integer.parseInt(etPax.getText().toString());
                    int discount = Integer.parseInt(etDiscount.getText().toString());
                    int checkedRadioId = rgPayment.getCheckedRadioButtonId();

                    if (amountToPay > 0 && PaxNum > 0){

                        if (SVStbtn.isChecked() && GSTtbtn.isChecked()) {

                            totalBill = (amountToPay * gst_svs) * (100 - discount) / 100;
                            PerPaxBill = totalBill / PaxNum;

                        } else if (SVStbtn.isChecked() && GSTtbtn.isChecked() == false) {
                            totalBill = (amountToPay * svs) * (100 - discount) / 100;
                            PerPaxBill = totalBill / PaxNum;

                        } else if (GSTtbtn.isChecked() && SVStbtn.isChecked() == false) {
                            totalBill = (amountToPay * gst) * (100 - discount) / 100;
                            PerPaxBill = totalBill / PaxNum;

                        } else {
                            totalBill = amountToPay * (100 - discount) / 100;
                            PerPaxBill = totalBill / PaxNum;

                        }

                        if (checkedRadioId == R.id.radioButtonCash) {
                            txt = "in cash";
                        } else if (checkedRadioId == R.id.radioButtonPayNow) {
                            txt = "via PayNow to 912345678";
                        }

                        tvTotalAmount.setText(String.format("Total Bill: $%.2f", totalBill));
                        tvPerPax.setText(String.format("Each Pays: $%.2f %s", PerPaxBill, txt));

                    } else {
                        // what happen if the amount and pax is 0?
                        tvTotalAmount.setTextColor(Color.parseColor("#FF0000"));
                        tvTotalAmount.setText("Amount and Pax cannot be less than 1!");
                        tvPerPax.setText("");
                    }

                } else {
                    // What happen if input empty?
                    Toast.makeText(MainActivity.this,"Inputs cannot be empty!", Toast.LENGTH_LONG).show();

                }

            }

        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etAmount.setText("");
                etPax.setText("");
                etDiscount.setText("");
                tvTotalAmount.setText("Total Bill: $0");
                tvPerPax.setText("Each Pays: $0");
                SVStbtn.setChecked(false);
                GSTtbtn.setChecked(false);
                rgPayment.check(R.id.radioButtonCash);
                tvTotalAmount.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });
    }
}