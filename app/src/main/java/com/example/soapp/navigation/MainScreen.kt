package com.example.soapp.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.soapp.core.network.NetworkMonitor
import com.example.soapp.feature.bookmark.presentation.BookmarkScreen
import com.example.soapp.feature.chat.presentation.ChatScreen
import com.example.soapp.feature.feed.presentation.FeedScreen
import com.example.soapp.feature.notification.presentation.NotificationScreen
import com.example.soapp.feature.profile.presentation.ProfileScreen
import com.example.soapp.feature.search.presentation.SearchScreen
import com.example.soapp.feature.settings.presentation.SettingsScreen
import com.example.soapp.feature.subscription.presentation.SubscriptionScreen

enum class BottomTab(
    val title: String,
    val icon: ImageVector
) {
    HOME("หน้าแรก", Icons.Default.Home),
    SEARCH("ค้นหา", Icons.Default.Search),
    CHAT("ข้อความ", Icons.AutoMirrored.Filled.Send),
    BOOKMARKS("บันทึก", Icons.Default.Star),
    NOTIFICATIONS("แจ้งเตือน", Icons.Default.Info),
    PROFILE("โปรไฟล์", Icons.Default.Person),
    SETTINGS("ตั้งค่า", Icons.Default.Settings)
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    var selectedTab by rememberSaveable { mutableStateOf(BottomTab.HOME) }
    var showSubscriptionScreen by rememberSaveable { mutableStateOf(false) }
    val isConnected by NetworkMonitor.isConnected.collectAsStateWithLifecycle()

    if (showSubscriptionScreen) {
        SubscriptionScreen(
            onClose = { showSubscriptionScreen = false }
        )
    } else {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            bottomBar = {
                NavigationBar {
                    BottomTab.entries.forEach { tab ->
                        NavigationBarItem(
                            selected = selectedTab == tab,
                            onClick = { selectedTab = tab },
                            icon = {
                                Icon(
                                    imageVector = tab.icon,
                                    contentDescription = tab.title
                                )
                            },
                            label = { Text(tab.title) }
                        )
                    }
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // Offline Status Banner
                AnimatedVisibility(visible = !isConnected) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Red)
                            .padding(vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "⚠️ กำลังใช้งานในโหมดออฟไลน์ (No Internet)",
                            color = Color.White,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Box(modifier = Modifier.weight(1f)) {
                    when (selectedTab) {
                        BottomTab.HOME -> FeedScreen()
                        BottomTab.SEARCH -> SearchScreen()
                        BottomTab.CHAT -> ChatScreen()
                        BottomTab.BOOKMARKS -> BookmarkScreen()
                        BottomTab.NOTIFICATIONS -> NotificationScreen()
                        BottomTab.PROFILE -> ProfileScreen(
                            onOpenSubscription = { showSubscriptionScreen = true }
                        )
                        BottomTab.SETTINGS -> SettingsScreen()
                    }
                }
            }
        }
    }
}
