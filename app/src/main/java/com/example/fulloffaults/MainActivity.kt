package com.example.fulloffaults

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import backtraceio.library.BacktraceClient
import backtraceio.library.BacktraceCredentials
import backtraceio.library.BacktraceDatabase
import backtraceio.library.enums.database.RetryBehavior
import backtraceio.library.enums.database.RetryOrder
import backtraceio.library.models.database.BacktraceDatabaseSettings
import com.example.faultgenlib.NativeLib
import com.example.fulloffaults.ui.theme.FullOfFaultsAppTheme
import java.io.File

class MainActivity : ComponentActivity() {
    private val faultGenLib by lazy { NativeLib() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeBacktraceClient()

        enableEdgeToEdge()
        setContent {
            FullOfFaultsAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding),
                        faultGenLib = faultGenLib
                    )
                }
            }
        }
    }

    private fun initializeBacktraceClient() {
        val backtraceCredentials = BacktraceCredentials(BuildConfig.BACKTRACE_SUBMISSION_URL)

        val dbPath = File(filesDir, "backtrace").absolutePath
        val dbSettings = BacktraceDatabaseSettings(dbPath).apply {
            setAutoSendMode(true)
        }
        dbSettings.maxRecordCount = 100
        dbSettings.maxDatabaseSize = 100
        dbSettings.retryBehavior = RetryBehavior.ByInterval
        dbSettings.isAutoSendMode = true
        dbSettings.retryOrder = RetryOrder.Queue

        val database = BacktraceDatabase(this, dbSettings)
        val backtraceClient = BacktraceClient(this, backtraceCredentials, database)

        backtraceClient.database.setupNativeIntegration(backtraceClient, backtraceCredentials)
    }
}

@Composable
fun Greeting(
    modifier: Modifier = Modifier,
    faultGenLib: NativeLib? = null,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 56.dp, start = 16.dp, end = 16.dp, bottom = 72.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = { faultGenLib?.genInvalidWrite() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Trigger SIGSEGV with null pointer")
        }
        Button(
            onClick = { faultGenLib?.genStackOverflow() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Trigger SIGSEGV stack overflow")
        }
        Button(
            onClick = { faultGenLib?.genSigAbort() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Trigger SIGABRT")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FullOfFaultsAppTheme {
        Greeting(
        )
    }
}
