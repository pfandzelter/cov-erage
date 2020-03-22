package computing.corona.mobile.cov_erage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView textViewYoursNumber = findViewById(R.id.textViewYoursNumber);
        SharedPreferences sharedPrefs = getApplicationContext()
                .getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);

        int submissionCounter = sharedPrefs.getInt("submissionCounter", 0);
        textViewYoursNumber.setText(submissionCounter + "");
    }

    public void onSubmitClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
