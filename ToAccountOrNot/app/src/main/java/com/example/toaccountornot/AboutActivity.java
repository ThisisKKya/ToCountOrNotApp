package com.example.toaccountornot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.drakeet.about.AbsAboutActivity;
import com.drakeet.about.Card;
import com.drakeet.about.Category;
import com.drakeet.about.Contributor;
import com.drakeet.about.License;

import java.util.List;

public class AboutActivity extends AbsAboutActivity {

    @Override
    protected void onCreateHeader(@NonNull ImageView icon, @NonNull TextView slogan, @NonNull TextView version) {
        icon.setImageResource(R.mipmap.ic_launcher);
        slogan.setText("爱记不记");
        version.setText("v" + BuildConfig.VERSION_NAME);
    }

    @Override
    protected void onItemsCreated(@NonNull List<Object> items) {
        items.add(new Category("介绍与帮助"));
        items.add(new Card(getString(R.string.card_content)));

        items.add(new Category("半糖去冰不加盐"));
        items.add(new Contributor(R.mipmap.ic_launcher, "caskr", "Developer", "https://github.com/caskr"));
        items.add(new Contributor(R.mipmap.ic_launcher, "ThisisKKya", "Developer", "https://github.com/ThisisKKya"));
        items.add(new Contributor(R.mipmap.ic_launcher, "linjiatong", "Developer", "https://github.com/linjiatong"));
        items.add(new Contributor(R.drawable.pegasus, "Pegasus0712", "Developer", "https://github.com/Pegasus0712"));


        items.add(new Category("Open Source Licenses"));
        items.add(new License("MultiType", "drakeet", License.APACHE_2, "https://github.com/drakeet/MultiType"));
        items.add(new License("about-page", "drakeet", License.APACHE_2, "https://github.com/drakeet/about-page"));
    }

}
