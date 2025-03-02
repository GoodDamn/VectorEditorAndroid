package good.damn.editor.vector.view.gradient;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

public final class VECanvasColorSeek {

    private final Paint mPaint = new Paint();

    final Rect rectColor = new Rect();


    @ColorInt
    public final int getColor() {
        return mPaint.getColor();
    }

    public final void setColor(
        @ColorInt int color
    ) {
        mPaint.setColor(
            color
        );
    }

    public final void layout(
        float x,
        float y,
        float width,
        float height
    ) {

        rectColor.left = (int) x;
        rectColor.top = (int) y;

        float s = (width > height ? height : width) * 0.2f;
        rectColor.right = (int) (x + s);
        rectColor.bottom = (int) (y + s);

    }

    public final void draw(
        @NonNull Canvas canvas
    ) {
        canvas.drawRect(
            rectColor,
            mPaint
        );
    }

    public final boolean touchEvent(
        @NonNull MotionEvent event
    ) {
        float x = event.getX();
        float y = event.getY();

        return !(rectColor.left > x ||
            rectColor.top > y ||
            rectColor.right < x ||
            rectColor.bottom < y);
    }

}
