package com.myfirebaseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = Firebase.database.reference

        val listener = object  : ValueEventListener {

            //ver los cambios en la bd al instante
            override fun onDataChange(snapshot: DataSnapshot) {
                //comprobar si existe la ruta
               if (snapshot.exists()) {
                    val data = snapshot.getValue(String::class.java)
                findViewById<TextView>(R.id.tvData).text = " Firebase remote: $data"
                } else {
                    findViewById<TextView>(R.id.tvData).text = "Ruta sin datos."
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Error al leer datos.", Toast.LENGTH_SHORT).show()
            }
        }

        //referencia final de la bd
        val dataRef = database.child("hola, firebase").child("data")

        ///listener para ver los cambios en tiempo real
        dataRef.addValueEventListener(listener)

        //evento del click
        findViewById<MaterialButton>(R.id.btnSend).setOnClickListener {
            //extraer el texto escrito para enviar a firebase
            val data = findViewById<TextInputEditText>(R.id.etData).text.toString()
            dataRef.setValue(data)
                .addOnSuccessListener {
                    Toast.makeText(this@MainActivity, "Enviado...", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this@MainActivity, "Error al enviar.", Toast.LENGTH_SHORT).show()
                }
                .addOnCompleteListener {
                    Toast.makeText(this@MainActivity, "Terminado.", Toast.LENGTH_SHORT).show()
                }
        }

        findViewById<MaterialButton>(R.id.btnSend).setOnLongClickListener {
            dataRef.removeValue()
            true
        }
    }
}