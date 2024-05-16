package xyz.bartkk.dragdrop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.DragStartHelper
import androidx.core.view.children
import androidx.customview.widget.ViewDragHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val images = listOf<View>(
            findViewById(R.id.drop1),
            findViewById(R.id.drop2),
            findViewById(R.id.drop3)
        )

        val dropTargets = listOf<LinearLayout>(
            findViewById(R.id.dropTarget1),
            findViewById(R.id.dropTarget2),
            findViewById(R.id.dropTarget3)
        )

        dropTargets.forEach {
            it.setOnDragListener { view, dragEvent ->
                when (dragEvent.action) {
                    DragEvent.ACTION_DROP -> {
                        val draggedView = dragEvent.localState as View
                        val parent = draggedView.parent as LinearLayout
                        parent.removeView(draggedView)
                        view as LinearLayout
                        view.addView(draggedView,it.childCount)
                    }
                }
                true
            }
        }

        images.forEach {
            it.setOnDragListener { view, dragEvent ->
                when (dragEvent.action) {
                    DragEvent.ACTION_DROP -> {
                        val draggedView = dragEvent.localState as View
                        val myParent = it.parent as LinearLayout
                        val myIndex = myParent.indexOfChild(it)

                        val parent = draggedView.parent as LinearLayout
                        parent.removeView(draggedView)
                        myParent.addView(draggedView, myIndex)
                    }
                }
                true
            }

            it.setOnTouchListener { view, motionEvent ->
                if (motionEvent.eventTime - motionEvent.downTime > 100) {
                    view.startDragAndDrop(null, View.DragShadowBuilder(view), view, 0)
                } else {
                    view.performClick()
                }

                true
            }
        }

    }
}