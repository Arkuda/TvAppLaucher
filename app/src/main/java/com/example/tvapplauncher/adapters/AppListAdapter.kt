package com.example.tvapplauncher.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tvapplauncher.R
import com.example.tvapplauncher.data_a.ApplicationInfo


class AppListAdapter(var apps: List<ApplicationInfo>, var itemClickCallback : ItemClickCallback) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AppItemVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.app_item_vh,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = apps.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AppItemVH) {
            holder.fill(apps[position],itemClickCallback)
        }
    }

    class AppItemVH(var view: View) : RecyclerView.ViewHolder(view) {
        val ico = view.findViewById<ImageView>(R.id.ico)
        val name = view.findViewById<TextView>(R.id.name)

        fun fill(
            item: ApplicationInfo,
            itemClickCallback: ItemClickCallback
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
            view.setOnClickListener {
                itemClickCallback.onClick(item)
            }
        }
    }


}

interface ItemClickCallback{
    fun onClick(appInfo : ApplicationInfo)
}
