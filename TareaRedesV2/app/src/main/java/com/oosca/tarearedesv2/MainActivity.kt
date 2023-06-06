package com.oosca.tarearedesv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oosca.tarearedesv2.ui.theme.TareaRedesV2Theme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.text.isDigitsOnly
import android.util.Log
import androidx.compose.ui.graphics.Color
import java.math.BigInteger
import java.time.format.TextStyle


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TareaRedesV2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NetworkTaskScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NetworkTaskScreen() {
    var ipBinary by remember { mutableStateOf("") }
    var ipMask by remember { mutableStateOf("") }
    var andResult by remember { mutableStateOf("") }
    var incrementedIP by remember { mutableStateOf("") }
    var negacionMascara by remember { mutableStateOf("") }
    var broadcastAddress by remember { mutableStateOf("") }
    var restarIP by remember { mutableStateOf("") }
    var sumOneToBroadcast by remember { mutableStateOf("") }
    var saltoRed by remember { mutableStateOf("") }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Tarea Redes",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp),
        )

        var ipText by remember { mutableStateOf("") }
        TextField(
            value = ipText,
            onValueChange = { ipText = it },
            modifier = Modifier.padding(bottom = 16.dp),
            label = { Text(text = "Ingresa una IP") }
        )

        var maskText by remember { mutableStateOf("") }
        TextField(
            value = maskText,
            onValueChange = { maskText = it },
            modifier = Modifier.padding(bottom = 16.dp),
            label = { Text(text = "Ingresa una máscara") }
        )

        TextWithPlaceholder("IP de Red",alignStart = true, andResult)
        TextWithPlaceholder("Primera IP asignable", alignStart = true,incrementedIP)
        TextWithPlaceholder("Última IP asignable",alignStart = true, restarIP)
        TextWithPlaceholder("IP de Broadcast", alignStart = true,broadcastAddress)
        TextWithPlaceholder("Siguiente ip de red",alignStart = true, sumOneToBroadcast)
        TextWithPlaceholder("Salto de Red",alignStart = true, saltoRed)

        Button(
            onClick = {
                ipBinary = convertToBinary(ipText)
                ipMask = convertToBinary(maskText)
                andResult = performAndOperation(ipBinary, ipMask)
                incrementedIP = incrementLastOctet(andResult)
                negacionMascara = negarMascara(ipMask)
                broadcastAddress = compararOctetos(ipText,negacionMascara)
                restarIP = restarUltimoOcteto(broadcastAddress)
                sumOneToBroadcast = sumarUnoAlBroadcast(broadcastAddress)
                saltoRed = restarOctetos(sumOneToBroadcast,andResult)


                Log.d("BinaryConversion", "IP (Binary): $ipBinary")
                Log.d("BinaryConversion", "Mask (Binary): $ipMask")
                Log.d("BinaryConversion", "AND Result: $andResult")
                Log.d("BinaryConversion", "Incremented IP: $incrementedIP")
                Log.d("BinaryConversion", "invertirMascara: $negacionMascara")
                Log.d("BinaryConversion", "compararOctetos: $broadcastAddress")
                Log.d("BinaryConversion", "Restar IP: $restarIP")
                Log.d("BinaryConversion", "sumar 1 broadcast: $sumOneToBroadcast")
                Log.d("BinaryConversion", "salto de red: $saltoRed")
            },
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Text(text = "Calcular")
        }
    }
}


@Composable
fun TextWithPlaceholder(
    placeholder: String,
    alignStart: Boolean = false,
    ip: String = "",

) {
    Column(
        modifier = Modifier.padding(vertical = 16.dp),
        horizontalAlignment = if (alignStart) Alignment.Start else Alignment.CenterHorizontally
    ) {
        if (ip.isNotEmpty()) {
            Text(text = placeholder, modifier = Modifier.padding(bottom = 8.dp))
            Text(text = ip)
        } else {
            Text(text = placeholder, modifier = Modifier.padding(bottom = 8.dp))
            Text(text = ip)
        }
    }
}

@Preview
@Composable
fun PreviewNetworkTaskScreen() {
    NetworkTaskScreen()
}


private fun convertToBinary(input: String): String {
    val inputBinary = input.split(".").joinToString(".") { it.toInt().toString(2).padStart(8, '0') }

    return inputBinary

}

private fun performAndOperation(ipBinary: String, maskBinary: String): String {
    val ipParts = ipBinary.split(".")
    val maskParts = maskBinary.split(".")

    val resultParts = ipParts.zip(maskParts) { ipPart, maskPart ->
        val ipOctet = ipPart.toInt(2).toBigInteger()
        val maskOctet = maskPart.toInt(2).toBigInteger()

        val andResult = ipOctet.and(maskOctet)
        andResult.toString()
    }

    return resultParts.joinToString(".")
}

private fun incrementLastOctet(ip: String): String {
    val ipParts = ip.split(".").toMutableList()
    val lastOctet = ipParts.last().toInt()
    val incrementedLastOctet = (lastOctet + 1) % 256
    ipParts[ipParts.size - 1] = incrementedLastOctet.toString()
    return ipParts.joinToString(".")
}

fun negarMascara(mascaraBinario: String): String {
    val mascaraBytes = mascaraBinario.split(".").map { it.toInt(2) }
    val mascaraNegadaBytes = mascaraBytes.map { it.inv() and 0xFF }
    return mascaraNegadaBytes.joinToString(".") { it.toString() }
}

fun compararOctetos(ip: String, mascara: String): String {
    val ipOctetos = ip.split(".").map { it.toInt() }
    val mascaraOctetos = mascara.split(".").map { it.toInt() }
    val resultadoOctetos = ipOctetos.zip(mascaraOctetos) { ipOcteto, mascaraOcteto ->
        if (mascaraOcteto != 0) mascaraOcteto.toString() else ipOcteto.toString()
    }
    return resultadoOctetos.joinToString(".")
}

private fun restarUltimoOcteto(ip: String): String {
    val ipParts = ip.split(".").toMutableList()
    val lastOctet = ipParts.last().toInt()
    val incrementedLastOctet = (lastOctet - 1) % 256
    ipParts[ipParts.size - 1] = incrementedLastOctet.toString()
    return ipParts.joinToString(".")
}

fun sumarUnoAlBroadcast(broadcast: String): String {
    val octetos = broadcast.split(".").map { it.toInt() }.toMutableList()
    var index = octetos.size - 1
    while (index >= 0) {
        if (octetos[index] == 255) {
            octetos[index] = 0
            index--
        } else {
            octetos[index]++
            break
        }
    }
    return octetos.joinToString(".")
}

fun restarOctetos(ip1: String, ip2: String): String {
    val octetos1 = ip1.split(".").map { it.toInt() }
    val octetos2 = ip2.split(".").map { it.toInt() }
    for (i in 0 until octetos1.size) {
        val diferencia = octetos1[i] - octetos2[i]
        if (diferencia != 0) {
            return diferencia.toString()
        }
    }
    return "0"
}