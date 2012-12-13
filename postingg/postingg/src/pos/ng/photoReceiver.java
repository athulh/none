package pos.ng;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.widget.Toast;

public class photoReceiver extends BroadcastReceiver
{
	  private static final String TAG = "photoReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		//intent = getIntent();
	    if (Intent.ACTION_SEND.equals(intent.getAction())) {
	        Bundle extras = intent.getExtras();
	        if (extras.containsKey(Intent.EXTRA_STREAM)) {
	            Uri uri = (Uri) extras.getParcelable(Intent.EXTRA_STREAM);
	            String scheme = uri.getScheme();
	            if (scheme.equals("content")) {
	                String mimeType = intent.getType();
	              //  ContentResolver contentResolver = getContentResolver();
	                //Cursor cursor = contentResolver.query(uri, null, null, null, null);
	               // cursor.moveToFirst();
	              //  String filePath = cursor.getString(cursor.getColumnIndexOrThrow(Images.Media.DATA));
	            }
	        }
	    }
	}
}
 
	   