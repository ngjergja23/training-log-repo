package org.unizd.rma.traininglog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import org.unizd.rma.traininglog.presentation.list.TreningListScreen
import org.unizd.rma.traininglog.presentation.navigation.TrainingLogNavigation
import org.unizd.rma.traininglog.ui.theme.TrainingLogTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrainingLogTheme {
                Surface {
                    TrainingLogNavigation()
                }
            }
        }
    }
}
