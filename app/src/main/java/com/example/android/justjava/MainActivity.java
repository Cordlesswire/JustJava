package com.example.android.justjava;

import android.content.Intent;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This app is a Coffee Ordering App i.e. displays a form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    private String addedwhippedCream = " No ";
    private String addedChocolate = " No ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int allNumbers) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(" " + allNumbers);
    }


    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100){
            return;
        }

        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 0) {
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.creamCheckBox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolateCheckBox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        EditText nameInput = (EditText) findViewById(R.id.nameField);
        String theName = nameInput.getText().toString();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, theName);


        //Intent to Display Order Information in Email App
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));   //only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for: " + theName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * Calculates the price of the order.
     * @param addWhippedCream is whether or not the user wants whipped cream
     * @param addChocolate is whether or not the user wants chocolate
     * @return is the total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {

        //Price of 1 cup of coffee
        int basePrice = 10;

        //Add R1 if the user wants whipped cream
        if (addWhippedCream) {
            addedwhippedCream = " Yes ";
            basePrice = basePrice + 1;
        }
        //Add R2 if the user wants chocolate
        if (addChocolate) {
            addedChocolate =" Yes ";
            basePrice = basePrice + 2;
        }
        //Calculate the total order price by multiplying by quantity
        return quantity * basePrice;

    }


    /**
     * Create summary of the order.
     * @param theName is the name entered in the input field
     //* @param hasWhippedCream is whether or not the user wants whipped cream topping
     //* @param hasChocolate is whether or not the user wants chocolate
     * @param price of the order
     * @return text summary
     */
    private String createOrderSummary(int price, String theName) {

        String priceMessage = "Name: " + theName;
        //Display "Yes" or "No" Depending whether the user Added Whipped Cream or Chocolate to their coffee more User friendly than true/false
        priceMessage += "\nAdded whipped cream? " + addedwhippedCream;
        priceMessage += "\nAdded chocolate? " + addedChocolate;
        priceMessage += "\nQuantity " + quantity;
        priceMessage += "\nTotal: R " + price + "\n";
        priceMessage += "\nThank you!";
        return priceMessage;

    }


}