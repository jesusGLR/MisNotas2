package com.example.misnotas


import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.misnotas.databinding.ActivityAgregarNotaBinding
import java.io.File
import java.lang.Exception
import java.io.FileOutputStream as FileOutputStream

class AgregarNotaActivity : AppCompatActivity() {
    lateinit var binding: ActivityAgregarNotaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarNotaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnGuardar.setOnClickListener{
            guardar_nota()
            setResult(RESULT_OK)
            finish()
        }
    }
    fun guardar_nota() {
        //Verifica que tenga los permisos
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
                //si no los tiene, los pide al usuario
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                235)

            // si tiene permiso lo guarda
        }else{
            guardar()
            setResult(RESULT_OK)

        } }


    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        when(requestCode){
            235->{
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    guardar()
                    setResult(RESULT_OK)

                }else{
                    //Si no acepto, coloca un mensaje
                    Toast.makeText(this, "Error: permisos denegados",Toast.LENGTH_SHORT).show()
                }

            }
        }
    }


    fun guardar() {
    var titulo = binding.etTitulo.text.toString()
        var cuerpo = binding.etContenido.text.toString()
        if(titulo == "" || cuerpo == ""){
            Toast.makeText(this, "Error: campos vacios", Toast.LENGTH_SHORT).show()
        }else{
            try {
                val archivo = File(ubicacion(), titulo + ".txt")
                val fos = FileOutputStream(archivo)
                fos.write(cuerpo.toByteArray())
                fos.close()
                Toast.makeText(this, "Se guardo el archivo en la carpeta publica", Toast.LENGTH_SHORT).show()

            }catch (e: Exception){
                Toast.makeText(this, "Error no se guardo el archivo", Toast.LENGTH_SHORT).show()

            }

        }
        finish()


    }

     fun ubicacion(): String? {
        val carpeta = File(getExternalFilesDir(null), "notas")
        if (!carpeta.exists()){
            carpeta.mkdir()
        }
        return carpeta.absolutePath
    }
}