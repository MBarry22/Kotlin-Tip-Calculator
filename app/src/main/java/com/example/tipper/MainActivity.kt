package com.example.tipper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tipper.ui.theme.TipperTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import java.text.NumberFormat


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipperTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TipCalculatorScreen()
                }
            }
        }
    }
}

@Composable
fun TipCalculatorScreen() {
    var serviceCostAmountInput by remember { mutableStateOf("") }
    var tipPercent by remember { mutableStateOf(15.0) }
    val amount = serviceCostAmountInput.toDoubleOrNull() ?: 0.0
    val tip = calculateTip(amount, tipPercent)
    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = { serviceCostAmountInput = (1..1000).random().toString() },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Generate Bill Amount")
        }
        Text(
            text = stringResource(R.string.tip_calculator_heading),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(16.dp))
        EditServiceCostField(
            value = serviceCostAmountInput,
            onValueChange = { serviceCostAmountInput = it }
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.tip_amount, tip),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            TipPercentageButton(10.0, tipPercent, onTipPercentageChanged = { tipPercent = it })
            TipPercentageButton(15.0, tipPercent, onTipPercentageChanged = { tipPercent = it })
            TipPercentageButton(20.0, tipPercent, onTipPercentageChanged = { tipPercent = it })
            TipPercentageButton(25.0, tipPercent, onTipPercentageChanged = { tipPercent = it })
        }
    }
}

@Composable
fun EditServiceCostField(
    value: String,
    onValueChange: (String) -> Unit
) {

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.bill_amount)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

private fun calculateTip(
    amount: Double,
    tipPercent: Double = 15.0
): String {
    val tip = tipPercent / 100 * amount
    return NumberFormat.getCurrencyInstance().format(tip)
}

@Composable
fun TipPercentageButton(
    tipPercent: Double,
    currentTipPercent: Double,
    onTipPercentageChanged: (Double) -> Unit
) {
    Button(
        onClick = { onTipPercentageChanged(tipPercent) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (tipPercent == currentTipPercent) {
                MaterialTheme.colors.secondary
            } else {
                MaterialTheme.colors.primary
            }
        ),
        contentPadding = PaddingValues(16.dp)
    ) {
        Text(
            text = "${tipPercent.toInt()}%",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = if (tipPercent == currentTipPercent) {
                MaterialTheme.colors.onSecondary
            } else {
                MaterialTheme.colors.onPrimary
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipperTheme {
        TipCalculatorScreen()
    }
}