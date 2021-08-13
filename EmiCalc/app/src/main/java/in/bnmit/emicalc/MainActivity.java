package in.bnmit.emicalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText pa, dp, ir, lt;
    Button cme;
    TextView emi;
    float emif, paf, irf, dpf, ltf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pa = (EditText) findViewById(R.id.principalET);
        dp = (EditText) findViewById(R.id.downET);
        ir = (EditText) findViewById(R.id.interestET);
        lt = (EditText) findViewById(R.id.loanET);
        cme = (Button) findViewById(R.id.button);
        emi = (TextView) findViewById(R.id.emiTV);


        cme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pas = pa.getText().toString();
                String dps = dp.getText().toString();
                String irs = ir.getText().toString();
                String lts = lt.getText().toString();

                paf = Float.parseFloat(pas);
                dpf = Float.parseFloat(dps);
                irf = Float.parseFloat(irs)/12;
                ltf = Float.parseFloat(lts);

                paf = paf - dpf;
                //EMI = P X R X (1+R)^N / [(1+R)^N-1]
                emif = (float) (paf * irf * (Math.pow((1 + irf), ltf))/((Math.pow((1+irf),ltf)) - 1))/12;
                String emis = Float.toString(emif);
                emi.setText("EMI: " + emis);
//                Toast.makeText(getBaseContext(), "Yo", Toast.LENGTH_LONG).show();
            }
        });

    }
}