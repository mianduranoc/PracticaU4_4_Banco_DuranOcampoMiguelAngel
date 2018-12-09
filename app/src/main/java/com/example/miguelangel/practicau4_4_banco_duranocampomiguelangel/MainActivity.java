package com.example.miguelangel.practicau4_4_banco_duranocampomiguelangel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    ImageView v1,v2,v3;
    TextView t1,t2,t3;
    ListView l1,l2,l3;
    ColaConCorrimiento c1,c2,c3;
    Ventanilla ventanilla1,ventanilla2,ventanilla3;
    //Personas generador;
    int total1,total2,total3;
    boolean disponible1,disponible2,disponible3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        v1=findViewById(R.id.v1);
        v2=findViewById(R.id.v2);
        v3=findViewById(R.id.v3);
        t1=findViewById(R.id.v1t);
        t2=findViewById(R.id.v2t);
        t3=findViewById(R.id.v3t);
        l1=findViewById(R.id.l1);
        l2=findViewById(R.id.l2);
        l3=findViewById(R.id.l3);
        c1=new ColaConCorrimiento(5);
        c2=new ColaConCorrimiento(5);
        c3=new ColaConCorrimiento(5);
        total1=total2=total3=0;
        disponible1=disponible2=disponible3=true;
        ventanilla1=new Ventanilla(v1,t1,l1,c1,total1,disponible1);
        ventanilla2=new Ventanilla(v2,t2,l2,c2,total2,disponible2);
        ventanilla3=new Ventanilla(v3,t3,l3,c3,total3,disponible3);
        //generador=new Personas(ventanilla1,ventanilla2,ventanilla3);
        //ventanilla1.encolar(((int)(Math.random()*7))+1);
        //ventanilla2.encolar(((int)(Math.random()*7))+1);
        //ventanilla3.encolar(((int)(Math.random()*7))+1);
        ventanilla1.start();
        ventanilla2.start();
        ventanilla3.start();
        //generador.start();
    }

    class Ventanilla extends Thread{
        ImageView imagen;
        TextView estado;
        ListView lista;
        ColaConCorrimiento cola;
        int transacciones;
        boolean disponible;
        public Ventanilla(ImageView imagen, TextView estado,ListView lista, ColaConCorrimiento cola, int transacciones,boolean disponible ){
            this.imagen=imagen;
            this.estado=estado;
            this.lista=lista;
            this.cola=cola;
            this.disponible=disponible;
            this.transacciones=transacciones;
        }
        public void run(){
            //this.encolar(((int)(Math.random()*7))+1);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    cargarLista();
                }
            });
            generador();
            while(true) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cargarLista();
                    }
                });
                if (transacciones >= 20) {
                    disponible = false;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imagen.setImageResource(R.drawable.esperar);
                            estado.setText("Espera");
                        }
                    });
                    try {
                        Thread.sleep(20000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    transacciones = 0;
                    disponible = true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imagen.setImageResource(R.drawable.ventanilla);
                            estado.setText("0/20");
                        }
                    });
                }
                try {
                    while ((int) (cola.actual()) > 0 && !cola.estaVacia()) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        cola.actualizarActual((int) (cola.actual()) - 1);
                        transacciones++;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cargarLista();
                                estado.setText(transacciones + "/20");
                            }
                        });
                    }
                    cola.desencolar();
                }catch(IndexOutOfBoundsException e){
                    this.encolar((int)(Math.random()*7)+1);
                }
            }

        }
        public void cargarLista(){
            if (!cola.estaVacia()){
                lista.setAdapter(new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,cola.mostrado()));
            }
            else{
                lista.setAdapter(new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1));
            }
        }
        public void encolar(Object dato){
            cola.encolar(dato);
        }
        public boolean getEstado(){
            return disponible;
        }

        public void generador(){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        int generado = (int) (Math.random() * 7) + 1;
                        if (!cola.estaLlena()&& getEstado()) {
                            cola.encolar(generado);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            }).start();
        }
    }
}
