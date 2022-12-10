package com.nextia.socioinfonavit.core.extension

import androidx.constraintlayout.motion.widget.MotionLayout

inline fun  MotionLayout.addTransitionListener(
    crossinline onTransitionStarted :(motionLayout: MotionLayout?, startId: Int, endId: Int) -> Unit = {_,_,_, ->},
    crossinline onTransitionChange :(motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float) -> Unit = {_,_,_,_, ->},
    crossinline onTransitionCompleted :(motionLayout: MotionLayout?, currentId: Int) -> Unit = {_,_, ->},
    crossinline onTransitionTrigger :(motionLayout: MotionLayout?, triggerId: Int, positive: Boolean, progress: Float) -> Unit = {_,_,_,_, ->}

    ) : MotionLayout.TransitionListener {

    val transitionLister = object : MotionLayout.TransitionListener {
        override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {
            onTransitionStarted.invoke(motionLayout, startId, endId)
        }

        override fun onTransitionChange(
            motionLayout: MotionLayout?,
            startId: Int,
            endId: Int,
            progress: Float
        ) {
            onTransitionChange.invoke(motionLayout, startId, endId, progress)
        }

        override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
            onTransitionCompleted.invoke(motionLayout, currentId)
        }

        override fun onTransitionTrigger(
            motionLayout: MotionLayout?,
            triggerId: Int,
            positive: Boolean,
            progress: Float
        ) {
            onTransitionTrigger.invoke(motionLayout, triggerId, positive, progress)
        }
    }
    addTransitionListener(transitionLister)
    return transitionLister
}