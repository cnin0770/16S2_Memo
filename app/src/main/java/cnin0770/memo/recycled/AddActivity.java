//package cnin0770.memo;
//
///**
// * Created by cnin0770 on 16/8/20.
// * this class is for adding new item and date to database, share layout with EditActivity.class
// */
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.EditText;
//import java.util.Date;
//import android.widget.TextView;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.lang.String;
//import java.text.SimpleDateFormat;
//import android.util.Log;
//
//import org.apache.commons.io.FileUtils;
//
//
//public class AddActivity extends Activity {
//
//        EditText editText;
//        TextView textView;
//        ArrayList<String> items;
//        ArrayList<String> dateStr;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edad);
//
//        textView = (TextView) findViewById(R.id.textView);
//        editText = (EditText) findViewById(R.id.editText);
//
//        Date date = new Date();
//        SimpleDateFormat outPutDate = new SimpleDateFormat ("E, MMM.dd, HH:mm");  //set date format
//        String dateString = "created at " + outPutDate.format(date);
//        textView.setText(dateString);
//
//        items = new ArrayList<String>();
//        dateStr = new ArrayList<String>();
//
//        readItemsFromDatabase();
//    }
//
//    public void onSave (View view){
//        String toAddString = editText.getText().toString();
//        if (toAddString != null && toAddString.length() > 0) {
//
//            Date date = new Date();
//            SimpleDateFormat outPutDate = new SimpleDateFormat ("E, MMM.dd, HH:mm");  //set date format
//            String dateString = "created at " + outPutDate.format(date);
//
//            items.add(toAddString);
//            dateStr.add(dateString);
//
//            for (int k = 0; k < items.size(); k ++) {
//                Log.i("MainAct", "item " + items.get(k));
//            }
//
//            saveItemsToDatabase();
//
//            finish();
//        }
//
//    }
//
//    public void onCancel (View view){
//        String toAddString = editText.getText().toString();
//
//        if (toAddString != null && toAddString.length() > 0) {
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
//            builder.setTitle(R.string.dialog_delete_title).setMessage(R.string.dialog_ed_delete_msg)
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
//        else { finish(); }
//    }
//
//    private void readItemsFromDatabase(){
//
//        List<ToDoItem> itemsFromORM = ToDoItem.listAll(ToDoItem.class);
//        items = new ArrayList<String>();
//        if (itemsFromORM != null & itemsFromORM.size() > 0) {
//            for (ToDoItem item : itemsFromORM) {
//                items.add(item.todo);
//            }
//        }
//
//        List<DateTime> datesFromORM = DateTime.listAll(DateTime.class);
//        dateStr = new ArrayList<String>();
//        if (datesFromORM != null & datesFromORM.size() > 0) {
//            for (DateTime date : datesFromORM) {
//                dateStr.add(date.datetime);
//            }
//        }
//    }
//
//    private void saveItemsToDatabase(){
//
//        int i =0;
//
//        ToDoItem.deleteAll(ToDoItem.class);
//        for (String todo : items) {
//
//            Log.i("MainAct", "saveItems" + i);
//            i++;
//
//            ToDoItem item = new ToDoItem(todo);
//            item.save();
//        }
//
//        DateTime.deleteAll(DateTime.class);
//        for (String datetime : dateStr) {
//            DateTime dateTime = new DateTime(datetime);
//            dateTime.save();
//        }
//    }
//
//}
