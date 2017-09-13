package com.example.administrator.pdfviewtest006;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnLoadCompleteListener;
import com.joanzapata.pdfview.listener.OnPageChangeListener;

import java.io.File;
import java.net.URI;

public class MainActivity extends AppCompatActivity implements OnPageChangeListener,OnLoadCompleteListener{
    @Override
    public void loadComplete(int nbPages) {
        Toast.makeText( MainActivity.this ,  "加载完成" + nbPages  , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        Toast.makeText(MainActivity.this, page+"/"+pageCount, Toast.LENGTH_SHORT).show();
    }

    PDFView pdfView;
    Button button;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // pdfView.fromFile()
        button=(Button)findViewById(R.id.button);
        pdfView=(PDFView)findViewById(R.id.pdfView);
        pdfView.fromAsset("b.pdf").onPageChange(this).onLoad(this).
                load();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {//是否选择，没选择就不会继续
            uri = data.getData();//得到uri
            //Log.e("Error",""+uri.getPath());
            try {
               // uri.getPath();
                File file=new File(new URI(uri.toString()));
                file.getAbsolutePath();
                pdfView.fromFile(file).
                        defaultPage(1).
                        load();
            } catch (Exception e) {
                e.printStackTrace();
                for(StackTraceElement element:e.getStackTrace())
                    Log.e("Error",""+element);
            }
        }
    }
}
