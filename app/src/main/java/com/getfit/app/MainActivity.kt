
package com.getfit.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.isSystemInDarkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GetFitApp()
        }
    }
}

data class NavItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun GetFitApp() {
    val dark = isSystemInDarkTheme()

    val colors = if (dark) {
        darkColorScheme(
            primary = androidx.compose.ui.graphics.Color(0xFF69E6A2),
            secondary = androidx.compose.ui.graphics.Color(0xFFB5DCC5)
        )
    } else {
        lightColorScheme(
            primary = androidx.compose.ui.graphics.Color(0xFF167D50),
            secondary = androidx.compose.ui.graphics.Color(0xFF426B55)
        )
    }

    MaterialTheme(colorScheme = colors) {
        var currentRoute by remember {
            mutableStateOf("Home")
        }

        val items = listOf(
            NavItem("Home", Icons.Filled.Home, "Home"),
            NavItem("Gym", Icons.Filled.FitnessCenter, "Gym"),
            NavItem("Graph", Icons.Filled.ShowChart, "Graph"),
            NavItem("Tasks", Icons.Filled.CheckCircle, "Tasks"),
            NavItem("Profile", Icons.Filled.Person, "Profile")
        )

        Scaffold(
            bottomBar = {
                NavigationBar {
                    items.forEach { item ->
                        NavigationBarItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                currentRoute = item.route
                            },
                            icon = {
                                Icon(
                                    item.icon,
                                    contentDescription = item.title
                                )
                            },
                            label = {
                                Text(item.title)
                            }
                        )
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        currentRoute = "AI"
                    }
                ) {
                    Icon(
                        Icons.Filled.AutoAwesome,
                        contentDescription = "GETFIT AI"
                    )
                }
            }
        ) { padding ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                when (currentRoute) {
                    "Home" -> HomeScreen()
                    "Gym" -> BasicScreen("Gym", "Your workout library is coming.")
                    "Graph" -> BasicScreen("Progress", "Your fitness analytics are coming.")
                    "Tasks" -> BasicScreen("Reminders", "Your daily reminders are coming.")
                    "Profile" -> BasicScreen("Profile", "Your fitness profile is coming.")
                    "AI" -> BasicScreen("GETFIT AI", "Your personal AI coach is coming.")
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Text(
            text = "GETFIT",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                "Welcome back.",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                "What's the plan today?",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard("0", "Day streak", Modifier.weight(1f))
            StatCard("0 XP", "Total points", Modifier.weight(1f))
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    "Today's progress",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                LinearProgressIndicator(
                    progress = { 0f },
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    "0 of 0 tasks completed",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Row(
                modifier = Modifier.padding(18.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Filled.AutoAwesome,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )

                Column {
                    Text(
                        "GETFIT AI",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Your personal fitness coach is coming.",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        Text(
            "Something New",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            "Your daily fitness inspiration will appear here.",
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun StatCard(
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                value,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun BasicScreen(title: String, description: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            description,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
