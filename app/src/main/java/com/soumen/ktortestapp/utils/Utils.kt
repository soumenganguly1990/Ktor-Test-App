package com.soumen.ktortestapp.utils

import android.app.ProgressDialog
import android.content.Context
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.soumen.ktortestapp.R
import com.soumen.ktortestapp.adapters.CommonRecyclerViewAdapter

class Utils {

    companion object {

        const val PAGEQUERYPARAM = "page"
        private var progressDialog: ProgressDialog? = null // Deprecated, but, there is a but, this is just an experimental project

        fun showProgressDialog(mContext: Context) {
            progressDialog?.let { dismissprogressDialog() }
            progressDialog = ProgressDialog(mContext)
            progressDialog?.apply {
                setTitle("Waiting!!")
                setMessage("Please hold on..")
                setCancelable(false)
                setCanceledOnTouchOutside(false)
                show()
            }
        }

        fun dismissprogressDialog() {
            try { progressDialog?.let { progressDialog?.dismiss() } }
            finally { progressDialog = null }
        }

        fun AppCompatImageView.loadImage(imageUrl: String) {
            this.load(imageUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
                transformations(CircleCropTransformation())
            }
        }

        fun View.visible() { this.visibility = View.VISIBLE }
        fun View.invisible() { this.visibility = View.INVISIBLE }
        fun View.gone() { this.visibility = View.GONE }

        fun RecyclerView.updateRecyclerView() { this.adapter?.notifyDataSetChanged() }
        fun <T> RecyclerView.setUpVertical(mContext: Context
                                           , isDividerNeeded: Boolean = false
                                           , rclAdapter: CommonRecyclerViewAdapter<T>) {
            this.apply {
                layoutManager = LinearLayoutManager(mContext)
                if (isDividerNeeded) DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL)
                adapter = rclAdapter
            }
        }
    }
}