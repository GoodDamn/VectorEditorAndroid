package good.damn.editor.animation.animator.scroller

abstract class VEIScroller {
    abstract var scrollValue: Float
    protected abstract var mAnchorValue: Float
    protected abstract var mScrollValue: Float

    abstract fun reset()
}