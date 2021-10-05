package com.example.converter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.converter.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var spinner: Spinner

    private var eur = 0
    private var cur = 0
    var selected =0
    lateinit var  fullData: Data

    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ////spinner/////
        val list = arrayListOf("ada", "aed", "afn", "all", "amd", "ang", "aoa", "ars")



        spinner = binding.spinner
        if(spinner!=null){

            val adapter= ArrayAdapter(this, android.R.layout.simple_spinner_item, list)

            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selected= position
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Toast.makeText(applicationContext, "Please, Select a currency", Toast.LENGTH_LONG).show()
                }
            }
        }

        ////spinner/////

        //// Api Call //////////

        val apiInterface = ApiClient().getClient()?.create(ApiInterface::class.java)

        val call: Call<Data?>? = apiInterface!!.getData()

        call?.enqueue(object : Callback<Data?> {
            override fun onResponse(call: Call<Data?>, response: Response<Data?>) {

               // var displayResponse = ""
                val resource: Data? = response.body()
                val date = resource?.date


                binding.tvDate.text = date
                fullData = response.body()!!

            }

            override fun onFailure(call: Call<Data?>, t: Throwable) {
                call.cancel()
                Toast.makeText(applicationContext, "Failure:  ${t.message}", Toast.LENGTH_LONG).show()
                Log.d("MainActivity", t.message.toString())
            }
        })

        //// Api Call //////////

        ///button actions///
        binding.btnCon.setOnClickListener {
            convert()
            binding.etAmount.text.clear()
        }

    }

    private fun cal(eur: Double?, cur: Double): Double {
                var result =0.0
            if (eur != null) {
                result = cur * eur
            }
        return result
    }

    private fun convert(){
        val input = binding.etAmount.text.toString()
        val  eur = input.toDouble()

        when(selected){
            0 -> {

                val result = cal(eur, fullData.eur?.ada?.toDouble()!!)
                binding.tvResult.text = "Result \n $result"
            }
            1 -> {

                val result = cal(eur, fullData.eur?.aed?.toDouble()!!)
                binding.tvResult.text = "Result \n $result"
            }
            2 -> {

                val result = cal(eur, fullData.eur?.afn?.toDouble()!!)
                binding.tvResult.text = "Result \n $result"
            }
            3 -> {

                val result = cal(eur, fullData.eur?.all?.toDouble()!!)
                binding.tvResult.text = "Result \n $result"
            }
            4 -> {

                val result = cal(eur, fullData.eur?.amd?.toDouble()!!)
                binding.tvResult.text = "Result \n $result"
            }
            5 -> {

                val result = cal(eur, fullData.eur?.ang?.toDouble()!!)
                binding.tvResult.text = "Result \n $result"
            }
            6 -> {

                val result = cal(eur, fullData.eur?.aoa?.toDouble()!!)
                binding.tvResult.text = "Result \n $result"
            }
            6 -> {

                val result = cal(eur, fullData.eur?.ars?.toDouble()!!)
                binding.tvResult.text = "Result \n $result"
            }
        }
    }

}