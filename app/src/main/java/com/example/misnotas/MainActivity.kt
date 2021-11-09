package com.example.misnotas


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.*
import com.example.misnotas.databinding.ActivityMainBinding
import java.io.*


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var notas = ArrayList<Nota>()
    lateinit var adaptador: AdaptadorNotas

     val responseLauncher =
        registerForActivityResult(StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                Toast.makeText(this,"Se ejecuto result code",Toast.LENGTH_SHORT).show()
                adaptador.notifyDataSetChanged()
                leerNotas()

            }else{

                Toast.makeText(this,"No se puede ejecutar",Toast.LENGTH_SHORT).show()

            }

        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        leerNotas()


        binding.fab.setOnClickListener {
            var intent = Intent(this, AgregarNotaActivity::class.java)
            responseLauncher.launch(intent)

        }



        adaptador = AdaptadorNotas(this, notas)
        binding.listview.adapter = adaptador


    }


    fun leerNotas() {
        notas.clear()
        var carpeta = File(ubicacion().absolutePath)

        if (carpeta.exists()) {
            var archivos = carpeta.listFiles()
            if (archivos != null) {
                for (archivo in archivos) {
                    leerArchivo(archivo)
                }
            }
        }
    }//Se cierra funsion leer notas

    fun leerArchivo(archivo: File) {
        val fis = FileInputStream(archivo)
        val di = DataInputStream(fis)
        val br = BufferedReader(InputStreamReader(di))
        var strLine: String? = br.readLine()
        var myData = ""

        //Lee los archivos almacenados en la memoria
        while (strLine != null) {
            myData = myData + strLine
            strLine = br.readLine()
        }
        br.close()
        di.close()
        fis.close()
        //Elimina el .txt
        var nombre = archivo.name.substring(0, archivo.name.length - 4)
        //Crea la nota y la agrega a la lista
        var nota = Nota(nombre, myData)
        notas.add(nota)
    }


    private fun ubicacion(): File {
        val folder = File(getExternalFilesDir(null), "notas")
        if (!folder.exists()) {
            folder.mkdir()
        }
        return folder
    }




}