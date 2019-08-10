package com.example.threadteste

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.os.HandlerCompat.postDelayed
import android.widget.Toast
import androidx.core.app.NotificationCompat


class MainActivity : AppCompatActivity() {

    private val handler = Handler()
    //Utilizar o Runnable sempre que quiser rodar um processo em uma thread
    private var runnableCodigo: Runnable? = null

    var CHANNEL_ID_ANDROID = "com.luizcarlos.ANDROID"
    var CHANNEL_NAME = "ANDROID"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //instancia a interface Runnable com o método run, que será executado pela thread
        runnableCodigo = Runnable {
            //aqui irá o bloco de códigos que fará a execução de tempos em tempos
            NotificacaoAlerta()

            //executa o objeto runnableCodigo a cada 10 segundo, configure aqui o tempo
            handler.postDelayed(runnableCodigo, 20000)
        }

        /*
            faz a primeira execução do código, uma vez iniciado, dentro do código será
            mantido sua execução, criando um processo pós execução.
        */
        handler.post(runnableCodigo)
    }

    override fun onStop() {
        super.onStop()
        /*Remove a execução caso o usuário não esteja utilizando a aplicação
        é importante parar a execução caso não precise mais, para não deixar rodando
        quando desnecessário
        */
        handler.removeCallbacks(runnableCodigo)
    }

    private fun NotificacaoAlerta(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val imp = NotificationManager.IMPORTANCE_HIGH
            val mNotificationChannel = NotificationChannel(CHANNEL_ID_ANDROID,CHANNEL_NAME,imp)
            val notificationBuilder: Notification.Builder = Notification.Builder(this@MainActivity, CHANNEL_ID_ANDROID)
                .setSmallIcon(R.drawable.notification_icon_background)
                .setContentTitle("Titulo")
                .setContentText("Conteudo!!.....")

            val notificationManager: NotificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mNotificationChannel)
            notificationManager.notify(0,notificationBuilder.build())
        }else{

            val notificationBuilder2: NotificationCompat.Builder = NotificationCompat.Builder(this@MainActivity, CHANNEL_ID_ANDROID)
                .setSmallIcon(R.drawable.notification_icon_background)
                .setContentTitle("Titulo...")
                .setContentText("Conteudo!!....")

            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0,notificationBuilder2.build())
        }
    }
}
