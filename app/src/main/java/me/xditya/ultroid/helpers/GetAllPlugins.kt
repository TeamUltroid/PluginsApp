package me.xditya.ultroid.helpers

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

fun getAllPlugins(ctx: Context, result: MutableState<String>) {
    val queue = Volley.newRequestQueue(ctx)
    val baseUrl = "https://plugins.xditya.me/getAllPluginsInfo"
    val request: StringRequest =
        object :
            StringRequest(Method.POST, baseUrl, Response.Listener { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val pluginData = jsonObject.getJSONArray("plugins")
                    result.value = pluginData.toString()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error -> // displaying toast message on response failure.
                Log.e("tag", "error is " + error!!.message)
                Toast.makeText(ctx, "Failed to fetch details..", Toast.LENGTH_SHORT).show()
                result.value = "[]"
            }) {
            override fun parseNetworkError(volleyError: VolleyError?): VolleyError {
                return super.parseNetworkError(volleyError)
            }
        }
    queue.add(request)
}