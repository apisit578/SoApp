package com.example.soapp.feature.subscription.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.soapp.core.model.PaymentResult
import com.example.soapp.core.model.SubscriptionPlan

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionScreen(
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SubscriptionViewModel = viewModel(factory = SubscriptionViewModel.provideFactory())
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("SoApp Pro Sandbox", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                actions = {
                    IconButton(onClick = onClose) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "ปิด")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val state = uiState) {
                is SubscriptionUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is SubscriptionUiState.Content -> {
                    SubscriptionContent(
                        state = state,
                        onEvent = viewModel::onEvent,
                        onClose = onClose
                    )
                }
                is SubscriptionUiState.Error -> {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
private fun SubscriptionContent(
    state: SubscriptionUiState.Content,
    onEvent: (SubscriptionUiEvent) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Success Dialog
    if (state.purchaseSuccessMessage != null) {
        AlertDialog(
            onDismissRequest = {
                onEvent(SubscriptionUiEvent.DismissSuccessMessage)
                onClose()
            },
            title = { Text("แจ้งเตือนระบบ 🎉", fontWeight = FontWeight.Bold) },
            text = { Text(state.purchaseSuccessMessage) },
            confirmButton = {
                TextButton(
                    onClick = {
                        onEvent(SubscriptionUiEvent.DismissSuccessMessage)
                        onClose()
                    }
                ) {
                    Text("ตกลง")
                }
            }
        )
    }

    // Error Dialog
    if (state.errorMessage != null) {
        AlertDialog(
            onDismissRequest = { onEvent(SubscriptionUiEvent.DismissErrorMessage) },
            title = { Text("การชำระเงินไม่สำเร็จ 🔴", fontWeight = FontWeight.Bold) },
            text = { Text(state.errorMessage) },
            confirmButton = {
                TextButton(onClick = { onEvent(SubscriptionUiEvent.DismissErrorMessage) }) {
                    Text("ปิด")
                }
            }
        )
    }

    // Payment Sheet Sandbox Dialog
    if (state.showPaymentSheet) {
        MockPaymentSheetDialog(
            selectedOption = state.selectedPaymentOption,
            isPurchasing = state.isPurchasing,
            onSelectOption = { option -> onEvent(SubscriptionUiEvent.SelectPaymentOption(option)) },
            onConfirm = { onEvent(SubscriptionUiEvent.ConfirmPayment) },
            onDismiss = { onEvent(SubscriptionUiEvent.ClosePaymentSheet) }
        )
    }

    val selectedPlan = state.plans.find { it.id == state.selectedPlanId } ?: state.plans.firstOrNull()

    Column(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Banner
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFFFD700).copy(alpha = 0.2f),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Pro",
                            tint = Color(0xFFD4AF37),
                            modifier = Modifier
                                .padding(16.dp)
                                .size(48.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (state.isPro) "สมาชิก SoApp Pro (เปิดใช้งานอยู่)" else "ยกระดับสู่ SoApp Pro",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (state.isPro) "คุณได้รับสิทธิประโยชน์ Pro ทั้งหมดเรียบร้อยแล้ว ✨" else "ปลดล็อกฟีเจอร์พรีเมียมและสนับสนุนแอปของเรา",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }

            if (state.isPro) {
                // Active Subscription Info Card & Management
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9E6)),
                        border = BorderStroke(1.dp, Color(0xFFD4AF37))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "รายละเอียดสมาชิกปัจจุบัน",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFB8860B)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "• สถานะ: เปิดใช้งาน (Active)", style = MaterialTheme.typography.bodyMedium)
                            Text(text = "• วันหมดอายุ: 30 พฤศจิกายน 2026", style = MaterialTheme.typography.bodyMedium)
                            Text(text = "• บัญชีผู้ใช้: @somchai_dev", style = MaterialTheme.typography.bodyMedium)

                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedButton(
                                onClick = { onEvent(SubscriptionUiEvent.CancelActiveSubscription) },
                                enabled = !state.isCancelling,
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
                            ) {
                                if (state.isCancelling) {
                                    CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("กำลังยกเลิก...")
                                } else {
                                    Text("ยกเลิกการเป็นสมาชิก (Sandbox Cancel)")
                                }
                            }
                        }
                    }
                }
            } else {
                // Benefits Checklist
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            BenefitRow("ไม่มีโฆษณาคั่นในหน้า Feed")
                            BenefitRow("ตราสัญลักษณ์ Pro สีทองบนโปรไฟล์")
                            BenefitRow("สิทธิ์ใช้งานฟีเจอร์ใหม่ล่วงหน้า")
                            BenefitRow("ซัพพอร์ตระดับ VIP ตลอด 24 ชม.")
                        }
                    }
                }

                // Plans Title
                item {
                    Text(
                        text = "เลือกแพ็กเกจที่เหมาะกับคุณ",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Plan List Cards
                items(
                    items = state.plans,
                    key = { (id) -> id }
                ) { plan ->
                    val isSelected = plan.id == state.selectedPlanId
                    PlanCard(
                        plan = plan,
                        isSelected = isSelected,
                        onClick = { onEvent(SubscriptionUiEvent.SelectPlan(plan.id)) }
                    )
                }
            }
        }

        // Bottom CTA Action (when not Pro)
        if (!state.isPro) {
            Surface(
                shadowElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Button(
                        onClick = { onEvent(SubscriptionUiEvent.OpenPaymentSheet) },
                        enabled = selectedPlan != null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD4AF37),
                            contentColor = Color.Black
                        )
                    ) {
                        Text(
                            text = if (selectedPlan != null) "ทดสอบชำระเงิน ${selectedPlan.price}/${selectedPlan.billingPeriod}" else "ทดสอบชำระเงิน",
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "ระบบ Sandbox สำหรับทดสอบชำระเงิน (ไม่มีการหักเงินจริง)",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
private fun MockPaymentSheetDialog(
    selectedOption: PaymentResult,
    isPurchasing: Boolean,
    onSelectOption: (PaymentResult) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text("Google Play Billing (Sandbox)", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Text("เลือกรุ่นบัตรทดสอบที่ต้องการทดลอง", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                PaymentResult.entries.forEach { option ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelectOption(option) },
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(
                            width = if (selectedOption == option) 2.dp else 1.dp,
                            color = if (selectedOption == option) Color(0xFFD4AF37) else MaterialTheme.colorScheme.outlineVariant
                        ),
                        color = if (selectedOption == option) Color(0xFFFFF9E6) else MaterialTheme.colorScheme.surface
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedOption == option,
                                onClick = { onSelectOption(option) }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(text = option.title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                                Text(text = option.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                enabled = !isPurchasing,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37), contentColor = Color.Black)
            ) {
                if (isPurchasing) {
                    CircularProgressIndicator(modifier = Modifier.size(16.dp), color = Color.Black, strokeWidth = 2.dp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("กำลังดำเนินการ...")
                } else {
                    Text("ยืนยันชำระเงิน", fontWeight = FontWeight.Bold)
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !isPurchasing) {
                Text("ยกเลิก")
            }
        }
    )
}

@Composable
private fun BenefitRow(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun PlanCard(
    plan: SubscriptionPlan,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) Color(0xFFD4AF37) else MaterialTheme.colorScheme.outlineVariant

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        border = BorderStroke(if (isSelected) 2.dp else 1.dp, borderColor),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFFFF9E6) else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = onClick
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = plan.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    if (plan.isBestValue) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            color = Color(0xFFD4AF37),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "คุ้มค่าที่สุด",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Black,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                Text(
                    text = plan.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${plan.price}/${plan.billingPeriod}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color(0xFFB8860B) else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
