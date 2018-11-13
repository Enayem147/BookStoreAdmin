package com.example.a84965.bookstoreadmin.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a84965.bookstoreadmin.R;
import com.example.a84965.bookstoreadmin.adapter.ChooseAuthorAdapter;
import com.example.a84965.bookstoreadmin.adapter.ChooseBookTypeAdapter;
import com.example.a84965.bookstoreadmin.adapter.SpinnerNXBAdapter;
import com.example.a84965.bookstoreadmin.model.NhaXuatBan;
import com.example.a84965.bookstoreadmin.model.TacGia;
import com.example.a84965.bookstoreadmin.model.TacGiaCheckList;
import com.example.a84965.bookstoreadmin.model.TheLoai;
import com.example.a84965.bookstoreadmin.model.TheLoaiCheckList;
import com.example.a84965.bookstoreadmin.ultil.GetChildFirebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import static android.provider.MediaStore.Images.Media.getBitmap;

public class AddBookActivity extends AppCompatActivity {
    TextView txtTL, txtTG;
    Toolbar toolbar;
    Spinner spnNXB;
    ChooseBookTypeAdapter chooseBookTypeAdapter;
    ChooseAuthorAdapter chooseAuthorAdapter;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 1;
    Button btnChonTheLoai, btnChonTacGia, btnThem;
    ImageView imgHinhAnh;
    private static DatabaseReference mDatabase;
    private ArrayList<TheLoaiCheckList> listTheLoai = new ArrayList<>();
    private ArrayList<TacGiaCheckList> listTacGia = new ArrayList<>();
    public static ArrayList<TheLoaiCheckList> listKetQuaTL = new ArrayList<>();
    public static ArrayList<TacGiaCheckList> listKetQuaTG = new ArrayList<>();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    String maNXB = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        callControls();
        initToolBar();
        loadTheLoai();
        chonTheLoai();
        loadTacGia();
        chonTacGia();
        chonHinh();
        initSpinnerNhaXB();
        themSach();
    }

    private void initSpinnerNhaXB() {
        SpinnerNXBAdapter spinnerNXBAdapter = new SpinnerNXBAdapter(this,android.R.layout.simple_list_item_1,BookListActivity.listNhaXB);
        spnNXB.setAdapter(spinnerNXBAdapter);
        spnNXB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                NhaXuatBan nxb = (NhaXuatBan) adapterView.getSelectedItem();
                maNXB = nxb.getNXB_Ma();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getListNhaXB(){

    }

    private void themSach() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddBookActivity.this, maNXB, Toast.LENGTH_SHORT).show();
//                Calendar calendar = Calendar.getInstance();
//                final StorageReference ref = storageRef.child("Book" + calendar.getTimeInMillis() + ".png");
//                imgHinhAnh.setDrawingCacheEnabled(true);
//                imgHinhAnh.buildDrawingCache();
//                Bitmap bitmap = ((BitmapDrawable) imgHinhAnh.getDrawable()).getBitmap();
//                Bitmap bitmapResized = Bitmap.createScaledBitmap(bitmap,(int)(bitmap.getWidth()*0.35),(int)(bitmap.getHeight()*0.35),true);
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bitmapResized.compress(Bitmap.CompressFormat.PNG, 100, baos);
//                byte[] data = baos.toByteArray();
//
//                final UploadTask uploadTask = ref.putBytes(data);
//                uploadTask.addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        Toast.makeText(AddBookActivity.this, "Lỗi upload hình ảnh", Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        //đã up hình thành công
//                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                String uploadUri = uri.toString();
//
//                            }
//                        });
//                    }
//                });
            }
        });
    }


    private void chonHinh() {
        imgHinhAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,PICK_IMAGE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null && resultCode == RESULT_OK){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgHinhAnh.setImageBitmap(bitmap);
        }
    }


    private void chonTacGia() {
        btnChonTacGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialogTacGia();
            }
        });
    }

    private void getDialogTacGia() {
        chooseAuthorAdapter = new ChooseAuthorAdapter(this, listTacGia);
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_multi_select);
        final ListView listViewTacGia = dialog.findViewById(R.id.listView_MultipleSelect);
        Button btnChonTacGia = dialog.findViewById(R.id.btnMultipleSelected);

        listViewTacGia.setAdapter(chooseAuthorAdapter);


        btnChonTacGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strTG = "";
                if (listKetQuaTG.size() > 0) {
                    strTG = listKetQuaTG.get(0).getTG_Ten();
                    for (int i = 1; i < listKetQuaTG.size(); i++) {
                        strTG += " , " + listKetQuaTG.get(i).getTG_Ten();
                    }
                    txtTG.setText(strTG);
                    dialog.dismiss();
                }
                // dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void loadTacGia() {
        mDatabase.child("TacGia").addChildEventListener(new GetChildFirebase() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                TacGia tacGia = dataSnapshot.getValue(TacGia.class);
                listTacGia.add(new TacGiaCheckList(tacGia.getTG_Ma(), tacGia.getTG_Ten(), false));
                super.onChildAdded(dataSnapshot, s);
            }
        });
    }

    private void chonTheLoai() {
        btnChonTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialogTheLoai();
            }
        });
    }

    private void getDialogTheLoai() {
        chooseBookTypeAdapter = new ChooseBookTypeAdapter(this, listTheLoai);
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_multi_select);
        final ListView listViewTheLoai = dialog.findViewById(R.id.listView_MultipleSelect);
        Button btnChonTheLoai = dialog.findViewById(R.id.btnMultipleSelected);

        listViewTheLoai.setAdapter(chooseBookTypeAdapter);


        btnChonTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strTL = "";
                if (listKetQuaTL.size() > 0) {
                    strTL = listKetQuaTL.get(0).getTL_Ten();
                    for (int i = 1; i < listKetQuaTL.size(); i++) {
                        strTL += " , " + listKetQuaTL.get(i).getTL_Ten();
                    }
                    txtTL.setText(strTL);
                    dialog.dismiss();
                }
                // dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void loadTheLoai() {
        mDatabase.child("TheLoai").addChildEventListener(new GetChildFirebase() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                TheLoai theLoai = dataSnapshot.getValue(TheLoai.class);
                listTheLoai.add(new TheLoaiCheckList(theLoai.getTL_Ma(), theLoai.getTL_Ten(), false));
                super.onChildAdded(dataSnapshot, s);
            }
        });
    }

    private void callControls() {
        txtTG = findViewById(R.id.txtAddBook_TacGia);
        txtTL = findViewById(R.id.txtAddBook_TheLoai);
        spnNXB = findViewById(R.id.spnAddBook_NhaXB);

        btnThem = findViewById(R.id.btnAddBook_ThemHinh);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        btnChonTheLoai = findViewById(R.id.btnChonTheLoai);
        btnChonTacGia = findViewById(R.id.btnChonTacGia);
        imgHinhAnh = findViewById(R.id.imgAddBook);
    }

    private void initToolBar() {
        toolbar = findViewById(R.id.toolBar_AddBook);
        toolbar.setNavigationIcon(R.drawable.ic_menu_back);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("Thêm sản phẩm mới");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


}
