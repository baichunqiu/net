package com.bcq.oklib.net.view.progress.style;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.os.Build;
import android.view.animation.LinearInterpolator;

import com.bcq.oklib.net.view.progress.animation.SpriteAnimatorBuilder;
import com.bcq.oklib.net.view.progress.sprite.CircleSprite;
import com.bcq.oklib.net.view.progress.sprite.Sprite;
import com.bcq.oklib.net.view.progress.sprite.SpriteContainer;

/**
 * Created by ybq.
 */
public class ChasingDots extends SpriteContainer {

    @Override
    public Sprite[] onCreateChild() {
        return new Sprite[]{
                new Dot(),
                new Dot()
        };
    }

    @Override
    public void onChildCreated(Sprite... sprites) {
        super.onChildCreated(sprites);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        if (Build.VERSION.SDK_INT >= 24) {
            sprites[1].setAnimationDelay(1000);
        } else {
            sprites[1].setAnimationDelay(-1000);
        }
    }

    @Override
    public ValueAnimator onCreateAnimation() {
        float fractions[] = new float[]{0f, 1f};
        return new SpriteAnimatorBuilder(this).
                rotate(fractions, 0, 360).
                duration(2000).
                interpolator(new LinearInterpolator()).
                build();
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        bounds = clipSquare(bounds);
        int drawW = (int) (bounds.width() * 0.6f);
        getChildAt(0).setDrawBounds(
                bounds.right - drawW,
                bounds.top,
                bounds.right
                , bounds.top + drawW
        );
        getChildAt(1).setDrawBounds(
                bounds.right - drawW,
                bounds.bottom - drawW,
                bounds.right,
                bounds.bottom
        );
    }

    private class Dot extends CircleSprite {

        Dot() {
            setScale(0f);
        }

        @Override
        public ValueAnimator onCreateAnimation() {
            float fractions[] = new float[]{0f, 0.5f, 1f};
            return new SpriteAnimatorBuilder(this).
                    scale(fractions, 0f, 1f, 0f).
                    duration(2000).
                    easeInOut(fractions)
                    .build();
        }
    }

}
