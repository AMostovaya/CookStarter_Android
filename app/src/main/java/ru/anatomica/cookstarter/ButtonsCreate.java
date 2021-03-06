package ru.anatomica.cookstarter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import java.math.BigDecimal;

import ru.anatomica.cookstarter.ui.cart.CartFragment;
import ru.anatomica.cookstarter.ui.profile.ProfileFragment;
import ru.anatomica.cookstarter.ui.restaurants.*;

public class ButtonsCreate {

    private MainActivity mainActivity;
    protected RestaurantsAdapter restaurantsAdapter;
    protected RestaurantsMenuAdapter restaurantsMenuAdapter;
    protected TextView quantity;

    public ButtonsCreate(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void createBtn(int todo) {
        // создаем адаптер
        if (todo == 1) restaurantsAdapter = new RestaurantsAdapter(mainActivity, R.layout.restaurant_items, HttpClients.restaurantsList);
        if (todo == 2) restaurantsMenuAdapter = new RestaurantsMenuAdapter(mainActivity, R.layout.restaurant_items, HttpClients.restaurantListsMenu);
        // устанавливаем адаптер
        if (todo == 1) RestaurantsFragment.restaurantsList.setAdapter(restaurantsAdapter);
        if (todo == 2) RestaurantsFragment.restaurantsList.setAdapter(restaurantsMenuAdapter);
    }

    @SuppressLint("SetTextI18n")
    public void reloadCartTable() {
        CartFragment.cartTableLayout.removeAllViews();
        if (RestaurantsFragment.cartFilesList != null) {
            if (RestaurantsFragment.cartFilesList.size() < 1) CartFragment.sendOrder.setVisibility(View.INVISIBLE);
            if (RestaurantsFragment.cartFilesList.size() >= 1) CartFragment.sendOrder.setVisibility(View.VISIBLE);

            for (int i = 0; i < RestaurantsFragment.cartFilesList.size(); i++) {
                TableRow tableRow = new TableRow(mainActivity);
                TableRow.LayoutParams params;
                tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                tableRow.setGravity(Gravity.CENTER);
                tableRow.setWeightSum(1f);

                TextView name = new TextView(mainActivity);
                params = new TableRow.LayoutParams(0, 150, 0.40f);
                params.setMargins(30, 20,50, 20);
                name.setText(RestaurantsFragment.cartFilesList.get(i).getName());
                name.setTextSize(TypedValue.TYPE_STRING,10);
                name.setTextColor(Color.BLACK);
                name.setGravity(Gravity.START | Gravity.CENTER);
                name.setLayoutParams(params);

                quantity = new TextView(mainActivity);
                params = new TableRow.LayoutParams(0, 150, 0.13f);
                quantity.setText(String.format("%s шт.", RestaurantsFragment.cartFilesList.get(i).getQuantity()));
                quantity.setGravity(Gravity.CENTER);
                quantity.setLayoutParams(params);

                TextView price = new TextView(mainActivity);
                params = new TableRow.LayoutParams(0, 150, 0.27f);
                params.leftMargin = 30;
                BigDecimal newPrice = RestaurantsFragment.cartFilesList.get(i).getPrice().multiply(BigDecimal.valueOf(RestaurantsFragment.cartFilesList.get(i).getQuantity()));
                price.setText(String.format("%s руб.", newPrice));
                price.setTextSize(TypedValue.TYPE_STRING,8);
                price.setTextColor(Color.BLACK);
                price.setGravity(Gravity.START | Gravity.CENTER);
                price.setLayoutParams(params);

                Button dec = new Button(mainActivity);
                params = new TableRow.LayoutParams(0, 120, 0.10f);
                dec.setText("-");
                dec.setLayoutParams(params);
                int finalI = i;
                dec.setOnClickListener(v -> {
                    int currentCount = RestaurantsFragment.cartFilesList.get(finalI).getQuantity();
                    if (currentCount > 0) {
                        currentCount--;
                        RestaurantsFragment.cartFilesList.get(finalI).setQuantity(currentCount);
                        mainActivity.reloadCartTable();
                    }
                    if (currentCount == 0) {
                        RestaurantsFragment.cartFilesList.remove(finalI);
                        mainActivity.reloadCartTable();
                    }
                });

                Button inc = new Button(mainActivity);
                params = new TableRow.LayoutParams(0, 120, 0.10f);
                inc.setText("+");
                inc.setLayoutParams(params);
                inc.setOnClickListener(v -> {
                    int currentCount = RestaurantsFragment.cartFilesList.get(finalI).getQuantity();
                    currentCount++;
                    RestaurantsFragment.cartFilesList.get(finalI).setQuantity(currentCount);
                    mainActivity.reloadCartTable();
                });

                tableRow.removeAllViews();
                tableRow.addView(name);
                tableRow.addView(dec);
                tableRow.addView(quantity);
                tableRow.addView(inc);
                tableRow.addView(price);
                tableRow.setClickable(true);

                tableRow.setOnClickListener(v -> {
                });
                tableRow.setOnLongClickListener(v -> {
                    return true;
                });

                CartFragment.cartTableLayout.addView(tableRow);
            }
        }
    }

    public void setProfile() {
        ProfileFragment.username.setText(ProfileFragment.userProfile.get(0).getFirstName());
        ProfileFragment.email.setText(ProfileFragment.userProfile.get(0).getEmail());
        ProfileFragment.phone.setText(ProfileFragment.userProfile.get(0).getPhone());
    }
}
