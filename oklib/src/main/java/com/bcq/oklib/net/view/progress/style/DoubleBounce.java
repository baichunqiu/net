package com.bcq.oklib.net.view.progress.style;

import android.animation.ValueAnimator;
import android.os.Build;

import com.bcq.oklib.net.view.progress.animation.SpriteAnimatorBuilder;
import com.bcq.oklib.net.view.progress.sprite.CircleSprite;
import com.bcq.oklib.net.view.progress.sprite.Sprite;
import com.bcq.oklib.net.view.progress.sprite.SpriteContainer;

/**
 * Created by ybq.
 */
public class DoubleBounce extends SpriteContainer {

    @Override
    public Sprite[] onCreateChild() {
        return new Sprite[]{
                new Bounce(), new Bounce()
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

    private class Bounce extends CircleSprite {

        Bounce() {
            setAlpha(153);
            setScale(0f);
        }

        @Override
        public ValueAnimator onCreateAnimation() {
            float fractions[] = new float[]{0f, 0.5f, 1f};
            return new SpriteAnimatorBuilder(this).scale(fractions, 0f, 1f, 0f).
                    duration(2000).
                    easeInOut(fractions)
                    .build();
        }
    }
}
