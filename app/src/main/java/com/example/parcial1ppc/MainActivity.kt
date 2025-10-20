package com.example.parcial1ppc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.parcial1ppc.ui.nav.AppRoot
import com.example.parcial1ppc.ui.theme.Parcial1PPCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Parcial1PPCTheme {
                // Punto de entrada de toda la app
                AppRoot()
            }
        }
    }
}
