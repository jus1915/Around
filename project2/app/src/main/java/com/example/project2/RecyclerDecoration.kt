package com.example.project2

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class RecyclerDecoration(//    리사이클러뷰 아이템간의 간격 설정
    private val divHeight: Int
) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) != parent.adapter?.getItemCount()
        ) outRect.top = divHeight


//        outRect.top = 값;
//        outRect.bottom = 값;
//        outRect.left = 값;
//        outRect.right = 값;
//        상하좌우 설정
    }

}