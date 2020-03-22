package computing.corona.mobile.cov_erage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    String userId;
    String postalCode;
    int gender;
    int yearOfBirth;
    int generalHealth;
    int coronaVirus;
    int numberOfContacts;
    int coughing;
    int temperature;
    int headache;
    int soreThroat;
    int runnyNose;
    int limbPain;
    int diarrhea;
    int loneliness;
    int insomnia;

    EditText postLeitZahlEditText;
    Spinner genderSpinner;
    Spinner yearOfBirthSpinner;
    SeekBar generalHealthSeekbar;
    Spinner coronaVirusSpinner;
    EditText numberOfContactsEditText;
    Spinner coughingSpinner;
    Spinner temperatureSpinner;
    Spinner headacheSpinner;
    Spinner soreThroatSpinner;
    Spinner runnyNoseSpinner;
    Spinner limbPainSpinner;
    Spinner diarrheaSpinner;
    Spinner lonelinessSpinner;
    Spinner insomniaSpinner;
    Button sendButton;
    SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPrefs = getApplicationContext()
                .getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);

        userId = sharedPrefs.getString("userId","-1");

        if (userId.equals("-1")) {
            userId = UUID.randomUUID().toString();
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("userId", userId);
            editor.apply();
        }

        postalCode = sharedPrefs.getString("postalCode","");
        gender = sharedPrefs.getInt("gender", -1);
        yearOfBirth = sharedPrefs.getInt("yearOfBirth",0);
        generalHealth = sharedPrefs.getInt("generalHealth",5);
        coronaVirus = sharedPrefs.getInt("coronaVirus",-1);
        numberOfContacts = sharedPrefs.getInt("numberOfContacts",-1);
        coughing = sharedPrefs.getInt("coughing",-1);
        temperature = sharedPrefs.getInt("temperature",-1);
        headache = sharedPrefs.getInt("headache",-1);
        soreThroat = sharedPrefs.getInt("soreThroat",-1);
        runnyNose = sharedPrefs.getInt("runnyNose",-1);
        limbPain = sharedPrefs.getInt("limbPain",-1);
        diarrhea = sharedPrefs.getInt("diarrhea",-1);
        loneliness = sharedPrefs.getInt("loneliness",-1);
        insomnia = sharedPrefs.getInt("insomnia",-1);

        postLeitZahlEditText = findViewById(R.id.postleitzahlEditText);
        postLeitZahlEditText.setText(postalCode);

        genderSpinner = findViewById(R.id.geschlechtSpinner);
        if (gender > 0) {
            genderSpinner.setSelection(gender);
        } else {
            genderSpinner.setSelection(0);
        }

        ArrayList<String> years = new ArrayList<String>();
        years.add("keine Angabe");
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1900; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);

        yearOfBirthSpinner = (Spinner)findViewById(R.id.yearOfBirthSpinner);
        yearOfBirthSpinner.setAdapter(adapter);
        if (yearOfBirth > 0) {
            int spinnerPosition = adapter.getPosition(String.valueOf(yearOfBirth));
            yearOfBirthSpinner.setSelection(spinnerPosition);
        } else {
            yearOfBirthSpinner.setSelection(0);
        }

        generalHealthSeekbar = findViewById(R.id.generalHealthSeekbar);
        generalHealthSeekbar.setProgress(generalHealth);

        coronaVirusSpinner = findViewById(R.id.coronaVirusSpinner);
        if (coronaVirus > 0) {
            coronaVirusSpinner.setSelection(coronaVirus);
        } else {
            coronaVirusSpinner.setSelection(0);
        }

        numberOfContactsEditText = findViewById(R.id.numberOfContactsEditText);
        if (numberOfContacts > 0) {
            numberOfContactsEditText.setText(numberOfContacts + "");
        }

        coughingSpinner = findViewById(R.id.coughingSpinner);
        if (coughing > 0) {
            coughingSpinner.setSelection(coughing);
        } else {
            coughingSpinner.setSelection(0);
        }

        temperatureSpinner = findViewById(R.id.temperatureSpinner);
        if (temperature > 0) {
            temperatureSpinner.setSelection(temperature);
        } else {
            temperatureSpinner.setSelection(0);
        }

        headacheSpinner = findViewById(R.id.headacheSpinner);
        if (headache > 0) {
            headacheSpinner.setSelection(headache);
        } else {
            headacheSpinner.setSelection(0);
        }

        soreThroatSpinner = findViewById(R.id.soreThroatSpinner);
        if (soreThroat > 0) {
            soreThroatSpinner.setSelection(soreThroat);
        } else {
            soreThroatSpinner.setSelection(0);
        }

        runnyNoseSpinner = findViewById(R.id.runnyNoseSpinner);
        if (runnyNose > 0) {
            runnyNoseSpinner.setSelection(runnyNose);
        } else {
            runnyNoseSpinner.setSelection(0);
        }

        limbPainSpinner = findViewById(R.id.limbPainSpinner);
        if (limbPain > 0) {
            limbPainSpinner.setSelection(limbPain);
        } else {
            limbPainSpinner.setSelection(0);
        }

        diarrheaSpinner = findViewById(R.id.diarrheaSpinner);
        if (diarrhea > 0) {
            diarrheaSpinner.setSelection(diarrhea);
        } else {
            diarrheaSpinner.setSelection(0);
        }

        lonelinessSpinner = findViewById(R.id.lonelinessSpinner);
        if (loneliness > 0) {
            lonelinessSpinner.setSelection(loneliness);
        } else {
            lonelinessSpinner.setSelection(0);
        }

        insomniaSpinner = findViewById(R.id.insomniaSpinner);
        if (insomnia > 0) {
            insomniaSpinner.setSelection(insomnia);
        } else {
            insomniaSpinner.setSelection(0);
        }

        sendButton = findViewById(R.id.sendButton);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPrefs.edit();
                postalCode = postLeitZahlEditText.getText().toString();
                editor.putString("postalCode",postalCode);

                if (genderSpinner.getSelectedItemPosition() > 0) {
                    gender = genderSpinner.getSelectedItemPosition();
                } else {
                    gender = -1;
                }
                editor.putInt("gender",gender);

                if (yearOfBirthSpinner.getSelectedItemPosition() > 0) {
                    yearOfBirth = Integer.parseInt((String)yearOfBirthSpinner.getSelectedItem());
                } else {
                    yearOfBirth = -1;
                }
                editor.putInt("yearOfBirth",yearOfBirth);

                generalHealth = generalHealthSeekbar.getProgress();
                editor.putInt("generalHealth",generalHealth);

                if (coronaVirusSpinner.getSelectedItemPosition() > 0) {
                    coronaVirus = coronaVirusSpinner.getSelectedItemPosition();
                } else {
                    coronaVirus = -1;
                }
                editor.putInt("coronaVirus",coronaVirus);

                if (numberOfContactsEditText.getText().toString().length()>0) {
                    numberOfContacts = Integer.valueOf(numberOfContactsEditText.getText().toString());
                } else {
                    numberOfContacts = -1;
                }
                editor.putInt("numberOfContacts",numberOfContacts);

                if (coughingSpinner.getSelectedItemPosition() > 0) {
                    coughing = coughingSpinner.getSelectedItemPosition();
                } else {
                    coughing = -1;
                }
                editor.putInt("coughing",coughing);

                if (temperatureSpinner.getSelectedItemPosition() > 0) {
                    temperature = temperatureSpinner.getSelectedItemPosition();
                } else {
                    temperature = -1;
                }
                editor.putInt("temperature",temperature);

                if (headacheSpinner.getSelectedItemPosition() > 0) {
                    headache = headacheSpinner.getSelectedItemPosition();
                } else {
                    headache = -1;
                }
                editor.putInt("headache",headache);

                if (soreThroatSpinner.getSelectedItemPosition() > 0) {
                    soreThroat = soreThroatSpinner.getSelectedItemPosition();
                } else {
                    soreThroat = -1;
                }
                editor.putInt("soreThroat",soreThroat);

                if (runnyNoseSpinner.getSelectedItemPosition() > 0) {
                    runnyNose = runnyNoseSpinner.getSelectedItemPosition();
                } else {
                    runnyNose = -1;
                }
                editor.putInt("runnyNose",runnyNose);

                if (limbPainSpinner.getSelectedItemPosition() > 0) {
                    limbPain = limbPainSpinner.getSelectedItemPosition();
                } else {
                    limbPain = -1;
                }
                editor.putInt("limbPain",limbPain);

                if (diarrheaSpinner.getSelectedItemPosition() > 0) {
                    diarrhea = diarrheaSpinner.getSelectedItemPosition();
                } else {
                    diarrhea = -1;
                }
                editor.putInt("diarrhea",diarrhea);

                if (lonelinessSpinner.getSelectedItemPosition() > 0) {
                    loneliness = lonelinessSpinner.getSelectedItemPosition();
                } else {
                    loneliness = -1;
                }
                editor.putInt("loneliness",loneliness);

                if (insomniaSpinner.getSelectedItemPosition() > 0) {
                    insomnia = insomniaSpinner.getSelectedItemPosition();
                } else {
                    insomnia = -1;
                }
                editor.putInt("insomnia",insomnia);

                /*
                    postalCode = sharedPrefs.getString("postalCode","");
                    gender = sharedPrefs.getInt("gender", -1);
                    yearOfBirth = sharedPrefs.getInt("yearOfBirth",0);
                    generalHealth = sharedPrefs.getInt("generalHealth",5);
                    coronaVirus = sharedPrefs.getInt("coronaVirus",-1);
                    numberOfContacts = sharedPrefs.getInt("numberOfContacts",-1);
                    coughing = sharedPrefs.getInt("coughing",-1);
                    temperature = sharedPrefs.getInt("temperature",-1);
                    headache = sharedPrefs.getInt("headache",-1);
                    soreThroat = sharedPrefs.getInt("soreThroat",-1);
                    runnyNose = sharedPrefs.getInt("runnyNose",-1);
                    limbPain = sharedPrefs.getInt("limbPain",-1);
                    diarrhea = sharedPrefs.getInt("diarrhea",-1);
                    loneliness = sharedPrefs.getInt("loneliness",-1);
                    insomnia = sharedPrefs.getInt("insomnia",-1);
                 */

                JSONObject postRequest = new JSONObject();
                try {
                    postRequest.put("userId", userId);
                    postRequest.put("postalCode", postalCode);
                    postRequest.put("gender", gender);
                    postRequest.put("yearOfBirth", yearOfBirth);
                    postRequest.put("generalHealth", generalHealth);
                    postRequest.put("coronaVirus", coronaVirus);
                    postRequest.put("numberOfContacts", numberOfContacts);
                    postRequest.put("coughing", coughing);
                    postRequest.put("temperature", temperature);
                    postRequest.put("headache", headache);
                    postRequest.put("soreThroat", soreThroat);
                    postRequest.put("runnyNose", runnyNose);
                    postRequest.put("limbPain", limbPain);
                    postRequest.put("diarrhea", diarrhea);
                    postRequest.put("loneliness", loneliness);
                    postRequest.put("insomnia", insomnia);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                long thisTS = Calendar.getInstance().getTime().getTime();
                long lastTS = sharedPrefs.getLong("lastSent",-1);
                if (getCountOfDays(thisTS,lastTS) < 0) {
                    new sendDataTask(postRequest).execute();
					Toast.makeText(MainActivity.this,"Daten gesendet",Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(MainActivity.this,"Jeden Tag nur ein Senden erlaubt",Toast.LENGTH_LONG).show();
                }
                editor.putLong("lastSent",thisTS);
                editor.apply();
            }
        });
    }

    public int getCountOfDays(long startTS, long endTS) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        Date createdConvertedDate = new Date(), expireConvertedDate = new Date(), todayWithZeroTime = null;
        try {
            createdConvertedDate.setTime(startTS);
            expireConvertedDate.setTime(endTS);

            Date today = new Date();

            todayWithZeroTime = dateFormat.parse(dateFormat.format(today));
        } catch (ParseException | java.text.ParseException e) {
            e.printStackTrace();
        }

        int cYear = 0, cMonth = 0, cDay = 0;

        if (createdConvertedDate.after(todayWithZeroTime)) {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(createdConvertedDate);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);

        } else {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(todayWithZeroTime);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);
        }

        Calendar eCal = Calendar.getInstance();
        eCal.setTime(expireConvertedDate);

        int eYear = eCal.get(Calendar.YEAR);
        int eMonth = eCal.get(Calendar.MONTH);
        int eDay = eCal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(cYear, cMonth, cDay);
        date2.clear();
        date2.set(eYear, eMonth, eDay);

        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();

        float dayCount = (float) diff / (24 * 60 * 60 * 1000);

        return (int) dayCount;
    }


    private class sendDataTask extends AsyncTask<String, String, String> {

        JSONObject postBody;


        private sendDataTask(JSONObject postBody) {
            this.postBody = postBody;
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url = new URL("https://aiulvempz3.execute-api.eu-central-1.amazonaws.com/production/droplet");
                HttpsURLConnection urlConnection =
                        (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                byte[] outputInBytes = postBody.toString().getBytes("UTF-8");
                OutputStream os = urlConnection.getOutputStream();
                os.write(outputInBytes);
                os.close();
                /*
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream()));
                String inputLine;
                String response = "";
                while ((inputLine = in.readLine()) != null) {
                    response += inputLine;
                }

                in.close();
                 */
                int status = urlConnection.getResponseCode();
                Log.d("COVID", "status: " + status);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}
