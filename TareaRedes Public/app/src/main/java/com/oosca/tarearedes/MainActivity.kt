package com.oosca.tarearedes

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.oosca.tarearedes.ui.theme.TareaRedesTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
private lateinit var nsdManager: NsdManager

class MainActivity : ComponentActivity() {
    private val PERMISSION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.INTERNET
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.INTERNET),
                PERMISSION_REQUEST_CODE
            )
        } else {
            createUI()
        }
    }

    private fun createUI() {
        setContent {
            TareaRedesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyScreenContent()
                }
            }
        }
    }

    @Composable
    fun MyScreenContent() {
        val ipActual = remember { mutableStateOf("") }
        val numeroDispositivos = remember { mutableStateOf(0) }
        val ipRed = remember { mutableStateOf("") }
        val ipBroadcast = remember { mutableStateOf("") }

        LaunchedEffect(Unit) {
            ipActual.value = obtenerIPActual()
            numeroDispositivos.value = obtenerNumeroDispositivosConectados()
            ipRed.value = mostrarIPDeRed()
            ipBroadcast.value = mostrarIPBroadcast()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Tarea Redes",
                modifier = Modifier.padding(bottom = 16.dp)
            )
            TextWithPlaceholder("IP conectada", alignStart = true, ip = ipActual.value)
            TextWithPlaceholder(
                "Número de dispositivos conectados",
                alignStart = true,
                numero = numeroDispositivos.value
            )
            TextWithPlaceholder("IP de red", alignStart = true, ip = ipRed.value)
            TextWithPlaceholder("IP de broadcast", alignStart = true, ip = ipBroadcast.value)
            Spacer(modifier = Modifier.weight(1f))
        }
    }

    @Composable
    fun TextWithPlaceholder(
        placeholder: String,
        alignStart: Boolean = false,
        ip: String = "",
        numero: Int = 0
    ) {
        Column(
            modifier = Modifier.padding(vertical = 24.dp),
            horizontalAlignment = if (alignStart) Alignment.Start else Alignment.CenterHorizontally
        ) {
            Text(text = placeholder)
            if (ip.isNotEmpty()) {
                Text(text = ip)
            } else if (numero > 0) {
                Text(text = numero.toString())
            } else {
                Text(text = "Información a mostrar")
            }
        }
    }

    private fun obtenerIPActual(): String {
        try {
            val interfaces = NetworkInterface.getNetworkInterfaces()
            while (interfaces.hasMoreElements()) {
                val networkInterface = interfaces.nextElement()
                val addresses = networkInterface.inetAddresses
                while (addresses.hasMoreElements()) {
                    val address = addresses.nextElement()
                    if (!address.isLoopbackAddress && address.hostAddress.indexOf(':') < 0) {
                        return address.hostAddress
                    }
                }
            }
        } catch (e: SocketException) {
            e.printStackTrace()
        }
        return ""
    }

    private fun mostrarIPDeRed(): String {
        val ipActual = obtenerIPActual()
        val mascaraRed = obtenerMascaraRed(ipActual)

        if (ipActual.isNotEmpty() && mascaraRed.isNotEmpty()) {
            val ipRed = aplicarMascaraRed(ipActual, mascaraRed)
            return ipRed
        }

        return "No disponible"
    }

    private fun obtenerMascaraRed(ip: String): String {
        try {
            val interfaces = NetworkInterface.getNetworkInterfaces()
            while (interfaces.hasMoreElements()) {
                val networkInterface = interfaces.nextElement()
                val addresses = networkInterface.interfaceAddresses
                for (address in addresses) {
                    if (address.address.hostAddress == ip) {
                        return getMascaraRed(address.networkPrefixLength)
                    }
                }
            }
        } catch (e: SocketException) {
            e.printStackTrace()
        }
        return ""
    }

    private fun getMascaraRed(prefixLength: Short): String {
        val mask = ByteArray(4)
        var prefix = prefixLength.toInt()
        for (i in 0 until 4) {
            prefix = prefix.coerceIn(0, 32)
            if (prefix >= 8) {
                mask[i] = 0xff.toByte()
                prefix -= 8
            } else {
                mask[i] = (0xff.toByte() - (1 shl 8 - prefix).toByte() + 1).toByte()
                prefix = 0
            }
        }
        return InetAddress.getByAddress(mask).hostAddress
    }


    private fun aplicarMascaraRed(ip: String, mascara: String): String {
        val ipBytes = InetAddress.getByName(ip).address
        val mascaraBytes = InetAddress.getByName(mascara).address
        val redBytes = ByteArray(4)
        for (i in 0 until 4) {
            redBytes[i] = (ipBytes[i].toInt() and mascaraBytes[i].toInt()).toByte()
        }
        return InetAddress.getByAddress(redBytes).hostAddress
    }
    private fun mostrarIPBroadcast(): String {
        val ipRed = mostrarIPDeRed()

        if (ipRed.isNotEmpty()) {
            val octetos = ipRed.split(".").toMutableList()
            if (octetos.size == 4) {
                octetos[3] = "255"
                return octetos.joinToString(".")
            }
        }

        return "No disponible"
    }


    private fun obtenerNumeroDispositivosConectados(): Int {
        try {
            val process = Runtime.getRuntime().exec("arp-scan --localnet")
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            var count = 0

            var line: String? = reader.readLine()
            while (line != null) {
                // Verificar si la línea contiene una dirección IP válida
                if (line.matches(Regex("(\\d{1,3}\\.){3}\\d{1,3}"))) {
                    count++
                }

                line = reader.readLine()
            }

            return count
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return 0
    }

}
