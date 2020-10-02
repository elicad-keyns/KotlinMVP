package com.ek.kotlinmvp.presentation.homeFragment

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


fun RecyclerView.addCustomScrollListener(
    threshold: Int,
    uploader: () -> Unit
) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val lastVisible =
                (layoutManager as? LinearLayoutManager)?.findLastVisibleItemPosition() ?: return
            val totalCount = adapter?.itemCount

            if (totalCount != null) {
                if (threshold >= totalCount - lastVisible)
                    uploader()
            }
        }
    })
}