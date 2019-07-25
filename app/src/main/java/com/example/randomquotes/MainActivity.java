package com.example.randomquotes;


import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editQuote, editQuoteId;
    Button addQuote;
    Button deleteQuote;
    Button viewAllQuotes;
    Button randomQuote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editQuote = (EditText) findViewById (R.id.editQuote);
        editQuoteId = (EditText) findViewById (R.id.editQuoteId);
        addQuote = (Button) findViewById (R.id.button_add);
        deleteQuote = (Button) findViewById (R.id.button_delete);
        viewAllQuotes = (Button) findViewById (R.id.button_view);
        randomQuote = (Button) findViewById (R.id.button_random);
        AddQuote();
        DeleteQuote();
        ViewAllQuotes();
        RandomQuote();
    }

    public void DeleteQuote() {
        deleteQuote.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer deleteRows = myDb.deleteQuote(editQuoteId.getText().toString());
                        if(deleteRows > 0)
                            Toast.makeText(MainActivity.this, "Quote deleted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Quote not deleted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void RandomQuote() {

        randomQuote.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor res = myDb.getRandomQuote();
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                           // buffer.append("Id: " + res.getString(0) + "\n");
                            buffer.append("Quote: " + res.getString(1) + "\n\n");
                        }
                        showMessage("Quotes", buffer.toString());
                    }
                }
        );
    }

    public void AddQuote() {
        addQuote.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean quoteExists = myDb.checkQuote(editQuote.getText().toString());
                        if (quoteExists == true) {
                            Toast.makeText(getBaseContext(), "Quote already exist. Please add another one", Toast.LENGTH_LONG).show();
                        } else {
                            myDb.insertQuote(editQuote.getText().toString());
                            Toast.makeText(getBaseContext(), "Quote inserted", Toast.LENGTH_LONG).show();
                        }
                        editQuote.setText("");

                    }
                }
        );

    }


    public void ViewAllQuotes() {
        viewAllQuotes.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor res = myDb.getAllQuotes();
                        if(res.getCount() == 0) {
                            showMessage("Error", "Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Id: " + res.getString(0) + "\n");
                            buffer.append("Quote: " + res.getString(1) + "\n\n");
                        }
                        showMessage("Quotes", buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
