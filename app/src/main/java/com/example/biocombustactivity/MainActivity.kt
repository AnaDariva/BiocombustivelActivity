package com.example.biocombustactivity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var etPrecoGasolina: EditText
    private lateinit var etConsumoGasolina: EditText
    private lateinit var etPrecoEtanol: EditText
    private lateinit var etConsumoEtanol: EditText
    private lateinit var tvResultado: TextView
    private lateinit var btCalcular: Button
    private lateinit var btSair: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        etPrecoGasolina = findViewById(R.id.etPrecoGasolina)
        etConsumoGasolina = findViewById(R.id.etConsumoGasolina)
        etPrecoEtanol = findViewById(R.id.etPrecoEtanol)
        etConsumoEtanol = findViewById(R.id.etConsumoEtanol)
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
        val precoEtanolStr = etPrecoEtanol.text.toString()
        val consumoEtanolStr = etConsumoEtanol.text.toString()

        if (precoGasolinaStr.isEmpty()) {
            etPrecoGasolina.error = "Informe o preço da gasolina"
            return
        }
        if (consumoGasolinaStr.isEmpty()) {
            etConsumoGasolina.error = "Informe o consumo da gasolina"
            return
        }
        if (precoEtanolStr.isEmpty()) {
            etPrecoEtanol.error = "Informe o preço do etanol"
            return
        }
        if (consumoEtanolStr.isEmpty()) {
            etConsumoEtanol.error = "Informe o consumo do etanol"
            return
        }

        try {
            val precoGasolina = precoGasolinaStr.toDouble()
            val consumoGasolina = consumoGasolinaStr.toDouble()
            val precoEtanol = precoEtanolStr.toDouble()
            val consumoEtanol = consumoEtanolStr.toDouble()

            if (consumoGasolina <= 0 || consumoEtanol <= 0) {
                Toast.makeText(this, "Consumo deve ser maior que zero", Toast.LENGTH_LONG).show()
                return
            }

            val custoKmGasolina = precoGasolina / consumoGasolina
            val custoKmEtanol = precoEtanol / consumoEtanol

            val resultado = if (custoKmGasolina < custoKmEtanol) {
                "Gasolina é mais vantajosa"
            } else if (custoKmEtanol < custoKmGasolina) {
                "etanol é mais vantajoso"
            } else {
                "Ambos têm o mesmo custo por km"
            }

            tvResultado.text = String.format(
                "Custo/km Gasolina: R$ %.2f\nCusto/km etanol: R$ %.2f\n\n%s",
                custoKmGasolina,
                custoKmEtanol,
                resultado
            )

        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Insira valores numéricos válidos", Toast.LENGTH_LONG).show()
        }
    }
}
