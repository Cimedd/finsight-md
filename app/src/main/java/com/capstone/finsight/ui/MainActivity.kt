package com.capstone.finsight.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.capstone.finsight.R
import com.capstone.finsight.data.SettingVMF
import com.capstone.finsight.data.SettingViewModel
import com.capstone.finsight.databinding.ActivityMainBinding
import com.capstone.finsight.dataclass.NewsItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.pubsub.v1.AckReplyConsumer
import com.google.cloud.pubsub.v1.MessageReceiver
import com.google.cloud.pubsub.v1.Subscriber
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.google.pubsub.v1.ProjectSubscriptionName
import com.google.pubsub.v1.PubsubMessage
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val settingVM by viewModels<SettingViewModel>{
        SettingVMF.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
        val navView: BottomNavigationView = binding.bottomNav
        val navController = findNavController(R.id.navHost)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.itemHome, R.id.itemFeed, R.id.itemInsight
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.appbar)))
        supportActionBar?.elevation = 0f

        PubSub()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHost)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val navController = findNavController(R.id.navHost)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            menu?.clear()
            when (destination.id) {
                R.id.itemFeed -> {
                    menuInflater.inflate(R.menu.feed_menu, menu)
                }
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.navHost)
        return when (item.itemId) {
            R.id.itemChatLog -> {
                navController.navigate(R.id.action_itemFeed_to_chatListFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun PubSub(){
        val projectId = "bangkit-capstone-441617"
        val subId = "finsight-news-sub"
        val rawResourceId = R.raw.credentials

        lifecycleScope.launch(Dispatchers.IO){
            try {
                val inputStream: InputStream = resources.openRawResource(rawResourceId)
                val credentials = GoogleCredentials.fromStream(inputStream)

                val subscriptionName = ProjectSubscriptionName.of(projectId, subId)

                Log.d("LOGS", "Listening for messages on subscription: $subscriptionName")
                val receiver = MessageReceiver { message: PubsubMessage, consumer: AckReplyConsumer ->
                    val jsonMessage = message.data.toStringUtf8()
                    val newsItemsType = object : TypeToken<List<NewsItem>>() {}.type
                    val newsItemsList: List<NewsItem> = Gson().fromJson(jsonMessage, newsItemsType)
                    Log.d("MESSAGEEEEE", "Received message: $jsonMessage")
                    lifecycleScope.launch{
                        if(settingVM.getNotif()){
                            sendNotification(newsItemsList[0].title ?: "",newsItemsList[0].content ?: "")
                        }
                    }
                    consumer.ack()
                }

                val subscriber = Subscriber
                    .newBuilder(subscriptionName, receiver)
                    .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                    .build()
                subscriber.startAsync().awaitRunning()
                Log.d("LOGS", "Running")

                subscriber.awaitTerminated()
            } catch (e: Exception) {
                Log.e("LOGS", "Error receiving Pub/Sub messages", e)
            }
        }
    }

    private fun sendNotification(title: String, message: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSubText(getString(R.string.notification_subtext))
        val notification = builder.build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            builder.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "channel_01"
        private const val CHANNEL_NAME = "Finsight channel"
    }
}

