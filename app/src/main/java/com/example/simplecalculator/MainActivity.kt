package com.example.simplecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleCalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalculatorScreen()
                }
            }
        }
    }
}

@Composable
fun CalculatorScreen() {
    // State untuk menyimpan nilai input dan hasil
    var number1 by remember { mutableStateOf("") }
    var number2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("0") }


    fun calculate(operation: (Double, Double) -> Double) {
        val num1 = number1.toDoubleOrNull()
        val num2 = number2.toDoubleOrNull()

        if (num1 != null && num2 != null) {
            try {
                val calcResult = operation(num1, num2)

                // Tampilkan hasil integer jika tidak ada desimal
                result = if (calcResult == calcResult.toInt().toDouble()) {
                    calcResult.toInt().toString()
                } else {
                    calcResult.toString()
                }
            } catch (e: ArithmeticException) {
                result = "Error: Div by zero"
            } catch (e: Exception) {
                result = "Error"
            }
        } else {
            result = "Invalid Input"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = result,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(bottom = 5.dp, top = 20.dp),
            fontSize = 36.sp,
            textAlign = TextAlign.End,
            color = MaterialTheme.colorScheme.onSurface
        )

        TextField(
            value = number1,
            onValueChange = { number1 = it.filter { char -> char.isDigit() || char == '.' } },
            label = { Text("Number 1") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(fontSize = 20.sp),
        )

        TextField(
            value = number2,
            onValueChange = { number2 = it.filter { char -> char.isDigit() || char == '.' } },
            label = { Text("Number 2") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(fontSize = 20.sp),
        )

        // Jarak sebelum tombol
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Memanggil CalculatorButton tanpa parameter warna
            CalculatorButton(
                text = "Add",
                onClick = { calculate { a, b -> a + b } }
            )
            CalculatorButton(
                text = "Sub",
                onClick = { calculate { a, b -> a - b } }
            )
            CalculatorButton(
                text = "Mul",
                onClick = { calculate { a, b -> a * b } }
            )
            CalculatorButton(
                text = "Div",
                onClick = {
                    if (number2.toDoubleOrNull() == 0.0) {
                        result = "Error: Div by zero"
                    } else {
                        calculate { a, b -> a / b }
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        InfoDisplay()
    }
}

// Composable untuk tombol
@Composable
fun CalculatorButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .height(48.dp)
            .widthIn(min = 70.dp)
    ) {
        Text(text, fontSize = 18.sp)
    }
}

// Composable untuk label info
@Composable
fun InfoDisplay() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Nama: Moch. Avin",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Text(
            text = "NRP: 5025221061",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimpleCalculatorTheme {
        CalculatorScreen()
    }
}