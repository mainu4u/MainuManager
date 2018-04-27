package eus.mainu.manager.internet;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import eus.mainu.manager.VariablesGlobales;
import eus.mainu.manager.datalayer.Plato;

/**
 * Created by narciso on 19/03/18.
 * Clase que gestiona el envio de informacion mediante POST
 */

public class HttpPostRequest extends AsyncTask<String, Void, String> {


    @Override
    protected String doInBackground(String... strings) {

        String data = "";
        String basicAuth = "Basic " + Base64.encodeToString("".getBytes(), Base64.NO_WRAP);

        HttpURLConnection httpURLConnection = null;
        try {

            httpURLConnection = (HttpURLConnection) new URL(strings[0]).openConnection();
            httpURLConnection.setRequestProperty ("Authorization", basicAuth); //Se pone el usuario y la contraseña en la cabecera
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type","application/json; charset=utf-8");

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(strings[1]);
            wr.flush();
            wr.close();

            InputStream in = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(in);

            int inputStreamData = inputStreamReader.read();
            while (inputStreamData != -1) {
                char current = (char) inputStreamData;
                inputStreamData = inputStreamReader.read();
                data += current;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d("TAG", result);//Lo que recibimos del servidor despues de haber recibido los datos
    }

    public void postPlato(Plato plato){

        try {
            JSONObject postData = new JSONObject();
            postData.put("nombre",plato.getNombre());
            postData.put("tipo",plato.getTipo());

            execute("https://api.mainu.eus/add_plato", postData.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void actualizaMenu(ArrayList<Plato> arrayPlato){

        StringBuffer menu= new StringBuffer();
        JSONArray postArray = new JSONArray();

        for(int i=0; i < arrayPlato.size(); i++){
            //menu.append(arrayPlato.get(i).getId());
            //menu.append(",");
            postArray.put(arrayPlato.get(i).getId());
        }

        try {
            JSONObject postData = new JSONObject();
            postData.put("menu",postArray);
            execute("https://api.mainu.eus/update_menu", postData.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
