package br.com.training.movie_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionMenuView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_bottom)
    ActionMenuView bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Menu bottomMenu = (Menu) bottomBar.getMenu();

        getMenuInflater().inflate(R.menu.menu_main, bottomMenu);

        for (int i = 0; i < bottomMenu.size(); i++){
            bottomMenu.getItem(i).setOnMenuItemClickListener(item -> onOptionsItemSelected(item));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_home:
                HomeFragment homeFragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, homeFragment).commit();
                break;
            case R.id.action_search:
                SearchFragment searchFragment= new SearchFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, searchFragment).commit();
                break;
            case R.id.action_fav:
                FavoriteFragment favoriteFragment = new FavoriteFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, favoriteFragment).commit();
                break;
            case R.id.action_more:
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(menuItem);
    }
}
