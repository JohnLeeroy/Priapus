package com.jli.gardener.util;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by john on 5/20/16.
 */
public class UriDeserializer implements JsonDeserializer<Uri> {

    private static final String DRAWABLE_COMPARE_PREFIX = "R.drawable.";

    Context context;

    public UriDeserializer(Context context) {
        this.context = context;
    }

    @Override
    public Uri deserialize(final JsonElement src, final Type srcType,
                           final JsonDeserializationContext dontUseThisContext) throws JsonParseException {
        String uriStr = src.getAsString();
        Uri uri = null;
        //NOTE: We don't have to parse from disk to get drawables in the near future.  Remove when we
        //          are not using any placeholder images. - JLi
        if (uriStr.contains(DRAWABLE_COMPARE_PREFIX)) {
            String imageName = uriStr.substring(DRAWABLE_COMPARE_PREFIX.length());
            int picId = this.context.getResources().getIdentifier(imageName, "drawable", this.context.getPackageName());
            uri = getUriForDrawable(this.context, picId);
        } else {
            uri = Uri.parse(uriStr);
        }
        return uri;
    }

    //Note: Remove when we aren't using any local placeholder images. - JLi
    private Uri getUriForDrawable(@NonNull Context context, @NonNull int drawable) {
        return Uri.parse("android.resource://" + context.getPackageName() + "/" + drawable);
    }
}