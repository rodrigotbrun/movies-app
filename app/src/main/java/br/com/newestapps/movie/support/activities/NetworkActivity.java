package br.com.newestapps.movie.support.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import br.com.newestapps.movie.R;
import br.com.newestapps.movie.exceptions.NoLollipopDevice;

public abstract class NetworkActivity extends AppCompatActivity {

    private Context context = NetworkActivity.this;

    private FragmentTransaction fragmentTransaction;

    private ConnectivityManager connectivityManager;
    private IntentFilter connectivityIntentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

    private int connectionType;
    private boolean lastOffline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (isNetworkOnline()) {
            this.onCreateIsConnected();
            lastOffline = false;

            connectionType = getNetworkConnectivityType();
        } else {
            lastOffline = true;
        }
    }

    /**
     * Renderizar > 1 < Fragment em FrameLayout
     *
     * @param fragmentContainerID ID correspondente ao FrameLayout
     * @param fragmentToRender    Fragment para ser renderizado
     */
    protected void renderFragment(@IdRes int fragmentContainerID, Fragment fragmentToRender, boolean replace) {
        if (fragmentTransaction == null)
            fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (replace) {
            fragmentTransaction.replace(fragmentContainerID, fragmentToRender);
            fragmentTransaction.addToBackStack(null);
        } else {
            fragmentTransaction.add(fragmentContainerID, fragmentToRender);
        }

        fragmentTransaction.commit();
    }

    /**
     * Renderizar > 1 < Fragment em FrameLayout
     *
     * @param fragmentContainerID ID correspondente ao FrameLayout
     * @param fragmentToRender    Fragment para ser renderizado
     */
    protected void renderReusableFragment(@IdRes int fragmentContainerID, Fragment fragmentToRender) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(fragmentContainerID, fragmentToRender);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    protected void renderFragment(@IdRes int fragmentContainerID, Fragment fragmentToRender) {
        renderFragment(fragmentContainerID, fragmentToRender, false);
    }

    @Override
    protected void onResume() {
        super.onResume();

        context.registerReceiver(networkBroadcast, connectivityIntentFilter);
    }

    @Override
    protected void onPause() {
        context.unregisterReceiver(networkBroadcast);

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        context = null;
    }

    /**
     * Evento ouvinte para as mudanÃ§as da conexÃ£o com a internet, resposÃ¡vel por disparar corretamente
     * os eventos onConnectionLost e onConnectionRestart.
     */
    private BroadcastReceiver networkBroadcast = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (isNetworkOnline()) {

                int type = getNetworkConnectivityType();

                if (type != connectionType) {
                    connectionType = type;
                    onConnectionTypeChanged(type);
                }

                if (lastOffline) {
                    lastOffline = false;
                    onConnectionRestart();
                }

            } else {
                lastOffline = true;
                onConnectionLost();
            }

        }
    };

    /**
     * Disparado quando a activity for criada e o usuÃ¡rio possuir conexÃ£o com a internet.
     */
    protected abstract void onCreateIsConnected();

    /**
     * Disparado quando a internet do usuÃ¡rio for perdida no meio da execuÃ§Ã£o da activity.
     */
    protected abstract void onConnectionLost();

    /**
     * Disparado quando a internet do usuÃ¡rio for reconectada apÃ³s uma queda de internet.
     */
    protected abstract void onConnectionRestart();

    /**
     * Disparado quando o tipo de internet conectada mudar. (Ex. de WIFI para 3G ou 4G para Wifi)
     */
    protected abstract void onConnectionTypeChanged(int connectionType);

    /**
     * Verifica se a rede esta disponÃ­vel atualmente, se existe conexÃ£o.
     *
     * @return Rede online?
     */
    protected boolean isNetworkOnline() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected());
    }

    /**
     * Retorna o tipo de conexÃ£o com a internet
     *
     * @return Tipo de conexÃ£o
     */
    protected int getNetworkConnectivityType() {
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork.getType();
    }

    protected boolean isConnectedOverWifi() {
        return (getNetworkConnectivityType() == ConnectivityManager.TYPE_WIFI);
    }

    protected boolean isConnectedOverWimax() {
        return (getNetworkConnectivityType() == ConnectivityManager.TYPE_WIMAX);
    }

    protected boolean isConnectedOverEthernet() {
        return (getNetworkConnectivityType() == ConnectivityManager.TYPE_ETHERNET);
    }

    protected boolean isConnectedOverMobile() {
        return (getNetworkConnectivityType() == ConnectivityManager.TYPE_MOBILE);
    }

    protected boolean isConnectedOverVPN() throws NoLollipopDevice {
        if (Build.VERSION.SDK_INT >= 21) {
            return (getNetworkConnectivityType() == ConnectivityManager.TYPE_VPN);
        }

        throw new NoLollipopDevice();
    }

    protected boolean isConnectedBluetooth() {
        return (getNetworkConnectivityType() == ConnectivityManager.TYPE_BLUETOOTH);
    }

    protected void animActivityTransition() {
//        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}