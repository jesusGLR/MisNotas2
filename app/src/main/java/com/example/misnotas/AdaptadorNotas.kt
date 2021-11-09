package com.example.misnotas

import android.content.Context
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.io.File

class AdaptadorNotas: BaseAdapter {
var context: Context
var notas = ArrayList<Nota>()


    constructor(context: Context, notas: ArrayList<Nota>){
        this.context = context
        this.notas= notas
    }


    override fun getCount(): Int {
        return notas.size
    }

    override fun getItem(p0: Int): Any {
        return notas[p0]
    }

    override fun getItemId(p0: Int): Long {
    return p0.toLong()
    }
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var inflator = LayoutInflater.from(context)
        var vista = inflator.inflate(R.layout.nota_layout, null)
        var nota = notas[p0]

        var tv_titulo_det = vista.findViewById(R.id.tv_titulo_det) as TextView
        var tv_contenido_det = vista.findViewById(R.id.tv_contenido_det) as TextView
        var btn_borrar = vista.findViewById(R.id.btn_borrar) as ImageView

        tv_contenido_det.setText(nota.contenido)
        tv_titulo_det.setText(nota.titulo)
        btn_borrar.setOnClickListener{
            eliminarExterno(nota.titulo)
            notas.remove(nota)
            this.notifyDataSetChanged()
        }

        return vista
    }
    private fun eliminarExterno(titulo: String){

        if (titulo == ""){
            Toast.makeText(context, "Error: título vacío", Toast.LENGTH_SHORT).show()
        }else{
            try{

                //val ofi = openFileInput(titulo + ".txt")
                val archivo = File(album(),titulo+".txt")
                archivo.delete()

                Toast.makeText(context, "Se eliminó el archivo", Toast.LENGTH_SHORT).show()
            }catch(e: Exception){

                Log.e("errorz", e.message.toString())
                Toast.makeText(context, "Error al eliminar el archivo", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun album(): String{
        val album = File(Environment.getExternalStorageDirectory(), "notas")
        if(!album.exists()){
            album.mkdir()
        }

        return album.absolutePath
    }
//    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
//        var inflator=LayoutInflater.from(context)
//        var vista=inflator.inflate(R.layout.nota_layout, null)
//        var nota= notas[p0]
//
//        var tv_titulo_det = vista.findViewById(R.id.tv_titulo_det) as TextView
//        var tv_contenido_det = vista.findViewById(R.id.tv_contenido_det) as TextView
//
//        tv_contenido_det.setText(nota.contenido)
//        tv_titulo_det.setText(nota.titulo)
//
//        return vista
//    }


}