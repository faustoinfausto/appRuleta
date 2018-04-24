package com.example.sergio.iter1demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    private Handler mHandler = new Handler();
    int num_ruleta;

    Lista_Smart_tv lista_smart_tv;
    String usuario_Smart_tv;
    boolean red_social;

    ConstraintLayout layout_loginSTV, layout_menuSTV, layout_detectar_user, layout_dia_especial,
            layout_menu_usuario, layout_ruleta, layout_premio;
    EditText loginSTV_user, loginSTV_pass;
    Button loginSTV_btn_login, menuSTV_btnUsuario, menuSTV_btnQR, detectar_user_btnContinuar,
            dia_especial_btn, ruleta_btngirar, ruleta_btnfin, premio_btnNext,
            menu_user_btn_ruleta, menu_user_btn_twitter, menu_user_btn_salir;
    VideoView menuSTV_video;
    ImageView detectar_user_imagen, ruleta_amarillo, ruleta_azul, ruleta_rojo, ruleta_verde,
            premio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista_smart_tv = new Lista_Smart_tv();

        usuario_Smart_tv = null;

        red_social = false;

        num_ruleta = 0;

        _asignar_elementos();
        _invisibilidad_layouts();

        layout_loginSTV.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.loginSTV_btn_login:
                if( lista_smart_tv.usuario_valido( loginSTV_user.getText().toString(),
                        loginSTV_pass.getText().toString()) ){
                    usuario_Smart_tv = loginSTV_user.getText().toString();
                    _invisibilidad_layouts();
                    _mostrar_menu_STV();
                }
                break;

            case R.id.menuSTV_btnUsuario:
                _invisibilidad_layouts();
                dispatchTakePictureIntent();
                layout_detectar_user.setVisibility(View.VISIBLE);
                break;

            case R.id.detectar_user_btnContinuar:
                _invisibilidad_layouts();
                layout_dia_especial.setVisibility(View.VISIBLE);
                break;
            case R.id.dia_especial_btn:
                _invisibilidad_layouts();
                layout_menu_usuario.setVisibility(View.VISIBLE);
                break;

            case R.id.menuSTV_btnQR:
                break;

            case R.id.menu_user_btn_ruleta:
                _mostrar_ruleta();
                break;

            case R.id.ruleta_btngirar:
                _girar_ruleta();
                break;

            case R.id.ruleta_btnfin:
                _invisibilidad_layouts();
                premio.setRotation(premio.getRotation() + 45);
                layout_premio.setVisibility(View.VISIBLE);
                break;

            case R.id.premio_btnNext:
                premio.setRotation(premio.getRotation() - 45);
                _invisibilidad_layouts();
                layout_menu_usuario.setVisibility(View.VISIBLE);
                break;

            case R.id.menu_user_btn_twitter:
                red_social = true;
                _invisibilidad_layouts();
                break;

            //case R.id.boton_twiter_sig:
            //_invisibilidad_layouts();
            //layout_mostrar_foto.setVisibility(View.VISIBLE);
            //break;

            //case R.id.boton_redsocial_fin:
            //red_social = false;
            //_invisibilidad_layouts();
            //layout_menu_usuario.setVisibility(View.VISIBLE);
            //break;

            case R.id.menu_user_btn_salir:
                //_invisibilidad_layouts();
                //layout_menuSTV.setVisibility(View.VISIBLE);
                _mostrar_menu_STV();
                break;

            default:
                break;
        }

    }

    private void _girar_ruleta(){
        if(num_ruleta == 200) {
            num_ruleta = 0;
            return;
        }

        if( num_ruleta < 100 ) {
            for (int i = 0; i < num_ruleta; i++) {
                ruleta_amarillo.setRotation(ruleta_amarillo.getRotation() + 1);
                ruleta_azul.setRotation(ruleta_azul.getRotation() + 1);
                ruleta_rojo.setRotation(ruleta_rojo.getRotation() + 1);
                ruleta_verde.setRotation(ruleta_verde.getRotation() + 1);
            }
        }
        else {
            for (int i = num_ruleta; i > 0; i--) {
                ruleta_amarillo.setRotation(ruleta_amarillo.getRotation() + 1);
                ruleta_azul.setRotation(ruleta_azul.getRotation() + 1);
                ruleta_rojo.setRotation(ruleta_rojo.getRotation() + 1);
                ruleta_verde.setRotation(ruleta_verde.getRotation() + 1);
            }
        }

        num_ruleta++;

        mHandler.postDelayed(new Runnable() {
            public void run() {
                _girar_ruleta();
            }
        }, 10);
    }

    private void _mostrar_ruleta(){
        _invisibilidad_layouts();
        layout_ruleta.setVisibility(View.VISIBLE);
        ruleta_amarillo.setRotation(ruleta_amarillo.getRotation() + 0);
        ruleta_azul.setRotation(ruleta_amarillo.getRotation() + 90);
        ruleta_rojo.setRotation(ruleta_amarillo.getRotation() + 180);
        ruleta_verde.setRotation(ruleta_amarillo.getRotation() + 270);
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            if(red_social)
                ;
            else
                detectar_user_imagen.setImageBitmap(imageBitmap);
        }
    }

    private void _mostrar_menu_STV(){

        String uriPath = ""; //"android.resource://"+getPackageName()+ "/" + R.raw.video_coca_cola;

        for (Auspiciadores each_auspiciador: Auspiciadores.values()) {
            if( lista_smart_tv.getAuspiciador( usuario_Smart_tv ).equals(each_auspiciador.getDescription()) ){
                uriPath = "android.resource://"+getPackageName()+ "/" + each_auspiciador.getNameVideo();
                break;
            }
        }

        Uri uri = Uri.parse(uriPath);
        menuSTV_video.setVideoURI(uri);

        menuSTV_video.start();
        layout_menuSTV.setVisibility(View.VISIBLE);
    }
    private void _invisibilidad_layouts(){
        layout_loginSTV.setVisibility(View.INVISIBLE);
        layout_menuSTV.setVisibility(View.INVISIBLE);
        layout_detectar_user.setVisibility(View.INVISIBLE);
        layout_dia_especial.setVisibility(View.INVISIBLE);
        layout_menu_usuario.setVisibility(View.INVISIBLE);
        layout_ruleta.setVisibility(View.INVISIBLE);
        layout_premio.setVisibility(View.INVISIBLE);
    }
    private void _asignar_elementos(){
        //Asignar layouts
        layout_loginSTV = (ConstraintLayout) findViewById(R.id.Layout_loginSTV);
        layout_menuSTV = (ConstraintLayout) findViewById(R.id.Layout_menuSTV);
        layout_detectar_user = (ConstraintLayout) findViewById(R.id.Layout_detectar_user);
        layout_dia_especial = (ConstraintLayout) findViewById(R.id.Layout_dia_especial);
        layout_menu_usuario = (ConstraintLayout) findViewById(R.id.Layout_menu_usuario);
        layout_ruleta = (ConstraintLayout) findViewById(R.id.Layout_ruleta);
        layout_premio = (ConstraintLayout) findViewById(R.id.Layout_premio);

        //Asignar EditText
        loginSTV_user = (EditText) findViewById(R.id.loginSTV_user);
        loginSTV_pass = (EditText) findViewById(R.id.loginSTV_pass);

        //Asignar Btn
        loginSTV_btn_login = (Button) findViewById(R.id.loginSTV_btn_login);
        menuSTV_btnUsuario = (Button) findViewById(R.id.menuSTV_btnUsuario);
        menuSTV_btnQR = (Button) findViewById(R.id.menuSTV_btnQR);
        detectar_user_btnContinuar = (Button) findViewById(R.id.detectar_user_btnContinuar);
        dia_especial_btn = (Button) findViewById(R.id.dia_especial_btn);
        menu_user_btn_ruleta = (Button) findViewById(R.id.menu_user_btn_ruleta);
        menu_user_btn_twitter = (Button) findViewById(R.id.menu_user_btn_twitter);
        menu_user_btn_salir = (Button) findViewById(R.id.menu_user_btn_salir);
        ruleta_btngirar = (Button) findViewById(R.id.ruleta_btngirar);
        ruleta_btnfin = (Button) findViewById(R.id.ruleta_btnfin);
        premio_btnNext = (Button) findViewById(R.id.premio_btnNext);

        //Asignar Video
        menuSTV_video = (VideoView) findViewById(R.id.menuSTV_video);

        //Asignar ImageView
        detectar_user_imagen = (ImageView) findViewById(R.id.detectar_user_imagen);
        ruleta_amarillo = (ImageView) findViewById(R.id.ruleta_amarillo);
        ruleta_azul = (ImageView) findViewById(R.id.ruleta_azul);
        ruleta_rojo = (ImageView) findViewById(R.id.ruleta_rojo);
        ruleta_verde = (ImageView) findViewById(R.id.ruleta_verde);
        premio = (ImageView) findViewById(R.id.premio);

        //OnClickListener
        loginSTV_btn_login.setOnClickListener(this);
        menuSTV_btnUsuario.setOnClickListener(this);
        menuSTV_btnQR.setOnClickListener(this);
        detectar_user_btnContinuar.setOnClickListener(this);
        dia_especial_btn.setOnClickListener(this);
        menu_user_btn_ruleta.setOnClickListener(this);
        menu_user_btn_twitter.setOnClickListener(this);
        menu_user_btn_salir.setOnClickListener(this);
        ruleta_btngirar.setOnClickListener(this);
        ruleta_btnfin.setOnClickListener(this);
        premio_btnNext.setOnClickListener(this);
    }
}
