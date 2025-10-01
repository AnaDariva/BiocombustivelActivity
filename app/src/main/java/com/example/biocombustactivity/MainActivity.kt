package com.example.biocombustactivity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var etPrecoGasolina: EditText
    private lateinit var etConsumoGasolina: EditText
    private lateinit var etPrecoAlcool: EditText
    private lateinit var etConsumoAlcool: EditText
    private lateinit var tvResultado: TextView
    private lateinit var btCalcular: Button
    private lateinit var btSair: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        etPrecoGasolina = findViewById(R.id.etPrecoGasolina)
        etConsumoGasolina = findViewById(R.id.etConsumoGasolina)
        etPrecoAlcool = findViewById(R.id.etPrecoAlcool)
        etConsumoAlcool = findViewById(R.id.etConsumoAlcool)
        tvResultado = findViewById(R.id.tvResultado)
        btCalcular = findViewById(R.id.btCalcular)
        btSair = findViewById(R.id.btSair)

        btCalcular.setOnClickListener { calcularMelhorOpcao() }
        btSair.setOnClickListener { finish() }
        btCalcular.setOnLongClickListener {
            Toast.makeText(this, "Calcula o combustível mais vantajoso", Toast.LENGTH_SHORT).show()
            true
        }
        btSair.setOnLongClickListener {
            Toast.makeText(this, "Fecha o aplicativo", Toast.LENGTH_SHORT).show()
            true
        }
    }

    private fun calcularMelhorOpcao() {
        val precoGasolinaStr = etPrecoGasolina.text.toString()
        val consumoGasolinaStr = etConsumoGasolina.text.toString()
        val precoAlcoolStr = etPrecoAlcool.text.toString()
        val consumoAlcoolStr = etConsumoAlcool.text.toString()
        val idioma = Locale.getDefault().language

        if (precoGasolinaStr.isEmpty()) {
            etPrecoGasolina.error = if (idioma == "pt") "Informe o preço da gasolina (por litro)" else "Enter the gasoline price (per gallon)"
            return
        }
        if (consumoGasolinaStr.isEmpty()) {
            etConsumoGasolina.error = if (idioma == "pt") "Informe o consumo da gasolina (km/l)" else "Enter the gasoline consumption (mpg)"
            return
        }
        if (precoAlcoolStr.isEmpty()) {
            etPrecoAlcool.error = if (idioma == "pt") "Informe o preço do álcool (por litro)" else "Enter the alcohol price (per gallon)"
            return
        }
        if (consumoAlcoolStr.isEmpty()) {
            etConsumoAlcool.error = if (idioma == "pt") "Informe o consumo do álcool (km/l)" else "Enter the alcohol consumption (mpg)"
            return
        }

        try {
            val precoGasolina = precoGasolinaStr.toDouble()
            val consumoGasolina = consumoGasolinaStr.toDouble()
            val precoAlcool = precoAlcoolStr.toDouble()
            val consumoAlcool = consumoAlcoolStr.toDouble()

            if (consumoGasolina <= 0 || consumoAlcool <= 0) {
                Toast.makeText(this, if (idioma == "pt") "Consumo deve ser maior que zero" else "Consumption must be greater than zero", Toast.LENGTH_LONG).show()
                return
            }

            if (idioma == "pt") {
                val custoKmGasolina = precoGasolina / consumoGasolina
                val custoKmAlcool = precoAlcool / consumoAlcool
                val resultado = when {
                    custoKmGasolina < custoKmAlcool -> "Gasolina é mais vantajosa"
                    custoKmAlcool < custoKmGasolina -> "Álcool é mais vantajoso"
                    else -> "Ambos têm o mesmo custo por km"
                }
                tvResultado.text = String.format(
                    "Custo/km Gasolina: R$ %.2f\nCusto/km Álcool: R$ %.2f\n\n%s",
                    custoKmGasolina,
                    custoKmAlcool,
                    resultado
                )
            } else {
                val custoMilhaGasolina = precoGasolina / consumoGasolina
                val custoMilhaAlcool = precoAlcool / consumoAlcool
                val resultado = when {
                    custoMilhaGasolina < custoMilhaAlcool -> "Gasoline is more cost-effective"
                    custoMilhaAlcool < custoMilhaGasolina -> "Alcohol is more cost-effective"
                    else -> "Both have the same cost per mile"
                }
                tvResultado.text = String.format(
                    "Cost/mile Gasoline: $%.2f\nCost/mile Alcohol: $%.2f\n\n%s",
                    custoMilhaGasolina,
                    custoMilhaAlcool,
                    resultado
                )
            }
        } catch (e: NumberFormatException) {
            Toast.makeText(this, if (idioma == "pt") "Insira valores numéricos válidos" else "Enter valid numeric values", Toast.LENGTH_LONG).show()
        }
    }
}
