package com.example.tvapplauncher.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tvapplauncher.R
import com.example.tvapplauncher.data_a.ApplicationInfo
import android.widget.Switch


class SelectionAppListAdapter(
    var apps: List<ApplicationInfo>,
    var  favApps : List<String>?,
    var itemClickCallback: ItemSettingsCallback
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AppItemVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.app_selection_item_vh,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = apps.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AppItemVH) {
            holder.fill(apps[position], itemClickCallback, favApps?.contains(apps[position].packageName))
        }
    }

    class AppItemVH(var view: View) : RecyclerView.ViewHolder(view) {
        val ico = view.findViewById<ImageView>(R.id.ico)
        val name = view.findViewById<TextView>(R.id.name)

        fun fill(
            item: ApplicationInfo,
            itemClickCallback: ItemSettingsCallback,
            contains: Boolean?
        ) {
            view.focusable = View.FOCUSABLE
            view.isFocusableInTouchMode = true
            view.isFocusable = true
            ico.setImageDrawable(item.ico)
            name.text = item.name


            view.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    view.background = view.context.getDrawable(R.drawable.selection_drawable)
                } else {
                    view.background = null
                }
            }

            val switch = view.findViewById<Switch>(R.id.switch1)
            if(contains != null){
                switch.isChecked = contains
            }


            view.setOnClickListener {
                switch.isChecked = !switch.isChecked
                itemClickCallback.onChecked(item, switch.isChecked)
            }


        }
    }

}


interface ItemSettingsCallback {
    fun onChecked(item: ApplicationInfo, state: Boolean)
}
