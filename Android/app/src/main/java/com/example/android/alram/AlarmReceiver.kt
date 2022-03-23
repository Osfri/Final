package com.example.android.alram

import android.R
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.android.MainActivity


class AlarmReceiver : BroadcastReceiver() {

    var manager: NotificationManager? = null
    var builder: NotificationCompat.Builder? = null

    //오레오 이상은 반드시 채널을 설정해줘야 Notification이 작동함
    private val CHANNEL_ID = "channel1"
    private val CHANNEL_NAME = "Channel1"

    override fun onReceive(context: Context, intent: Intent) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        builder = null
        manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager!!.createNotificationChannel(
                NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            )
            NotificationCompat.Builder(context, CHANNEL_ID)
        } else {
            NotificationCompat.Builder(context)
        }

        //알림창 클릭 시 activity 화면 부름
        val intent2 = Intent(context, AlarmActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 101, intent2, PendingIntent.FLAG_UPDATE_CURRENT)


        //알림창 제목
        builder!!.setContentTitle("정해진 시간에 다 되었습니다")
        //알림창 아이콘
        builder!!.setSmallIcon(com.example.android.R.drawable.ic_launcher_background)
        //알림창 터치시 자동 삭제
        builder!!.setAutoCancel(true)
        builder!!.setContentIntent(pendingIntent)
        val notification: Notification = builder!!.build()
        manager!!.notify(1, notification)
    }
}

/*

private fun createNotificationChannel(context: Context) {
    // context : 실행하고 있는 앱의 상태나 맥락을 담고 있음
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            "onoff알람",
            NotificationManager.IMPORTANCE_HIGH
        )

        NotificationManagerCompat.from(context)
            .createNotificationChannel(notificationChannel)
    }
}

private fun notifyNotification(context: Context) {
    with(NotificationManagerCompat.from(context)) {
        val build = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("알람")
            .setContentText("설정하신 시간이 되었습니다")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.ic_launcher_foreground)

        notify(NOTIFICATION_ID, build.build())
    }
}
*/
