package pos.ng;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;



import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;

import org.apache.http.impl.client.DefaultHttpClient;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class PostinggActivity extends Activity {
    /** Called when the activity is first created. */
	ImageView twitter,facebook,img1,img2,img3,imgcamera;
	Button btnsend;
	Boolean bool=true;
	boolean bool1=true;
	int a=0;
	private Bitmap image;
	private static final int CAMERA_REQUEST = 1888; 
	private static final int IMAGE_PICK 	= 1;
	private static final int IMAGE_CAPTURE 	= 2;
	Boolean imgbool=false;
	
	
	final String [] items=new String []{"From Camera","From Gallery"};
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
   

    twitter=(ImageView)findViewById(R.id.imgtwitter);
    facebook=(ImageView)findViewById(R.id.imgfacebook);
    img1=(ImageView)findViewById(R.id.img1);
    img2=(ImageView)findViewById(R.id.img2);
    img3=(ImageView)findViewById(R.id.img3);
    imgcamera=(ImageView)findViewById(R.id.imgcamera);
    btnsend=(Button)findViewById(R.id.btnsend);
    twitter.setBackgroundResource(R.drawable.twitterunselected);
    facebook.setBackgroundResource(R.drawable.facebookunselected);
    img1.setBackgroundResource(R.drawable.icon1);
    img2.setBackgroundResource(R.drawable.icon1);
    img3.setBackgroundResource(R.drawable.icon1);
    imgcamera.setBackgroundResource(R.drawable.camera);
    final AlertDialog.Builder builder=new AlertDialog.Builder(this);
    
    Intent intent=getIntent();
   

	String action = intent.getAction();
   
	
	
	if (Intent.ACTION_SEND.equals(intent.getAction())) {
        Bundle extras = intent.getExtras();
        if (extras.containsKey(Intent.EXTRA_STREAM)) {
            Uri uri = (Uri) extras.getParcelable(Intent.EXTRA_STREAM);
            String scheme = uri.getScheme();
            if (scheme.equals("content")) {
                String mimeType = intent.getType();
             ContentResolver contentResolver = getContentResolver();
                Cursor cursor = contentResolver.query(uri, null, null, null, null);
              cursor.moveToFirst();
            
              String filePath = cursor.getString(cursor.getColumnIndexOrThrow(Images.Media.DATA));
              this.updateImageView(BitmapFactory.decodeFile(filePath));
            
            }
        }
    }


    imgcamera.setOnClickListener(new View.OnClickListener() {

        public void onClick(View v) {
        	
        	builder.setTitle("Items alert");
			builder.setItems(items, new OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			
				String wh=Integer.toString(which);
				if(items[which]=="From Camera")
				{
				Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, IMAGE_CAPTURE);
				}
				else
				{
					
						Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						intent.setType("image/*");
						startActivityForResult(Intent.createChooser(intent, "Escolha uma Foto"), IMAGE_PICK);
				}
			}
			});
			
			builder.show();
		
        	
        }
    });
     

    twitter.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			
		
		
		if(bool)
		{
			twitter.setBackgroundResource(R.drawable.twitterselected);
		}	
		else {
			twitter.setBackgroundResource(R.drawable.twitterunselected);
		}
		bool= !bool;
		}
	});
    
facebook.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			
		
		
		if(bool1)
		{
			facebook.setBackgroundResource(R.drawable.facebookselected);
		}	
		else {
			facebook.setBackgroundResource(R.drawable.facebookunselected);
			
		}
		bool1= !bool1;
		}
	});
    
img1.setOnClickListener(new View.OnClickListener() {

public void onClick(View v) {
	// TODO Auto-generated method stub
	a=1;
	



	img1.setBackgroundResource(R.drawable.aaa);
	img2.setBackgroundResource(R.drawable.icon1);
	img3.setBackgroundResource(R.drawable.icon1);


}
});

img2.setOnClickListener(new View.OnClickListener() {

public void onClick(View v) {
	// TODO Auto-generated method stub
	a=2;
	



	img2.setBackgroundResource(R.drawable.aaa);
	img1.setBackgroundResource(R.drawable.icon1);
	img3.setBackgroundResource(R.drawable.icon1);


}
});


img3.setOnClickListener(new View.OnClickListener() {

public void onClick(View v) {
	// TODO Auto-generated method stub
	a=3;
	



	img3.setBackgroundResource(R.drawable.aaa);
	img1.setBackgroundResource(R.drawable.icon1);
	img2.setBackgroundResource(R.drawable.icon1);


}
});

btnsend.setOnClickListener(new View.OnClickListener() {
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if(imgbool)
		{
			try {
				executeMultipartPost();
			} catch (Exception e) {
				// TODO: handle exception
				Log.e(e.getClass().getName(), e.getMessage());

			}
		}
		else
		{
			Toast.makeText(getApplicationContext(), "no image",Toast.LENGTH_LONG).show();
		}

		
	}
		

});
    
    
}





protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
    if (resultCode == RESULT_OK) {
    	
       // Bitmap photo = (Bitmap) data.getExtras().get("data");
       
       // 	imgcamera.setImageBitmap(photo);
    	switch (requestCode) {
    	case IMAGE_PICK:	
			this.imageFromGallery(resultCode, data);
			break;
		case IMAGE_CAPTURE:
			this.imageFromCamera(resultCode, data);
			break;
		default:
			break;
		}
        	
		
        
    }  
}
private void updateImageView(Bitmap newImage) {
	BitmapProcessor bitmapProcessor = new BitmapProcessor(newImage, 100, 100, 90);
	
	this.image = bitmapProcessor.getBitmap();
	this.imgcamera.setImageBitmap(this.image);
	imgbool=true;
}
 
    
    private void imageFromCamera(int resultCode, Intent data) {
    	//Bitmap photo = ((Bitmap) data.getExtras().get("data"));
    	//imgcamera.setImageBitmap(photo);
    	
    	this.updateImageView(((Bitmap) data.getExtras().get("data")));
    	imgbool=true;
    	
    }
    private void imageFromGallery(int resultCode, Intent data) {
    	Uri selectedImage = data.getData();
    	String [] filePathColumn = {MediaStore.Images.Media.DATA};
    	
    	Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
    	cursor.moveToFirst();
    	
    	int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
    	String filePath = cursor.getString(columnIndex);
    	cursor.close();
    	
    	this.updateImageView(BitmapFactory.decodeFile(filePath));
    }
    
    
    public void executeMultipartPost() throws Exception {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			image.compress(CompressFormat.JPEG, 75, bos);
			byte[] data = bos.toByteArray();
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost(
					"http://api.challgren-dev.geoclique.me/object.json");
			ByteArrayBody bab = new ByteArrayBody(data, "forest.jpg");
			// File file= new File("/mnt/sdcard/forest.png");
			// FileBody bin = new FileBody(file);
			MultipartEntity reqEntity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);
			reqEntity.addPart("uploaded", bab);
			reqEntity.addPart("photoCaption", new StringBody("sfsdfsdf"));
			postRequest.setEntity(reqEntity);
			HttpResponse response = httpClient.execute(postRequest);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), "UTF-8"));
			String sResponse;
			StringBuilder s = new StringBuilder();

			while ((sResponse = reader.readLine()) != null) {
				s = s.append(sResponse);
			}
			System.out.println("Response: " + s);
		} catch (Exception e) {
			// handle exception here
			Log.e(e.getClass().getName(), e.getMessage());
		}
	}
    
   

    
   

}