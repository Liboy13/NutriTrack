package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.example.ui.NutriTrackApp
import com.example.ui.NutriTrackViewModel
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    private val viewModel: NutriTrackViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Request notification permission on Android 13+ (API 33+)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            val permission = android.Manifest.permission.POST_NOTIFICATIONS
            if (checkSelfPermission(permission) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(permission), 101)
            }
        }

        setContent {
            MyApplicationTheme {
                NutriTrackApp(viewModel = viewModel)
            }
        }
    }
}

// Unused Greeting Composable preserved for test compatibilities
@Composable
fun Greeting(name: String, modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier) {
    androidx.compose.material3.Text(text = "Hello $name!", modifier = modifier)
}
