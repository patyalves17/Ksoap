package com.example.paty.ksoap;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends AppCompatActivity {

    String METHOD_NAME="soma";
    String SOAP_ACTION="";
    String NAMESPACE="http://paty.com.br/";
    //String SOAP_URL="http://10.3.1.10:8080/Calculadora/Calculadora";
    String SOAP_URL = "http://192.168.2.20:8080/CalculadoraWSService/CalculadoraWS";

    private EditText etValor1;
    private EditText etValor2;



    SoapObject request;
    SoapPrimitive calcular;
    ProgressDialog pdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etValor1=(EditText)findViewById(R.id.etValor1);
        etValor2=(EditText)findViewById(R.id.etValor2);

      //  valor1=Integer.parseInt(etValor1.getText().toString());
    //    valor2=Integer.parseInt(etValor2.getText().toString());

//        CalcularAsync calcularAsync = new CalcularAsync();
//        calcularAsync.execute();
    }

    public void soma(View v){

       int valor1=Integer.parseInt(etValor1.getText().toString());
       int valor2=Integer.parseInt(etValor2.getText().toString());

        System.out.println(etValor1.getText());
        System.out.println(etValor2.getText());

        CalcularAsync calcularAsync = new CalcularAsync();
        calcularAsync.execute(valor1,valor2);
    }

    private class CalcularAsync extends AsyncTask<Integer, Void, String> {

        @Override
        protected String doInBackground(Integer... params) {

            request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("numero1", params[0]);
            request.addProperty("numero2", params[1]);
            //request.addProperty("op", "+");

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_URL);
            try {
                httpTransport.call(SOAP_ACTION, envelope);
                calcular = (SoapPrimitive) envelope.getResponse();
            } catch (Exception e) {
                e.getMessage();
            }
            //return null;
            return calcular.toString();
        }

        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);
            pdialog.dismiss();
            Toast.makeText(getApplicationContext(), "Resultado: " + calcular.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = new ProgressDialog(MainActivity.this);
            pdialog.setMessage("Converting...");
            pdialog.show();
        }
    }
}



