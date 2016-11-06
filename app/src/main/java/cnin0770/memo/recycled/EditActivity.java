//package cnin0770.memo;
//
///**
// * Created by cnin0770 on 16/8/29.
// * this class is for editing items only, share layout with AddActivity.class
// */
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.TextView;
//
//public class EditActivity extends Activity {
//    public int position = 0;
//    EditText edItem;
//    TextView textView;
//
//
//    @Override
//    protected void onCreate(Bundle saveInstanceState) {
//        super.onCreate(saveInstanceState);
//        setContentView(R.layout.activity_edad);
//
//        String editItem = getIntent().getStringExtra("item");
//        String dateString = getIntent().getStringExtra("date");
//
//        textView = (TextView) findViewById(R.id.textView);
//        textView.setText(dateString);
//
//        edItem = (EditText)findViewById(R.id.editText);
//        edItem.setText(editItem);
//
//        position = getIntent().getIntExtra("position", -1);
//
//    }
//
//    public void onSave (View v) {
//        edItem = (EditText) findViewById(R.id.editText);
//
//        Intent data = new Intent();
//
//        data.putExtra("item", edItem.getText().toString());
//        data.putExtra("position", position);
//
//        setResult(RESULT_OK, data);
//        finish();
//    }
//
//    public void onCancel (View view){
//        String toAddString = edItem.getText().toString();
//
//        if (toAddString != null && toAddString.length() > 0) {
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
//            builder.setTitle(R.string.quit_edit).setMessage(R.string.dialog_ed_delete_msg)
//                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener(){
//                        @Override
//                        public void onClick (DialogInterface dialog, int id) {
//                            finish();
//                        }
//
//                    })
//                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
//                        @Override
//                        public void onClick (DialogInterface dialog, int id) { }
//                    });
//            builder.create().show();
//        }
//    }
//
//}
