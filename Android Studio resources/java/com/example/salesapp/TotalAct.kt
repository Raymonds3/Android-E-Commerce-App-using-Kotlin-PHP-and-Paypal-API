package com.example.salesapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.paypal.android.sdk.payments.*
import kotlinx.android.synthetic.main.activity_total.*
import java.math.BigDecimal

class TotalAct : AppCompatActivity() {

    var config:PayPalConfiguration?=null
    var amount:Double=0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total)

        var url="http://192.168.8.101:81/SalesApp/get_total.php?bill_no=" + intent.getStringExtra("bno")

        var rq: RequestQueue = Volley.newRequestQueue(this)
        var sr= StringRequest(Request.Method.GET,url, Response.Listener { response ->

            total_tv.text=response

        }, Response.ErrorListener { error ->
            Toast.makeText(this,error.message, Toast.LENGTH_LONG).show()
        })

        rq.add(sr)

        config=PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(UserInfo.client_id)
        var i=Intent(this,PayPalService::class.java)
        i.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config)
        startService(i)

        paypal_btn.setOnClickListener {
            amount=total_tv.text.toString().toDouble()
            var payment=PayPalPayment(BigDecimal.valueOf(amount),"USD","Sales App",PayPalPayment.PAYMENT_INTENT_SALE)
            var intent=Intent(this,PaymentActivity::class.java)
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config)
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payment)
            startActivityForResult(intent,123)

        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==123)
        {
            if (resultCode==Activity.RESULT_OK)
            {
                var obj=Intent(this,ConfirmAct::class.java)
                startActivity(obj)
            }
        }

    }

    override fun onDestroy() {
        stopService(Intent(this,PayPalService::class.java))
        super.onDestroy()
    }
}
