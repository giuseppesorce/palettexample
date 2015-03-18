/*
 * Copyright (C) 2015 Giuseppe Sorce.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gsorce.colorpalette;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


public class PaletteActivity extends ActionBarActivity {

    public static final String EXTRA_IMAGE = "detail:image";
    private ImageView image;
    private Toolbar toolbar;
    private Target target = new Target() {


        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            if (bitmap != null) {
                image.setImageBitmap(bitmap);
                setColorFromPalette(bitmap);
            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    public static void launch(Activity activity, View transitionView, String url) {
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity, transitionView, EXTRA_IMAGE);
        Intent intent = new Intent(activity, PaletteActivity.class);
        intent.putExtra(EXTRA_IMAGE, url);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationIcon(R.drawable.ic_ab_drawer);
        image = (ImageView) findViewById(R.id.image);
        ViewCompat.setTransitionName(image, EXTRA_IMAGE);
        Picasso.with(this).load(getIntent().getStringExtra(EXTRA_IMAGE)).into(target);
    }

    private void setColorFromPalette(Bitmap bitmap) {

        Palette palette = Palette.generate(bitmap);
        int vibrant = palette.getVibrantColor(0x000000);
        int vibrantLight = palette.getLightVibrantColor(0x000000);
        int vibrantDark = palette.getDarkVibrantColor(0x000000);
        int muted = palette.getMutedColor(0x000000);
        int mutedLight = palette.getLightMutedColor(0x000000);
        int mutedDark = palette.getDarkMutedColor(0x000000);
        findViewById(R.id.vVibrant).setBackgroundColor(vibrant);
        findViewById(R.id.vVibrantLight).setBackgroundColor(vibrantLight);
        findViewById(R.id.vVibrantDark).setBackgroundColor(vibrantDark);
        findViewById(R.id.vMuted).setBackgroundColor(muted);
        findViewById(R.id.vMutedLight).setBackgroundColor(mutedLight);
        findViewById(R.id.vMutedDark).setBackgroundColor(mutedDark);
        toolbar.setBackgroundColor(vibrant);

        Palette.Swatch swatch = palette.getVibrantSwatch();
        Log.i("gsorce", "swatch getPopulation: "+swatch.getPopulation());
        Log.i("gsorce", "swatch getTitleTextColor : "+swatch.getTitleTextColor());
        Log.i("gsorce", "swatch getRgb : "+swatch.getRgb());
        Log.i("gsorce", "swatch getBodyTextColor : "+swatch.getBodyTextColor());





    }
}
