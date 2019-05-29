package com.example.webservice;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.HttpsTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText et;
    private Button button;
    private TextView tv;
    String fahren;
    String celsius;
    final String NAMESPACE = "https://www.w3schools.com/xml/";
    final String URL="https://www.w3schools.com/xml/tempconvert.asmx";
    final String SOAP_ACTION="https://www.w3schools.com/xml/CelsiusToFahrenheit";
    final String METHOD_NAME="CelsiusToFahrenheit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.button);
        tv=findViewById(R.id.tv);
        et=findViewById(R.id.et);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et.getText().length() !=0 && et.getText().toString()!=null){
                    celsius=et.getText().toString();
                    AsyncCallWS task = new AsyncCallWS();
                    task.execute();
                }
                else{
                    tv.setText("asdasdad");
                }


            }
        });
    }

    private class AsyncCallWS extends AsyncTask<String,Void,Void> {
        @Override
        protected Void doInBackground(String... params) {
            getFahrenheit(celsius);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tv.setText("hesaplaniyor.");
        }

        @Override
        protected void onPostExecute(Void o) {
            super.onPostExecute(o);
            tv.setText(fahren + "F");
        }

        @Override
        protected void onProgressUpdate(Void[] values) {
            super.onProgressUpdate(values);
        }

    }
    public void getFahrenheit(String celsius){
        SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName("Celsius");
        celsiusPI.setValue(celsius);
        celsiusPI.setType(double.class);
        request.addProperty(celsiusPI);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE se=new HttpTransportSE(URL);
        try {
            se.call(SOAP_ACTION,envelope);
            SoapPrimitive responce = (SoapPrimitive) envelope.getResponse();
            fahren = responce.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

    }
}
