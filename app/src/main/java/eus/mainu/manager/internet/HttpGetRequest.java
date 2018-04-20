package eus.mainu.manager.internet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.Menu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;

import eus.mainu.manager.R;
import eus.mainu.manager.datalayer.Imagen;
import eus.mainu.manager.datalayer.Valoracion;


//Clase para hacer las peticiones GET
public class HttpGetRequest extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        String url = params[0];
        String result, inputLine;
        String basicAuth = "Basic " + Base64.encodeToString("usuario:clave".getBytes(), Base64.NO_WRAP);

        try {
            URL myUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection)myUrl.openConnection();
            connection.setRequestProperty ("Authorization", basicAuth); //Se pone el usuario y la contraseña en la cabecera
            connection.setRequestMethod("GET");
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.connect();

            InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }

            reader.close();
            streamReader.close();
            result = stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            result = null;
        }

        return result;
    }

    public boolean isConnected(Context mContext) {
            ConnectivityManager cm =
                    (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }


    //Metodo para pedir el menu del dia, todas las operaciones de parseo del mensaje JSON se hacen dentro de el
    public ArrayList<Valoracion> getValoraciones() {

        String result;
        ArrayList<Valoracion> valoraciones = new ArrayList<>();

        try {
            result = execute("https://api.mainu.eus/valoraciones").get();

            Administrador_JSON json = new Administrador_JSON();
            valoraciones = json.getValoraciones(result);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return valoraciones;

    }

    //Metodo para pedir el menu del dia, todas las operaciones de parseo del mensaje JSON se hacen dentro de el
    public ArrayList<Imagen> getImagenes() {

        String result;
        ArrayList<Imagen> imagenes = new ArrayList<>();

        try {
            result = execute("https://api.mainu.eus/imagenes").get();

            Administrador_JSON json = new Administrador_JSON();
            imagenes = json.getImagenes(result);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return imagenes;

    }

    public void aceptar(Valoracion valoracion){

        try {
            String result = execute("https://api.mainu.eus/update_val/"+valoracion.getTipo()+"/"+valoracion.getId()+"?action=visible").get();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void aceptar(Imagen imagen){

        try {
            String result = execute("https://api.mainu.eus/update_val/"+imagen.getTipo()+"/"+imagen.getId()+"?action=visible").get();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelar(Valoracion valoracion){


        try {
            String result = execute("https://api.mainu.eus/update_val/"+valoracion.getTipo()+"/"+valoracion.getId()+"?action=delete").get();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelar(Imagen imagen){


        try {
            String result = execute("https://api.mainu.eus/update_val/"+imagen.getTipo()+"/"+imagen.getId()+"?action=delete").get();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
