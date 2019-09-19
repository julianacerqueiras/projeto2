package br.com.projetofinal.location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.content.DialogInterface;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    FusedLocationProviderClient client;
    GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        client = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMinZoomPreference(6.0f);
        mMap.setMaxZoomPreference(20.0f);

        //zoom no Mapa
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.setMyLocationEnabled(true);
        //marcador azul de localização
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
    }

    protected void onResume() {
        super.onResume();
        int errorCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        switch (errorCode) {
            //verifica se o serviço do google play service esta indisponivel no aparelho antes de iniciar o app
            case ConnectionResult.SERVICE_MISSING:
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
            case ConnectionResult.SERVICE_DISABLED:
                Log.d("Teste", "show dialog");

                //finaliza a aplicação caso não tenho o google play service atualizado
                GoogleApiAvailability.getInstance().getErrorDialog(this, errorCode,
                        0, new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                finish();
                            }
                        }).show();
                break;
            case ConnectionResult.SUCCESS:
                Log.d("Teste", "Google Play Services up-to-date");
                break;
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
        PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details
            return;
        }

        client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Log.i("Teste", location.getLatitude() + " " + location.getLongitude());

                    LatLng origin = new LatLng(location.getLatitude(), location.getLongitude());
                   // mMap.addMarker(new MarkerOptions().position(origin).title("Estou aqui"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin,14));

                    // Coordenadas dos QRCode
                    LatLng lasalle = new LatLng(-22.895840, -43.106460);
                    LatLng largoboticario= new LatLng(-22.939032, -43.201441);
                    LatLng largocarioca = new LatLng(-22.906554, -43.178122);

                    mMap.addMarker(new MarkerOptions().position(lasalle).title("Unilasalle"));
                    mMap.addMarker(new MarkerOptions().position(largoboticario).title("Largo do Boticario"));
                    mMap.addMarker(new MarkerOptions().position(largocarioca).title("Largo da Carioca"));

                } else {
                    Log.i("Teste", "null");
                }
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });

        LocationRequest locationRequest = LocationRequest.create();

        //intervalo de tempo em milisegundos para o app busca a localização do usuario
        locationRequest.setInterval(15 * 1000);
        //localização vinda de um outro app pode ser utilizada como serviço de localização
        locationRequest.setFastestInterval(5 * 1000);
        // Balancear tanto a precisão quanto a bateria
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(this) ;
        settingsClient.checkLocationSettings(builder.build())
                .addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i("Teste", locationSettingsResponse.getLocationSettingsStates().isNetworkLocationPresent() + "");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof ResolvableApiException){
                            try {
                                ResolvableApiException resolvable = (ResolvableApiException) e;
                                resolvable.startResolutionForResult(MainActivity.this, 10);
                            }catch (IntentSender.SendIntentException e1) {
                            }
                        }
                    }
                });
        //Listens - esculta toda posição nova que chegar do provider
        LocationCallback locationCallBack = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    Log.i("Teste2", "local is null");
                    return;
                }

                for (Location location : locationResult.getLocations()) {
                    Log.i("Teste2", location.getLatitude() + "");

                }
            }

            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                Log.i("Teste", locationAvailability.isLocationAvailable() + "");

            }
        };
        client.requestLocationUpdates(locationRequest, locationCallBack, null);
        //chamar a requisição de update

    }   }